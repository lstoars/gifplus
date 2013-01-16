package com.lstoars.gifplus.service;

import java.util.List;

import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.result.PageResult;

public interface IUserService {
	
	/**
	 * 根据UID 和token 去q+拿用户信息
	 * @param uId
	 * @param token
	 * @throws ServiceException
	 */
	public void saveUser(String uId,String token) throws ServiceException;
	
	/**
	 * 分页查询用户
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult<List<UserDO>> queryUser(int pageNum,int pageSize);
}
