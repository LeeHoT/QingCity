package com.qingcity.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.constants.ChatConstant;
import com.qingcity.constants.CmdConstant;
import com.qingcity.data.manager.PlayerChannelManager;
import com.qingcity.data.manager.PlayerManager;
import com.qingcity.data.manager.SocietyGroup;
import com.qingcity.data.manager.WorldGroup;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.proto.GameMessage.UsersCheckResp;
import com.qingcity.service.PlayerService;
import com.qingcity.service.UserService;
import com.qingcity.util.ExceptionUtils;
import com.qingcity.util.TimeUtil;

/**
 * 处理所有用户用户
 * 
 * 登录、注册、添加个人信息等请求
 * 
 * @author 李慧婷
 *
 */

@Controller("userHandler")
public class UserHandler extends HandlerMsg implements CmdHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

	@Autowired
	private UserEntity userEntity;

	private volatile String result;

	@Autowired
	private UserService userService;
	@Autowired
	private PlayerService playerService;

	public void handleMsg(MsgEntity msgEntity, GameResponse response) {
		if (isErrorMsg(msgEntity, response)) {
			logger.error("实际消息和原消息不符!长度不一致。");
			return;
		}
		switch (msgEntity.getCmdCode()) {// 根据命令码对应找到对应处理方法
		case CmdConstant.USER_LOGIN:
			logger.info("一位玩家请求登录");
			handlerLoginCheck(msgEntity, response);
			break;
		case CmdConstant.USER_REGISTER:
			logger.info("有用户正在请求进行注册");
			handleRegisterCheck(msgEntity, response);
			break;
		case CmdConstant.USER_ADD_EMAIL:
			logger.info("有用户在添加邮箱信息");
		case CmdConstant.USER_ADD_IDCARD:
			logger.info("有用户在进行实名制认证");
		default:
			logger.warn("命令码[{}]找不到", msgEntity.getCmdCode());
		}
	}

	/**
	 * 验证用户名和密码格式和内容是否符合最低标准
	 * 
	 * @param user
	 * @return
	 */
	public boolean validate(String username, String password) {
		boolean flag = true; // 用户是否已登录成功
		if (username.trim() == null && username.trim().length() <= 6) {
			// 用户名为空或用户名过短，无法登录
			System.out.println("用户名不能为空");
			flag = false;
		} else if (password.trim() == null) {
			// 密码为空，无法登录
			System.out.println("密码不能为空");
			flag = false;
		}
		return flag;
	}

	/**
	 * 处理用户登录请求
	 * 
	 * @param msgEntity
	 *            消息体 包含消息长度，协议号以及消息体
	 * @param response
	 *            返回消息对象
	 */
	private synchronized void handlerLoginCheck(MsgEntity msgEntity, GameResponse response) {
		LoginCheck login;
		UsersCheckResp.Builder uCheck = UsersCheckResp.newBuilder();// 新建builder
		try {
			login = LoginCheck.parseFrom(msgEntity.getData());// 反序列化消息体
			userEntity.setUsername(login.getUsername());
			userEntity.setPasswordMd5(login.getPassword());
		} catch (InvalidProtocolBufferException e) {
			logger.error("protobuf格式不正确");
			e.printStackTrace();
		}
		if (!validate(userEntity.getUsername(), userEntity.getPasswordMd5())) {
			System.out.println("用户名或密码格式错误，请重新输入");
			uCheck.setContent("登录失败，请核对用户名和密码格式后再登录");
			handlerResMsg(uCheck.build(), CmdConstant.ANY_FAIL, response);
			return;
		}
		int userId = userService.login(userEntity);
		if (userId < 0) {
			System.out.println("登录失败");
			uCheck.setContent("登录失败，请核对用户名和密码是否正确后再登录");
			handlerResMsg(uCheck.build(), CmdConstant.ANY_FAIL, response);
			return;
		} else {
			logger.info("恭喜您" + userEntity.getUsername() + "成功登录");
			uCheck.setContent("登录成功");
			handlerResMsg(uCheck.build(), CmdConstant.ANY_SUCCESS, response);
			// 处理登录成功的逻辑函数
			loginSuccess(userId, response);
		}
	}

	/**
	 * 登录成功
	 */
	public void loginSuccess(int userId, GameResponse response) {
		// 登录成功，记录玩家channel
		PlayerManager.getInstance().add(response.getChannel());
		PlayerChannelManager.getInstance().add(userId, response.getChannel());
		// 分配玩家频道
		// 查询玩家公会
		PlayerEntity player = playerService.getPlayerSocietyInfo(userId);
		SocietyGroup.getInstance().addToGroup(player.getSocietyId(), response.getChannel());
		System.out.println("公会" + player.getSocietyId() + "在线人数有"
				+ SocietyGroup.getInstance().getChannels(player.getSocietyId()).size());
		WorldGroup.getInstance().addToGroup(ChatConstant.WORLD_MSG, response.getChannel());
		System.out.println("世界频道在线人数有" + WorldGroup.getInstance().getChannels(ChatConstant.WORLD_MSG).size());
	}

	/**
	 * 处理用户注册请求
	 * 
	 * @param msgEntity
	 * @param response
	 */
	private synchronized void handleRegisterCheck(MsgEntity msgEntity, GameResponse response) {
		UsersCheckResp.Builder uCheck = UsersCheckResp.newBuilder();
		try {
			RegisterCheck register = RegisterCheck.parseFrom(msgEntity.getData());
			if (!validate(register.getUsername(), register.getPassword())) {
				System.out.println("用户名和密码格式不正确");
				// 处理protobuf消息
				uCheck.setContent("用户名和密码格式不正确");
				handlerResMsg(uCheck.build(), CmdConstant.ANY_FAIL, response);
			} else if (!register.getPassword().trim().equals(register.getPassword2().trim())) {
				System.out.println("两次密码不一致");
				// 处理protobuf消息
				uCheck.setContent("两次密码不一致");
				handlerResMsg(uCheck.build(), CmdConstant.ANY_FAIL, response);
			} else {
				userEntity.setUsername(register.getUsername());
				userEntity.setPasswordMd5(register.getPassword2());
				userEntity.setEmail(register.getEmail());
				userEntity.setRegTime(TimeUtil.Date2Timestamp(new Date()));
				userEntity.setCharged(false);
				result = userService.register(userEntity);
				// 处理protobuf消息
				uCheck.setContent(register.getUsername() + " " + result);
				logger.info("用户[{}]成功注册", register.getUsername());
				handlerResMsg(uCheck.build(), CmdConstant.ANY_SUCCESS, response);
			}

		} catch (InvalidProtocolBufferException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}

	/**
	 * 处理添加邮箱信息的请求
	 * 
	 * @param msgEntity
	 * @param response
	 */
	private void handlerAddEmail(MsgEntity msgEntity, GameResponse response) {

	}

	/**
	 * 处理添加电话号信息的请求
	 * 
	 * @param msgEntity
	 * @param response
	 */
	private void handlerAddPhone(MsgEntity msgEntity, GameResponse response) {

	}

	/**
	 * 处理用户实名认证的信息请求
	 * 
	 * @param msgEntity
	 * @param response
	 */
	private void handlerAddIdCard(MsgEntity msgEntity, GameResponse response) {

	}
}
