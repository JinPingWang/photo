package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PhotosBean;
import json.Json;
import photo.Photo;
import util.PathMapType;

public class Controller extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doGet(request, response);
	}
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/**
		 * 1：获取参数
		 */
		int page = Integer.parseInt(request.getParameter("page"));
		String path = request.getServletPath().split("/")[1];
		int type = PathMapType.getInstance().getType(path);
		
		/**
		 * 2：根据参数取得相应的照片信息
		 */
		PhotosBean photosBean = null;
		Photo photo = new Photo();
		try {
//			photosBean = photo.getPhoto(page, type);
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * 3：将图片信息写回客户端
		 */
		response.setContentType("application/json;charSet=utf-8");
		PrintWriter out =response.getWriter();
		Json json = new Json();
		out.write(json.getJson(photosBean));
		out.flush();
		out.close();
	}
}
