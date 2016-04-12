package jxxsoft.com.cn.paohuzi.activity;

import java.util.List;
import java.util.Random;

import jxxsoft.com.cn.paohuzi.app.MainApplication;
import jxxsoft.com.cn.paohuzi.bean.Card;
import jxxsoft.com.cn.paohuzi.bean.GameGrab;
import jxxsoft.com.cn.paohuzi.bean.GameStep;
import jxxsoft.com.cn.paohuzi.bean.PlayerStatus;
import jxxsoft.com.cn.paohuzi.util.DialogUtil;


import android.content.Context;
import android.view.MotionEvent;

/**
 * �¼�������
 * @author Administrator
 *
 */
public class EventAction {

	private MotionEvent event;
	private SingleGameView gameView;
	private Context context;
	public EventAction(Context context,SingleGameView view, MotionEvent event) {
		this.context=context;
		this.event = event;
		this.gameView = view;
	}
	
	/**
	 * �ж��Ƿ��˳� ��ť��������Ӧҵ����
	 * @param sx ��ʼx����
	 * @param sy ��ʼy����
	 * @param ex ����x����
	 * @param ey ����y����
	 */
	public void exitButton(int sx,int sy,int ex,int ey){
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			MainApplication.getInstance().play("SpecOk.ogg");
			System.out.println("�˳���ť�������");
			
			DialogUtil.exitGameDialog(context);
			
		}
	}
	
	
	
	/**
	 * �ж��Ƿ����� ��ť��������Ӧҵ����
	 * @param sx ��ʼx����
	 * @param sy ��ʼy����
	 * @param ex ����x����
	 * @param ey ����y����
	 */
	public void setButton(int sx,int sy,int ex,int ey){
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			MainApplication.getInstance().play("SpecOk.ogg");
			System.out.println("���ð�ť�����");
			DialogUtil.setupDialog(context,2);
		}
	}
	
	/**
	 * ׼����ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setPrepareButtont(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.ready){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			MainApplication.getInstance().play("SpecOk.ogg");
			System.out.println("׼����ť����� :"+event.getAction());
			
			
			
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				gameView.prepareButtonbgBitmap=gameView.prepareButtondownbgBitmap;
			}else if(event.getAction()==MotionEvent.ACTION_UP){
				gameView.prepareButtonbgBitmap=gameView.prepareButtonupbgBitmap;
				//����״̬
				gameView.gameStep=GameStep.deal;
			}
			gameView.repaint=true;
		}
		if(event.getAction()==MotionEvent.ACTION_MOVE){
			if(((x>sx)&&(y>sy)&&(x<ex)&&(y<ey))==false){
				gameView.repaint=true;
				gameView.prepareButtonbgBitmap=gameView.prepareButtonupbgBitmap;
			}
		}
	}
	
	/**
	 * ÿ���Ƶ���
	 */
	public void setCard(){
		if(gameView.gameStep!=GameStep.landlords){
			return;
		}
		System.out.println("����˿���");
		float x=event.getX();
		float y=event.getY();
		List<Card> cards=gameView.player2.getCards();
		boolean selectstatus=false;
		for(Card card:cards){
			if((x>card.getX())&&(y>card.getY())&&(x<card.getX()+card.getSw())&&(y<card.getY()+card.getHeight())){
				card.setClicked(!card.isClicked());
				gameView.repaint=true;
				MainApplication.getInstance().play("SpecSelectCard.ogg");
			}
			if(card.isClicked()){
				selectstatus=true;
			}
		}
		if(selectstatus){
			gameView.pstatus=PlayerStatus.select;
			gameView.repaint=true;
		}else{
			gameView.pstatus=PlayerStatus.none;
			gameView.repaint=true;
		}
		
		
		
	}
	
	/**
	 * ���������а�ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setGrabGameBQButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.grab){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			//��ʾ���2�Ѿ�ȷ�����л��߲�������
			gameView.player2grab=true;
			if(gameView.dizhubei==0){
				gameView.player2.setStatus(GameGrab.bj);
				MainApplication.getInstance().play("Woman_NoOrder.mp3");
			}else{
				gameView.player2.setStatus(GameGrab.bq);
				MainApplication.getInstance().play("Woman_NoRob.mp3");
			}
			System.out.println("�������");
			
			
		}
	}
	/**
	 * �����е�����ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setGrabGameQDZButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.grab){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			gameView.player2grab=true;
			if(gameView.dizhubei==0){
				gameView.dizhubei=1;
				gameView.player2.setStatus(GameGrab.jdz);
				MainApplication.getInstance().play("Woman_Order.mp3");
			}else{
				gameView.dizhubei=gameView.dizhubei*2;
				gameView.player2.setStatus(GameGrab.qdz);
				MainApplication.getInstance().play("Woman_Rob3.mp3");
			}
			System.out.println("�����");
			gameView.player2.setCurrself(gameView.dizhubei);
			
		}
	}
	
	/**
	 * ������  ���ư�ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setlandlordsGameQDZButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.landlords){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			if((gameView.player2out==false)&&(gameView.pstatus==PlayerStatus.select)){
				MainApplication.getInstance().play("SpecOk.ogg");
				List<Card> cards=gameView.player2.getCards();
				gameView.player2.clearOut();
				
				for(Card card:cards){
					if(card.isClicked()){
						System.out.println("ѡ����Ϊ:"+card.getName());
						gameView.player2.addOutcards(card);
						
					}
					
				}
				gameView.player2.removeCards(gameView.player2.getOutcards());
				gameView.repaint=true;
				gameView.pstatus=PlayerStatus.send;
				gameView.player2out=true;
				gameView.player2.setPlay(true);
			}
		}
	}
	
	/**
	 * 
	 * ��ʾ��ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setHintGameQDZButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.landlords){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			if(gameView.player2out==false){
				MainApplication.getInstance().play("SpecOk.ogg");
				List<Card> cards=gameView.player2.getCards();
				gameView.player2.clearOut();
				
				for(Card card:cards){
					card.setClicked(false);
					
				}
				//�������㷨
				if(cards.size()>0){
					cards.get(new Random().nextInt(cards.size())).setClicked(true);
				}
				gameView.pstatus=PlayerStatus.send;
				gameView.repaint=true;
			}
		}
	}
	
	/**
	 * ��ѡѡ��ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setResetGameQDZButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.landlords){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			if((gameView.player2out==false)&&(gameView.pstatus==PlayerStatus.select)){
				MainApplication.getInstance().play("SpecOk.ogg");
				List<Card> cards=gameView.player2.getCards();
				for(Card card:cards){
					card.setClicked(false);					
				}
				gameView.repaint=true;
			}
		}
	}
	/**
	 * �����ư�ť
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public void setNotLandlordsGameQDZButton(int sx,int sy,int ex,int ey){
		if(gameView.gameStep!=GameStep.landlords){
			return;
		}
		float x=event.getX();
		float y=event.getY();
		if((x>sx)&&(y>sy)&&(x<ex)&&(y<ey)){
			if(gameView.player2out==false){
				MainApplication.getInstance().play("SpecOk.ogg");
				gameView.pstatus=PlayerStatus.send;
				gameView.player2out=true;
				gameView.player2.setPlay(false);
				gameView.repaint=true;
			}
		}
	}
	
	
}
