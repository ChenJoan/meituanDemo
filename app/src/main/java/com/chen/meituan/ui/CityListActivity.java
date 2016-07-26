package com.chen.meituan.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.meituan.R;
import com.chen.meituan.myview.SideBar;
import com.chen.meituan.myview.SideBar.OnTouchingLetterChangedListener;
import com.chen.meituan.pro.City;
import com.chen.meituan.utils.SaxXMLParser;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class CityListActivity extends Activity {
	@ViewInject(R.id.city_list_lv)
	private ListView listView;

	@ViewInject(R.id.sidebar)
	private SideBar sidebar;

	@ViewInject(R.id.dialog)
	private TextView dialog;

	@ViewInject(R.id.city_list_rb1)
	private RadioButton rb1;
	@ViewInject(R.id.city_list_rb2)
	private RadioButton rb2;

	@ViewInject(R.id.activity_city_list_tv_back)
	private TextView tv_back;

	private List<City> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_list);
		ViewUtils.inject(this);

		// ����listview
		View view = LayoutInflater.from(this).inflate(
				R.layout.header_city_list, null);

		rb1.setChecked(true);// ����Ĭ��ѡ�е� �� ��ȫ���� ��ť

		listView.addHeaderView(view); // ���listheader

		initCityList();// �����ݼ��ص�list��
		CityListAdapter adapter = new CityListAdapter(list, this);// �½�������
		listView.setAdapter(adapter);// ��������

		sidebar.setTextView(dialog);

		// sidebar���ü���
		sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String sortKey) {
				// TODO Auto-generated method stub
				listView.setSelection(findIndexBySortKey(list, sortKey) + 1); // ��Ϊ��header
																				// ����+1
			}
		});
	}

	@OnItemClick({ R.id.city_list_lv })
	// item�ĵ���¼�
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent();
		Toast.makeText(this, "position:" + position + " id:" + id, 3000).show();
		if (position != 0) { // position==0 ֤���ǵ���� listview��header
								// ��������ʱ����������header�����
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_tv_cityName);
			it.putExtra("cityName", textView.getText().toString());
			Log.e("jhd1", "onItemClick:" + textView.getText().toString());
			setResult(RESULT_OK, it);
			finish();
		}
	}

	@OnClick({ R.id.activity_city_list_tv_back })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_city_list_tv_back:// ���˷��ؼ� ���ô���ֱ��finish
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city_list, menu);
		return true;
	}

	private StringBuffer buffer = new StringBuffer();// y������һ�α�������ĸ����
	private List<String> firstList = new ArrayList<String>();// ��������ֵ����ĳ�����

	// �ڲ��� ������
	class CityListAdapter extends BaseAdapter {
		private List<City> listdata;
		private LayoutInflater inflater;

		public CityListAdapter(List<City> list, Context context) {

			this.listdata = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listdata.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listdata.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View converView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if (converView == null) {
				vh = new ViewHolder();
				converView = inflater.inflate(R.layout.activity_city_list_item,
						null);
				ViewUtils.inject(vh, converView);
				// vh.sortKey=(TextView)
				// converView.findViewById(R.id.city_list_item_tv_sortKey);
				// vh.cityName=(TextView)
				// converView.findViewById(R.id.city_list_item_tv_cityName);
				converView.setTag(vh);
			} else {
				vh = (ViewHolder) converView.getTag();
			}
			// ������ʾ����
			City c = listdata.get(arg0);
			String sort = c.getSortKey();
			String name = c.getName();

			if (buffer.indexOf(sort) == -1) { // ��������if ������ʾ����ĸ����
				buffer.append(sort);
				firstList.add(name);
			}
			if (firstList.contains(name)) {
				vh.sortKey.setText(sort);
				vh.sortKey.setVisibility(View.VISIBLE);// ��ʾ
			} else {
				vh.sortKey.setVisibility(View.GONE);// ����ʾ
			}
			vh.cityName.setText(name);

			return converView;
		}

		class ViewHolder {
			@ViewInject(R.id.city_list_item_tv_sortKey)
			TextView sortKey;
			@ViewInject(R.id.city_list_item_tv_cityName)
			TextView cityName;
		}

	}

	private void initCityList() // ����Ŀ�� city
								// �б����assets/citys.xml�ļ��У�ͨ��xml�����õ���Ҫ��city����
	{
		SaxXMLParser parser = new SaxXMLParser();
		try {
			InputStream inputStream = getAssets().open("citys.xml");
			list = parser.getListBySaxXMLParser(inputStream);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ����sortkey�ҵ�����λ��
	private int findIndexBySortKey(List<City> list, String sortKey) {

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				City c = list.get(i);
				if (sortKey.equals(c.getSortKey())) {
					return i;
				}
			}
		} else {
			Log.e("jhd", "û�и��ݴ�����sortKey�ҵ���Ӧ������ֵ");
		}
		return -2; // /������header ���Բ��ܷ���-1������-2

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

		super.finish();
		// ����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);

	}

}
