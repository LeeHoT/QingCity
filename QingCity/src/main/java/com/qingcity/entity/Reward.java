package com.qingcity.entity;

import java.io.Serializable;

/**
 * 存储所有奖励
 * 
 * @author Administrator
 *
 */

public class Reward implements Serializable {

	private static final long serialVersionUID = -6737533988601538145L;

	private Integer id;
	private String content;// 奖励内容

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
