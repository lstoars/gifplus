package com.lstoars.gifplus.sevlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.result.PageResult;
import com.lstoars.gifplus.service.IUserService;
import com.lstoars.gifplus.service.ImageService;

public class ViewListServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private ImageService imageService;

	private IUserService userService;

	public static final int PAGE_SIZE = 10;
	
	public static List<String> blacklist = new ArrayList<String>();
	
	static{
		blacklist.add("6D005DE42FF58EA98CF221B928DD05F9");
	}

	private static final long serialVersionUID = 4554447365920124900L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int pageNum = NumberUtils.toInt(req.getParameter("pageNum"), 1);
		String tagdIdStr = req.getParameter("tagId");
		int tagId = NumberUtils.toInt(tagdIdStr, 0);
		String orderBy = req.getParameter("orderBy");
		String uId = req.getParameter("app_openid");
		String openKey = req.getParameter("app_openkey");
		
		//过滤下黑名单
		if(filterBlacklist(uId,req)){
			req.getRequestDispatcher("/index.htm").forward(req, resp);
			return;
		}
		
		try {
			PageResult<List<ImageDO>> result = imageService.queryImageList(
					pageNum, PAGE_SIZE, tagId ,orderBy);
			
			handleFromShare(req, result);
			if (result.isSuccess()) {
				req.setAttribute("imageResult", result);
			}

			List<TagDO> tagList = imageService.queryTagList();
			req.setAttribute("tagList", tagList);
			
			req.setAttribute("orderBy", StringUtils.isBlank(orderBy) && StringUtils.isNotEmpty(openKey) ? "new" : orderBy);

		
			if(StringUtils.isNotBlank(uId)){
				req.setAttribute("app_openid", uId);
				if(StringUtils.isNotBlank(openKey) && pageNum==1){
					userService.saveUser(uId, openKey);
				}
				
				if(StringUtils.isNotBlank(openKey)) {
					req.setAttribute("app_openkey", openKey);
				}
			}
			
			printLog(req,false);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/viewListV2.jsp");
			dispatcher.forward(req, resp);
		} catch (ServiceException e) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
		}

	}
	
	private void printLog(HttpServletRequest req,boolean limit){
		int pageNum = NumberUtils.toInt(req.getParameter("pageNum"), 1);
		String tagdIdStr = req.getParameter("tagId");
		int tagId = NumberUtils.toInt(tagdIdStr, 0);
		String orderBy = req.getParameter("orderBy");
		String uId = req.getParameter("app_openid");
		if(StringUtils.isBlank(uId)){
			uId = (String) req.getSession().getAttribute("appOpenid");
		}
		if(StringUtils.isBlank(orderBy) && StringUtils.isEmpty(tagdIdStr)){
			orderBy = "new";
		}
		
		if(limit){
			logger.warn("limit uId:"+uId+",pageNum:"+pageNum+",tagId:"+tagId+",orderBy:"+orderBy);
		} else {
			logger.warn("user access uId:"+uId+",pageNum:"+pageNum+",tagId:"+tagId+",orderBy:"+orderBy);
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
		userService = (IUserService) context.getBean("userService");
		super.init(config);
	}
	
	private boolean filterBlacklist(String uId,HttpServletRequest req) {
		if(blacklist.contains(uId) || StringUtils.isBlank(uId)){
			printLog(req,true);
			return true;
		}
		return false;
	}
	
	private void handleFromShare(HttpServletRequest req, PageResult<List<ImageDO>> result) {
		String imageIdStr = req.getParameter("imageId");
		if(StringUtils.isNotBlank(imageIdStr) && !StringUtils.equals("0", imageIdStr)) {
			long imageId = NumberUtils.toLong(imageIdStr);
			ImageDO image = imageService.queryImageById(imageId);
			if(null != image && !isExist(image.getId(),result.getValue())) {
				result.getValue().add(0, image);
			}
			String uId = req.getParameter("app_openid");
			String suid = req.getParameter("suid");
			logger.warn("uId:" + uId + " from share; visit imageid:"+imageId+"; suid:"+suid );
		}
	}
	
	private boolean isExist(long imageId, List<ImageDO> list){
		List<Long> ids = new ArrayList<Long>();
		for (ImageDO image : list) {
			ids.add(image.getId());
		}
		return ids.contains(imageId);
	}

}
