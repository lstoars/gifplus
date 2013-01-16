package com.lstoars.gifplus.dao;

import java.util.List;

import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.exception.DAOException;
import com.lstoars.gifplus.exception.ServiceException;

public interface ImageDAO {
	
	/**
	 * 分页查询image
	 * @param pageNum
	 * @param tagId
	 * @return
	 * @throws DAOException
	 */
	public List<ImageDO> queryImageList(int pageNum,int pageSize, int tagId ,String orderBy)
			throws DAOException;
	
	/**
	 * 查询image 总数
	 * @param tagId
	 * @return
	 */
	public int queryImageCount(int tagId) throws DAOException;
	
	/**
	 * ����id ��ѯͼƬ
	 * @param imageId
	 * @return
	 * @throws DAOException
	 */
	public ImageDO queryImageById(long imageId) throws DAOException;
	
	/**
	 * 查询image
	 * @param image
	 * @return
	 * @throws DAOException
	 */
	public int save(ImageDO image) throws DAOException;
	
	/**
	 * 增加分享次数
	 * @return
	 * @throws ServiceException
	 */
	public int incrShareCount(long id) throws DAOException;
	
}
