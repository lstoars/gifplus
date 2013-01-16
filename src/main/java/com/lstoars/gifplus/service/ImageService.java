package com.lstoars.gifplus.service;

import java.util.List;

import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.result.PageResult;

public interface ImageService {
	
	/**
	 * 分页查询image
	 * @param pageNum
	 * @param tagId
	 * @return
	 */
	public PageResult<List<ImageDO>> queryImageList(int pageNum,int pageSize,int tagId ,String orderBy) throws ServiceException;
	
	public ImageDO queryImageById(long imageId);
	
	/**
	 * 查询tag 带map 缓存
	 * @return
	 * @throws ServiceException
	 */
	public List<TagDO> queryTagList() throws ServiceException; 
	
	/**
	 * 保存image
	 * @param image
	 * @return 记录ID
	 * @throws ServiceException
	 */
	public int saveImage(ImageDO image) throws ServiceException;
	
	/**
	 * 增加分享次数
	 * @return
	 * @throws ServiceException
	 */
	public int incrShareCount(long id) throws ServiceException;
}
