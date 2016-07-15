package filters;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import condense.CompressionResponseWrapper;

public class CondenseFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//判断客户端支持压缩与否
		String valid_encodings = req.getHeader("Accept-Encoding");
		if(valid_encodings.indexOf("gzip") > -1){
			CompressionResponseWrapper wrappedResp = new CompressionResponseWrapper(resp);
			wrappedResp.setHeader("Content-Encoding", "gzip");

			chain.doFilter(req, wrappedResp);
			
			GZIPOutputStream gzos = wrappedResp.getGZIPOutputStream();
			gzos.finish();
		}
		else{
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
