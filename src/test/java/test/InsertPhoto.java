package test;

import java.nio.file.Paths;
import java.sql.SQLException;

import bean.PhotosBean;
import photo.Photo;

public class InsertPhoto {

	public static void main(String[] args) throws SQLException, Exception {
		// TODO Auto-generated method stub
		Photo photo = new Photo();
		photo.addPhoto("黄银杏", "", "2016-11-25", "2", "/photo/image/2016/黄银杏/", Paths.get("D:\\Documents\\Downloads\\银杏\\白天已完成"));
		PhotosBean photosBean = photo.getPhoto(1, 1);
		System.out.println(photosBean.getData().size());
	}

}
