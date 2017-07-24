package com.catv.p2p.mgrsite.filter;

import com.catv.p2p.base.util.BidConst;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 用来同步图片的过滤器
 * 
 * @author Administrator
 * 
 */
public class SyncImgFilter implements Filter {
	private ServletContext ctx;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.ctx = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 得到这次要请求的图片是啥;
		String file = req.getRequestURI();
        //System.out.println(FilenameUtils.getName(file));
        // 去本地应用找这个图片;
		String fileFullPath = ctx.getRealPath(file);
        //System.out.println(fileFullPath);
        // 如果图片存在,放行;
		File localFile = new File(fileFullPath);
		if (localFile.exists()) {
			chain.doFilter(req, resp);
		} else {
			// 如果图片不存在,去公共文件夹拷贝;
			File publicFile = new File(BidConst.PUBLIC_IMG_SHARE_PATH,
					FilenameUtils.getName(file));
            System.out.println(publicFile);
            if (publicFile.exists()) {
				FileUtils.copyFile(publicFile, localFile);
				// 再放行;
				resp.setHeader("Cache-Control", "no-store");
				resp.setHeader("Pragma", "no-cache");
				resp.setDateHeader("Expires", 0);
				ServletOutputStream responseOutputStream = response
						.getOutputStream();
				responseOutputStream.write(FileUtils
						.readFileToByteArray(localFile));
				responseOutputStream.flush();
				responseOutputStream.close();
			}
		}
	}

	@Override
	public void destroy() {

	}

}
