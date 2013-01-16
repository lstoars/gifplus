package com.lstoars.gifplus.result;

import java.io.Serializable;

public class PageResult<T> implements Serializable{

	private static final long serialVersionUID = -437477738135978652L;
	
	private boolean success = true;
	
	private int  totalCount;
	
	private int currentPage = 1;
	
	private int pageSize = 5;
	
	private T value;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
				/ pageSize + 1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
