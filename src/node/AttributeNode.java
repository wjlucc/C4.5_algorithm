package node;

import java.util.ArrayList;

/**
 * 这个类是一个属性节点，包含两个字段，该属性的名字和该属性的儿子的属性名字。
 * 
 * @author WJLUCK
 * 
 */
public class AttributeNode {
	// 存放该属性的名字，若为叶子节点，则只有“yes”和“no”两种情况。
	public String attributeName = "";
	// 表示该属性是不是叶子，true表示不是叶子，false表示是叶子。默认为true，表示不是叶子。
	public boolean noLeaf = true;
	// 存放该属性下的自属性。
	public ArrayList<AttributeNode> childAttribute = new  ArrayList<AttributeNode>();

	//存放的是父节点分到这一支对应的值。
	public String fatherValue = "";
	
	public AttributeNode(String name){
	
		this.attributeName = name;
		System.out.println("new 了 一个AttributeNode对象，新建了一个节点，啦啦啦啦啦啦啦啦啦啦啦啦，名字是："+name);
		if(name.equals("yes") || name.equals("no")){//如果该属性的名字是"yes"或者"no"，表示这个属性是个叶子。
			noLeaf = false;
		}		
	}	
}
