package com.chen.meituan.dao;


import com.chen.meituan.db.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserDao {

	DbHelper helper;
	public UserDao(Context context) {
		// TODO Auto-generated constructor stub
		helper=new DbHelper(context);
	}

	//�û�ע��

	//private String   signup_sql="insert into user (name,password) values(?,?)";

//	public  void  Signup(User u){
//		SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL(signup_sql, new Object[]{u.getName(),u.getPassword()});
//		db.close();//�ر����ݿ�
//	}

	//	private String update_sql="update student set name=?,age=?,gender=?,address=? where num=?";
	//	public void update(Student s)
	//	{
	//		SQLiteDatabase db = helper.getWritableDatabase();
	//		db.execSQL(update_sql, new Object[]{s.getName(),s.getAge(),s.getGender(),s.getAddress(),s.getNum()});
	//		db.close();
	//	}

	//�û���¼
	//���ֻ��ŵ�¼
	String login_sql="select * from user where phone=?";
	public boolean login(String phone)
	{
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(login_sql, new String []{phone});
		boolean isValid=(cursor.getCount()==1);
		db.close();
		return isValid	;
	}
}
