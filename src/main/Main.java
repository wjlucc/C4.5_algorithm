package main;

import java.io.IOException;
import java.util.ArrayList;

import node.AttributeNode;
import buildTree.BuildDecisionTree;


public class Main {
	public static void main(String[] args) throws IOException {
		BuildDecisionTree tree = new BuildDecisionTree("数据.txt");		
		tree.readData();
		
		ArrayList<String> attribute =tree.att;
		ArrayList<ArrayList<String>> data = tree.data;
		
		AttributeNode root = tree.buildTree(attribute, data);
		
		System.out.println("开始打印树-----------------------------------------------");		
		tree.printNode(root,"");
	
	}
}
