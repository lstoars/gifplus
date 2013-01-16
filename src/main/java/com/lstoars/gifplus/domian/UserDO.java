package com.lstoars.gifplus.domian;

import com.lstoars.gifplus.qplus.QOpenInfo;

public class UserDO {
	
	/**
	 * 自增主键
	 */
	private long id;
	
	/** 对应 qplus的pushid */
	private String pushId;
	
	/** 性别 */
    private String gender;
    
    /** 昵称 */
    private String nick;
    
    /** 现在还不知道干什么用的 */
    private String outh;
    
    /**
     * 创建时间
     */
    private String gmtCreate;
    
    public UserDO() {
		super();
	}

	public UserDO(String pushId,QOpenInfo info) {
		super();
		this.pushId = pushId;
		this.gender = info.getGender();
		this.nick = info.getNick();
		this.outh = info.getOuth();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getOuth() {
		return outh;
	}

	public void setOuth(String outh) {
		this.outh = outh;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
    
}
