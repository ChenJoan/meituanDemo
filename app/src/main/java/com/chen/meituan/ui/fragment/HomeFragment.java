package com.chen.meituan.ui.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.meituan.AllCategoryActivity;
import com.chen.meituan.R;
import com.chen.meituan.myview.WrapContentHeightViewPager;
import com.chen.meituan.ui.CityListActivity;
import com.chen.meituan.ui.detail.ActivityHomeList1;
import com.chen.meituan.utils.MyConstant;
import com.chen.meituan.utils.SharedUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

//��ҳ
public class HomeFragment extends Fragment implements LocationListener {
	@ViewInject(R.id.home_top_city)
	private TextView topCity;

	@ViewInject(R.id.index_home_viewpager)
	private WrapContentHeightViewPager viewPager;

	@ViewInject(R.id.index_home_rb1)
	// radiogroup 1���Լ�3��radiobutton
	private RadioButton rb1;
	@ViewInject(R.id.index_home_rb2)
	private RadioButton rb2;
	@ViewInject(R.id.index_home_rb3)
	private RadioButton rb3;

	@ViewInject(R.id.index_home_list3_second)
	// ������������
	private TextView tv_second;
	private boolean isCounting = true;// �����̼߳�ʱ�������Ƿ�ر����߳�

	private GridView gridView1;
	private GridView gridView2;
	private GridView gridView3;

	// tips
	private boolean displayTips = false;
	@ViewInject(R.id.index_home_tip)
	private ImageView tips;
	@ViewInject(R.id.index_home_tips_arrow)
	private ImageView tips_arrow;
	@ViewInject(R.id.index_home_tips_content)
	private LinearLayout tips_content;

	private String cityName;
	private LocationManager locationManager;

	// ���ܴ�����Ϣ
	private Handler handler = new Handler(new Handler.Callback() {// ��ʱ��������������

				@Override
				public boolean handleMessage(Message arg0) {
					if (arg0.what == 2) {
						// ����ʱ���ʱ
						int i = Integer
								.parseInt(tv_second.getText().toString()) + 1;
						if (i >= 60)
							i = 0;
						if (i < 10)
							tv_second.setText("0" + i);
						else
							tv_second.setText("" + i);
						Log.e("jhd", "handler--what==2 ");
					} else if (arg0.what == 1) {
						initViewPager();// ��ʼ�� viewpager ����л�����ʾ������
					}
					return false;
				}
			});

	// �߳����
	Thread thread = new Thread() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (isCounting) {
				handler.sendEmptyMessage(2);
				Log.e("jhd", "thread: " + Thread.currentThread().getName());
				try {
					sleep(1000);// һ��һִ��
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.e("jhd", "thread: over");
		}

	};

	@Override
	public void onStart() {
		super.onStart();
		// initViewPager();
		Log.e("jhd", "onStart");

		// TODO Auto-generated method stub ��ʱ������
		// checkGPSIsOpen();
	}

	private void checkGPSIsOpen() {
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		boolean isOpen = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isOpen) {
			Log.e("jhd",
					"HomeFragment--: checkGPSIsOpen():GPS is close please open it");
			// ����gps������ҳ��
			Intent intent = new Intent();
			intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			// �����µ�����
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 0);
		}

		Log.e("jhd", "HomeFragment--1: checkGPSIsOpen():GPS is open ");
		// ��ʼ��λ
		startLocation();
	}

	// ʹ��gps��λ�ķ���
	private void startLocation() {
		Log.e("jhd", "HomeFragment--2: startLocation():----- ");
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 10, this);

	}

	// ��ʼ�� �ؼ������ز���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.index_home, null);
		ViewUtils.inject(this, view); // ע��ؼ�
		// ��ȡ���ݲ���ʾ
		topCity.setText(SharedUtils.getCityName(getActivity()));

		initGridView();

		Log.e("jhd", "onCreateView");

		handler.sendEmptyMessage(1);// ���߳� ��ʼ��viewpager ����л�ҳ�浼��viewpager�е�����Ϊ��

		// ������������
		thread.setName("MyThread");
		thread.start();
		return view;

	}

	@OnClick({ R.id.home_top_city, R.id.index_home_tip })
	public void onClick(View view) {
		if (view.getId() == R.id.home_top_city) {
			// startActivityForResult(new Intent(getActivity(),
			// CityListActivity.class), MyConstant.RequestCityCode);

			Intent intent = new Intent();
			Context cxt = getActivity();
			intent.setClass(cxt, CityListActivity.class);
			// intent.setClassName(cxt,"com.test.mrn.android.route.RouteDemoActivity");//��һ��activity
			startActivityForResult(intent, MyConstant.RequestCityCode);
			((Activity) cxt).overridePendingTransition(
					R.anim.activity_slide_in_left,
					R.anim.activity_slide_out_right);

		} else if (view.getId() == R.id.index_home_tip) {
			Toast.makeText(getActivity(), "+", Toast.LENGTH_SHORT).show();
			if (displayTips) {
				tips_arrow.setVisibility(View.GONE);
				tips_content.setVisibility(View.GONE);
			} else {
				tips_arrow.setVisibility(View.VISIBLE);
				tips_content.setVisibility(View.VISIBLE);
			}
			displayTips = !displayTips;

		}
	}

	// ������ֵ���
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MyConstant.RequestCityCode
				&& resultCode == Activity.RESULT_OK) {
			cityName = data.getStringExtra("cityName");
			topCity.setText(cityName);
			Log.e("jhd1", "onActivityResult:" + cityName);
		}
	}

	// λ����Ϣ����ִ�еķ���
	@Override
	public void onLocationChanged(Location location) {
		// ���µ�ǰ����ֻ��Ϣ
		Log.e("jhd",
				"HomeFragment--3: onLocationChanged(Location location):----- ");
		updateWithNewLocation(location);

	}

	// ��ȡ��Ӧλ�õľ�γ�ȣ�����λ����
	private void updateWithNewLocation(Location location) {
		double lat = 0.0, lng = 0.0;
		if (location != null) {

			lat = location.getLatitude();
			lng = location.getLongitude();
			Log.e("jhd",
					"HomeFragment--4-1: updateWithNewLocation(Location location):lat:"
							+ lat + "lng:" + lng);
		} else {
			Log.e("jhd",
					"HomeFragment--4-2: updateWithNewLocation(Location location):�޷���ȡ������Ϣ----- ");
			cityName = "�޷���ȡ������Ϣ";
		}
		// ͨ����γ�Ȼ�õ�ַ�����ڵ�ַ���ж��������;�γ�Ⱦ�ȷ���йأ���ʵ���ж�������󷵻���2�����ڼ�������2��ֵ
		List<Address> list = null;
		Geocoder geocoder = new Geocoder(getActivity());
		try {
			list = geocoder.getFromLocation(lat, lng, 2);
			if (list != null) {
				Log.e("jhd",
						"HomeFragment--4-3: updateWithNewLocation(Location location):--list ��Ϊ��"
								+ cityName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (list != null) {
			Log.e("jhd",
					"HomeFragment--4-3: updateWithNewLocation(Location location):--list.size:"
							+ list.size());
			for (Address a : list) {

				cityName = a.getLocality();
				Log.e("jhd",
						"HomeFragment--4-3: updateWithNewLocation(Location location):--"
								+ cityName);
			}
		}
		// ���Ϳ���Ϣ

		// ��ַ������ �õ������鳤��Ϊ0
		cityName = "��ַ���鳤��Ϊ0";
		handler.sendEmptyMessage(1);

	}

	@Override
	public void onProviderDisabled(String arg0) {

		Log.e("jhd", "HomeFragment--: Disabled:----- ");
	}

	@Override
	public void onProviderEnabled(String arg0) {

		Log.e("jhd", "HomeFragment--: Enabled:----- ");
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		Log.e("jhd", "onStatusChanged HomeFragment--: onStatusChanged:----- ");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("jhd", "onResume");
	}

	@Override
	public void onDestroy() {
		Log.e("jhd", "onDestroy");
		isCounting = false;// �رյ���ʱ����߳�

		super.onDestroy();

		// �������
		SharedUtils.putCityName(getActivity(), cityName);

		// ֹͣ��λ
		// TODO Auto-generated method stub ��ʱ������
		// stopLocation();
	}

	// ֹͣ��λ
	private void stopLocation() {
		locationManager.removeUpdates(this);
	}

	// gridview ��������
	public class GridViewAdapter extends BaseAdapter {

		// �ҵ�������utils���µ�MyConstant�ж������
		private LayoutInflater inflater;
		private int page;

		public GridViewAdapter(Context context, int page) {
			super();
			this.inflater = LayoutInflater.from(context);
			this.page = page;
		}

		@Override
		public int getCount() {
			if (page != -1) {
				return 8;
			} else {
				return MyConstant.navSort.length;
			}
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {

			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = inflater.inflate(R.layout.index_home_grid_item,
						null);
				ViewUtils.inject(vh, convertView);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			vh.iv_navsort.setImageResource(MyConstant.navSortImages[position
					+ 8 * page]);
			vh.tv_navsort.setText(MyConstant.navSort[position + 8 * page]);
			if (position == 8 - 1 && page == 2) {
				vh.iv_navsort.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(getActivity(),
								AllCategoryActivity.class));
					}
				});
			} else {
				vh.iv_navsort.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(getActivity(),
								ActivityHomeList1.class));
					}
				});
			}

			return convertView;
		}
	}

	// gridview ��������holder��
	private class ViewHolder {
		@ViewInject(R.id.index_home_iv_navsort)
		ImageView iv_navsort;
		@ViewInject(R.id.index_home_tv_navsort)
		TextView tv_navsort;
	}

	private void initGridView() {
		gridView1 = (GridView) LayoutInflater.from(getActivity()).inflate(
				R.layout.index_home_gridview, null);
		gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
		gridView2 = (GridView) LayoutInflater.from(getActivity()).inflate(
				R.layout.index_home_gridview, null);
		gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
		gridView3 = (GridView) LayoutInflater.from(getActivity()).inflate(
				R.layout.index_home_gridview, null);
		gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
	}

	private void initViewPager() { // ��ʼ��viewpager
		List<View> list = new ArrayList<View>(); // ����ʵ�ֶ�̬�������gridview
		// GridView gridView1=(GridView)
		// LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview,
		// null);
		// gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
		// GridView gridView2=(GridView)
		// LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview,
		// null);
		// gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
		// GridView gridView3=(GridView)
		// LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview,
		// null);
		// gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
		list.add(gridView1);
		list.add(gridView2);
		list.add(gridView3);
		viewPager.setAdapter(new MyViewPagerAdapter(list));
		// viewPager .setOffscreenPageLimit(2); //meiyong
		rb1.setChecked(true);// ����Ĭ�� ����ĵ�ѡ�е��ǵ�һ��
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) { // ʵ�ֻ����Ǹ�ҳ�棬�Ǹ�ҳ������ĵ�ᱻѡ��
				// TODO Auto-generated method stub
				if (position == 0) {
					rb1.setChecked(true);
				} else if (position == 1) {
					rb2.setChecked(true);
				} else if (position == 2) {
					rb3.setChecked(true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	// �Զ���viewpager��������
	private class MyViewPagerAdapter extends PagerAdapter {

		List<View> list;

		// List<String> titles;
		public MyViewPagerAdapter(List<View> list) {
			// TODO Auto-generated constructor stub

			this.list = list;
			// this.titles=titles;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		// �ж� ��ǰ��view �Ƿ��� Object ����
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));
			Log.e("jhd", "���--" + position);

			return list.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView(list.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			// return titles.get(position);
			return "1"; // ��ʱû�õ�
		}
	}

	// @Override
	// public void onPause() {
	// // TODO Auto-generated method stub
	// super.onPause();
	// Log.e("jhd", "onPause");
	// }
	// @Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("jhd", "onStop");
	}
	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onActivityCreated(savedInstanceState);
	// Log.e("jhd", "onActivityCreated");
	// }
	// @Override
	// public void onAttach(Activity activity) {
	// // TODO Auto-generated method stub
	// super.onAttach(activity);
	// Log.e("jhd", "onAttach");
	//
	// }
	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// Log.e("jhd", "onCreate");
	// }
	// @Override
	// public void onDestroyView() {
	// // TODO Auto-generated method stub
	// super.onDestroyView();
	// Log.e("jhd", "onDestroyView");
	// }
	// @Override
	// public void onDetach() {
	// // TODO Auto-generated method stub
	// super.onDetach();
	// Log.e("jhd", "onDetach");
	// }
	//

}
