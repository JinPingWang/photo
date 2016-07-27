package photo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import DataSource.DbcpDataSource;
import bean.DBPhotoBean;
import bean.PhotoBean;
import bean.PhotosBean;

public class Photo {
	
	/**
	 * 两种情况会返回"No more pictures"
	 * 其一是：数据没有更多的记录
	 * 其二是：数据库异常
	 * @return
	 */
	public PhotosBean getPhoto(int page, int type){
		PhotosBean photosBean = new PhotosBean();
		try{			
			//1：连接数据库，查询数据库，获得结果集
			Connection conn = DbcpDataSource.getInstance().getConnection();
			Statement stat = conn.createStatement();
			int number = page*20;
			int numberBefore = (page-1)*20;
			String sql = "";
			
			//如果type=1，则选择所有类型照片进行展现；否则选择特定类型照片进行展现
			if(type == 1){
				sql = "select * from (select * from photo  order by time desc limit "+number+" )as t1 where id not in "
					+ "(select id from (select id from photo  order by time desc limit "+numberBefore+")as t2) order by time desc";
			}
			else{
				sql = "select * from (select * from photo where type="+type+" order by time desc limit "+number+" )as t1 where id not in "
						+ "(select id from (select id from photo where type="+type+" order by time desc limit "+numberBefore+")as t2)";
			}
			
			ResultSet rs = stat.executeQuery(sql);
			
			//2：将结果集写入photosBean中
			while(rs.next()){
				PhotoBean photoBean = new PhotoBean();
				
				//设置id
				photoBean.setId(rs.getString(1));
				
				//设置title
				String title="";
				String site = rs.getString(2);
				String titleDB = rs.getString(3);
				if(site.length() != 0){
					title += site;
				}
				if(titleDB.length() != 0){
					title += titleDB;
				}
				photoBean.setTitle(title);
				
				//设置time
				photoBean.setTime(rs.getString(4));
				
				//设置url
				photoBean.setUrl(rs.getString(5));
				
				//设置width
				photoBean.setWidth(rs.getString(6));
				
				//设置height
				photoBean.setHeight(rs.getString(7));
				
				//设置image
				photoBean.setImage(rs.getString(8));
				
				//设置preview
				photoBean.setPreview(rs.getString(9));
				
				photosBean.addPhotoBean(photoBean);
			}
			
			//3：判断是否有数据
			if(photosBean.getData().size() == 0){
				photosBean.setSuccess(false);
	//			用于表示这一条请求完后，没数据了，即客户端得到这次响应后，就不会发起下一个请求
				photosBean.setMessage("No more pictures");
			}
			
			//4：关闭连接
			rs.close();
			stat.close();
			conn.close();
			
			//5：返回结果
			return photosBean;
		}
		catch(Exception e){
			Logger.getLogger("Photo").log(Level.SEVERE, e.getMessage());
			photosBean.setSuccess(false);
			photosBean.setMessage("No more pictures");
		}
		//如果抛出异常，则返回"No more pictures"
		return photosBean;
	}
	
	public void addPhoto(String site, String title, String time, String type, String url, Path directory){
		List<DBPhotoBean> beans = new ArrayList<DBPhotoBean>();
		//1：读取目录中的文件，保存到一个Bean中（与数据库的字段对应，id除外）
		try(DirectoryStream<Path> entries = Files.newDirectoryStream(directory)){
		    for(Path path : entries){
		    	DBPhotoBean dbPhotoBean = new DBPhotoBean();
				dbPhotoBean.setSite(site);
				dbPhotoBean.setTitle(title);
				dbPhotoBean.setTime(time);
				dbPhotoBean.setUrl(url);
								
				File file = new File(path.toString());
				BufferedImage bufferedImage = ImageIO.read(file);   
				int width = bufferedImage.getWidth();   
				int height = bufferedImage.getHeight(); 
				dbPhotoBean.setWidth(width+"");
				dbPhotoBean.setHeight(height+"");
				dbPhotoBean.setImage(dbPhotoBean.getUrl()+"big/"+file.getName());
				dbPhotoBean.setPreview(dbPhotoBean.getUrl()+"small/"+file.getName());
				dbPhotoBean.setType(type);
				
				beans.add(dbPhotoBean);
		    }
		}
		catch(Exception e){
			//记录到系统日志中
			Logger.getLogger("Photo").log(Level.SEVERE, e.getMessage());
		}
		
		//2：依次遍历Bean，将其插入数据库中
		try{
			Connection conn = DbcpDataSource.getInstance().getConnection();
			for(DBPhotoBean dbPhotoBean : beans){
				CallableStatement stat = conn.prepareCall("{call insertIntoPhoto(?,?,?,?,?,?,?,?,?)}");
				stat.setString(1, dbPhotoBean.getSite());
				stat.setString(2, dbPhotoBean.getTitle());
				stat.setString(3, dbPhotoBean.getTime());
				stat.setString(4, dbPhotoBean.getUrl());
				stat.setString(5, dbPhotoBean.getWidth());
				stat.setString(6, dbPhotoBean.getHeight());
				stat.setString(7, dbPhotoBean.getImage());
				stat.setString(8, dbPhotoBean.getPreview());
				stat.setString(9, dbPhotoBean.getType());
				stat.executeUpdate();
				stat.close();
			}
			conn.close();
		}
		catch(Exception e){
			//记录到系统日志中
			Logger.getLogger("Photo").log(Level.SEVERE, e.getMessage());
		}
	}
}
