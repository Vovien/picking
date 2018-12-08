package com.holderzone.android.holderpick.screen.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Administrator
 * @time   2015年8月8日下午5:02:28
 * @DESC   sharedPreferences工具类
 */
public class PreferenceUtils {
	private static SharedPreferences sp;
	
	private static SharedPreferences getSharedPreferences(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("phone_safe_config", Context.MODE_PRIVATE);
		}
		return sp;
	}
	
	/**
	 * 使用SharedPreferences存储数据
	 * @param context 上下文
	 * @param key 键
	 * @param value 值
	 */
	public static void storeBooleanData(Context context, String key, boolean value) {
		SharedPreferences preferences = getSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
		editor.clear();
		preferences = null;
	}
	
	/**
	 * 使用SharedPreferences获取数据
	 * @param context 上下文
	 * @param key  键
	 * @param defValue 默认值
	 * @return 没有数据，默认返回defValue
	 */
	public static boolean getBooleanData(Context context, String key, boolean defValue) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getBoolean(key, defValue);
	}
	
	/**
	 * 使用SharedPreferences获取数据
	 * @param context 上下文
	 * @param key 键
	 * @return 没有有数据，默认值返回false
	 */
	public static boolean getData(Context context, String key) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getBoolean(key, false);
	}
	
	/**
	 * 存储string数据
	 * @param context 上下文对象
	 * @param key 键
	 * @param value 值
	 */
	public static void storeStringData(Context context, String key, String value) {
		SharedPreferences preferences = getSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		editor.clear();
		preferences = null;
	}
	
	/**
	 * 获取String数据
	 * @param key 键
	 * @param defValue 默认值
	 * @return 
	 */
	public static String getStringData(Context context, String key, String defValue) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getString(key, defValue);
	}
	
	/**
	 * 获取String数据
	 * @param key 键
	 * @return  没有有数据，默认返回null
	 */
	public static String getStringData(Context context, String key) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getString(key, null);
	}
	
	/**
	 * 存储Int数据
	 * @param context 上下文对象
	 * @param key 键
	 * @param value 值
	 */
	public static void storeIntData(Context context, String key, int value) {
		SharedPreferences preferences = getSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
		editor.clear();
		preferences = null;
	}
	
	/**
	 * 获取String数据
	 * @param key 键
	 * @return  没有有数据，默认返回-1
	 */
	public static int getIntData(Context context, String key) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getInt(key, -1);
	}
	
	/**
	 * 获取Int数据
	 * @param key 键
	 * @param defValue 默认值
	 * @return 
	 */
	public static int getIntData(Context context, String key, int defValue) {
		SharedPreferences preferences = getSharedPreferences(context);
		return preferences.getInt(key, defValue);
	}
	
}
