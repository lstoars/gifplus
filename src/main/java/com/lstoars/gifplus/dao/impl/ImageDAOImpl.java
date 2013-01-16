package com.lstoars.gifplus.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lstoars.gifplus.dao.ImageDAO;
import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.exception.DAOException;

public class ImageDAOImpl implements ImageDAO {
	@Autowired
	public SqlSessionTemplate sqlSession;

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.dao.ImageDAO#queryImageList(int, int)
	 */
	public List<ImageDO> queryImageList(int pageNum, int pageSize, int tagId ,String orderBy)
			throws DAOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", tagId);
		params.put("startRow", (pageNum - 1) * pageSize);
		params.put("pageSize", pageSize);
		if(StringUtils.equals(orderBy, "hot")){
			params.put("columnName", "share_count");
		} else {
			params.put("columnName", "gmt_create");
		}
		try {
			return sqlSession.selectList("ImageDAO.queryImageList", params);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lstoars.gifplus.dao.ImageDAO#save()
	 */
	public int save(ImageDO image) throws DAOException {
		try {
			return sqlSession.insert("ImageDAO.save", image);
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public int queryImageCount(int tagId) throws DAOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", tagId);
		try {
			return sqlSession.selectOne("ImageDAO.queryImageCount", params);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public int incrShareCount(long id) throws DAOException {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		try{
			return sqlSession.update("ImageDAO.incrShareCount", params);
		} catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public ImageDO queryImageById(long imageId) throws DAOException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", imageId);
		try{
			return sqlSession.selectOne("ImageDAO.queryImageById", params);
		} catch(Exception e) {
			throw new DAOException(e);
		}
	}

}
