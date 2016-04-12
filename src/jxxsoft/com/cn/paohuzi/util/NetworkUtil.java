package jxxsoft.com.cn.paohuzi.util;

import jxxsoft.com.cn.paohuzi.app.MainApplication;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	private static final String TAG = NetworkUtil.class.getSimpleName();
	/**
	 * ���Ӧ�ó���������
	 */
	private final static Application mApp = MainApplication.getInstance();

	/**
	 * �ж��Ƿ�����������
	 * @return
	 */
	public static boolean isNetworkConnected() {
		//������ӹ�����
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mApp
				.getSystemService(Application.CONNECTIVITY_SERVICE);
		//��ü�����������
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			//����������ͨ
			return mNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * �ж�WIFI�����Ƿ����
	 * @return
	 */
	public static boolean isWifiConnected() {
		//������ӹ�����
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mApp
				.getSystemService(Application.CONNECTIVITY_SERVICE);
		//���wifi�������
		NetworkInfo mWiFiNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWiFiNetworkInfo != null) {
			//����wifi�Ƿ����
			return mWiFiNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * �ж�MOBILE�����Ƿ����
	 * 
	 * @return
	 */
	public static boolean isMobileConnected() {
		//������ӹ�����
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mApp
				.getSystemService(Application.CONNECTIVITY_SERVICE);
		//����ֻ����Ӷ���
		NetworkInfo mMobileNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mMobileNetworkInfo != null) {
			//�����ֻ������Ƿ����
			return mMobileNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * ��ȡ��ǰ�������ӵ�������Ϣ one of TYPE_MOBILE, TYPE_WIFI, TYPE_WIMAX, TYPE_ETHERNET,
	 * TYPE_BLUETOOTH, or other types defined by ConnectivityManager
	 * 
	 * @return
	 */
	public static int getConnectedType() {
		//������ӹ�����
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mApp
				.getSystemService(Application.CONNECTIVITY_SERVICE);
		//��ÿ��õ��������
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
			//�����������ӵ�����
			return mNetworkInfo.getType();
		}
		return -1;
	}
}