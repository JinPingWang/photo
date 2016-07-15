package test;

import java.io.IOException;

import org.junit.Test;

import bean.PhotosBean;
import json.Json;

public class TestJson {
	@Test
	public void test() throws IOException{
		Json json = new Json();
		PhotosBean photosBean = new PhotosBean();
		System.out.println(json.getJson(photosBean));
	}
}
