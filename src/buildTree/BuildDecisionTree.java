package buildTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import node.AttributeNode;
import selectAttribute.GainRation;

public class BuildDecisionTree {
	// 是否将这两个变量定义在方法中？？？？
	// 这个List存放当前要建树的时所剩的所有属性。
	// ArrayList<String> existingAttribute = new ArrayList<String>();
	// 这个List存放当前的数据，List中每一项是保存了一条数据的ArrayList。
	// ArrayList<ArrayList<String>> existingDataItems = new
	// ArrayList<ArrayList<String>>();

	public String path = null;
	public ArrayList<String> att = new ArrayList<String>();
	public ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	
	
	public BuildDecisionTree(String path){
		this.path = path;
	}
	
	public AttributeNode buildTree(ArrayList<String> existingAttribute,
			ArrayList<ArrayList<String>> existingDataItems) {

		System.out.println("当前建树时的属性："+existingAttribute);
		System.out.println("当前建树时的数据："+existingDataItems);
		
		// 如果数据的决策值都相等，则返回一个叶子节点。
		int n = existingAttribute.size();
		if (aDecisionValue(n, existingDataItems)) {
			String name = existingDataItems.get(0).get(n - 1);
			AttributeNode leaf = new AttributeNode(name);
			leaf.noLeaf = false;
			System.out.println("到达终点，返回一个叶子节点");
			System.out.println("*************************************这个结点的信息");

			System.out.println("结点所在的属性名字："+leaf.attributeName+"结点是由父节点该值引出的："+leaf.fatherValue+"结点是内部结点："+leaf.noLeaf);
			for(AttributeNode t:leaf.childAttribute){
				System.out.println(t.attributeName);
			}
			
			System.out.println("*************************************显示完毕");
			return leaf;
			
		}

		// 定义一个GainRation对象，传入当前剩余的全部属性和剩余的全部数值。
		GainRation gainRation = new GainRation(existingAttribute,
				existingDataItems);

		// 选择好对应的信息增益率最大的属性名称，传回来。
		String attributeName = gainRation.selectAttribute();
	
		
		// 新定义一个节点，存放该属性作为名字。子属性的名字是在这里赋予的。
		AttributeNode root = new AttributeNode(attributeName);

		// 遍历一遍数据，取该属性的所有值。attributeValue 中对应的是该属性的全部取值。
		int attributeIndex = existingAttribute.indexOf(attributeName);
		HashSet<String> attributeValue = new HashSet<String>();
		attributeValue = getAttributeValue(attributeIndex, existingDataItems);
		
		System.out.println("该属性的名字："+attributeName);
		System.out.println("该属性的取值为："+attributeValue);
		
		// 如果这个属性是连续的怎么办？？？？如果是数字的话重新做一下。

		// 存放剩余的属性。这里可以吗？？？？？？？？如果删掉了，其他地方也都删了。。。
		ArrayList<String> existingAttribute2 = new ArrayList<String>();
		removeAttributeName(attributeName,existingAttribute,existingAttribute2);
	

		// 利用该属性的值将数据集做多个划分,对每个划分建树
		for (String tem : attributeValue) {
			ArrayList<ArrayList<String>> existingDataItems2 = new ArrayList<ArrayList<String>>();
			existingDataItems2 = splitExistingDataItems(attributeIndex, tem,
					existingDataItems);
			
			System.out.println("tem:----"+tem);
			System.out.println("existingAttribute2:"+existingAttribute2);
			System.out.println("existingDataItems2:"+existingDataItems2);
			System.out.println("要进入递归建树了-----------------------------");
			AttributeNode child = buildTree(existingAttribute2,
					existingDataItems2);
			
			child.fatherValue = tem;
			System.out.println("这条分支："+child.fatherValue);
			root.childAttribute.add(child);
		}
		
		
		System.out.println("*************************************这个结点的信息");

		System.out.println("结点所在的属性名字："+root.attributeName+"结点是由父节点该值引出的："+root.fatherValue+"结点是内部结点："+root.noLeaf);
		for(AttributeNode t:root.childAttribute){
			System.out.println(t.attributeName);
		}		
		System.out.println("*************************************显示完毕");
		
		return root;

	}

	/**
	 * 将属性中相同的删除掉。没有更改原来的属性集。
	 * @param attributeName
	 * @param existingAttribute
	 * @param existingAttribute2
	 */
	private void removeAttributeName(String attributeName,
			ArrayList<String> existingAttribute,
			ArrayList<String> existingAttribute2) {
		for(String attribute:existingAttribute){
			if(!attribute.equals(attributeName)){
				existingAttribute2.add(attribute);
			}
		}
	}

	// 判断该数据集是不是只有一个决策值，是的话返回true，否则返回false；
	public boolean aDecisionValue(int n,
			ArrayList<ArrayList<String>> existingDataItems) {

		String value = existingDataItems.get(0).get(n - 1);
		for (ArrayList<String> dataItem : existingDataItems) {
			if (!dataItem.get(n - 1).equals(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回 i 所对应的属性的全部取值
	 * 
	 * @param i
	 * @param existingDataItems
	 * @return
	 */
	public HashSet<String> getAttributeValue(int i,
			ArrayList<ArrayList<String>> existingDataItems) {
		HashSet<String> attributeValue = new HashSet<String>();
		for (ArrayList<String> dataItem : existingDataItems) {
			String tempValue = dataItem.get(i);
			attributeValue.add(tempValue);
		}
		return attributeValue;
	}

	/**
	 * 在每条数据的索引 i 中，选出其值为 name 的全部数据。
	 * @param i
	 * @param name
	 * @param existingDataItems
	 * @return
	 */
	public ArrayList<ArrayList<String>> splitExistingDataItems(int i,
			String name, ArrayList<ArrayList<String>> existingDataItems) {
		ArrayList<ArrayList<String>> existingDataItems2 = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> dataItem : existingDataItems) {
			if (dataItem.get(i).equals(name)) {
				dataItem.remove(i);
				existingDataItems2.add(dataItem);
			}
		}
		return existingDataItems2;
	}
	
	public void readData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));

		att = new ArrayList(Arrays.asList(br.readLine().split(" ")));
		att.remove(0);
		String line = null;
		while ((line = br.readLine()) != null) {
			ArrayList<String> tempList = new ArrayList(Arrays.asList(line
					.split(" ")));
			tempList.remove(0);
			data.add(tempList);
		}
	}
	public void printNode(AttributeNode root,String tab){
		System.out.println("["+root.attributeName+"]");
		
		if(!root.noLeaf){
			return;
		}		
		for(AttributeNode childNode:root.childAttribute){
			System.out.print("	"+tab+childNode.fatherValue+"---");
			printNode(childNode,tab+"	");
		}
	}
}
