package com.lstoars.gifplus.dao;

import java.util.List;

import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.DAOException;

public interface TagDAO {
	
	public List<TagDO> queryAllTag() throws DAOException;
}
