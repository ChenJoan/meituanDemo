package com.chen.meituan.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chen.meituan.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WelcomeGuideActivity extends Activity {
	@ViewInject(R.id.welcome_guide_btn)
	// ע����ʽ
	private ImageView btn; // btn��imageview ʵ�ֵ� // ע��

	@ViewInject(R.id.welcome_guide_viewpager)
	private ViewPager pager;

	private List<View> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_guide);
		ViewUtils.inject(this);

		initViewPager();
	}

	@OnClick(R.id.welcome_guide_btn)
	// btn �ĵ���¼�
	public void click(View view) {

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	// ��ʼ��viewpager
	public void initViewPager() {
		list = new ArrayList<View>();
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.guide_01);
		list.add(iv);
		ImageView iv1 = new ImageView(this);
		iv1.setImageResource(R.drawable.guide_02);
		list.add(iv1);
		ImageView iv2 = new ImageView(this);
		iv2.setImageResource(R.drawable.guide_03);
		list.add(iv2);
		pager.setAdapter(new MyViewPagerAdapter());

		// ��������Ч��
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			// Ҳ����ѡ�еķ���
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 2)// ����ǵ�����ҳ��
				{

					btn.setVisibility(View.VISIBLE);

				} else {

					btn.setVisibility(View.GONE);

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
	class MyViewPagerAdapter extends PagerAdapter {

		// ������Ҫ����item��ʾ
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		// ��ʼ��itemʵ������
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}

		// item�����ٵķ���
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			// ����ע��������ķ���
			// super.destroyItem(container, position, object);
			container.removeView(list.get(position));
		}

	}

}
