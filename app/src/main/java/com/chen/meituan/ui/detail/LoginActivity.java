package com.chen.meituan.ui.detail;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.meituan.R;
import com.chen.meituan.UserSetActivity;
import com.chen.meituan.dao.UserDao;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class LoginActivity extends Activity {
	@ViewInject(R.id.login_phone)//�˺������
	EditText et_phone;
	@ViewInject(R.id.login) //��¼��ť
	Button login;
	@ViewInject(R.id.login_back)
	ImageView img_back;
	
	//������¼��ʽ
	@ViewInject(R.id.login_qq)
	TextView tv_login_qq;
	@ViewInject(R.id.login_weixin)
	TextView tv_login_weixin;
	@ViewInject(R.id.login_weibo)
	TextView tv_login_weibo;
	@ViewInject(R.id.login_zhifubao)
	TextView tv_login_zhifubao;
	
	//������¼�ĵ���������
	@ViewInject(R.id.login_container)
	LinearLayout container;
	@ViewInject(R.id.needmiss)//���ص����ˡ�֧������¼
	LinearLayout needmiss;
	@ViewInject(R.id.tanchu)//������������
	LinearLayout tanchu;
	@ViewInject(R.id.login_click_up_click_down)//���ʹ�����ܵ����İ�ť
	ImageView login_click_up_click_down;
	private boolean isUp=false;//��ʼ״̬��û�е���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(et_phone.getText().toString().length()>=5){
					login.setEnabled(true);
					Log.e("jhd1", "login-set-true");
				}
				else{
					login.setEnabled(false);
					Log.e("jhd1", "login-set-false");
				}				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});


		//
		//��̬���ÿؼ����
		ViewTreeObserver vto = container.getViewTreeObserver();

		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
		{
			public boolean onPreDraw()
			{



				int H = needmiss.getMeasuredHeight();

				FrameLayout.LayoutParams lp1=(android.widget.FrameLayout.LayoutParams) tanchu.getLayoutParams();
				lp1.bottomMargin=-H;
				return true;
			}
		});
	}


	@OnClick({R.id.login,R.id.login_click_up_click_down,
		R.id.login_qq,R.id.login_weibo,R.id.login_weixin,R.id.login_zhifubao,
		R.id.login_back})
	public void onClick(View v){
		if(v.getId()==R.id.login){
			Log.e("jhd1", "click_login");
			UserDao ud=new UserDao(this);
			if(ud.login(et_phone.getText().toString())){
				startActivity(new Intent(this, UserSetActivity.class));
			}
			else{
				Toast.makeText(this, "�������������ԣ�", Toast.LENGTH_SHORT).show();
			}
		}
		else if(v.getId()==R.id.login_click_up_click_down){
			if(isUp){
				translationYAnimRunDown(tanchu);
				login_click_up_click_down.setImageResource(R.drawable.navibar_arrow_up);

			}else{
				translationYAnimRunUp(tanchu);
				login_click_up_click_down.setImageResource(R.drawable.navibar_arrow_down);
			}
			isUp=!isUp;
		}else if(v.getId()==R.id.login_qq||v.getId()==R.id.login_weibo
				||v.getId()==R.id.login_weixin||v.getId()==R.id.login_zhifubao){
			Toast.makeText(this, "��������¼Ŀǰ��δʵ�֣�", Toast.LENGTH_SHORT).show();
		}else if(v.getId()==R.id.login_back){
			finish();
		}
		
	}

	@SuppressLint("NewApi")
	public void translationYAnimRunUp(View view)  //����ƽ��  ��
	{  
		int h = needmiss.getHeight();    
		ObjectAnimator anim=ObjectAnimator.ofFloat(view, "translationY",0.0F , -h);               
		anim.setDuration(1000);// ����ʱ�� 
		anim.setInterpolator(new BounceInterpolator());//��ֵ�����仯���ʻ��߹���      
		anim.start();  //��ʼִ��
	}
	@SuppressLint("NewApi")
	public void translationYAnimRunDown(View view)  
	{ 
		int h = needmiss.getHeight();     
		ObjectAnimator anim=ObjectAnimator.ofFloat(view, "translationY",-h , 0.0f);
		anim.setDuration(1000);// ����ʱ�� 
		anim.setInterpolator(new BounceInterpolator());//��ֵ�����仯���ʻ��߹���       
		anim.start();  //��ʼִ��
	}


}
