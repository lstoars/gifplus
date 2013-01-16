package com.lstoars.gifplus.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lstoars.gifplus.dao.UserDAO;
import com.lstoars.gifplus.domian.UserDO;
import com.lstoars.gifplus.exception.DAOException;

public class UserDAOImpl implements UserDAO {
	
	@Autowired
	public SqlSessionTemplate sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lstoars.gifplus.dao.UserDAO#save(com.lstoars.gifplus.domian.UserDO)
	 */
	public void save(UserDO user) throws DAOException {
		try{
			sqlSession.insert("UserDAO.save", user);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.dao.UserDAO#queryById(java.lang.String)
	 */
	public UserDO queryById(String uId) throws DAOException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uId", uId);
		try{
			return sqlSession.selectOne("UserDAO.queryById", params);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.dao.UserDAO#queryUserPaged(int, int)
	 */
	public List<UserDO> queryUserPaged(int pageNum, int pageSize)
			throws DAOException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startRow", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		try{
			return sqlSession.selectList("UserDAO.queryUserPaged", params);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.dao.UserDAO#queryCountUser()
	 */
	public int queryCountUser() throws DAOException {
		try{
			return sqlSession.selectOne("UserDAO.queryCountUser");
		}catch(Exception e){
			throw new DAOException(e);
		}
	}

}
