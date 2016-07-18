package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PathMapType {
	private static volatile PathMapType pathMapType;
	private Map<String, Integer> map;
	
	private PathMapType() throws IOException{
		map = new HashMap<String, Integer>();
		Properties propertiesIn = new Properties();
		FileInputStream in = new FileInputStream(PathMapType.class.getResource("/pathmaptype.properties").getPath());
		propertiesIn.load(in);
		Set<Entry<Object, Object>> set = propertiesIn.entrySet();
		for(Entry<Object, Object> entry : set){
			map.put(entry.getKey().toString(), Integer.parseInt(entry.getValue().toString()));
		}
	}
	
	public static PathMapType getInstance() throws IOException{
		if(pathMapType == null){
			synchronized(PathMapType.class){
				if(pathMapType == null){
					pathMapType = new PathMapType();
				}
			}
		}
		return pathMapType;
	}
	
	
	public int getType(String path){
		//判断是否有人利用ajax进行非法请求，对于非法请求都将返回值设置为1
		Integer result = map.get(path);
		if(result == null){
			return 1;
		}
		else{
			return result;
		}
	}
}
