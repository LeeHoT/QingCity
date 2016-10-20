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
			COMMIT_RESULT= 2000,//提交游戏结果
			
			PAY_DIAMOND=2001,
			PAY_GOLD=2002,
			GET_ALL_MUSIC=2003,
			GET_SEASON_RANK=2004,
			GET_WEEK_RANK=2005,
			GET_DAY_RANK=2006,
			GET_SEASON_REWARD=2007,
			GET_COMMON_REWARD=2008,
			GET_INTERACT_REWARD=2009,//获取互动奖励
			GET_RIVAL_SCORE=2010,//获取对手成绩
			GET_PLAYSER_INFO=2011,
			DIAMOND_BUY_GOLD=2012,
			
	        
			
			

			ANY_FAIL=3000,

			LOGIN_SUCCESS = 1501, // 登陆成功
			REGISTER_SUCCESS = 1511, // 注册成功
			ADD_EMAIL_SUCCESS = 1521, // 添加电话号成功
			ADD_IDCARD_SUCCESS = 1531, // 添加身份证号成功
			ADD_PHONE__SUCCESS = 1541, // 添加电话号成功

			MESSAGE_ERROR = 1000;//消息有误
}
