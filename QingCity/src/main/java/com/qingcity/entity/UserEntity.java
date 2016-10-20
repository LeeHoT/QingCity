package com.qingcity.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 用户实体类User 存放用户基本信息，均不经常修改的内容
 * 
 * 对应users表
 * 
 * @author leehot(李慧婷)
 *
 */
@Repository(value="userEntity")
@Scope(value="prototype")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId; // 用户ID
	private String username; // 用户名
	private String passwordMd5; // 用户密码,用MD5 格式存储

	private String email;// 用户邮箱
	private String phone; // 用户电话号码
	private Long regTime; // 注册时间
	private String idCard;// 身份证号

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Long getRegTime() {
		return regTime;
	}

	public void setRegTime(Long regTime) {
		this.regTime = regTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordMd5() {
		return passwordMd5;
	}

	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
