package test;

import java.nio.file.Paths;
import java.sql.SQLException;

import bean.PhotosBean;
import photo.Photo;

public class InsertPhoto {

	public static void main(String[] args) throws SQLException, Exception {
		// TODO Auto-generated method stub
		Photo photo = new Photo();
		photo.addPhoto("下沙", "", "2012-04-16", "4", "/photo/image/2012/下沙/", Paths.get("H:\\xp\\游记\\下沙\\big"));
		PhotosBean photosBean = photo.getPhoto(1, 1);
		System.out.println(photosBean.getData().size());
	}

}
