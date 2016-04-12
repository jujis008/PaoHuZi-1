package jxxsoft.com.cn.paohuzi.activity;

import jxxsoft.com.cn.paohuzi.R;
import jxxsoft.com.cn.paohuzi.R.layout;
import jxxsoft.com.cn.paohuzi.app.MainApplication;
import jxxsoft.com.cn.paohuzi.util.DialogUtil;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * ��Ϸ��ʼ����
 * @author Administrator
 *
 */
public class StartActivity extends BaseActivity implements OnClickListener {

	//���MainApplicationʵ��
	private MainApplication app=MainApplication.getInstance();
	private TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ز���
		setContentView(R.layout.activity_start);
		//�󶨰�ť�����¼�
		findViewById(R.id.start_screen_start).setOnClickListener(this);
		findViewById(R.id.start_screen_feedback).setOnClickListener(this);
		findViewById(R.id.start_screen_exit).setOnClickListener(this);
		//���ű�������
		app.playbgMusic("MusicEx_Welcome.ogg");
	}
	

	/**
	 * ���ز˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	/**
	 * �˵�ѡ�ѡ�񴥷�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//����������Ч
		app.play("SpecOk.ogg");
		
		switch (item.getItemId()) {
			case R.id.action_settings:
				//ѡ�����ò˵����������öԻ���
				DialogUtil.setupDialog(this,1);
				break;
	
			case R.id.action_exits:
				//ѡ���˳��˵��������˳��Ի���
				DialogUtil.exitSystemDialog(this);
				break;
		}
		return true;
	}

	

	//���ذ�ť,�˳�ϵͳ�������˳��Ի���
	@Override
	public void onBackPressed() {
		//����������Ч
		app.play("SpecOk.ogg");
		//ϵͳ�˳��Ի���
		DialogUtil.exitSystemDialog(this);
		
	}


	/**
	 * ��ť�ĵ���������
	 */
	@Override
	public void onClick(View v) {
		//����������Ч
		app.play("SpecOk.ogg");
		switch (v.getId()) {
			case R.id.start_screen_start:
				//������Ϸ��ʼ��ť��������Ϸѡ�����
				startActivity(new Intent(this,SelectActivity.class ));
				break;
			case R.id.start_screen_feedback:
					//������Ϸ��������������Ի����ύ������Ϣ	
				break;
			case R.id.start_screen_exit:
				//�����˳���Ϸ������ϵͳ�˳��Ի���
				DialogUtil.exitSystemDialog(this);
				break;
		}
		
	}

}
