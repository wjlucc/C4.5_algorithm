package main;

import java.io.IOException;
import java.util.ArrayList;

import node.AttributeNode;
import buildTree.BuildDecisionTree;


public class Main {
	public static void main(String[] args) throws IOException {
		BuildDecisionTree tree = new BuildDecisionTree("����.txt");		
		tree.readData();
		
		ArrayList<String> attribute =tree.att;
		ArrayList<ArrayList<String>> data = tree.data;
		
		AttributeNode root = tree.buildTree(attribute, data);
		
		System.out.println("��ʼ��ӡ��-----------------------------------------------");		
		tree.printNode(root,"");
	
	}
}
