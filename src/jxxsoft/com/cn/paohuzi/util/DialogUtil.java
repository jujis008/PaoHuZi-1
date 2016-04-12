package jxxsoft.com.cn.paohuzi.util;

import jxxsoft.com.cn.paohuzi.R;
import jxxsoft.com.cn.paohuzi.app.MainApplication;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * �Ի��������
 * @author Administrator
 *
 */
public class DialogUtil {

	/**
	 * �˳�������Ϸ
	 * @param mycontext
	 */
	public static  void exitSystemDialog(Context mycontext){
		//�����Ի���Builer����
		AlertDialog.Builder builder=new AlertDialog.Builder(mycontext);
		//ȡ�������Ļ�����ؼ��رնԻ���
		builder.setCancelable(false);
		//��ʾ�Ի��򣬲����ضԻ������
		final AlertDialog dialog=builder.show();
		//������Ϣ����
		View view=LayoutInflater.from(mycontext).inflate(R.layout.message_box_exit_game, null);
		//���öԻ���Ľ���
		dialog.getWindow().setContentView(view);
		//���Ի���Ľ���ȡ����ť�󶨵����¼�
		view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//������Ч
				MainApplication.getInstance().play("SpecOk.ogg");
				//�رնԻ���
				dialog.dismiss();				
			}
		});
		//���Ի���Ľ���ȷ����ť�󶨵����¼�
		view.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
						
				@Override
				public void onClick(View v) {
					//������Ч
					MainApplication.getInstance().play("SpecOk.ogg");
					//�رնԻ���
					dialog.dismiss();
					//�˳���Ϸ���
					MainApplication.getInstance().exit();
				}
		});
	}
	/**
	 * �˳���Ϸ���ڶԻ���
	 * @param mycontext
	 */
	public static  void exitGameDialog(Context mycontext){
		//�����Ի���Builer����
		AlertDialog.Builder builder=new AlertDialog.Builder(mycontext);
		//ȡ�������Ļ�����ؼ��رնԻ���
		builder.setCancelable(false);
		//��ʾ�Ի��򣬲����ضԻ������
		final AlertDialog dialog=builder.show();
		//������Ϣ����
		View view=LayoutInflater.from(mycontext).inflate(R.layout.message_box_exit_game, null);
		//���öԻ���Ľ���
		dialog.getWindow().setContentView(view);
		//���Ի���Ľ���ȡ����ť�󶨵����¼�
		view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//������Ч
				MainApplication.getInstance().play("SpecOk.ogg");
				//�رնԻ���
				dialog.dismiss();					
			}
		});
		//���Ի���Ľ���ȷ����ť�󶨵����¼�
		view.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							MainApplication app=MainApplication.getInstance();
							//������Ч
							app.play("SpecOk.ogg");
							//�رնԻ���
							dialog.dismiss();
							//�˳���Ϸ
							app.getActivityList().get(app.getActivityList().size()-1).finish();
							//�л���������
							app.playbgMusic("MusicEx_Welcome.ogg");
						}
		});
	}
	
	
	
	
	/**
	 * ��Ϸ���öԻ���
	 * @param mycontext
	 * site: 1��ʾ�ڻ�ӭ���棬2��ʾ�ڵ�����Ϸ����
	 */
	public static  void setupDialog(final Context mycontext,final int site){
		//�����Ի���Builer����
		AlertDialog.Builder builder=new AlertDialog.Builder(mycontext);
		//��ʾ�Ի��򣬲����ضԻ������
		final AlertDialog dialog=builder.show();
		//������Ϣ����
		View view=LayoutInflater.from(mycontext).inflate(R.layout.setting_panel, null);
		//��öԻ���Ĵ��ڶ���
		Window window=dialog.getWindow();
		//���öԻ���Ľ���
		window.setContentView(view);
		//����������Ϣ���󶨵��ؼ�
		//���SharedPreferencesUtilʵ������
		SharedPreferencesUtil spu=SharedPreferencesUtil.getInstance(mycontext);
		//��ý������ؿؼ�
		RadioButton male=(RadioButton)view.findViewById(R.id.male_check);
		RadioButton female=(RadioButton)view.findViewById(R.id.female_check);
		CheckBox effect=(CheckBox)view.findViewById(R.id.effect_check);
		CheckBox music=(CheckBox)view.findViewById(R.id.music_check);
		SeekBar game_speed=(SeekBar)view.findViewById(R.id.game_speed_bar);
		//�ж��Ա�ѡ�� ���󶨿ؼ�
		if(spu.getPreferencesInt("sex")==1){
			male.setChecked(true);
		}else if(spu.getPreferencesInt("sex")==2){
			female.setChecked(true);
		}
		//������Чѡ��
		effect.setChecked(spu.getPreferencesBoolean("effectmusic"));
		//���ñ�������ѡ��
		music.setChecked(spu.getPreferencesBoolean("bgmusic"));
		//������Ϸ�ٶ�
		game_speed.setProgress(spu.getPreferencesInt("speed"));
		//������Ϣ��ͨ����ÿ���ؼ���Ӽ���������ѡ��ı�ʱ�򣬾ͱ���ֵ��ͬʱͬ��MainApplication����Ӧֵ
		
		//��ѡ��ı�ʱ�򣬾ͱ����Ա�
		((RadioGroup)view.findViewById(R.id.radioGroupSex)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//�Ա𱣴� 0��ʾΪѡ��1��ʾ�У�2��ʾŮ
				//������Ч
				MainApplication.getInstance().play("SpecOk.ogg");
				switch (checkedId) {
					case R.id.male_check:
						//ͬʱͬ��MainApplication����Ӧֵ
						MainApplication.getInstance().setSex(1);
						//����
						SharedPreferencesUtil.getInstance(mycontext).savePreferences("sex", 1);
						break;
					case R.id.female_check:
						//ͬʱͬ��MainApplication����Ӧֵ
						MainApplication.getInstance().setSex(2);
						//����
						SharedPreferencesUtil.getInstance(mycontext).savePreferences("sex", 2);
						break;
				}
				
			}
		});
		
		effect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				//������Ч
				MainApplication.getInstance().play("SpecOk.ogg");
				//ͬ������
				MainApplication.getInstance().setEffectmusic(isChecked);
				//����ֵ
				SharedPreferencesUtil.getInstance(mycontext).savePreferences("effectmusic", isChecked);				
			}
		});
		
		music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//��¼��Ϣ
				MainApplication app=MainApplication.getInstance();
				//������Ч
				app.play("SpecOk.ogg");
				//ͬ������
				app.setBgmusic(isChecked);
				//�ж��Ƿ�ر����֣���������
				if(isChecked==false){
					//�ر���������
					app.stopbgMusic();
				}else{
					//�������ֲ���
					if(site==1){
						app.playbgMusic("MusicEx_Welcome.ogg");
					}else if(site==2){
						app.playbgMusic("MusicEx_Normal2.ogg");
					}
				}
				//����ֵ
				SharedPreferencesUtil.getInstance(mycontext).savePreferences("bgmusic", isChecked);				
			}
		});
		//��Ϸ�ٶȼ���
		game_speed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			//���������ı��Ժ�
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//������Ч
				MainApplication.getInstance().play("SpecOk.ogg");
				//ͬ������
				MainApplication.getInstance().setSpeed(progress);
				//����ֵ
				SharedPreferencesUtil.getInstance(mycontext).savePreferences("speed", progress);
				
			}
		});
		
		 
		
	}
	
	/**
	 * ����wifi������ʾ�Ի���
	 * @param context
	 */
	public static void wifiSetDialog(final Context context){
		//�����Ի���
		final AlertDialog wifiDialog=new AlertDialog.Builder(context).create();
		//��ʾ�Ի���
		wifiDialog.show();
		//��öԻ��򴰿�
		Window window=wifiDialog.getWindow();
		//���ô��ڵ���ͼ
		window.setContentView(R.layout.message_box_set_wifi);
		//ȡ����ť�󶨵����¼�
		window.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ȡ��wifi����
				wifiDialog.dismiss();
				
			}
		});
		//���ð�ť�󶨵����¼�
		window.findViewById(R.id.btn_set_wifi).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��wifi���ý���
				context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
				wifiDialog.dismiss();
				
			}
		});
	}
}
