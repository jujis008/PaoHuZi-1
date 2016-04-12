package jxxsoft.com.cn.paohuzi.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences �Ĳ�����  ���õ�̬���ģʽ
 * @author Administrator
 *
 */
public class SharedPreferencesUtil {

	private static SharedPreferencesUtil instance;
	private static SharedPreferences preferences;
	
	private SharedPreferencesUtil(){
		
	}
	/**
	 * ����SharedPreferencesUtil��ʵ������
	 * @param context
	 * @return
	 */
	public static SharedPreferencesUtil getInstance(Context context){
		if(instance==null){
			preferences=context.getSharedPreferences("gameset", Context.MODE_PRIVATE);
			instance=new SharedPreferencesUtil();
		}
		return instance;
	}
	
	/**
	 * �洢�ַ���
	 * @param key
	 * @param value
	 */
	public void savePreferences(String key,String value){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * �洢����ֵ
	 * @param key
	 * @param value
	 */
	public void savePreferences(String key,boolean value){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	/**
	 * �洢����
	 * @param key
	 * @param value
	 */
	public void savePreferences(String key,int value){
		SharedPreferences.Editor editor=preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	/**
	 * ͨ��key�������ֵ ���û�з���-1
	 * @param key
	 * @return
	 */
	public int getPreferencesInt(String key){
		return preferences.getInt(key, -1);
	}
	
	/**
	 * ͨ��key����ַ���ֵ�����û�з���null
	 * @param key
	 * @return
	 */
	public String getPreferencesString(String key){
		return preferences.getString(key, "");
	}
	
	/**
	 * ͨ��key��ò���ֵ�����û�з���false
	 * @param key
	 * @return
	 */
	public boolean getPreferencesBoolean(String key){
		return preferences.getBoolean(key, false);
	}
	
	
	
}
