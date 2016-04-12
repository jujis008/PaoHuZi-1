package jxxsoft.com.cn.paohuzi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import jxxsoft.com.cn.paohuzi.bean.Card;
import jxxsoft.com.cn.paohuzi.bean.CardModel;

/**
 * 
 Ϊÿһ�����Ͷ���Ȩֵ�Ĵ�С��
����                              1
danpai,//���ơ�����һ�ŵ���

����                              2
duipai,//���ơ��������ŵ�����ͬ���ơ�

����                              3
sanbudai,//3�������������ŵ�����ͬ���ƣ���888��
sandaiyi,//3��1��������ͬ��������+һ�ŵ��ơ��磺333+6 
sandaier,//3��2��������ͬ��������+һ���ơ��磺444+99��

��˳                              4  (ÿ��һ����Ȩֵ+1)
danshun,//��˳���������Ż��������ϵ����������ƣ��磺45678��78910JQK�������� 2��˫����

˫˳                              5��ÿ��һ���ƣ�Ȩֵ+2��
shuangshun,//˫˳�����Ի������������ƣ��磺334455��7788991010JJ�������� 2 ���˫����

�ɻ�                              7��ÿ���Էɻ���Ȩֵ�ڻ�����+3��
feiji,//�ɻ����������������������ƣ��磺333444 ��555666777888�������� 2 ���˫����
feijidaidan,//�ɻ�������.444555+79
feiijidaiduizi,//�ɻ�������.333444555+7799JJ

ը��                             10�������������ڣ� 
zhadan,//ը�������ŵ�����ͬ���ƣ��磺7777��
huojian,//��� ��˫����������С�����������ơ�


sidaier,//4��2����������һ�� ,�����ƣ������ơ��磺5555��3��8 ��
sidaierdui,//4��2�� �����ƣ��������� 4444��55��77 


error//���ܳ���
 *
 */
public class LandlordsUtil {

	/**
	 * ������еĶ���
	 * @param list
	 * @param model
	 */
	public static void getDuiZi(List<Card> list, CardModel model) {
		// ����2����ͬ
		for (int i = 0, len = list.size(); i < len; i++) {
			if (i + 1 < len&& list.get(i).getName() == list.get(i + 1).getName()) {
				String s = list.get(i).getCid() + ","+list.get(i + 1).getCid();
				model.duizi.add(s);
				i = i + 1;
			}
		}
	}
	
	/**
	 * ������е�3��
	 * @param list
	 * @param model
	 */
	public static void getThree(List<Card> list, CardModel model) {
		// ����3����ͬ
		for (int i = 0, len = list.size(); i < len; i++) {
			if (i + 2 < len&&list.get(i).getName() == list.get(i + 2).getName()) {
				String s = list.get(i).getCid() + ",";
				s += list.get(i + 1).getCid() + ",";
				s += list.get(i + 2).getCid();
				model.sandai.add(s);
				i = i + 2;
			}
		}
	}
	
	/**
	 * ���ը��
	 * @param list ����õ��Ƽ���
	 * @param model
	 */
	public static void getBoomb(List<Card> list, CardModel model) {
		if(list.size()<1)
			return;
		// ��ը
		if (list.size() >= 2 && (list.get(list.size()-1)).getName() == 16&& list.get(list.size()-2).getName() == 17) {
			model.zhadan.add(list.get(0).getName() + "," + list.get(1).getName()); // �����ּ���
		}
		// һ���ը��
		for (int i = 0, len = list.size(); i < len; i++) {
			if (i + 3 < len&& list.get(i).getName() == list.get(i + 3).getName()) {
				String s = list.get(i).getCid() + ",";
				s += list.get(i + 1).getCid() + ",";
				s += list.get(i + 2).getCid() + ",";
				s += list.get(i + 3).getCid();
				model.zhadan.add(s);
				i = i + 3;
			}
		}
	}
	
	/**
	 * ���˫˳ ,�Ӷ�����Ѱ��
	 * ���Ի������������ƣ��磺334455��7788991010JJ�������� 2 ���˫����
	 * @param list
	 * @param model
	 */
	public static void getshuangshun(List<Card> list,CardModel model) {
		
		List<String> l = model.duizi;
		if (l.size() < 3){
			return;
		}
		String[] ids = new String[l.size()];
		int[] names=new int[l.size()];
		for (int i = 0, len = l.size(); i < len; i++) {
			ids = l.get(i).split(",");
			for(int j=0,n=list.size();j<n;j++){
				if(list.get(j).getCid().equals(ids[0])){
					names[i]=list.get(j).getName();
					break;
				}
			}
		}
		
		// 1,2,3,4 13,9,8,7,6
		for (int i = 0, len = l.size(); i < len; i++) {
			int k = i;
			for (int j = i; j < len; j++) {
				if (names[i] - names[j] == j - i){
					k = j;
				}
			}
			
			// k=4 i=1
			if (k - i >= 2){
				// ˵����i��k������
				String ss = "";
				for (int j = i; j < k; j++) {
					ss += l.get(j) + ",";
				}
				ss += l.get(k);
				model.shuangshun.add(ss);
				i = k;
			}
		}
	}
	
	/**
	 * ��÷ɻ�  ��model�����3����
	 * ��˳(�ɻ�)���������������������ƣ��磺333444 ��555666777888�������� 2 ���˫����
	 * �ɻ��������˳��ͬ�����ĵ��ƻ�ͬ�����Ķ��ơ��磺444555+79 ��333444555+7799JJ
	 */
	public static void getPlane(List<Card> list, CardModel model) {
		List<String> l = model.sandai;
		if (l.size() < 2){
			return;
		}
		
		String[] ids = new String[l.size()];
		int[] names=new int[l.size()];
		for (int i = 0, len = l.size(); i < len; i++) {
			ids = l.get(i).split(",");
			for(int j=0,n=list.size();j<n;j++){
				if(list.get(j).getCid().equals(ids[0])){
					names[i]=list.get(j).getName();
					break;
				}
			}
		}
		
		for (int i = 0, len = l.size(); i < len; i++) {
			int k = i;
			for (int j = i; j < len; j++) {
				if (names[i] - names[j] == j - i)
					k = j;
			}
			if (k != i) {// ˵����i��k�Ƿɻ�
				String ss = "";
				for (int j = i; j < k; j++) {
					ss += l.get(j) + ",";
				}
				ss += l.get(k);
				model.feiji.add(ss);
				i = k;
			}
		}
		
	}
	
	
	/**
	 * ������ӣ���˳��
	 * @param list
	 * @param model
	 */
	public static void getdanshun(List<Card> list, CardModel model) {
		if (list.size() < 5){
			return;
		}
		// ��Ҫ�����в��ظ����ƹ�Ϊһ�࣬��ֹ3��������Ӱ��
		List<Card> list2 = new ArrayList<Card>(list);
		List<Card> temp = new ArrayList<Card>();
		
		List<Integer> integers = new ArrayList<Integer>();
		
		for (Card card : list2) {
			if (integers.indexOf(card.getName()) < 0&&card.getName()<=15) {
				integers.add(card.getName());
				temp.add(card);
			}
		}
		Collections.sort(temp);
		for (int i = 0, len = temp.size(); i < len; i++) {
			int k = i;
			for (int j = i; j < len; j++) {
				if (temp.get(i).getName() - temp.get(j).getName() == j- i) {
					k = j;
				}
			}
			
			if (k - i >= 4) {
				String s = "";
				for (int j = i; j < k; j++) {
					s += temp.get(j).getCid() + ",";
				}
				s += temp.get(k).getCid();
				model.danshun.add(s);
				i = k;
			}
		}
	}
	
	/**
	 * ������е���
	 * @param list
	 * @param model
	 */
	public static void getSingle(List<Card> list, CardModel model){
		
		for (int i = 0, len = list.size(); i < len; i++) {
			model.danzi.add(list.get(i).getCid());
		}
		//ɾ������
		delSingle(model.duizi,model);
		//ɾ��3��
		delSingle(model.sandai,model);
		//ը��
		delSingle(model.zhadan,model);
		//ɾ������
		delSingle(model.danshun,model);
		//ɾ��˫˳
		delSingle(model.shuangshun,model);
		//ɾ���ɻ�
		delSingle(model.feiji,model);
	}
	
	/**
	 * ɾ��������
	 * @param list
	 * @param model
	 */
	public static void delSingle(List<String> list,CardModel model){
		for(int i=0,len=list.size();i<len;i++)
		{
			String s[]=list.get(i).split(",");
			for(int j=0;j<s.length;j++){
				model.danzi.remove(s[j]);
			}
		}
	}
	
	/**
	 * ��������
	 * @param model
	 * @return
	 */
	public static int getTimes(CardModel model){
		int count=0;
		count+=model.zhadan.size()+model.sandai.size()+model.duizi.size();
		count+=model.feiji.size()+model.shuangshun.size()+model.danshun.size();
		
		int temp=0;
		temp=model.danzi.size()-model.sandai.size()*2-model.zhadan.size()*3-model.feiji.size()*3;
		count+=temp;
		return count;
	}
	
	/**
	 * ����Ȩֵ   ��1 ����2 ��3 ը��10 �ɻ�7 ˫˳5 ˳��4
	 * @param model
	 * @return
	 */
	public static int getCountValues(CardModel model){
		int count=0;
		count+=model.danzi.size()+model.duizi.size()*2+model.sandai.size()*3;
		count+=model.zhadan.size()*10+model.feiji.size()*7+model.shuangshun.size()*5+model.danshun.size()*4;
		return count;
	}
}
