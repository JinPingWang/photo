package condense;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class GZIPServletOutputStream extends ServletOutputStream {

	GZIPOutputStream internalGzipOS;
	
	GZIPServletOutputStream(ServletOutputStream sos) throws IOException{
		this.internalGzipOS = new GZIPOutputStream(sos);
	}
	
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		internalGzipOS.write(b);
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		// TODO Auto-generated method stub
		
	}

}