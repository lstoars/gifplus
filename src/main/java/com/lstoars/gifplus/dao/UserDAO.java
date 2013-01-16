package com.lstoars.gifplus.dao;

import java.util.List;

import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.DAOException;

public interface UserDAO {
	
	/**
	 * �����û�����
	 * @param user
	 * @throws Exception
	 */
	public void save(UserDO user) throws DAOException;
	
	/**
	 * ����ID��ѯ�û�����
	 * @param uId
	 * @return
	 * @throws DAOException
	 */
	public UserDO queryById(String uId) throws DAOException;
	
	/**
	 * ��ҳ��ѯ�û�����
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<UserDO> queryUserPaged(int pageNum,int pageSize) throws DAOException;
	
	/**
	 * ��ѯ�û�����
	 * @return
	 * @throws DAOException
	 */
	public int queryCountUser() throws DAOException;
}
