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
import util.FormatsPicture;

public class CacheFilter implements Filter {
	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}

	/**
	 * 处理静态文件与图片缓存
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		//在指定类型资源头部添加缓存信息
		HttpServletRequest req = (HttpServletRequest)request;
		String paths[] = req.getServletPath().split("\\.");
		String path = paths[paths.length-1];
		HttpServletResponse resp = (HttpServletResponse)response;
		if(FormatsPicture.contain(path)){
			resp.addHeader("Cache-Control", "max-age=360000");
		}
		
		chain.doFilter(req, resp);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
