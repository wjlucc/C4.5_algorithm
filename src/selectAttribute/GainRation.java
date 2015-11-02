package selectAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 这个类作用是计算出每个属性的信息增益率，存放在 gainRation 这个变量中。 attribute 存放的是全部属性 ,value 存放的是每一条数据
 * 
 * @author WJLUCK
 * 
 */
public class GainRation {

	private ArrayList<String> attribute = new ArrayList<String>();
	private ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();

	private HashMap<String, Double> gainValue = new HashMap<String, Double>();
	private HashMap<String, Double> splitInfo = new HashMap<String, Double>();
	private HashMap<String, Double> gainRation = new HashMap<String, Double>();

	private int len;
	private double es;

	/**
	 * 构造方法，初始化attribute 和 value len反映属性数目
	 * 
	 * @throws IOException
	 */
	public GainRation(ArrayList<String> existingAttribute,
			ArrayList<ArrayList<String>> existingDataItems) {

		this.attribute = existingAttribute;

		this.value = existingDataItems;
		
		len = attribute.size();

		es = getEs();
		System.out.println("E(S):" + es);
		getAllGain();

		setGainRation();

		System.out.println("计算gainRation时传入的属性：" + attribute);
		System.out.println("计算gainRation时传入的数据：" + value);

		System.out.println("gainValue值:" + gainValue);
		System.out.println("splitInfo值:" + splitInfo);
		System.out.println("gainRation值:" + gainRation);

	}

	// 计算总的E(s)
	private double getEs() {
		double info = 0.0;
		HashMap<String, Integer> attributeNum = new HashMap<String, Integer>();
		setAttributeNum(len - 1, attributeNum);
		int n = value.size();
		info = info(attributeNum, n);
		// System.out.println(info);
		return info;
	}

	private void getAllGain() {
		for (int i = 0; i < (len - 1); i++) {
			String key = attribute.get(i);
			HashMap<String, Integer> attributeNum = new HashMap<String, Integer>();
			int n = setAttributeNum(i, i, attributeNum, splitInfo);
			// System.out.println(attributeNum+"----"+n);
			double info = getAInfo(i, attributeNum, n);
			double gain = (es - info);
			// System.out.println(key+" gain-----" + gain);
			gainValue.put(key, gain);

		}
	}

	private double getAInfo(int i, HashMap<String, Integer> attributeNum, int n) {
		// System.out.println(attributeNum+"---"+n);

		Set<Map.Entry<String, Integer>> set = attributeNum.entrySet();
		double info = 0.0;
		for (Map.Entry<String, Integer> me : set) {
			HashMap<String, Integer> att = new HashMap<String, Integer>();
			String key = me.getKey();
			int k = 0;
			for (ArrayList<String> termList : value) {
				if (termList.get(i).equals(key)) {
					if (att.containsKey(termList.get(len - 1))) {
						Integer num = att.get(termList.get(len - 1));
						att.put(termList.get(len - 1), num + 1);
					} else {
						att.put(termList.get(len - 1), 1);
					}
					k++;
				}
			}
			// System.out.println(key + att);
			double term = info(att, k);
			info += ((double) me.getValue() / n) * term;
		}
		// System.out.println(attribute.get(i)+"  E(S,A)-------"+info);
		return info;
	}

	private double getAInfo(int i, int e) {
		double info = 0.0;
		HashMap<String, Integer> attributeNum = new HashMap<String, Integer>();
		int n = setAttributeNum(i, e, attributeNum, null);
		info = info(attributeNum, n);
		return info;
	}

	private double info(HashMap<String, Integer> attributeNum, int num) {
		double info = 0.0;
		Set<Map.Entry<String, Integer>> set = attributeNum.entrySet();
		for (Map.Entry<String, Integer> me : set) {
			double value = (double) me.getValue();
			value /= num;
			double log = Math.log(value) / Math.log(2);
			double temp = -value * log;
			info += temp;
		}
		return info;
	}

	private int setAttributeNum(int i, HashMap<String, Integer> attributeNum) {
		int n = 0;

		for (ArrayList<String> termList : value) {
			String attributeValue = termList.get(i);
			if (attributeNum.containsKey(attributeValue)) {
				Integer num = attributeNum.get(attributeValue);
				attributeNum.put(attributeValue, num + 1);
			} else {
				attributeNum.put(attributeValue, 1);
			}
			n++;
		}

		return n;
	}

	private int setAttributeNum(int i, int e,
			HashMap<String, Integer> attributeNum,
			HashMap<String, Double> splitInfo) {

		int n = 0;

		for (ArrayList<String> termList : value) {

			String attributeValue = termList.get(e);
			if (attributeNum.containsKey(attributeValue)) {
				Integer num = attributeNum.get(attributeValue);
				attributeNum.put(attributeValue, num + 1);
			} else {
				attributeNum.put(attributeValue, 1);
			}
			n++;
		}
		// System.out.println(attributeNum+": "+n);

		String key = attribute.get(i);
		double d = info(attributeNum, n);
		// System.out.println(key + "---" + d);
		splitInfo.put(key, d);

		return n;
	}

	private void setGainRation() {
		Set<Map.Entry<String, Double>> set = gainValue.entrySet();
		for (Map.Entry<String, Double> me : set) {
			String key = me.getKey();
			Double value = me.getValue();
			value /= splitInfo.get(key);
			gainRation.put(key, value);

		}

	}

	/**
	 * 该方法用于选出信息增益率最大的属性。
	 * 
	 * @return 返回该属性的名字。
	 */
	public String selectAttribute() {
		Set<Map.Entry<String, Double>> set = gainRation.entrySet();
		Double d = 0.0;
		String name = "";
		for (Map.Entry<String, Double> me : set) {
			String key = me.getKey();
			Double value = me.getValue();
			if (d < value) {
				d = value;
				name = key;
			}
		}
		return name;
	}

}
