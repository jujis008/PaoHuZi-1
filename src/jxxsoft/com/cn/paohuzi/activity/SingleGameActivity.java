package jxxsoft.com.cn.paohuzi.activity;

import jxxsoft.com.cn.paohuzi.app.MainApplication;
import jxxsoft.com.cn.paohuzi.bean.ScreenType;
import jxxsoft.com.cn.paohuzi.util.DialogUtil;


import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;

/**
 * ������Ϸ����
 * @author Administrator
 *
 */
public class SingleGameActivity extends BaseActivity {

	//���MainApplication����
	private MainApplication app=MainApplication.getInstance();
	//��Ϸ������ͼ
	private SingleGameView gameview;
	//�ֱ��ʴ�С
	private ScreenType screenType;
	//����Handler�����������߳���UI�߳�ͨ��
	private Handler handler=new Handler(){
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display=getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics=new DisplayMetrics();
		display.getMetrics(metrics);
		if(metrics.heightPixels<480){
			screenType=ScreenType.low;
		}else if(metrics.heightPixels>=480&&metrics.heightPixels<720){
			screenType=ScreenType.middle;
		}else if(metrics.heightPixels>=720){
			screenType=ScreenType.large;
		}
		
		//������Ϸ������ͼ
		gameview=new SingleGameView(this,handler,screenType);
		//���ؽ���
		setContentView(gameview);
		//����������Ч
		app.playbgMusic("MusicEx_Normal2.ogg");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//�������ذ�ť��ʱ�򣬵����˳��Ի���
		//System.out.println(KeyEvent.KEYCODE_BACK+","+keyCode+","+event.getKeyCode());
		if(KeyEvent.KEYCODE_BACK==keyCode){
			DialogUtil.exitGameDialog(this);
		}		
		return true;
		
	}

}
