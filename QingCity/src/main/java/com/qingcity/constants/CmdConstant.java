package com.qingcity.constants;

/**
 * 命令码常量检测
 */
public interface CmdConstant {
	
	short  
	
	        USER_LOGIN_CHECK = 104, // 登录验证
			USER_REGISTER_CHECK = 101, // 注册验证
			USER_ADD_EMAIL = 102, // 添加邮箱
			USER_ADD_IDCARD = 103, // 添加身份证号
			//ADD_PHONE = 104, // 添加电话号

			LOGIN_FAIL = 150, // 登陆失败
			REGISTER_FAIL = 151, // 注册失败
			ADD_EMAIL_FAIL = 152, // 添加邮箱失败
			ADD_IDCARD_FAIL = 153, // 添加身份证号失败
			ADD_PHONE_FAIL = 154, // 添加电话号失败

			LOGIN_SUCCESS = 1501, // 登陆成功
			REGISTER_SUCCESS = 1511, // 注册成功
			ADD_EMAIL_SUCCESS = 1521, // 添加电话号成功
			ADD_IDCARD_SUCCESS = 1531, // 添加身份证号成功
			ADD_PHONE__SUCCESS = 1541, // 添加电话号成功

			MESSAGE_ERROR = 1000;//消息有误
}
