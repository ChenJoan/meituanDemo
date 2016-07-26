package com.chen.meituan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chen.meituan.R;
import com.chen.meituan.ui.detail.LoginActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
//Ê×Ò³
public class MyFragment extends Fragment {
	@ViewInject(R.id.index_my_list1_touxiang)
	ImageView touxiang;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.index_my, null);
		ViewUtils.inject(this, view);   //×¢Èë¿Ø¼þ
		return view;
	}
	@OnClick({R.id.index_my_list1_touxiang})
	public void onClick(View v){
		if(v.getId()==R.id.index_my_list1_touxiang){
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}
	}

}
