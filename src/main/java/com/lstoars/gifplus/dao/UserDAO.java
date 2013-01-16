package com.lstoars.gifplus.dao;

import java.util.List;

import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.DAOException;

public interface UserDAO {
	
	/**
	 * 保存用户对象
	 * @param user
	 * @throws Exception
	 */
	public void save(UserDO user) throws DAOException;
	
	/**
	 * 根据ID查询用户对象
	 * @param uId
	 * @return
	 * @throws DAOException
	 */
	public UserDO queryById(String uId) throws DAOException;
	
	/**
	 * 分页查询用户对象
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws DAOException
	 */
	public List<UserDO> queryUserPaged(int pageNum,int pageSize) throws DAOException;
	
	/**
	 * 查询用户总数
	 * @return
	 * @throws DAOException
	 */
	public int queryCountUser() throws DAOException;
}
