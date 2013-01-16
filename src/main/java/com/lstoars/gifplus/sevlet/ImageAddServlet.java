package com.lstoars.gifplus.sevlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.service.ImageService;

public class ImageAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1277712596257406862L;
	
	private ImageService imageService = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String action = req.getParameter("action");
			if(StringUtils.isBlank(action)){
				List<TagDO> tagList = imageService.queryTagList();
				req.setAttribute("tagList", tagList);
				req.getRequestDispatcher("/addImage.jsp").forward(req, resp);
			} else {
				int tagId = NumberUtils.toInt(req.getParameter("tagId"),0);
				String imageUrl = req.getParameter("url");
				String desc = req.getParameter("desc");
				
				ImageDO image = new ImageDO();
				image.setTagId(tagId);
				image.setImageUrl(imageUrl);
				image.setImageDesc(desc);
				int id = imageService.saveImage(image);
				req.setAttribute("id", id);
				req.getRequestDispatcher("/addImageSuccess.jsp").forward(req, resp);
			}
			
		} catch (ServiceException e) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		imageService = (ImageService) context.getBean("imageService");
		super.init(config);
	}
	
	
}
