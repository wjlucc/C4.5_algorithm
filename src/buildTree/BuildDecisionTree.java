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
	// �Ƿ����������������ڷ����У�������
	// ���List��ŵ�ǰҪ������ʱ��ʣ���������ԡ�
	// ArrayList<String> existingAttribute = new ArrayList<String>();
	// ���List��ŵ�ǰ�����ݣ�List��ÿһ���Ǳ�����һ�����ݵ�ArrayList��
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

		System.out.println("��ǰ����ʱ�����ԣ�"+existingAttribute);
		System.out.println("��ǰ����ʱ�����ݣ�"+existingDataItems);
		
		// ������ݵľ���ֵ����ȣ��򷵻�һ��Ҷ�ӽڵ㡣
		int n = existingAttribute.size();
		if (aDecisionValue(n, existingDataItems)) {
			String name = existingDataItems.get(0).get(n - 1);
			AttributeNode leaf = new AttributeNode(name);
			leaf.noLeaf = false;
			System.out.println("�����յ㣬����һ��Ҷ�ӽڵ�");
			System.out.println("*************************************���������Ϣ");

			System.out.println("������ڵ��������֣�"+leaf.attributeName+"������ɸ��ڵ��ֵ�����ģ�"+leaf.fatherValue+"������ڲ���㣺"+leaf.noLeaf);
			for(AttributeNode t:leaf.childAttribute){
				System.out.println(t.attributeName);
			}
			
			System.out.println("*************************************��ʾ���");
			return leaf;
			
		}

		// ����һ��GainRation���󣬴��뵱ǰʣ���ȫ�����Ժ�ʣ���ȫ����ֵ��
		GainRation gainRation = new GainRation(existingAttribute,
				existingDataItems);

		// ѡ��ö�Ӧ����Ϣ�����������������ƣ���������
		String attributeName = gainRation.selectAttribute();
	
		
		// �¶���һ���ڵ㣬��Ÿ�������Ϊ���֡������Ե������������︳��ġ�
		AttributeNode root = new AttributeNode(attributeName);

		// ����һ�����ݣ�ȡ�����Ե�����ֵ��attributeValue �ж�Ӧ���Ǹ����Ե�ȫ��ȡֵ��
		int attributeIndex = existingAttribute.indexOf(attributeName);
		HashSet<String> attributeValue = new HashSet<String>();
		attributeValue = getAttributeValue(attributeIndex, existingDataItems);
		
		System.out.println("�����Ե����֣�"+attributeName);
		System.out.println("�����Ե�ȡֵΪ��"+attributeValue);
		
		// ��������������������ô�죿��������������ֵĻ�������һ�¡�

		// ���ʣ������ԡ���������𣿣����������������ɾ���ˣ������ط�Ҳ��ɾ�ˡ�����
		ArrayList<String> existingAttribute2 = new ArrayList<String>();
		removeAttributeName(attributeName,existingAttribute,existingAttribute2);
	

		// ���ø����Ե�ֵ�����ݼ����������,��ÿ�����ֽ���
		for (String tem : attributeValue) {
			ArrayList<ArrayList<String>> existingDataItems2 = new ArrayList<ArrayList<String>>();
			existingDataItems2 = splitExistingDataItems(attributeIndex, tem,
					existingDataItems);
			
			System.out.println("tem:----"+tem);
			System.out.println("existingAttribute2:"+existingAttribute2);
			System.out.println("existingDataItems2:"+existingDataItems2);
			System.out.println("Ҫ����ݹ齨����-----------------------------");
			AttributeNode child = buildTree(existingAttribute2,
					existingDataItems2);
			
			child.fatherValue = tem;
			System.out.println("������֧��"+child.fatherValue);
			root.childAttribute.add(child);
		}
		
		
		System.out.println("*************************************���������Ϣ");

		System.out.println("������ڵ��������֣�"+root.attributeName+"������ɸ��ڵ��ֵ�����ģ�"+root.fatherValue+"������ڲ���㣺"+root.noLeaf);
		for(AttributeNode t:root.childAttribute){
			System.out.println(t.attributeName);
		}		
		System.out.println("*************************************��ʾ���");
		
		return root;

	}

	/**
	 * ����������ͬ��ɾ������û�и���ԭ�������Լ���
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

	// �жϸ����ݼ��ǲ���ֻ��һ������ֵ���ǵĻ�����true�����򷵻�false��
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
	 * ���� i ����Ӧ�����Ե�ȫ��ȡֵ
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
	 * ��ÿ�����ݵ����� i �У�ѡ����ֵΪ name ��ȫ�����ݡ�
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
