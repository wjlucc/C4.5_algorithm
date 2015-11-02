package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GainRation2 {
	//存放当前要计算的所剩属性	
	public ArrayList<String> existingAttribute = new ArrayList<String>();
	//存放当前的全部数据
	public ArrayList<ArrayList<String>> existingDataItems = new ArrayList<ArrayList<String>>();	
	
	//存放当前属性的gain值。
	public HashMap<String, Double> gainValue = new HashMap<String, Double>();
	//存放属性的splitInfo值。
	private HashMap<String, Double> splitInfo = new HashMap<String, Double>();
	//最终的gainRation存放在这里。
	private HashMap<String, Double> gainRation = new HashMap<String, Double>();
	
	//存放E(S)
	public double Es = 0;
	
	
	
	public GainRation2(ArrayList<String> attribute,ArrayList<ArrayList<String>> value){
		this.existingAttribute = attribute;
		this.existingDataItems = value;
		this.Es = getEs();
	}



	/**
	 * 先计算出活动的Es
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
	 * 传入一个值的名字及数目，公式：E(s) = -(i/n)log(i/n)-.........
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
