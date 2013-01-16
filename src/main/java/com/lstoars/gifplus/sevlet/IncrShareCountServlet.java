package com.lstoars.gifplus.sevlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lstoars.gifplus.constant.GifPlusConstant;
import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.qplus.QOpenResult;
import com.lstoars.gifplus.qplus.QOpenService;
import com.lstoars.gifplus.service.ImageService;

public class IncrShareCountServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = -6080717644397213324L;
	
	public static boolean openFeed = true;

	private ImageService imageService = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long id = NumberUtils.toLong(req.getParameter("id"));
		try {
			imageService.incrShareCount(id);
			String uId = req.getParameter("app_openid");
			logger.warn("uId:"+uId+" share image id is :"+id);
			if(openFeed){
				String openKey = req.getParameter("app_openkey");
				QOpenService service = QOpenService.createInstance(
						GifPlusConstant.APP_ID, GifPlusConstant.APP_SECRETkEY);
				ImageDO image = imageService.queryImageById(id);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("appid", GifPlusConstant.APP_ID+"");
				map.put("openid", uId);
				map.put("openkey", openKey);
				map.put("title", "搞笑GIF图-让你开心每一天");
				map.put("comment", image.getImageDesc());
				map.put("source", "3");
				map.put("images", image.getImageUrl());
				map.put("pushParam", "fromfeed-0-"+uId);
				QOpenResult result = service.feed(map);
				logger.warn("feed result:"+result);
			}
		} catch (ServiceException e) {
			logger.error("IncrShareCountServlet error", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		imageService = (ImageService) context.getBean("imageService");
		super.init(config);
	}


}
