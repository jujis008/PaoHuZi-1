package jxxsoft.com.cn.paohuzi.activity;

import jxxsoft.com.cn.paohuzi.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

/**
 * ������Ϸ�������
 * @author Administrator
 *
 */
public class Multi_Game_Join_Activity extends BaseActivity implements OnClickListener {

	private AlertDialog helpDialog=null;//������ʾ�Ի���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_game_join);
		findViewById(R.id.multi_game_exit).setOnClickListener(this);
		findViewById(R.id.multi_game_help).setOnClickListener(this);
		findViewById(R.id.multi_game_btn_user_info).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.multi_game_exit:
				//�˳�
				this.finish();
				break;
			case R.id.multi_game_help:
				//������ʾ
				helpDialogShow();
				break;
			case R.id.multi_game_btn_user_info:
				//�޸��û�ͷ������
				startActivityForResult(new Intent(this, Person_info_Activity.class),0x01);
				break;
		}
		
	}
	
	
	//�޸�ͷ����������
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void helpDialogShow(){
		//�����Ի���
		helpDialog=new AlertDialog.Builder(this).create();
		//��ʾ�Ի���
		helpDialog.show();
		//��öԻ��򴰿�
		Window window=helpDialog.getWindow();
		//���ô��ڵ���ͼ
		window.setContentView(R.layout.multigame_help);
	}

}
