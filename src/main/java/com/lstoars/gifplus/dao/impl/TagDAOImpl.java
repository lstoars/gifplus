package com.lstoars.gifplus.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lstoars.gifplus.dao.TagDAO;
import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.DAOException;

public class TagDAOImpl implements TagDAO {
	
	@Autowired
	public SqlSessionTemplate sqlSession;

	@Override
	public List<TagDO> queryAllTag() throws DAOException{
		try{
			return sqlSession.selectList("TagDAO.queryAllTag");
		}catch(Exception e){
			throw new DAOException(e.getMessage(), e);
		}
	}

}
