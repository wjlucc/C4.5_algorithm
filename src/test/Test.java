package test;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		ArrayList<String> l = new ArrayList<String>();
		l.add("1");
		l.add("2");
		System.out.println(l.size());
		System.out.println(l.get(l.size()-1));
	}
}
