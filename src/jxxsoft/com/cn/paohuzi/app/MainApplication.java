package jxxsoft.com.cn.paohuzi.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxxsoft.com.cn.paohuzi.util.SharedPreferencesUtil;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

public class MainApplication extends Application {

	private final static String Tag = "MainApplication";
	private static MainApplication instance;
	
	//����Activity�ļ���
	private List<Activity> mActivityList = new ArrayList<Activity>();
	//��Ϸ������Ϣ���������ط�ʹ��
	private int sex;
	private boolean bgmusic;
	private boolean effectmusic;
	private int speed;
	
	//��Ч
	private SoundPool soundpool;
	//������Ч��¼
	private Map<String, Integer> soundmap=new HashMap<String, Integer>();
	//���ŵ���Ч��¼
	private Map<String, Integer> soundplaymap=new HashMap<String, Integer>();
	
	//��õ�ǰApllication��ʵ��
	public synchronized static MainApplication getInstance() {
		return instance;
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		//��MainApplication ��ֵ
		instance=this;
		//������������
		initData();
		//������Ч��
		soundpool=new SoundPool(200, AudioManager.STREAM_MUSIC, 0);
		//�첽������Ƶ
		new loadSound().execute(this);
		
	}
	/**
	 * ��ʼ����������
	 */
	public void initData(){
		SharedPreferencesUtil spu=SharedPreferencesUtil.getInstance(this);
		boolean b=spu.getPreferencesBoolean("notfirst");
		if(b==false){
			//��һ��ʹ������� ����Ĭ��ֵ
			spu.savePreferences("notfirst", true);
			spu.savePreferences("sex", 1);//�Ա�
			spu.savePreferences("bgmusic", true);//��Ϸ��������
			spu.savePreferences("effectmusic", true);//��Ϸ��Ч
			spu.savePreferences("speed", 1000);//��Ϸ�ٶ�
		}
		
		sex=spu.getPreferencesInt("sex");
		bgmusic=spu.getPreferencesBoolean("bgmusic");
		effectmusic=spu.getPreferencesBoolean("effectmusic");
		speed=spu.getPreferencesInt("speed");
		
	}
	
	
	
	/**
	 * �첽������Ч�ļ�
	 * @author Administrator
	 *
	 */
	class loadSound extends AsyncTask<Context, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Context... params) {
			//������Ƶ�ļ�����Ч��
			AssetManager am=getAssets();
			int sid=-1;
			
			try {
				sid=soundpool.load(am.openFd("cancel.mp3"), 1);
				soundmap.put("cancel.mp3", sid);
				
				sid=soundpool.load(am.openFd("SpecOk.ogg"), 1);
				soundmap.put("SpecOk.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Welcome.ogg"), 1);
				soundmap.put("MusicEx_Welcome.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Exciting.ogg"), 1);
				soundmap.put("MusicEx_Exciting.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Lose.ogg"), 1);
				soundmap.put("MusicEx_Lose.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Normal2.ogg"), 1);
				soundmap.put("MusicEx_Normal2.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Normal.ogg"), 1);
				soundmap.put("MusicEx_Normal.ogg", sid);
				
				sid=soundpool.load(am.openFd("MusicEx_Win.ogg"), 1);
				soundmap.put("MusicEx_Win.ogg", sid);
				
				sid=soundpool.load(am.openFd("Special_alert.mp3"), 1);
				soundmap.put("Special_alert.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_Bomb.mp3"), 1);
				soundmap.put("Special_Bomb.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_Dispatch.mp3"), 1);
				soundmap.put("Special_Dispatch.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_give.mp3"), 1);
				soundmap.put("Special_give.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_Multiply.mp3"), 1);
				soundmap.put("Special_Multiply.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_plane.mp3"), 1);
				soundmap.put("Special_plane.mp3", sid);
				
				sid=soundpool.load(am.openFd("Special_star.ogg"), 1);
				soundmap.put("Special_star.ogg", sid);
				
				
				
				sid=soundpool.load(am.openFd("SpecSelectCard.ogg"), 1);
				soundmap.put("SpecSelectCard.ogg", sid);
				
				
				sid=soundpool.load(am.openFd("Man_baojing1.mp3"), 1);
				soundmap.put("Man_baojing1.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_baojing2.mp3"), 1);
				soundmap.put("Man_baojing2.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_baojing1.mp3"), 1);
				soundmap.put("Woman_baojing1.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_baojing2.mp3"), 1);
				soundmap.put("Woman_baojing2.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_bujiabei.mp3"), 1);
				soundmap.put("Woman_bujiabei.mp3", sid);
				
			
				
				
				sid=soundpool.load(am.openFd("Man_feiji.mp3"), 1);
				soundmap.put("Man_feiji.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_feiji.mp3"), 1);
				soundmap.put("Woman_feiji.mp3", sid);
				
				
				sid=soundpool.load(am.openFd("Man_liandui.mp3"), 1);
				soundmap.put("Man_liandui.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_liandui.mp3"), 1);
				soundmap.put("Woman_liandui.mp3", sid);
				
				
				sid=soundpool.load(am.openFd("Man_NoOrder.mp3"), 1);
				soundmap.put("Man_NoOrder.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_NoOrder.mp3"), 1);
				soundmap.put("Woman_NoOrder.mp3", sid);
				
				
				
				sid=soundpool.load(am.openFd("Man_NoRob.mp3"), 1);
				soundmap.put("Man_NoRob.mp3", sid);
				
				
				sid=soundpool.load(am.openFd("Woman_NoRob.mp3"), 1);
				soundmap.put("Woman_NoRob.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_Order.mp3"), 1);
				soundmap.put("Woman_Order.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_Order.mp3"), 1);
				soundmap.put("Man_Order.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_sandaiyi.mp3"), 1);
				soundmap.put("Man_sandaiyi.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_sandaiyi.mp3"), 1);
				soundmap.put("Woman_sandaiyi.mp3", sid);
				
				
				sid=soundpool.load(am.openFd("Man_sandaiyidui.mp3"), 1);
				soundmap.put("Man_sandaiyidui.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_sandaiyidui.mp3"), 1);
				soundmap.put("Woman_sandaiyidui.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_Share.mp3"), 1);
				soundmap.put("Man_Share.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_Share.mp3"), 1);
				soundmap.put("Woman_Share.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_shunzi.mp3"), 1);
				soundmap.put("Man_shunzi.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_shunzi.mp3"), 1);
				soundmap.put("Woman_shunzi.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_sidaier.mp3"), 1);
				soundmap.put("Man_sidaier.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_sidaier.mp3"), 1);
				soundmap.put("Woman_sidaier.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_sidailiangdui.mp3"), 1);
				soundmap.put("Woman_sidailiangdui.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_sidailiangdui.mp3"), 1);
				soundmap.put("Man_sidailiangdui.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_sidaier.mp3"), 1);
				soundmap.put("Man_sidaier.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_sidaier.mp3"), 1);
				soundmap.put("Woman_sidaier.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_wangzha.mp3"), 1);
				soundmap.put("Man_wangzha.mp3", sid);
				
				sid=soundpool.load(am.openFd("Woman_wangzha.mp3"), 1);
				soundmap.put("Woman_wangzha.mp3", sid);
				
				sid=soundpool.load(am.openFd("Man_zhadan.mp3"), 1);
				soundmap.put("Man_zhadan.mp3", sid);
							
				sid=soundpool.load(am.openFd("Woman_zhadan.mp3"), 1);
				soundmap.put("Woman_zhadan.mp3", sid);
				
				
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			for(int i=1;i<=15;i++){
				try {
					sid=soundpool.load(am.openFd("Man_"+i+".mp3"), 1);
					soundmap.put("Man_"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Woman_"+i+".mp3"), 1);
					soundmap.put("Woman_"+i+".mp3", sid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			for(int i=1;i<=3;i++){
				try {
					sid=soundpool.load(am.openFd("Man_dani"+i+".mp3"), 1);
					soundmap.put("Man_dani"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Man_Rob"+i+".mp3"), 1);
					soundmap.put("Man_Rob"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Woman_dani"+i+".mp3"), 1);
					soundmap.put("Woman_dani"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Woman_Rob"+i+".mp3"), 1);
					soundmap.put("Woman_Rob"+i+".mp3", sid);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			for(int i=1;i<=4;i++){
				try {
					sid=soundpool.load(am.openFd("Man_buyao"+i+".mp3"), 1);
					soundmap.put("Man_buyao"+i+".mp3", sid);
					
				
					sid=soundpool.load(am.openFd("Woman_buyao"+i+".mp3"), 1);
					soundmap.put("Woman_buyao"+i+".mp3", sid);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			for(int i=1;i<=13;i++){
				try {
					sid=soundpool.load(am.openFd("Man_dui"+i+".mp3"), 1);
					soundmap.put("Man_dui"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Man_tuple"+i+".mp3"), 1);
					soundmap.put("Man_tuple"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Woman_dui"+i+".mp3"), 1);
					soundmap.put("Woman_dui"+i+".mp3", sid);
					
					sid=soundpool.load(am.openFd("Woman_tuple"+i+".mp3"), 1);
					soundmap.put("Woman_tuple"+i+".mp3", sid);
					
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				System.out.println("�������");
			}
		}
		
		
		
	}
	
	/**
	 * ���Activity��������
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		Log.d(Tag, "���Activity=" + activity.getLocalClassName());
		if (!mActivityList.contains(activity)) {
			mActivityList.add(activity);
		}
	}

	/**
	 * �Ƴ�Activity
	 * @param activity
	 * @return
	 */
	public boolean removeActivity(Activity activity) {
		boolean flag = mActivityList.remove(activity);
		Log.i(Tag, "�Ƴ�Activity " + activity.getClass().getSimpleName() + ",mActivityList.size():" + mActivityList.size());
		return flag;
	}
	
	/**
	 * �������Activity
	 * @return
	 */
	public List<Activity> getActivityList() {
		return this.mActivityList;
	}

	/**
	 * �˳�ϵͳ
	 */
	public void exit() {
		try {
			//�ȹر�����Activity
			for (Activity activity : mActivityList) {
				if (activity != null) {
					activity.finish();
					Log.i(Tag, "Activity " + activity.getClass()+ " is finished!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//ϵͳ�˳�
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}


	/**
	 * ������������ԵĻ�á�����
	 * @return
	 */
	public SoundPool getSoundpool() {
		return soundpool;
	}


	public Map<String, Integer> getSoundmap() {
		return soundmap;
	}
	
	
	
	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public boolean getBgmusic() {
		return bgmusic;
	}


	public void setBgmusic(boolean bgmusic) {
		this.bgmusic = bgmusic;
	}


	public boolean getEffectmusic() {
		return effectmusic;
	}


	public void setEffectmusic(boolean effectmusic) {
		this.effectmusic = effectmusic;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public Map<String, Integer> getSoundplaymap() {
		return soundplaymap;
	}


	/**
	 * ������Ч
	 * ������Ч����
	 */
	public void play(String music){
		if(effectmusic){
			Integer sid=soundmap.get(music);
			if(sid==null){
				return;
			}
			int ssd= soundpool.play(sid, 1.0f,1.0f,0,0,1.0f);
			System.out.println(ssd+"��"+sid);
		}
	}
	
	/**
	 * ���ű�������
	 * ������Ч����
	 */
	public void playbgMusic(String music){
		//�жϱ��������Ƿ񲥷�
		if(bgmusic){
			//�Ȱ�֮ǰ���ű������ֹرյ������
			Collection<Integer> coll= soundplaymap.values();
			for(Integer i:coll){
				soundpool.stop(i);
			}
			soundplaymap.clear();
			//�����±������֣���ӵ����ż�¼
			Integer sid=soundmap.get(music);
			if(sid==null){
				return;
			}
			int streamid= soundpool.play(sid, 1.0f,1.0f,0,-1,1.0f);
			soundplaymap.put(music, streamid);
		}
		
	}
	/**
	 * ֹͣ���ű�������
	 */
	public void stopbgMusic(){
		Collection<Integer> coll= soundplaymap.values();
		for(Integer i:coll){
			soundpool.stop(i);
		}
		soundplaymap.clear();
	}
}
