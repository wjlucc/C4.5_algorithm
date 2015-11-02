package test;

import java.util.ArrayList;

public class Test3 {
	public static void main(String[] args) {
		ArrayList<Integer> index = includeingConAtt();

		System.out.println(index);

	}

	private static ArrayList<Integer> includeingConAtt() {

		ArrayList<Integer> index = new ArrayList<Integer>();

		// 分析一条数据中的值，即可判断出全部的连续属性。
		ArrayList<String> first = new ArrayList<String>();
		first.add("sunny");
		first.add("85");
		first.add("90");
		first.add("false");
		first.add("no");
		first.add("8787");

		int ind = 0;
		for (String term : first) {
			
			try{
				Integer.parseInt(term);				
				index.add(ind);
			}catch (NumberFormatException e) {
				// TODO: handle exception
			}	
			ind++;
		}

		return index;
	}
}
