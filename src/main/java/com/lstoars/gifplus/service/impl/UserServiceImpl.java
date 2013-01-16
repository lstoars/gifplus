package com.lstoars.gifplus.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lstoars.gifplus.constant.GifPlusConstant;
import com.lstoars.gifplus.dao.UserDAO;
import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.DAOException;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.qplus.QOpenBean;
import com.lstoars.gifplus.qplus.QOpenInfo;
import com.lstoars.gifplus.qplus.QOpenResult;
import com.lstoars.gifplus.qplus.QOpenService;
import com.lstoars.gifplus.result.PageResult;
import com.lstoars.gifplus.service.IUserService;

public class UserServiceImpl implements IUserService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.service.IUserService#saveUser(java.lang.String,
	 * java.lang.String)
	 */
	public void saveUser(String uId, String token) throws ServiceException {
		if (StringUtils.isBlank(uId) || StringUtils.isBlank(token)) {
			return;
		}

		try {
			UserDO user = userDAO.queryById(uId);
			if (user != null) {
				return;
			}

			QOpenService openService = QOpenService.createInstance(
					GifPlusConstant.APP_ID, GifPlusConstant.APP_SECRETkEY);

			final QOpenBean bean = new QOpenBean(uId, token, "");
			QOpenResult result = openService.checkLogin(bean);
			if (!result.isSuccess()) {
				return;
			}

			result = openService.getUserInfo(bean);
			if (result.isSuccess()) {
				QOpenInfo info = result.getQPlusInfo();
				UserDO userDO = new UserDO(uId, info);
				this.userDAO.save(userDO);
			}
		} catch (IOException e) {
			logger.error("UserService@saveUser error", e);
		} catch (DAOException e) {
			logger.error("UserService@saveUser error", e);
		}
	}

	@Override
	public PageResult<List<UserDO>> queryUser(int pageNum, int pageSize) {
		
		PageResult<List<UserDO>> result = new PageResult<List<UserDO>>();
		try {
			result.setTotalCount(userDAO.queryCountUser());
			result.setValue(userDAO.queryUserPaged(pageNum, pageSize));
			result.setPageSize(pageSize);
			result.setCurrentPage(pageNum);
			result.setSuccess(true);
		} catch (DAOException e) {
			logger.error("UserService@queryUser error", e);
			result.setSuccess(false);
		}
		return result;
	}

}
