package bean;

import java.util.ArrayList;
import java.util.List;

public class PhotosBean {
	private boolean success;
	private String message;	
	private List<PhotoBean> data;
	
	public PhotosBean(){
		data = new ArrayList<PhotoBean>();
		success = true;
		message = "Retrieved pictures";
	}
	
	public void addPhotoBean(PhotoBean photoBean){
		data.add(photoBean);
	}
	
	public List<PhotoBean> getData(){
		return data;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
