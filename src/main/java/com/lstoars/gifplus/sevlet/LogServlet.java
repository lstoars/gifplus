package com.lstoars.gifplus.sevlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LogServlet extends HttpServlet {

	private static final long serialVersionUID = 4504792155912136797L;
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String type = req.getParameter("type");
		String uId = req.getParameter("app_openid");
		if (StringUtils.equals("version", type)) {
			// log 用户的Q+版本号
			String version = req.getParameter("version");
			logger.warn("uId:" + uId + ",version is:" + version);
		} else if (StringUtils.equals("fromShare", type)) {
			String fromShare = req.getParameter("fromShare");
			logger.warn("uId:" + uId + ",visit "+fromShare);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
