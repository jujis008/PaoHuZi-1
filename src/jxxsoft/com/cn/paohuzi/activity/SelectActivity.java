package jxxsoft.com.cn.paohuzi.activity;

import jxxsoft.com.cn.paohuzi.R;
import jxxsoft.com.cn.paohuzi.app.MainApplication;
import jxxsoft.com.cn.paohuzi.util.DialogUtil;
import jxxsoft.com.cn.paohuzi.util.NetworkUtil;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * ��Ϸѡ�����
 * @author Administrator
 *
 */
public class SelectActivity extends BaseActivity implements OnClickListener{

	//���MainApplication����
	private MainApplication app=MainApplication.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ز���
		setContentView(R.layout.activity_select);
		//����Ӧ��ť�����¼�
		findViewById(R.id.choose_game_exit).setOnClickListener(this);
		findViewById(R.id.choose_game_btn_multi_play).setOnClickListener(this);
		findViewById(R.id.choose_game_btn_single_play).setOnClickListener(this);
	}

	/**
	 * ��ť�ĵ���������
	 */
	@Override
	public void onClick(View v) {
		//����������Ч
		app.play("SpecOk.ogg");
		switch (v.getId()) {
			case R.id.choose_game_exit:
				//�رյ�ǰ���棬���ؿ�ʼ����
				this.finish();
				break;
			case R.id.choose_game_btn_multi_play:
				//���˾�������ս �ж�wifi��ͨ
				if(NetworkUtil.isWifiConnected()){
					//��ͨ�Ļ������������Ϸ����
					startActivity(new Intent(this, Multi_Game_Join_Activity.class));
				}else{
					//û����ͨ�Ļ�������wifi�Ի���
					DialogUtil.wifiSetDialog(this);
				}
				break;
			case R.id.choose_game_btn_single_play:
				//���뵥����Ϸ
				startActivity(new Intent(this, SingleGameActivity.class));
				break;
		}
	}
	
	
	
	
	
}
