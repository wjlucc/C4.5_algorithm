package test;

import java.util.ArrayList;
import java.util.Arrays;

public class Test2 {
	public static void main(String[] args) {
		ArrayList<String> attribute = new ArrayList<String>();
		ArrayList<ArrayList<String>> value = new ArrayList<ArrayList<String>>();
		String s = "OutLook, Temperature, Humidity, Wind, PlayTennis";
		attribute = new ArrayList(Arrays.asList(s.split(", ")));
		System.out.println(attribute);
		
		

	}
}
