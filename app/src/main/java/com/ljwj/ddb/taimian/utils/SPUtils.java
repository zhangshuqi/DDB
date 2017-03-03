package com.ljwj.ddb.taimian.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences封装
 * @author Administrator
 * @date 2016-7-18
 * @pagename com.itheima.mobilesafe28.utils
 * @desc TODO
 * @svn author $Author$
 * @svn date $Date$
 * @Id  $Id$
 * @version $Rev$
 * @url $URL$ 
 *
 */
public class SPUtils {
	private static final String FILENAME = "DDBTAIMAIN";
	private static final String FILECOOKIE = "COOKIES";
	private static SharedPreferences sharedPreferences;

	public static void putInt(Context context,String key,int value){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putInt(key, value);
		edit.commit();
	}
	public static int getInt(Context context,String key,int defValue){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defValue);
	}
	//登录界面记住密码的falg标志
	public static void putIsCheck(Context context,String key,boolean value){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key,value);
		edit.commit();
	}

	//获取保存的标志
	public static boolean getIsCheck(Context context,String key,boolean defValue){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defValue);
	}
	//账号密码保存
	public static void putNumberString(Context context,String key,String value){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString(key,value);
		edit.commit();
	}
	//获取账号密码
	public static String getNumberString(Context context,String key,String defValue){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defValue);
	}
	//密令保存
	public static void putToken(Context context,String key,String value){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString(key,value);
		edit.commit();
	}
	//密令获取
	public static String getToken(Context context,String key,String value){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key,value);
	}

	//记住登录状态
	public static void putLogin(Context context, String key, boolean value) {
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key,value);
		edit.commit();
	}
	//获取登录状态
	public static boolean getLogin(Context context,String key,boolean defValue){
		sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defValue);
	}

	//保存获取到的cookie信息
	public static void putCookie(Context context,String key,String value){
		sharedPreferences = context.getSharedPreferences(FILECOOKIE, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString(key,value);
		edit.apply();
	}

	//获取已保存到的cookie信息
	public static String getCookie(Context context,String key,String value){
		sharedPreferences = context.getSharedPreferences(FILECOOKIE, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key,value);
	}
}
