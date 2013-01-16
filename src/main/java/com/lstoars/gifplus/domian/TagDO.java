package com.lstoars.gifplus.domian;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TagDO {
	
	private int id;
	
	private String tagName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
