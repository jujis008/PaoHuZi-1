package jxxsoft.com.cn.paohuzi.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jxxsoft.com.cn.paohuzi.R;
import jxxsoft.com.cn.paohuzi.app.MainApplication;
import jxxsoft.com.cn.paohuzi.bean.Card;
import jxxsoft.com.cn.paohuzi.bean.GameGrab;
import jxxsoft.com.cn.paohuzi.bean.GameStep;
import jxxsoft.com.cn.paohuzi.bean.Player;
import jxxsoft.com.cn.paohuzi.bean.PlayerStatus;
import jxxsoft.com.cn.paohuzi.bean.ScreenType;
import jxxsoft.com.cn.paohuzi.util.ImageUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * ������Ϸ��ͼ
 * �̳�SurfaceView��ʵ��SurfaceHolder.Callback��Runnable
 * 
 *ʵ��Runnable  ���ã����ڻ��ƽ����߳�
 *
 */
public class SingleGameView extends SurfaceView implements SurfaceHolder.Callback,Runnable  {

	//���MainApplicationʵ��
	private MainApplication app=MainApplication.getInstance();
	//���Asset��Դ������
	private AssetManager assetManager;
	//�ֱ��ʴ�С
	private ScreenType screenType;
	//��ͼ������
	private SurfaceHolder surfaceHolder=null;
	//Handler����
	private Handler handler=null;
	//����
	private Canvas canvas=null;
	//����ͼ��
	private Bitmap bgBitmap=null;
	//��ʼ���ǵ����ͷ��ͼ��
	private Bitmap initHeadBitmap=null;
	//�˳�ͼ��
	private Bitmap exitBitmap=null;
	//����ͼ��
	private Bitmap setupBitmap=null;
	//�Ʊ���ͼ��
	private Bitmap cardBgBitmap=null;
	//�Ʊ���ͼ��
	private Bitmap cardbeforeBitmap=null;
	//׼����ť����
	private Bitmap prepareButtontextBitmap=null;
	//��ǰ׼����ť����
	public Bitmap prepareButtonbgBitmap=null;
	//׼��û�а��°�ť����
	public Bitmap prepareButtonupbgBitmap=null;
	//׼�����°�ť����
	public Bitmap prepareButtondownbgBitmap=null;
	//׼����ͼ��
	public Bitmap prepareButtonokBitmap=null;
	//ͷ��ͼ��
	public List<Bitmap> HeaderBitmaps=new ArrayList<Bitmap>();
	//����ͼ��
	public List<Bitmap> numberBitmaps=new ArrayList<Bitmap>();
	
	//�Ƹ�������ͼ��
	public List<Bitmap> cardNumberBitmaps=new ArrayList<Bitmap>();
		
	//����ͼ��
	public Bitmap beiBitmap=null;
	
	
	// ��Ļ��Ⱥ͸߶�
	private	int screen_height=0;
	private	int screen_width=0;
	//��Ϸ�߳�
	private Thread gameThread=null;
	//��ͼ�߳�
	private Thread drawThread=null;
	
	
	////����
	private int turn=-1;
	
	//�Ƿ��ػ�
	public Boolean repaint=false;
	//�Ƿ�ʼ��Ϸ
	private Boolean start=false;
	
	//������
	private Context appContext=null;
	
	//��Ϸ״̬
	public GameStep gameStep=GameStep.init;
	
	// ��ͼƬ��Դ
	private Bitmap[] cardnumblackBitmap = new Bitmap[13];//��ɫ����
	private Bitmap[] cardnumredBitmap = new Bitmap[13];//��ɫ����
	private Bitmap[] cardlogoBitmap = new Bitmap[4];//ͼ��
	private Bitmap  dwBitmap;//����
	private Bitmap xwBitmap;//С��
	
	private Bitmap  dwtopBitmap;//���� ������
	private Bitmap xwtopBitmap;//С�� ������
	
	private Bitmap playCardBitmap;//���1 3���Ʊ���
	private Bitmap cardFaceBitmap;//���2�� ��������
	// �ƶ���
	private Card card[] = new Card[54];
	//������
	private List<Card> dizhuList=new ArrayList<Card>();

	//���1��3�Ƿ��Ѿ�������ƶ���
	public  boolean player1out=false;
	public  boolean player3out=false;
	
	//���2 �Լ���״̬
	public PlayerStatus pstatus=PlayerStatus.none;
	public  boolean player2out=false;
	
	//�����Ϣ ����� ���� ���м� �Լ� �� �ұ� ����
	public Player player1=new Player(1,false);
	public Player player2=new Player(2,true);//�����Լ�
	public Player player3=new Player(3,false);
	
	//��ע����
	public	int dizhubei=1;
	//�ֵ�˭������
	public int grabindex=0;
	//�Ƿ�ȷ����һ���е�����
	private boolean firstGrab=false;
	//����ѭ������ַ
	private int[] nextGrab={1,2,0,1};
	private	Player[] players={player1,player2,player3};
	//�������2�Ƿ��Ѿ�������
	public boolean player2grab=false;
	
	//����\��ť����ͼ��
	private Bitmap[] gramTextBitmap = new Bitmap[19];//ͼ��
	
	//��Ϸ������˭��˭Ӯף��
	private Bitmap[] overGameBitmaps=new Bitmap[4];
	private Bitmap overGamecurrBitmap=null;
	
	/**
	 * ���췽��
	 * @param context ������
	 * @param handler handler����
	 */
	public SingleGameView(Context context,Handler handler,ScreenType screenType) {
		super(context);
		assetManager=context.getAssets();
		//��ǰ��ͼ��ý���
		setFocusable(true);
		//��ֵ
		this.screenType=screenType;
		this.appContext=context;
		this.handler=handler;
		//�����ͼ����������ֵ
		surfaceHolder = this.getHolder();
		//����ͼ��������Ӽ���
		surfaceHolder.addCallback(this);
	}
	
	/**
	 * ��ʼ����  ����54���˿���
	 */
	public void  initCard(){
		
		//A 2
		card[0]=new Card(cardnumblackBitmap[0], cardlogoBitmap[0],"c1", 14);
		card[1]=new Card(cardnumblackBitmap[0], cardlogoBitmap[1],"c2", 14);
		card[2]=new Card(cardnumblackBitmap[0], cardlogoBitmap[2],"c3", 14);
		card[3]=new Card(cardnumblackBitmap[0], cardlogoBitmap[3],"c4", 14);
		
		card[4]=new Card(cardnumblackBitmap[1], cardlogoBitmap[0],"c5", 15);
		card[5]=new Card(cardnumblackBitmap[1], cardlogoBitmap[1],"c6", 15);
		card[6]=new Card(cardnumblackBitmap[1], cardlogoBitmap[2],"c7", 15);
		card[7]=new Card(cardnumblackBitmap[1], cardlogoBitmap[3],"c8", 15);
		
		//��3��K
		for(int i=2;i<13;i++){
			for(int j=0;j<4;j++){
				if(j<2){
					card[i*4+j]=new Card(cardnumblackBitmap[i], cardlogoBitmap[j],"c"+(4*i+j+1), i+1);
				}else{
					card[i*4+j]=new Card(cardnumredBitmap[i], cardlogoBitmap[j],"c"+(4*i+j+1), i+1);
				}
			}
		}
		
		
		
		//���С��������
		card[52]=new Card(xwBitmap, xwBitmap, "c"+53,16);
		card[53]=new Card(dwBitmap, dwBitmap, "c"+54,17);

	}

	public void initLowBitMap(){

		//����
		bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);
		

		try {
			initHeadBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_unknown.png")),(float)(1.0/3));
			
			exitBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_exit.png")),(float)(1.0/3));
			setupBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_setting.png")),(float)(1.0/3));
			cardBgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_back_header.png")),(float)(1.0/3));
			
			prepareButtontextBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_ready.png")),(float)(1.0/3));
			prepareButtonupbgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_green_btn.png")),(float)(1.0/3));
			prepareButtonbgBitmap=prepareButtonupbgBitmap;
			prepareButtondownbgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_green_btn_down.png")),(float)(1.0/3));
			prepareButtonokBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/ready.png")),(float)(1.0/3));
			//����ͼƬ
			for(int i=0;i<10;i++){
				numberBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/beishu_"+i+".png")),(float)(1.0/3)));
			}
			
			//����ͼ��
			beiBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_bei.png")),(float)(1.0/3));
			
			for(int n=0;n<10;n++){
				cardNumberBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/card_count_"+n+".png")),(float)(1.0/3)));
			}
			
			for(int n=0;n<13;n++){
				//big_black_1.png
				cardnumblackBitmap[n]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_black_"+(n+1)+".png")),(float)(3.0/8));
			}
			for(int n=0;n<13;n++){
				//big_red_1.png
				cardnumredBitmap[n]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_red_"+(n+1)+".png")),(float)(3.0/8));
			}
			
			cardlogoBitmap[0]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_grass_big.png")),(float)(3.0/8));//��
			cardlogoBitmap[1]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_peach_big.png")),(float)(3.0/8));//��
			cardlogoBitmap[2]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_heart_big.png")),(float)(3.0/8));//��
			cardlogoBitmap[3]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_square_big.png")),(float)(3.0/8));//��
			
			dwBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/dawang_big.png")),(float)(3.0/8));
			xwBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/xiaowang_big.png")),(float)(3.0/8));
			cardFaceBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_gb_big.png")),(float)(3.0/8));
			
			dwtopBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/dawang_header.png")),(float)(1.0/3));
			xwtopBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/xiaowang_header.png")),(float)(1.0/3));
			
			playCardBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_back_small.png")),(float)(1.0/3));
			
			
			//������
			gramTextBitmap[0]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_bu.png")),(float)(1.0/3));
			gramTextBitmap[1]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_chu.png")),(float)(1.0/3));
			gramTextBitmap[2]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_di.png")),(float)(1.0/3));
			gramTextBitmap[3]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_jiao.png")),(float)(1.0/3));
			gramTextBitmap[4]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_qiang.png")),(float)(1.0/3));
			gramTextBitmap[5]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_zhu.png")),(float)(1.0/3));
			
			gramTextBitmap[6]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_bj.png")),(float)(1.0/3));
			gramTextBitmap[7]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_bq.png")),(float)(1.0/3));
			gramTextBitmap[8]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_cue.png")),(float)(1.0/3));
			gramTextBitmap[9]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_jdz.png")),(float)(1.0/3));
			gramTextBitmap[10]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_pass.png")),(float)(1.0/3));
			gramTextBitmap[11]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_qdz.png")),(float)(1.0/3));
			gramTextBitmap[12]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_ready.png")),(float)(1.0/3));
			gramTextBitmap[13]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_repick.png")),(float)(1.0/3));
			gramTextBitmap[14]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_send_card.png")),(float)(1.0/3));
			
			gramTextBitmap[15]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/blue_btn.png")),(float)(1.0/3));
			gramTextBitmap[16]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/green_btn.png")),(float)(1.0/3));
			gramTextBitmap[17]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/red_btn.png")),(float)(1.0/3));
			gramTextBitmap[18]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/other_btn_disable.png")),(float)(1.0/3));
			
			//ͷ��ͼ��
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu.png")),(float)(1.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu_w.png")),(float)(1.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin.png")),(float)(1.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin_w.png")),(float)(1.0/3)));
			
			//�����汳��
			cardbeforeBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_gb_header.png")),(float)(1.0/3));
			
			overGameBitmaps[0]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_lose.png"));
			overGameBitmaps[1]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_win.png"));
			overGameBitmaps[2]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_lose.png"));
			overGameBitmaps[3]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_win.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initMiddleBitMap(){

		//����
		bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);
		

		try {
			initHeadBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_unknown.png")),(float)(2.0/3));
			
			exitBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_exit.png")),(float)(2.0/3));
			setupBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_setting.png")),(float)(2.0/3));
			cardBgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_back_header.png")),(float)(2.0/3));
			
			prepareButtontextBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_ready.png")),(float)(2.0/3));
			prepareButtonupbgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_green_btn.png")),(float)(2.0/3));
			prepareButtonbgBitmap=prepareButtonupbgBitmap;
			prepareButtondownbgBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_green_btn_down.png")),(float)(2.0/3));
			prepareButtonokBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/ready.png")),(float)(2.0/3));
			//����ͼƬ
			for(int i=0;i<10;i++){
				numberBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/beishu_"+i+".png")),(float)(2.0/3)));
			}
			
			//����ͼ��
			beiBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/game_icon_bei.png")),(float)(2.0/3));
			
			for(int n=0;n<10;n++){
				cardNumberBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/card_count_"+n+".png")),(float)(2.0/3)));
			}
			
			for(int n=0;n<13;n++){
				//big_black_1.png
				cardnumblackBitmap[n]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_black_"+(n+1)+".png")),(float)(4.5/8));
			}
			for(int n=0;n<13;n++){
				//big_red_1.png
				cardnumredBitmap[n]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/big_red_"+(n+1)+".png")),(float)(4.5/8));
			}
			
			cardlogoBitmap[0]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_grass_big.png")),(float)(4.5/8));//��
			cardlogoBitmap[1]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_peach_big.png")),(float)(4.5/8));//��
			cardlogoBitmap[2]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_heart_big.png")),(float)(4.5/8));//��
			cardlogoBitmap[3]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/mark_square_big.png")),(float)(4.5/8));//��
			
			dwBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/dawang_big.png")),(float)(4.5/8));
			xwBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/xiaowang_big.png")),(float)(4.5/8));
			cardFaceBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_gb_big.png")),(float)(4.5/8));
			
			dwtopBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/dawang_header.png")),(float)(2.0/3));
			xwtopBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/xiaowang_header.png")),(float)(2.0/3));
			
			playCardBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_back_small.png")),(float)(2.0/3));
			
			
			//������
			gramTextBitmap[0]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_bu.png")),(float)(2.0/3));
			gramTextBitmap[1]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_chu.png")),(float)(2.0/3));
			gramTextBitmap[2]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_di.png")),(float)(2.0/3));
			gramTextBitmap[3]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_jiao.png")),(float)(2.0/3));
			gramTextBitmap[4]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_qiang.png")),(float)(2.0/3));
			gramTextBitmap[5]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/string_zhu.png")),(float)(2.0/3));
			
			gramTextBitmap[6]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_bj.png")),(float)(2.0/3));
			gramTextBitmap[7]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_bq.png")),(float)(2.0/3));
			gramTextBitmap[8]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_cue.png")),(float)(2.0/3));
			gramTextBitmap[9]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_jdz.png")),(float)(2.0/3));
			gramTextBitmap[10]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_pass.png")),(float)(2.0/3));
			gramTextBitmap[11]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_qdz.png")),(float)(2.0/3));
			gramTextBitmap[12]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_ready.png")),(float)(2.0/3));
			gramTextBitmap[13]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_repick.png")),(float)(2.0/3));
			gramTextBitmap[14]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/text_send_card.png")),(float)(2.0/3));
			
			gramTextBitmap[15]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/blue_btn.png")),(float)(2.0/3));
			gramTextBitmap[16]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/green_btn.png")),(float)(2.0/3));
			gramTextBitmap[17]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/red_btn.png")),(float)(2.0/3));
			gramTextBitmap[18]=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/other_btn_disable.png")),(float)(2.0/3));
			
			//ͷ��ͼ��
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu.png")),(float)(2.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu_w.png")),(float)(2.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin.png")),(float)(2.0/3)));
			HeaderBitmaps.add(ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin_w.png")),(float)(2.0/3)));
			
			//�����汳��
			cardbeforeBitmap=ImageUtil.zoomBitmap(BitmapFactory.decodeStream(assetManager.open("images/poke_gb_header.png")),(float)(2.0/3));
			
			overGameBitmaps[0]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_lose.png"));
			overGameBitmaps[1]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_win.png"));
			overGameBitmaps[2]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_lose.png"));
			overGameBitmaps[3]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_win.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initLargeBitMap(){

		//����
		bgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);
		

		try {
			initHeadBitmap=BitmapFactory.decodeStream(assetManager.open("images/logo_unknown.png"));
			
			exitBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_exit.png"));
			setupBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_setting.png"));
			cardBgBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_back_header.png"));
			
			prepareButtontextBitmap=BitmapFactory.decodeStream(assetManager.open("images/text_ready.png"));
			prepareButtonupbgBitmap=BitmapFactory.decodeStream(assetManager.open("images/big_green_btn.png"));
			prepareButtonbgBitmap=prepareButtonupbgBitmap;
			prepareButtondownbgBitmap=BitmapFactory.decodeStream(assetManager.open("images/big_green_btn_down.png"));
			prepareButtonokBitmap=BitmapFactory.decodeStream(assetManager.open("images/ready.png"));
			//����ͼƬ
			for(int i=0;i<10;i++){
				numberBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/beishu_"+i+".png")));
			}
			
			//����ͼ��
			beiBitmap=BitmapFactory.decodeStream(assetManager.open("images/game_icon_bei.png"));
			
			for(int n=0;n<10;n++){
				cardNumberBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/card_count_"+n+".png")));
			}
			
			for(int n=0;n<13;n++){
				//big_black_1.png
				cardnumblackBitmap[n]=BitmapFactory.decodeStream(assetManager.open("images/big_black_"+(n+1)+".png"));
			}
			for(int n=0;n<13;n++){
				//big_red_1.png
				cardnumredBitmap[n]=BitmapFactory.decodeStream(assetManager.open("images/big_red_"+(n+1)+".png"));
			}
			
			cardlogoBitmap[0]=BitmapFactory.decodeStream(assetManager.open("images/mark_grass_big.png"));//��
			cardlogoBitmap[1]=BitmapFactory.decodeStream(assetManager.open("images/mark_peach_big.png"));//��
			cardlogoBitmap[2]=BitmapFactory.decodeStream(assetManager.open("images/mark_heart_big.png"));//��
			cardlogoBitmap[3]=BitmapFactory.decodeStream(assetManager.open("images/mark_square_big.png"));//��
			dwBitmap=BitmapFactory.decodeStream(assetManager.open("images/dawang_big.png"));
			xwBitmap=BitmapFactory.decodeStream(assetManager.open("images/xiaowang_big.png"));
			dwtopBitmap=BitmapFactory.decodeStream(assetManager.open("images/dawang_header.png"));
			xwtopBitmap=BitmapFactory.decodeStream(assetManager.open("images/xiaowang_header.png"));
			playCardBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_back_small.png"));
			cardFaceBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_gb_big.png"));
			
			//������
			gramTextBitmap[0]=BitmapFactory.decodeStream(assetManager.open("images/string_bu.png"));
			gramTextBitmap[1]=BitmapFactory.decodeStream(assetManager.open("images/string_chu.png"));
			gramTextBitmap[2]=BitmapFactory.decodeStream(assetManager.open("images/string_di.png"));
			gramTextBitmap[3]=BitmapFactory.decodeStream(assetManager.open("images/string_jiao.png"));
			gramTextBitmap[4]=BitmapFactory.decodeStream(assetManager.open("images/string_qiang.png"));
			gramTextBitmap[5]=BitmapFactory.decodeStream(assetManager.open("images/string_zhu.png"));
			
			gramTextBitmap[6]=BitmapFactory.decodeStream(assetManager.open("images/text_bj.png"));
			gramTextBitmap[7]=BitmapFactory.decodeStream(assetManager.open("images/text_bq.png"));
			gramTextBitmap[8]=BitmapFactory.decodeStream(assetManager.open("images/text_cue.png"));
			gramTextBitmap[9]=BitmapFactory.decodeStream(assetManager.open("images/text_jdz.png"));
			gramTextBitmap[10]=BitmapFactory.decodeStream(assetManager.open("images/text_pass.png"));
			gramTextBitmap[11]=BitmapFactory.decodeStream(assetManager.open("images/text_qdz.png"));
			gramTextBitmap[12]=BitmapFactory.decodeStream(assetManager.open("images/text_ready.png"));
			gramTextBitmap[13]=BitmapFactory.decodeStream(assetManager.open("images/text_repick.png"));
			gramTextBitmap[14]=BitmapFactory.decodeStream(assetManager.open("images/text_send_card.png"));
			
			gramTextBitmap[15]=BitmapFactory.decodeStream(assetManager.open("images/blue_btn.png"));
			gramTextBitmap[16]=BitmapFactory.decodeStream(assetManager.open("images/green_btn.png"));
			gramTextBitmap[17]=BitmapFactory.decodeStream(assetManager.open("images/red_btn.png"));
			gramTextBitmap[18]=BitmapFactory.decodeStream(assetManager.open("images/other_btn_disable.png"));
			
			//ͷ��ͼ��
			HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu.png")));
			HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_dizhu_w.png")));
			HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin.png")));
			HeaderBitmaps.add(BitmapFactory.decodeStream(assetManager.open("images/logo_nongmin_w.png")));
			
			//�����汳��
			cardbeforeBitmap=BitmapFactory.decodeStream(assetManager.open("images/poke_gb_header.png"));
			
			overGameBitmaps[0]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_lose.png"));
			overGameBitmaps[1]=BitmapFactory.decodeStream(assetManager.open("images/text_dizhu_win.png"));
			overGameBitmaps[2]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_lose.png"));
			overGameBitmaps[3]=BitmapFactory.decodeStream(assetManager.open("images/text_nongmin_win.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ��ʼ�� ������ԴͼƬ
	public void initBitMap() {
		
		switch (screenType) {
		case large:
				initLargeBitMap();
			break;
		case middle:
			initMiddleBitMap();
			break;
		case low:
		default:
			initLowBitMap();
			break;
		
		}
		
		
	}
	
	
	public void initData(){
		//��ջ�������
		player1.init();
		player2.init();
		player3.init();
		dizhuList.clear();
		
		
		//�������1��3
		player1out=false;
		player2out=false;
		player3out=false;
		//����
		dizhubei=1;
		//���2 �Լ���״̬
		pstatus=PlayerStatus.none;
		player2grab=false;
		
		//��ʼ������Ϣ
		initCard();
				
		//ϴ��
		washCards();
		firstGrab=false;
		grabindex=0;
		
		
		
		//��Ϸ״̬����׼��״̬
		gameStep=GameStep.ready;
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//SurfaceView�������Ĵ���
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//SurfaceView�����ɹ�����
		//�����Ļ�߶ȡ����
		screen_height = this.getHeight();
		screen_width = this.getWidth();
		System.out.println("��Ļ�ֱ��ʣ�"+screen_width+"*"+screen_height);
		// ��ʼ��������Ϣ ������Դ
		initBitMap();
		
		//��Ϸ��ʼ
		start=true;
		
		// ��ʼ��Ϸ�̣߳�������Ϸҵ�����̴���
		//����Ϸ�߳��п��ƻ�ͼ�߳�ͨ�� rapaint��ʾ
		gameThread=new Thread(new Runnable() {
			@Override
			public void run() {
				while(start){
					if(gameStep==GameStep.init){
						initData();
					}
					if(gameStep==GameStep.deal){
							//��ʼ����
							app.play("Special_Dispatch.mp3");
							handCards();
							
					}
					
					//������ ��ʼ
					if(gameStep==GameStep.grab){
						//���õ�һ���е�����
						if(firstGrab==false){
							//�����������Ĵ���
							Random rd=new Random();
							int sgrab=rd.nextInt(3);
							switch (sgrab) {
							case 0:
								nextGrab=new int[]{0,1,2,0};
								break;
							case 1:
								nextGrab=new int[]{1,2,0,1};
								break;
							case 2:
								nextGrab=new int[]{2,0,1,2};
								break;
							}
							//�����ж��Ƿ��Ѿ����˵�һ�ν��˵���
							dizhubei=0;
							grabindex=0;
							firstGrab=true;
						}
						
						grabDiZhu();
					}
					
					//������������
					if(gameStep==GameStep.landlords){
							switch (turn) {
								case 0:
									playergame1();
									break;
								case 1:
									playergame2();
									break;
								case 2:
									playergame3();
									break;
								default:
									break;
							}
							//�ж���Ӯ
							win();
					}
				}
			}
		});
		gameThread.start();
		
		// ��ʼ��ͼ�߳�
		drawThread=new Thread(this);
		drawThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		start=false;
	}
	
	/**
	 *��ͼ����
	 */
	public void onGameDraw(){
		synchronized (surfaceHolder) {
			try {
				//����������ͼ
				canvas = surfaceHolder.lockCanvas();
				// ������
				drawBackground();
				//����׼������
				drawPrepareScreen();
				//���ƹرա����ð�ť��
				drawCommonButton();
				
				//�������1 ���3����
				drawPlayer1_3();
				//�������2 �Լ�
				drawPlayer2();
				//�������������
				drawGrabDiZhu();
				//���ƶ������������
				drawDDZStatus();
				//������Ӯ���
				drawGameOverBitmap();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null){
					//������ϣ����йرգ��ύˢ��
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
	

	//��Ҫ��ͼ�߳�
	@Override
	public void run() {
		onGameDraw();
		while (start) {
			if(repaint)
			{
				//���ƽ���
				onGameDraw();
				repaint=false;
			}
			//�޸�50����
			Sleep(50);
		}
	}
	// ������
	public void drawBackground() {
			Rect src = new Rect(0, 0, bgBitmap.getWidth(),bgBitmap.getHeight());
			Rect dst = new Rect(0, 0, screen_width, screen_height);
			canvas.drawBitmap(bgBitmap, src, dst, null);
	}
	
	//����׼������
	public void drawPrepareScreen(){
		//�������ͷ��
		if(gameStep==GameStep.landlords){
			if(player1.isDizhuflag()){
				canvas.drawBitmap(HeaderBitmaps.get(0), 10, 10, null);
			}else{
				canvas.drawBitmap(HeaderBitmaps.get(2), 10, 10, null);
			}
			
			if(player2.isDizhuflag()){
				canvas.drawBitmap(HeaderBitmaps.get(0), 10, screen_height/2, null);
			}else{
				canvas.drawBitmap(HeaderBitmaps.get(2), 10, screen_height/2, null);
			}
			
			if(player3.isDizhuflag()){
				canvas.drawBitmap(HeaderBitmaps.get(0), screen_width-10-HeaderBitmaps.get(0).getWidth(), 10, null);			
			}else{
				canvas.drawBitmap(HeaderBitmaps.get(2), screen_width-10-HeaderBitmaps.get(0).getWidth(), 10, null);			
			}
			
			
			
		}else{
			canvas.drawBitmap(initHeadBitmap, 10, 10, null);
			canvas.drawBitmap(initHeadBitmap, screen_width-10-initHeadBitmap.getWidth(), 10, null);
			canvas.drawBitmap(initHeadBitmap, 10, screen_height/2, null);
		}
		
		
		if(gameStep==GameStep.landlords){
			//����������
			
			drawThreeBitmap(dizhuList.get(0), screen_width/3+cardbeforeBitmap.getWidth()+5, 10);
			drawThreeBitmap(dizhuList.get(1), screen_width/3+2*cardbeforeBitmap.getWidth()+10, 10);
			drawThreeBitmap(dizhuList.get(2), screen_width/3+3*cardbeforeBitmap.getWidth()+15, 10);
			
		}else{
			//����������
			canvas.drawBitmap(cardBgBitmap, screen_width/3+cardBgBitmap.getWidth()+5, 10, null);
			canvas.drawBitmap(cardBgBitmap, screen_width/3+2*cardBgBitmap.getWidth()+10, 10, null);
			canvas.drawBitmap(cardBgBitmap, screen_width/3+3*cardBgBitmap.getWidth()+15, 10, null);
		}
	
		
		if(gameStep==GameStep.ready){
			//����׼����ť
			canvas.drawBitmap(prepareButtonbgBitmap, screen_width/2-prepareButtonbgBitmap.getWidth()/2, screen_height/2, null);
			canvas.drawBitmap(prepareButtontextBitmap, screen_width/2-prepareButtontextBitmap.getWidth()/2, screen_height/2+prepareButtonbgBitmap.getHeight()/2-prepareButtontextBitmap.getHeight()/2, null);
		}
		
		if(gameStep==GameStep.ready){
			//׼��okͼ��
			canvas.drawBitmap(prepareButtonokBitmap, 10+initHeadBitmap.getWidth()/2-prepareButtonokBitmap.getWidth()/2, 20+initHeadBitmap.getHeight(), null);
			canvas.drawBitmap(prepareButtonokBitmap, screen_width-prepareButtonokBitmap.getWidth()-10-(initHeadBitmap.getWidth()/2-prepareButtonokBitmap.getWidth()/2),20+initHeadBitmap.getHeight(), null);
			
		}
		
		if(dizhubei<10){
			//��������ͼ�� 
			canvas.drawBitmap(numberBitmaps.get(dizhubei), screen_width/3+4*cardBgBitmap.getWidth()+30, 10+cardBgBitmap.getHeight()/2-numberBitmaps.get(dizhubei).getHeight()/2, null);
			//���Ʊ��� beiBitmap
			canvas.drawBitmap(beiBitmap, screen_width/3+4*cardBgBitmap.getWidth()+30+numberBitmaps.get(dizhubei).getWidth(), 10+cardBgBitmap.getHeight()/2-beiBitmap.getHeight()/2, null);
			
		}else{
			int a=dizhubei/10;
			int b=dizhubei%10;
			//��������ͼ�� 
			canvas.drawBitmap(numberBitmaps.get(a), screen_width/3+4*cardBgBitmap.getWidth()+30, 10+cardBgBitmap.getHeight()/2-numberBitmaps.get(a).getHeight()/2, null);
			canvas.drawBitmap(numberBitmaps.get(b), screen_width/3+4*cardBgBitmap.getWidth()+30+numberBitmaps.get(a).getWidth(), 10+cardBgBitmap.getHeight()/2-numberBitmaps.get(a).getHeight()/2, null);
			//���Ʊ��� beiBitmap
			canvas.drawBitmap(beiBitmap, screen_width/3+4*cardBgBitmap.getWidth()+30+numberBitmaps.get(a).getWidth()+numberBitmaps.get(b).getWidth(), 10+cardBgBitmap.getHeight()/2-beiBitmap.getHeight()/2, null);
			
		}
		
		
		
	}
	
	/**
	 * ����������
	 * @param card
	 * @param left
	 * @param top
	 */
	public void drawThreeBitmap(Card card,int left,int top){
		//cardbeforeBitmap  �Д��ǲ��ǵ�����
		switch (card.getName()) {
				case 16:
					canvas.drawBitmap(xwtopBitmap, left, top, null);
					break;
				case 17:
					canvas.drawBitmap(dwtopBitmap, left, top, null);
					break;
				default:
					canvas.drawBitmap(cardbeforeBitmap, left, top, null);
					canvas.drawBitmap(card.getNumbitmap(), left+cardbeforeBitmap.getWidth()/2-card.getNumbitmap().getWidth()/2, top+10, null);
					canvas.drawBitmap(card.getLogobitmap(), left+cardbeforeBitmap.getWidth()/2-card.getLogobitmap().getWidth()/2, top+10+card.getNumbitmap().getHeight(), null);

					break;
				}
		
			}
	
	
	//���ƹرա����ð�ť
	public void drawCommonButton(){
		canvas.drawBitmap(exitBitmap, screen_width/3-exitBitmap.getWidth()-10, 20, null);
		canvas.drawBitmap(setupBitmap, screen_width/3-5, 20, null);
	}
	
	//�߳����߷���
	public void Sleep(long i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//ϴ��
	public void washCards() {
		//����˳��
		for(int i=0;i<100;i++){
			Random random=new Random();
			int a=random.nextInt(54);
			int b=random.nextInt(54);
			Card k=card[a];
			card[a]=card[b];
			card[b]=k;
		}
	}
	
	/**
	 * ����ҵ��
	 */
	public void handCards(){
		//��ʼ����
		System.out.println("��ʼ����");
		int t=0;
		//�������
		player1.clear();
		player2.clear();
		player3.clear();
		
		for(int i=0;i<54;i++)
		{
			if(i>50)//������
			{
				//���õ�����
				dizhuList.add(card[i]);
				continue;
			}
			switch ((t++)%3) {
				case 0:
					//������
					player1.addCards(card[i]);
					break;
				case 1:
					//��
					player2.addCards(card[i]);
					
					break;
				case 2:
					//�ұ����
					player3.addCards(card[i]);
					break;
			}
			repaint=true;
			Sleep(100);
		}
		System.out.println("��������");
		//��������
		player1.sort();
		player2.sort();
		player3.sort();
		System.out.println("����");
		repaint=true;
		
		//�����������׶�
		gameStep=GameStep.grab;
		
	}
	
	// ���1��3����
	public void drawPlayer1_3(){
		if(gameStep==GameStep.ready||gameStep==GameStep.init)
		{
			return;
		}
		//����ͼ��
		canvas.drawBitmap(playCardBitmap, 5, 20+initHeadBitmap.getHeight(), null);
		//�������1
		int count=player1.size();
		if(count<10){
			Bitmap nbm=cardNumberBitmaps.get(count);
			canvas.drawBitmap(nbm, 5+playCardBitmap.getWidth()/2-nbm.getWidth()/2, 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm.getHeight()/2, null);
		}else{
			int x=count/10;//ʮλ������
			int y=count%10;//��λ������
			Bitmap nbm1=cardNumberBitmaps.get(x);
			Bitmap nbm2=cardNumberBitmaps.get(y);
			canvas.drawBitmap(nbm1, 5+playCardBitmap.getWidth()/2-nbm1.getWidth(), 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm1.getHeight()/2, null);
			canvas.drawBitmap(nbm2,  5+playCardBitmap.getWidth()/2, 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm1.getHeight()/2, null);
		}
		
		//�������2
		canvas.drawBitmap(playCardBitmap, screen_width-playCardBitmap.getWidth()-10-(initHeadBitmap.getWidth()/2-playCardBitmap.getWidth()/2),20+initHeadBitmap.getHeight(), null);
		count=player3.size();
		if(count<10){
			Bitmap nbm=cardNumberBitmaps.get(count);
			canvas.drawBitmap(nbm, screen_width-5-nbm.getWidth()-(playCardBitmap.getWidth()/2-nbm.getWidth()/2), 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm.getHeight()/2, null);
		}else{
			int x=count/10;
			int y=count%10;
			Bitmap nbm1=cardNumberBitmaps.get(x);
			Bitmap nbm2=cardNumberBitmaps.get(y);
			canvas.drawBitmap(nbm1, screen_width-5-playCardBitmap.getWidth()/2-nbm1.getWidth(), 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm1.getHeight()/2, null);
			canvas.drawBitmap(nbm2,  screen_width-5-playCardBitmap.getWidth()/2, 20+initHeadBitmap.getHeight()+playCardBitmap.getHeight()/2-nbm1.getHeight()/2, null);
			
		}
		
	}
	
	/**
	 * �м��������� ���2�����
	 */
	public void drawPlayer2(){
		if(gameStep==GameStep.ready||gameStep==GameStep.init)
		{
			return;
		}
		int count=player2.size();
		System.out.println("�Լ�����Ŀ��"+count);
		int w=screen_width/21;
		int span=(screen_width-w*count-(cardFaceBitmap.getWidth()-w))/2;
		Card card=null;
		for(int i=0;i<count;i++){
			card=player2.getCard(i);
			if(card.isClicked()){
				card.setLocationAndSize(span+i*w,screen_height-cardFaceBitmap.getHeight()-25,cardFaceBitmap.getWidth(),cardFaceBitmap.getHeight(),w);
			}else{
				card.setLocationAndSize(span+i*w,screen_height-cardFaceBitmap.getHeight()-5,cardFaceBitmap.getWidth(),cardFaceBitmap.getHeight(),w);
			}
			
			if(card.getName()>15){
				canvas.drawBitmap(card.getNumbitmap(), card.getX(), card.getY(), null);
			}else{
				//���Ʊ���
				canvas.drawBitmap(cardFaceBitmap,  card.getX(), card.getY(), null);
				
				//������
				canvas.drawBitmap(card.getNumbitmap(),card.getUnx(),card.getUny(),null);
				canvas.drawBitmap(card.getLogobitmap(),card.getUlx(),card.getUly(),null);
				//������
				canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getNumbitmap()),card.getDnx(),card.getDny(),null);
				canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getLogobitmap()),card.getDlx(),card.getDly(),null);
			}
		}
	}
	
	/**
	 * ������ 
	 * dizhuFlag��
	 *  0�� ���1
	 *  1�� ���2
	 *  2�� ���3
	 *  
	 *  1 2 0 1   int[]
	 *  
	 *  grabindex  0,1,2,3... �ڼ���
	 */
	public void grabDiZhu(){
		//System.out.println("���"+(nextGrab[grabindex]+1)+"������");
		Player player=null;
		if(grabindex==0){
			player=players[nextGrab[grabindex]];
			switch (nextGrab[grabindex]) {
				case 0://���һ
					if(player1grabdizhu()){
						dizhubei=1;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.jdz);
					}else{
						dizhubei=0;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.bj);
					}
					break;
				case 1://��Ҷ�
					while(player2grab==false){
						Sleep(100);
					}
					player2grab=false;
					break;
				case 2://�����
					if(player3grabdizhu()){
						dizhubei=1;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.jdz);
					}else{
						dizhubei=0;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.bj);
					}
					break;
			}
			repaint=true;
			grabindex++;
			Sleep(2000);
		}else if(grabindex==1||grabindex==2){		
			player=players[nextGrab[grabindex]];
			if(dizhubei==0){
				//��û��˭�е���
				switch (nextGrab[grabindex]) {
					case 0://���һ
						if(player1grabdizhu()){
							dizhubei=1;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.jdz);
						}else{
							dizhubei=0;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.bj);
						}
						break;
					case 1://��Ҷ�
						while(player2grab==false){
							Sleep(100);
						}
						player2grab=false;
						break;
					case 2://�����
						if(player3grabdizhu()){
							dizhubei=1;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.jdz);
						}else{
							dizhubei=0;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.bj);
						}
						break;
				}
				repaint=true;
				grabindex++;
				Sleep(2000);
				
			}else{
				switch (nextGrab[grabindex]) {
				case 0://���һ
					if(player1grabdizhu()){
						dizhubei=dizhubei*2;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.qdz);
					}else{
						player.setStatus(GameGrab.bq);
					}
					break;
				case 1://��Ҷ�
					while(player2grab==false){
						Sleep(100);
					}
					player2grab=false;
					break;
				case 2://�����
					if(player3grabdizhu()){
						dizhubei=dizhubei*2;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.qdz);
					}else{
						player.setStatus(GameGrab.bq);
					}
					break;
				}
				repaint=true;
				grabindex++;
				Sleep(2000);
			}
				
		}else if(grabindex==3){
			player=players[nextGrab[grabindex]];
			if((dizhubei==player.getCurrself())&&(dizhubei!=0)){
				//��ǰ�������Լ���һ����ͬ��������û����
				grabindex++;
				return;
			}
			
			if(dizhubei==0){
				//��û��˭�е���
				switch (nextGrab[grabindex]) {
					case 0://���һ
						player.setStatus(GameGrab.none);
						repaint=true;
						Sleep(500);
						if(player1grabdizhu()){
							dizhubei=1;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.jdz);
						}else{
							dizhubei=0;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.bj);
						}
						break;
					case 1://��Ҷ�
						while(player2grab==false){
							Sleep(100);
						}
						player2grab=false;
						break;
					case 2://�����
						player.setStatus(GameGrab.none);
						repaint=true;
						Sleep(500);
						if(player3grabdizhu()){
							dizhubei=1;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.jdz);
						}else{
							dizhubei=0;
							player.setCurrself(dizhubei);
							player.setStatus(GameGrab.bj);
						}
						break;
				}
				repaint=true;
				grabindex++;
				Sleep(2000);
				
			}else{
				switch (nextGrab[grabindex]) {
				case 0://���һ
					player.setStatus(GameGrab.none);
					repaint=true;
					Sleep(500);
					if(player1grabdizhu()){
						dizhubei=dizhubei*2;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.qdz);
					}else{
						player.setStatus(GameGrab.bq);
					}
					break;
				case 1://��Ҷ�
					while(player2grab==false){
						Sleep(100);
					}
					player2grab=false;
					break;
				case 2://�����
					player.setStatus(GameGrab.none);
					repaint=true;
					Sleep(500);
					if(player3grabdizhu()){
						dizhubei=dizhubei*2;
						player.setCurrself(dizhubei);
						player.setStatus(GameGrab.qdz);
					}else{
						player.setStatus(GameGrab.bq);
					}
					break;
				}
				repaint=true;
				grabindex++;
				Sleep(2000);
			}
			
			
		}else{
			Sleep(1000);
			if(dizhubei==0){
				repaint=true;
				//������Ϸ״̬������
				gameStep=GameStep.init;
				return;
			}
			
			//�жϵ�ǰ������˭���ֵ�˭�ȳ���
			Player dizhu=null;
			if(player1.getCurrself()>player2.getCurrself()&&player1.getCurrself()>player3.getCurrself()){
				player1.setDizhuflag(true);
				turn=0;//�ֵ�˭����
				dizhu=player1;
				System.out.println("���1����");
			}else if(player2.getCurrself()>player1.getCurrself()&&player2.getCurrself()>player3.getCurrself()){
				player2.setDizhuflag(true);
				turn=1;
				dizhu=player2;
				System.out.println("���2����");
			}else{
				turn=2;
				dizhu=player3;
				player3.setDizhuflag(true);
				System.out.println("���3����");
			}
			//�����ƽ�����ַ
			dizhu.addCards(dizhuList.get(0));
			dizhu.addCards(dizhuList.get(1));
			dizhu.addCards(dizhuList.get(2));
			//�ƽ�������
			dizhu.sort();
			repaint=true;
			//������Ϸ״̬������
			gameStep=GameStep.landlords;
		}
		
	}
	
	/**
	 * �������������
	 */
	public void drawGrabDiZhu(){
		//�ǲ�������������
		if(GameStep.grab==gameStep){
			//������1
			switch (player1.getStatus()) {
				case jdz:
					canvas.drawBitmap(gramTextBitmap[3],10+playCardBitmap.getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[2],10+playCardBitmap.getWidth()+gramTextBitmap[3].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[5],10+playCardBitmap.getWidth()+gramTextBitmap[3].getWidth()+gramTextBitmap[2].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
	
				case qdz:
					canvas.drawBitmap(gramTextBitmap[4],10+playCardBitmap.getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[2],10+playCardBitmap.getWidth()+gramTextBitmap[4].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[5],10+playCardBitmap.getWidth()+gramTextBitmap[4].getWidth()+gramTextBitmap[2].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				case bj:
					canvas.drawBitmap(gramTextBitmap[0],10+playCardBitmap.getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[3],10+playCardBitmap.getWidth()+gramTextBitmap[3].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				case bq:
					canvas.drawBitmap(gramTextBitmap[0],10+playCardBitmap.getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[4],10+playCardBitmap.getWidth()+gramTextBitmap[0].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				default:
					
					break;
			}
			
			//������2
			if(grabindex<4&&nextGrab[grabindex]==1){
				if((grabindex==0&&nextGrab[0]==1)||dizhubei==0){
					//����
					canvas.drawBitmap(gramTextBitmap[17], screen_width/2-gramTextBitmap[17].getWidth(), screen_height/2, null);
					canvas.drawBitmap(gramTextBitmap[6], screen_width/2-gramTextBitmap[17].getWidth()+(gramTextBitmap[17].getWidth()/2-gramTextBitmap[6].getWidth()/2), screen_height/2+(gramTextBitmap[17].getHeight()/2-gramTextBitmap[6].getHeight()/2), null);
					//�е���
					canvas.drawBitmap(gramTextBitmap[15], screen_width/2+20, screen_height/2, null);
					canvas.drawBitmap(gramTextBitmap[9], screen_width/2+20+(gramTextBitmap[15].getWidth()/2-gramTextBitmap[9].getWidth()/2), screen_height/2+(gramTextBitmap[15].getHeight()/2-gramTextBitmap[9].getHeight()/2), null);
				}else{
					//����
					canvas.drawBitmap(gramTextBitmap[17], screen_width/2-gramTextBitmap[17].getWidth(), screen_height/2, null);
					canvas.drawBitmap(gramTextBitmap[7], screen_width/2-gramTextBitmap[17].getWidth()+(gramTextBitmap[17].getWidth()/2-gramTextBitmap[7].getWidth()/2), screen_height/2+(gramTextBitmap[17].getHeight()/2-gramTextBitmap[7].getHeight()/2), null);
					//������
					canvas.drawBitmap(gramTextBitmap[15], screen_width/2+20, screen_height/2, null);
					canvas.drawBitmap(gramTextBitmap[11], screen_width/2+20+(gramTextBitmap[15].getWidth()/2-gramTextBitmap[11].getWidth()/2), screen_height/2+(gramTextBitmap[15].getHeight()/2-gramTextBitmap[11].getHeight()/2), null);

				}
			}else{
				switch (player2.getStatus()) {
					case jdz:
						canvas.drawBitmap(gramTextBitmap[3],screen_width/2-gramTextBitmap[3].getWidth(),screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[2],screen_width/2,screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[5],screen_width/2+gramTextBitmap[2].getWidth(),screen_height/2,null);	
						break;
					case qdz:
						canvas.drawBitmap(gramTextBitmap[4],screen_width/2-gramTextBitmap[4].getWidth(),screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[2],screen_width/2,screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[5],screen_width/2+gramTextBitmap[2].getWidth(),screen_height/2,null);
						break;
					case bj:
						canvas.drawBitmap(gramTextBitmap[0],screen_width/2-gramTextBitmap[0].getWidth(),screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[3],screen_width/2,screen_height/2,null);
						break;
					case bq:
						canvas.drawBitmap(gramTextBitmap[0],screen_width/2-gramTextBitmap[0].getWidth(),screen_height/2,null);
						canvas.drawBitmap(gramTextBitmap[4],screen_width/2,screen_height/2,null);
						break;
					default:
						
						break;
				}
			}
			
			
			//������3
			switch (player3.getStatus()) {
				case jdz:
					canvas.drawBitmap(gramTextBitmap[3],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[3].getWidth()-gramTextBitmap[2].getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[2],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[2].getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[5],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);	
					break;
				case qdz:
					canvas.drawBitmap(gramTextBitmap[4],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[4].getWidth()-gramTextBitmap[2].getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[2],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[2].getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[5],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[5].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				case bj:
					canvas.drawBitmap(gramTextBitmap[0],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[3].getWidth()-gramTextBitmap[0].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[3],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[3].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				case bq:
					canvas.drawBitmap(gramTextBitmap[0],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[4].getWidth()-gramTextBitmap[0].getWidth(),20+initHeadBitmap.getHeight(),null);
					canvas.drawBitmap(gramTextBitmap[4],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[4].getWidth(),20+initHeadBitmap.getHeight(),null);
					break;
				default:
					
					break;
			}
			
			
		}
	}
	
	/**
	 * ���1�Ƿ�ȷ���� ��������
	 * @return
	 */
	public boolean player1grabdizhu(){
		return new Random().nextBoolean();
	}
	/**
	 * ���3�Ƿ�ȷ���� ��������
	 * @return
	 */
	public boolean player3grabdizhu(){
		return new Random().nextBoolean();
	}
	
	/**
	 * �����˿��Ƴ������ͼ
	 */
	public void drawDDZStatus(){
		if(gameStep!=GameStep.landlords){
			return;
		}
		player1OutCard();
		player2OutCard();
		player3OutCard();
	}

	/**
	 * ���1����
	 */
	public void player1OutCard(){
		if(gameStep==GameStep.over){
			return;
		}
		
		if(player1out==false){
			return;
		}
		if(player1.isPlay()){
			//��ʾ����
			player1.outSort();//����һ��
			int count=player1.outSize();
			if(count==0){
				return;
			}
			System.out.println("palyer1������Ŀ��"+count);
			int w=(screen_width-2*playCardBitmap.getWidth()-20)/20;
		//	int span=((screen_width-2*playCardBitmap.getWidth()-20)-w*count-(cardFaceBitmap.getWidth()-w))/2;
			Card card=null;
			for(int i=0;i<count;i++){
				card=player1.getOutCard(i);
				card.setLocationAndSize(playCardBitmap.getWidth()+i*w+5,initHeadBitmap.getHeight()+10,cardFaceBitmap.getWidth(),cardFaceBitmap.getHeight(),w);
				
				if(card.getName()>15){
					canvas.drawBitmap(card.getNumbitmap(), card.getX(), card.getY(), null);
				}else{
					//���Ʊ���
					canvas.drawBitmap(cardFaceBitmap,  card.getX(), card.getY(), null);
					
					//������
					canvas.drawBitmap(card.getNumbitmap(),card.getUnx(),card.getUny(),null);
					canvas.drawBitmap(card.getLogobitmap(),card.getUlx(),card.getUly(),null);
					//������
					canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getNumbitmap()),card.getDnx(),card.getDny(),null);
					canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getLogobitmap()),card.getDlx(),card.getDly(),null);
				}
			}
		}else{
			//���Ʋ�����ͼ��
			canvas.drawBitmap(gramTextBitmap[0],10+playCardBitmap.getWidth(),20+initHeadBitmap.getHeight(),null);
			canvas.drawBitmap(gramTextBitmap[1],10+playCardBitmap.getWidth()+gramTextBitmap[1].getWidth(),20+initHeadBitmap.getHeight(),null);

		}
		
				
	}
	
	/**
	 * ���2�������
	 */
	public void player2OutCard(){
		if(gameStep==GameStep.over){
			return;
		}
		//�Ѿ�����
		if((player2out==false)&&(turn==1)){
			//δ���ƣ���ʾ���ư�ť
			
			switch (pstatus) {
				case none:
					//���Ƴ��ư�ť ������
					canvas.drawBitmap(gramTextBitmap[18], screen_width-gramTextBitmap[18].getWidth()-10, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[18].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[14],screen_width-gramTextBitmap[18].getWidth()-10+(gramTextBitmap[18].getWidth()-gramTextBitmap[14].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[18].getHeight()+(gramTextBitmap[18].getHeight()-gramTextBitmap[14].getHeight())/2,null);
					//��ʾ��ť
					canvas.drawBitmap(gramTextBitmap[15], screen_width-2*gramTextBitmap[18].getWidth()-20, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[15].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[8],screen_width-2*gramTextBitmap[18].getWidth()-20+(gramTextBitmap[15].getWidth()-gramTextBitmap[8].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[15].getHeight()+(gramTextBitmap[15].getHeight()-gramTextBitmap[8].getHeight())/2,null);
					//��ѡ��ť
					canvas.drawBitmap(gramTextBitmap[18], screen_width-3*gramTextBitmap[18].getWidth()-30, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[16].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[13],screen_width-3*gramTextBitmap[18].getWidth()-30+(gramTextBitmap[18].getWidth()-gramTextBitmap[13].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[18].getHeight()+(gramTextBitmap[18].getHeight()-gramTextBitmap[13].getHeight())/2,null);
					break;
				case select:
					//���Ƴ��ư�ť ����
					canvas.drawBitmap(gramTextBitmap[16], screen_width-gramTextBitmap[16].getWidth()-10, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[16].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[14],screen_width-gramTextBitmap[16].getWidth()-10+(gramTextBitmap[16].getWidth()-gramTextBitmap[14].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[16].getHeight()+(gramTextBitmap[16].getHeight()-gramTextBitmap[14].getHeight())/2,null);
					//��ʾ��ť
					canvas.drawBitmap(gramTextBitmap[15], screen_width-2*gramTextBitmap[18].getWidth()-20, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[15].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[8],screen_width-2*gramTextBitmap[18].getWidth()-20+(gramTextBitmap[15].getWidth()-gramTextBitmap[8].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[15].getHeight()+(gramTextBitmap[15].getHeight()-gramTextBitmap[8].getHeight())/2,null);
					//��ѡ��ť
					canvas.drawBitmap(gramTextBitmap[15], screen_width-3*gramTextBitmap[18].getWidth()-30, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[16].getHeight()-30, null);
					canvas.drawBitmap(gramTextBitmap[13],screen_width-3*gramTextBitmap[18].getWidth()-30+(gramTextBitmap[18].getWidth()-gramTextBitmap[13].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[18].getHeight()+(gramTextBitmap[18].getHeight()-gramTextBitmap[13].getHeight())/2,null);
					break;
				default:
					break;
			}
			if(player1.isPlay()||player3.isPlay()){
				//���Ʋ�����ť
				canvas.drawBitmap(gramTextBitmap[17], screen_width-4*gramTextBitmap[17].getWidth()-40, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[17].getHeight()-30, null);
				canvas.drawBitmap(gramTextBitmap[10],screen_width-4*gramTextBitmap[17].getWidth()-40+(gramTextBitmap[17].getWidth()-gramTextBitmap[10].getWidth())/2,screen_height-cardFaceBitmap.getHeight()-30-gramTextBitmap[17].getHeight()+(gramTextBitmap[17].getHeight()-gramTextBitmap[10].getHeight())/2,null);

			}
			
		}else{
			
			if(player2.isPlay()){
				//���Ƴ������
				player2.outSort();//����һ��
				int count=player2.outSize();
				if(count<=0){
					return;
				}
				System.out.println("player2������Ŀ��"+count);
				int w=screen_width/21;
				int span=(screen_width-w*count-(cardFaceBitmap.getWidth()-w))/2;
				Card card=null;
				for(int i=0;i<count;i++){
					card=player2.getOutCard(i);
					card.setLocationAndSize(span+i*w,screen_height-2*cardFaceBitmap.getHeight()-5,cardFaceBitmap.getWidth(),cardFaceBitmap.getHeight(),w);
					if(card.getName()>15){
						canvas.drawBitmap(card.getNumbitmap(), card.getX(), card.getY(), null);
					}else{
						//���Ʊ���
						canvas.drawBitmap(cardFaceBitmap,  card.getX(), card.getY(), null);
						
						//������
						canvas.drawBitmap(card.getNumbitmap(),card.getUnx(),card.getUny(),null);
						canvas.drawBitmap(card.getLogobitmap(),card.getUlx(),card.getUly(),null);
						//������
						canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getNumbitmap()),card.getDnx(),card.getDny(),null);
						canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getLogobitmap()),card.getDlx(),card.getDly(),null);
					}
				}
			}
			if(player2out&&(player2.isPlay()==false)){
				//���Ʋ�����ͼ��
				canvas.drawBitmap(gramTextBitmap[0],screen_width/2-gramTextBitmap[0].getWidth(),screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[0].getHeight()-30,null);
				canvas.drawBitmap(gramTextBitmap[1],screen_width/2,screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[1].getHeight()-30,null);
			}
		}
		
		
	}
	
	/**
	 * ���������
	 * 
	 */
	public void player3OutCard(){
		if(gameStep==GameStep.over){
			return;
		}
		if(player3out==false){
			return;
		}
		if(player3.isPlay()){
			//��ʾ����
			player3.outSort();//����һ��
			int count=player3.outSize();
			if(count==0){
				return;
			}
			System.out.println("player3������Ŀ��"+count);
			int w=(screen_width-2*playCardBitmap.getWidth()-20)/20;
			int span=screen_width-playCardBitmap.getWidth()-(cardFaceBitmap.getWidth()-w)-w*count-10;
			Card card=null;
			for(int i=0;i<count;i++){
				card=player3.getOutCard(i);
				card.setLocationAndSize(span+i*w+10,initHeadBitmap.getHeight()+10,cardFaceBitmap.getWidth(),cardFaceBitmap.getHeight(),w);
				if(card.getName()>15){
					canvas.drawBitmap(card.getNumbitmap(), card.getX(), card.getY(), null);
				}else{
					//���Ʊ���
					canvas.drawBitmap(cardFaceBitmap,  card.getX(), card.getY(), null);
					
					//������
					canvas.drawBitmap(card.getNumbitmap(),card.getUnx(),card.getUny(),null);
					canvas.drawBitmap(card.getLogobitmap(),card.getUlx(),card.getUly(),null);
					//������
					canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getNumbitmap()),card.getDnx(),card.getDny(),null);
					canvas.drawBitmap(ImageUtil.createBitmapForUpDownLetRight(card.getLogobitmap()),card.getDlx(),card.getDly(),null);
				}
			}
		}else{
			//���Ʋ�����ͼ��
			canvas.drawBitmap(gramTextBitmap[0],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[0].getWidth()-gramTextBitmap[1].getWidth(),20+initHeadBitmap.getHeight(),null);
			canvas.drawBitmap(gramTextBitmap[1],screen_width-10-playCardBitmap.getWidth()-gramTextBitmap[1].getWidth(),20+initHeadBitmap.getHeight(),null);

		}
	}
	
	//���1����
	public void playergame1(){
		
		
		Sleep(5000-app.getSpeed());
		player1.clearOut();
		int count=0;
		while((count=new Random().nextInt(player1.size()+1))>5){
			
		}
		
		for(int i=0;i<count;i++){
			player1.addOutcards(player1.getCard(0));
			player1.removeCards(player1.getOutcards());
		}
		player1.setPlay(true);
		if(count==0){
			player1.setPlay(false);
		}
	
		repaint=true;
		player1out=true;
		Sleep(200);
		player2out=false;
		nextTurn();
	}
	//���2����
	public void playergame2(){
		repaint=true;
		while(player2out==false){
			Sleep(100);
		}
		repaint=true;
		System.out.println("�˳��ȴ�ѭ��");
		nextTurn();
	}
	//���3����
	public void playergame3(){
		
		
		Sleep(5000-app.getSpeed());
		player3.clearOut();
		int count=0;
		while((count=new Random().nextInt(player1.size()+1))>5){
			
		}
		for(int i=0;i<count;i++){
			player3.addOutcards(player3.getCard(0));
			player3.removeCards(player3.getOutcards());
		}
		player3.setPlay(true);
		if(count==0){
			player3.setPlay(false);
		}
		repaint=true;
		player3out=true;
		Sleep(200);
		nextTurn();
	}
	
	//��һ�����
	public void nextTurn(){
		turn=(turn+1)%3;
		repaint=true;
	}
	
	public void  drawGameOverBitmap(){
		if(gameStep==GameStep.over){
			//overGamecurrBitmap
			canvas.drawBitmap(overGamecurrBitmap, screen_width/2-overGamecurrBitmap.getWidth()/2, screen_height/2-overGamecurrBitmap.getHeight(), null);
		}
	}
	
	//�ж�˭Ӯ
	public void win(){
		if(player1.size()==0){
			gameStep=GameStep.over;
			if(player1.isDizhuflag()){
				overGamecurrBitmap=overGameBitmaps[2];//ũ��ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[1];//����ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
				
			}else{
				overGamecurrBitmap=overGameBitmaps[0];//����ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[3];//ũ��ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
			}
			Sleep(2000);
			gameStep=GameStep.init;
			repaint=true;
		}
		
		if(player2.size()==0){
			gameStep=GameStep.over;
			if(player2.isDizhuflag()){
				overGamecurrBitmap=overGameBitmaps[2];//ũ��ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[1];//����ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
				
			}else{
				overGamecurrBitmap=overGameBitmaps[0];//����ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[3];//ũ��ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
			}
			Sleep(2000);
			gameStep=GameStep.init;
			repaint=true;
		}
		
		if(player3.size()==0){
			gameStep=GameStep.over;
			if(player3.isDizhuflag()){
				overGamecurrBitmap=overGameBitmaps[2];//ũ��ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[1];//����ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
				
			}else{
				overGamecurrBitmap=overGameBitmaps[0];//����ʧ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Lose.ogg");
				Sleep(1000);
				overGamecurrBitmap=overGameBitmaps[3];//ũ��ʤ��
				repaint=true;
				MainApplication.getInstance().play("MusicEx_Win.ogg");
				
			}
			Sleep(2000);
			gameStep=GameStep.init;
			repaint=true;
			
			
		}
		
	}

	

	/**
	 * down ����
	 * move �ƶ�
	 * up �ɿ�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//��ť�¼�
		EventAction eventAction=new EventAction(appContext,this,event);
		//����׼����ť�Ƿ���
		eventAction.setPrepareButtont(screen_width/2-prepareButtonbgBitmap.getWidth()/2, screen_height/2, screen_width/2+prepareButtonbgBitmap.getWidth()/2, screen_height/2+prepareButtonbgBitmap.getHeight()/2);
		
		//ֻ���ܰ����¼�
		if(event.getAction()!=MotionEvent.ACTION_UP){
			return true;
		}
		//���������а�ť
		eventAction.setGrabGameBQButton(screen_width/2-gramTextBitmap[17].getWidth(),screen_height/2,screen_width/2,screen_height/2+gramTextBitmap[17].getHeight());
		//�����е���
		eventAction.setGrabGameQDZButton(screen_width/2+20,screen_height/2,screen_width/2+20+gramTextBitmap[15].getWidth(),screen_height/2+gramTextBitmap[15].getHeight());
		//���� 
		eventAction.setlandlordsGameQDZButton(screen_width-gramTextBitmap[18].getWidth()-10, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[18].getHeight()-30, screen_width-10, screen_height-cardFaceBitmap.getHeight()-30);
		//��ʾ��ť
		eventAction.setHintGameQDZButton(screen_width-2*gramTextBitmap[18].getWidth()-20, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[18].getHeight()-30, screen_width-20-gramTextBitmap[18].getWidth(), screen_height-cardFaceBitmap.getHeight()-30);
		//��ѡ��ť
		eventAction.setResetGameQDZButton(screen_width-3*gramTextBitmap[18].getWidth()-30, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[18].getHeight()-30, screen_width-30-gramTextBitmap[18].getWidth()*2, screen_height-cardFaceBitmap.getHeight()-30);
		//������ť
		eventAction.setNotLandlordsGameQDZButton(screen_width-4*gramTextBitmap[18].getWidth()-40, screen_height-cardFaceBitmap.getHeight()-gramTextBitmap[18].getHeight()-30, screen_width-40-gramTextBitmap[18].getWidth()*3, screen_height-cardFaceBitmap.getHeight()-30);
		//�Ƶļ���
		eventAction.setCard();
		//�����˳���ť�����ð�ť�Ƿ��¡�
		eventAction.exitButton(screen_width/3-exitBitmap.getWidth()-10,20,screen_width/3-10,20+exitBitmap.getHeight());
		eventAction.setButton(screen_width/3-5,20,screen_width/3-5+setupBitmap.getWidth(),20+setupBitmap.getHeight());
		
		return true;
		
	}
	
	
}
