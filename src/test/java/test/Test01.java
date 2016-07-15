package test;

import java.util.ArrayList;
import java.util.List;

import util.FormatsPicture;

public class Test01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String s = "/photo/image/01.jpg";
//		String paths[] = s.split("\\.");
//		System.out.println(paths[paths.length-1]);
		
		List<String> list = new ArrayList<String>();
		list.add("jpg");
		list.add("gif");
		list.add("png");

		System.out.println(list.contains("jpg"));
		System.out.println(FormatsPicture.contain("jpg"));
	}

}
