package com.lstoars.gifplus.service;

import java.util.List;

import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.result.PageResult;

public interface IUserService {
	
	/**
	 * ����UID ��token ȥq+���û���Ϣ
	 * @param uId
	 * @param token
	 * @throws ServiceException
	 */
	public void saveUser(String uId,String token) throws ServiceException;
	
	/**
	 * ��ҳ��ѯ�û�
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult<List<UserDO>> queryUser(int pageNum,int pageSize);
}
