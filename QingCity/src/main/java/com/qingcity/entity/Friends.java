package com.qingcity.entity;

import java.io.Serializable;

/**
 * 
 * @author leehotin
 * @Date 2017年2月17日 上午10:21:58
 * @Description friend实体类
 */
public class Friends implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer user1;
	private Integer user2;

	private Boolean isFriend;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser1() {
		return user1;
	}

	public void setUser1(Integer user1) {
		this.user1 = user1;
	}

	public Integer getUser2() {
		return user2;
	}

	public void setUser2(Integer user2) {
		this.user2 = user2;
	}

	public Boolean getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(Boolean isFriend) {
		this.isFriend = isFriend;
	}
}