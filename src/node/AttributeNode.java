package node;

import java.util.ArrayList;

/**
 * �������һ�����Խڵ㣬���������ֶΣ������Ե����ֺ͸����ԵĶ��ӵ��������֡�
 * 
 * @author WJLUCK
 * 
 */
public class AttributeNode {
	// ��Ÿ����Ե����֣���ΪҶ�ӽڵ㣬��ֻ�С�yes���͡�no�����������
	public String attributeName = "";
	// ��ʾ�������ǲ���Ҷ�ӣ�true��ʾ����Ҷ�ӣ�false��ʾ��Ҷ�ӡ�Ĭ��Ϊtrue����ʾ����Ҷ�ӡ�
	public boolean noLeaf = true;
	// ��Ÿ������µ������ԡ�
	public ArrayList<AttributeNode> childAttribute = new  ArrayList<AttributeNode>();

	//��ŵ��Ǹ��ڵ�ֵ���һ֧��Ӧ��ֵ��
	public String fatherValue = "";
	
	public AttributeNode(String name){
	
		this.attributeName = name;
		System.out.println("new �� һ��AttributeNode�����½���һ���ڵ㣬�������������������������������ǣ�"+name);
		if(name.equals("yes") || name.equals("no")){//��������Ե�������"yes"����"no"����ʾ��������Ǹ�Ҷ�ӡ�
			noLeaf = false;
		}		
	}	
}
