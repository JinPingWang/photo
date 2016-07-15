package test;

import java.io.IOException;

import org.junit.Test;

import util.PathMapType;

public class TestPathMapType {
	@Test
	public void test() throws IOException{
		System.out.println(PathMapType.getInstance().getType("travel"));
		
	}
}
