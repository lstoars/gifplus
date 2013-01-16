package com.lstoars.gifplus.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lstoars.gifplus.dao.ImageDAO;
import com.lstoars.gifplus.dao.TagDAO;
import com.lstoars.gifplus.domian.ImageDO;
import com.lstoars.gifplus.domian.TagDO;
import com.lstoars.gifplus.exception.DAOException;
import com.lstoars.gifplus.exception.ServiceException;
import com.lstoars.gifplus.result.PageResult;
import com.lstoars.gifplus.service.ImageService;

public class ImageServiceImpl implements ImageService {

	private Logger logger = Logger.getLogger(this.getClass());

	private static Map<String, List<TagDO>> tagCache = new ConcurrentHashMap<String, List<TagDO>>();

	@Autowired
	private ImageDAO imageDAO;

	@Autowired
	private TagDAO tagDAO;

	@Override
	public PageResult<List<ImageDO>> queryImageList(int pageNum, int pageSize,
			int tagId ,String orderBy) throws ServiceException {
		PageResult<List<ImageDO>> result = new PageResult<List<ImageDO>>();
		List<ImageDO> images = null;
		try {
			images = imageDAO.queryImageList(pageNum, pageSize, tagId,orderBy);
			result.setValue(images);
			result.setTotalCount(imageDAO.queryImageCount(tagId));
			result.setCurrentPage(pageNum);
			result.setPageSize(pageSize);
		} catch (DAOException e) {
			result.setSuccess(false);
			logger.error("ImageService@queryImageList error", e);
			throw new ServiceException(e);
		}
		return result;
	}

	public static void clear() {
		tagCache.clear();
	}

	@Override
	public List<TagDO> queryTagList() throws ServiceException {
		List<TagDO> result = tagCache.get("ALL_TAG");
		if (CollectionUtils.isNotEmpty(result)) {
			return result;
		} else {
			try {
				result = tagDAO.queryAllTag();
			} catch (DAOException e) {
				logger.error("ImageService@queryTagList error", e);
				throw new ServiceException(e);
			}
		}
		return result;
	}

	@Override
	public int saveImage(ImageDO image) throws ServiceException {
		try {
			return imageDAO.save(image);
		} catch (DAOException e) {
			logger.error("ImageService@saveImage error", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int incrShareCount(long id) throws ServiceException {
		try {
			return imageDAO.incrShareCount(id);
		} catch (DAOException e) {
			logger.error("ImageService@incrShareCount error", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public ImageDO queryImageById(long imageId) {
		try {
			return imageDAO.queryImageById(imageId);
		} catch (DAOException e) {
			logger.error("ImageService@queryImageById error", e);
		}
		return null;
	}

}
