package com.chen.meituan.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.meituan.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
//��ҳ
public class TuanFragment extends Fragment {
	@ViewInject(R.id.index_tuan_list6_tv)
	private TextView tv;//�����tv�� ɾ����
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.index_tuan, null);
		ViewUtils.inject(this, view);
		tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		return view;
	}

}
