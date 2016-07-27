package test;

import java.nio.file.Paths;
import java.sql.SQLException;

import bean.PhotosBean;
import photo.Photo;

public class InsertPhoto {

	public static void main(String[] args) throws SQLException, Exception {
		// TODO Auto-generated method stub
		Photo photo = new Photo();
		photo.addPhoto("马拉松", "", "2016-03-30", "4", "/photo/image/2016/马拉松/", Paths.get("D:\\相片\\马拉松"));
		PhotosBean photosBean = photo.getPhoto(1, 1);
		System.out.println(photosBean.getData().size());
	}

}
