package com.chen.meituan.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

//ʵ�ֱ�ǵ�д��Ͷ�ȡ
public class SharedUtils {
	private static final String FILE_NAME="meituan";
	private static final String MODE_NAME="welcome";
	//��ȡbooleanֵ
	public static boolean isFirstStart(Context context)
	{


		Log.e("chen", "SharedUtils-isFirstStart   :  "+context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true));
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true);

	}
	//д��
	public static void putIsFirstStart(Context context,boolean isFirst){
		Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
		editor.putBoolean(MODE_NAME, isFirst);
		editor.commit();
		Log.e("chen", "SharedUtils-put    :  "+isFirst);
	}


	//д��һ��String���͵�����
	public static void putCityName(Context context,String cityName)
	{
		Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
		editor.putString("cityName", cityName);
		editor.commit();
	}
	//��ȡString���͵�����
	public static String getCityName(Context context)
	{	
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("cityName", "ѡ�����");	
	}
}
