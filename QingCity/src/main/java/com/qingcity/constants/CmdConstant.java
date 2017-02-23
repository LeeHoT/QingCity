package com.qingcity.constants;

/**
 * 命令码常量检测
 */
public interface CmdConstant {

	short PING = 5000, // 心跳检测
			REP_PING = 5001, // 恢复心跳检测

			USER_LOGIN = 104, // 登录验证
			USER_REGISTER = 101, // 注册验证
			USER_ADD_EMAIL = 102, // 添加邮箱
			USER_ADD_IDCARD = 103, // 添加身份证号
			C2S_ADDDIAMOND = 105, // 添加电话号
			S2C_ADDDIAMOND = 106, PK_SEARCH = 201, // pk查找对手
			PK_COMPLETE = 202, // PK完成
			DRAW_A_LOTTERY = 203, // 抽奖

			COMMIT_RESULT = 2000, // 提交游戏结果

			PAY_DIAMOND = 2001, // 购买钻石
			PAY_GOLD = 2002, // 购买金币

			GET_ALL_MUSIC = 2003, // 获取所有音乐
			GET_SEASON_RANK = 2004, // 获取赛季榜单
			GET_WEEK_RANK = 2005, // 获取周榜单
			GET_DAY_RANK = 2006, // 获取每日榜单

			GET_SEASON_REWARD = 2007, // 获取赛季奖励
			GET_COMMON_REWARD = 2008, // 获取普通奖励
			GET_INTERACT_REWARD = 2009, // 获取互动奖励

			GET_RIVAL_SCORE = 2010, // 获取对手成绩
			SEARCH_ENERMY = 2013, GET_PLAYSER_INFO = 2011, DIAMOND_BUY_GOLD = 2012,

			ANY_SUCCESS = 3001, // 登陆成功
			ANY_FAIL = 3000,

			MESSAGE_ERROR = 1000;// 消息有误
}
