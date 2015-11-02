package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GainRation2 {
	//��ŵ�ǰҪ�������ʣ����	
	public ArrayList<String> existingAttribute = new ArrayList<String>();
	//��ŵ�ǰ��ȫ������
	public ArrayList<ArrayList<String>> existingDataItems = new ArrayList<ArrayList<String>>();	
	
	//��ŵ�ǰ���Ե�gainֵ��
	public HashMap<String, Double> gainValue = new HashMap<String, Double>();
	//������Ե�splitInfoֵ��
	private HashMap<String, Double> splitInfo = new HashMap<String, Double>();
	//���յ�gainRation��������
	private HashMap<String, Double> gainRation = new HashMap<String, Double>();
	
	//���E(S)
	public double Es = 0;
	
	
	
	public GainRation2(ArrayList<String> attribute,ArrayList<ArrayList<String>> value){
		this.existingAttribute = attribute;
		this.existingDataItems = value;
		this.Es = getEs();
	}



	/**
	 * �ȼ�������Es
	 * @return
	 */
	private double getEs() {
		HashMap<String, Integer> actNum = new HashMap<String, Integer>();
		double info = 0.0;
		int n = existingAttribute.size()-1;
		
		for(ArrayList<String> term:existingDataItems){			
			String act = term.get(n);
			if(actNum.containsKey(act)){
				Integer num = actNum.get(act);
				actNum.put(act, num+1);
			}else{
				actNum.put(act, 1);
			}	
		}		
		info = getInfo(actNum,existingDataItems.size());
		return info;
	}

	
	/**
	 * ����һ��ֵ�����ּ���Ŀ����ʽ��E(s) = -(i/n)log(i/n)-.........
	 * @param actNum 
	 * @param i
	 * @return
	 */
	private double getInfo(HashMap<String, Integer> actNum, int i) {		
		double info = 0.0;
		Set<Map.Entry<String, Integer>> set = actNum.entrySet();
		for (Map.Entry<String, Integer> me : set) {			
			
			double value = (double) me.getValue();
			value /= i;
			double log = Math.log(value) / Math.log(2);
			double temp = -value * log;
			info += temp;
		}
		return info;

	}
	
	
	
	
	
	
	
	
	
}
