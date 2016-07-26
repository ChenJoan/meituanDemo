package com.chen.meituan.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.chen.meituan.R;
import com.chen.meituan.utils.SharedUtils;

//仿大众点评欢迎页面  //图片素材来自版本7.9.1
public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message arg0) {
				// TODO Auto-generated method stub\
				// 是第一次启动
				if (SharedUtils.isFirstStart(getBaseContext())) {
					startActivity(new Intent(getApplicationContext(),
							WelcomeGuideActivity.class));
					// 修改成不是第一次启动
					SharedUtils.putIsFirstStart(getBaseContext(), false);
				} else {
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
				}
				finish();
				return false;
			}

		}).sendEmptyMessageDelayed(0, 1500);
	}
}
