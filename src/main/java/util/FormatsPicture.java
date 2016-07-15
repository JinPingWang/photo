package util;

import java.util.ArrayList;
import java.util.List;

public class FormatsPicture {
	private static List<String> list = new ArrayList<String>();
	
	static {
		list.add("jpg");
		list.add("gif");
		list.add("png");
		list.add("css");
		list.add("js");
	}
	
	public static boolean contain(String formant){
		return list.contains(formant);
	}
}
