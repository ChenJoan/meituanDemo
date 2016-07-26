package com.chen.meituan.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.chen.meituan.R;
import com.chen.meituan.ui.fragment.HomeFragment;
import com.chen.meituan.ui.fragment.MyFragment;
import com.chen.meituan.ui.fragment.SearchFragment;
import com.chen.meituan.ui.fragment.TuanFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	@ViewInject(R.id.main_bottom_tabs)
	private RadioGroup group;
	@ViewInject(R.id.main_home)
	private RadioButton main_home;
	private FragmentManager fragmentManager;// ����fragment
	private HomeFragment home;
	private MyFragment my;
	private SearchFragment search;
	private TuanFragment tuan;
	private long exitTime = 0;// ���ΰ������˳�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		// ��ʼ��fragmentManager
		fragmentManager = getSupportFragmentManager();
		// ����Ĭ��ѡ��
		main_home.setChecked(true);
		group.setOnCheckedChangeListener(this);
		// �л���ͬ��fragment
		changeFragment(0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		switch (checkedId) {
		case R.id.main_home:
			changeFragment(0);
			break;
		case R.id.main_tuan:
			changeFragment(1);
			break;
		case R.id.main_search:
			changeFragment(2);
			break;
		case R.id.main_my:
			changeFragment(3);
			break;
		default:
			break;
		}
	}

	// �л���ͬ��fragment
	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾhome��1��ʾtuan��2��ʾsearch��3��ʾmy��
	 */
	public void changeFragment(int index)// ͬʱ����ÿ��fragment
	{
		FragmentTransaction beginTransaction = fragmentManager
				.beginTransaction();
		hideFragments(beginTransaction);
		switch (index) {
		case 0:
			if (home == null) {
				home = new HomeFragment();
				beginTransaction.add(R.id.main_content, home);
			} else {
				beginTransaction.show(home);
			}
			break;
		case 1:
			if (tuan == null) {
				tuan = new TuanFragment();
				beginTransaction.add(R.id.main_content, tuan);
			} else {
				beginTransaction.show(tuan);
			}
			break;
		case 2:
			if (search == null) {
				search = new SearchFragment();
				beginTransaction.add(R.id.main_content, search);
			} else {
				beginTransaction.show(search);
			}
			break;
		case 3:
			if (my == null) {
				my = new MyFragment();
				beginTransaction.add(R.id.main_content, my);
			} else {
				beginTransaction.show(my);
			}
			break;

		default:
			break;
		}
		beginTransaction.commit();// ��Ҫ�ύ����
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (home != null)
			transaction.hide(home);
		if (my != null)
			transaction.hide(my);
		if (tuan != null)
			transaction.hide(tuan);
		if (search != null)
			transaction.hide(search);
	}

	@Override
	public void onBackPressed() {

		exit(); // /�˳�Ӧ��

	}

	public void exit() { // �˳�Ӧ��
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			// System.exit(0);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		// this.overridePendingTransition(0,R.anim.activity_close);
	}

}
