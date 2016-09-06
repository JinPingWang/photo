package condense;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionResponseWrapper extends HttpServletResponseWrapper{

	private GZIPServletOutputStream servletGzipOS = null;
	private PrintWriter pw = null;
	
	public CompressionResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	public GZIPOutputStream getGZIPOutputStream(){
		return this.servletGzipOS.internalGzipOS;
	}
	
	private Object streamUsed = null;
	
	public ServletOutputStream getOutputStream() throws IOException{
		if((streamUsed != null) && (streamUsed != pw)){
			throw new IllegalStateException();
		}
		
		if(servletGzipOS == null){
			servletGzipOS = new GZIPServletOutputStream(getResponse().getOutputStream());
			streamUsed = servletGzipOS;
		}
		return servletGzipOS;
	}
	
	public PrintWriter getWriter() throws IOException{
		if((streamUsed != null) && (streamUsed != servletGzipOS)){
			throw new IllegalStateException();
		}
		
		if(pw == null){
			servletGzipOS = new GZIPServletOutputStream(getResponse().getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(servletGzipOS, getResponse().getCharacterEncoding());
			pw = new PrintWriter(osw);
			streamUsed = pw;
		}
		return pw;
	}
}
