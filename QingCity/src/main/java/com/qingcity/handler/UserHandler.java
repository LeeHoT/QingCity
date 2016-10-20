package com.qingcity.handler;

import javax.management.relation.RelationServiceNotRegisteredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.constants.CmdConstant;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.proto.GameMessage.UsersCheckResp;
import com.qingcity.proto.MessageError.Error;
import com.qingcity.service.UserService;
import com.qingcity.utils.ExceptionUtils;
import com.qingcity.utils.TimerUtil;

/**
 * 处理所有用户用户
 * 
 * 登录、注册、添加个人信息等请求
 * 
 * @author 李慧婷
 *
 */

public class UserHandler implements CmdHandler {
	protected Logger logger = LoggerFactory.getLogger(UserHandler.class);
	@Autowired
	private UserEntity userEntity;
	@Autowired
	private MsgEntity resEntity;// 回复信息

	@Autowired
	private UserService userService;

	public void handleMsg(MsgEntity msgEntity, GameResponse response) {
		// 消息错误,返回错误信息
		if (msgEntity.getMsgLength() != msgEntity.getData().length) {
			Error.Builder error = Error.newBuilder();
			error.setContent("消息出错");
			Error err = error.build();
			byte[] errText = err.toByteArray();
			resEntity.setMsgLength((short) errText.length);
			resEntity.setCmdCode(CmdConstant.MESSAGE_ERROR);
			resEntity.setData(errText);
			response.write(resEntity);
		}
		switch (msgEntity.getCmdCode()) {// 根据命令码对应找到对应处理方法
		case CmdConstant.USER_LOGIN_CHECK:
			logger.info("一位玩家请求登录");
			handlerLoginCheck(msgEntity, response);
			break;
		case CmdConstant.USER_REGISTER_CHECK:
			logger.info("有用户正在请求进行注册");
			System.out.println(msgEntity.getMsgLength());
			System.out.println(msgEntity.getData());
			System.out.println(msgEntity.getData().length);
			handleRegisterCheck(msgEntity, response);
			break;
		case CmdConstant.USER_ADD_EMAIL:
			logger.info("有用户在添加邮箱信息");
		case CmdConstant.USER_ADD_IDCARD:
			logger.info("有用户在进行实名制认证");
			// case CmdConstant.ADD_PHONE:
			// logger.info("有用户在添加电话信息");
		default:
			System.out.println("找不到对应的命令码");
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
	private void handlerLoginCheck(MsgEntity msgEntity, GameResponse response) {
		LoginCheck login;
		UsersCheckResp.Builder uCheck = UsersCheckResp.newBuilder();// 新建builder
		try {
			login = LoginCheck.parseFrom(msgEntity.getData());// 反序列化消息体
			userEntity.setUsername(login.getUsername());
			userEntity.setPasswordMd5(login.getPassword());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		if (validate(userEntity.getUsername(), userEntity.getPasswordMd5())) {
			System.out.println("用户名或密码格式错误，请重新输入");
			uCheck.setContent("登录失败，请核对用户名和密码后再登录");
			UsersCheckResp check = uCheck.build();
			byte[] b = check.toByteArray();
			resEntity.setCmdCode(CmdConstant.ANY_FAIL);
			resEntity.setData(b);	
		} else if (!userService.login(userEntity)) {
			System.out.println("登录失败");
			uCheck.setContent("登录失败，请核对用户名和密码后再登录");
			UsersCheckResp check = uCheck.build();
			byte[] b = check.toByteArray();
			resEntity.setCmdCode(CmdConstant.ANY_FAIL);
			resEntity.setData(b);
		} else {
			System.out.println("恭喜您" + userEntity.getUsername() + "成功登录");
			uCheck.setContent("登录失败，请核对用户名和密码后再登录");
			UsersCheckResp check = uCheck.build();
			byte[] b = check.toByteArray();
			resEntity.setCmdCode(CmdConstant.ANY_FAIL);
			resEntity.setData(b);
		}
		response.setRtMessage(resEntity);
	}

	/**
	 * 处理用户注册请求
	 * 
	 * @param msgEntity
	 * @param response
	 */

	private void handleRegisterCheck(MsgEntity msgEntity, GameResponse response) {
		UsersCheckResp.Builder uCheck = UsersCheckResp.newBuilder();
		byte[] b;
		String result = null;
		try {
			RegisterCheck register = RegisterCheck.parseFrom(msgEntity.getData());
			if (!validate(register.getUsername(), register.getPassword())) {
				System.out.println("用户名和密码格式不正确");
			} else if (!register.getPassword().trim().equals(register.getPassword2().trim())) {
				System.out.println("两次密码不一致");
			} else {
				userEntity.setUsername(register.getUsername());
				userEntity.setPasswordMd5(register.getPassword2());
				userEntity.setEmail(register.getEmail());
				userEntity.setRegTime(System.currentTimeMillis());
				result = userService.register(userEntity);
			}
		} catch (InvalidProtocolBufferException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		uCheck.setContent(result);
		UsersCheckResp u = uCheck.build();
		b = u.toByteArray();
		resEntity.setCmdCode(CmdConstant.ANY_FAIL);
		resEntity.setData(b);
		response.setRtMessage(b);

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

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public void setResEntity(MsgEntity resEntity) {
		this.resEntity = resEntity;
	}

	// private void handleNameCheck(MsgEntity msgEntity, List<MsgEntity>
	// commandList) {
	// // com.server.proto.demo.
	// NameCheckReq req = null;
	// try {// 按照与客户端约定,指定命令码使用指定的解码class解码
	// // 这里可以通过反射做成自动解码,参考:http://vincepeng.iteye.com/blog/2171310
	// req = NameCheckReq.parseFrom(msgEntity.getData());
	// } catch (InvalidProtocolBufferException e) {
	// System.out.println("protobuf解码错误");
	// e.printStackTrace();
	// return;
	// }
	// String name = req.getName();
	// if (name == null || name.isEmpty()) {
	// return;
	// }
	//
	// boolean isExist = ServerCache.CheckName(name);
	// if (!isExist) {// 如果没有存在/则模拟注册
	// ServerCache.addNewPlayer(name, msgEntity.getChannel());//
	// 由于是单线程操作,无需加锁.参考:实战1的第2条
	// }
	// NameCheckResp.Builder resp = NameCheckResp.newBuilder();
	// resp.setIsExist(isExist);
	// msgEntity.setData(resp.build().toByteArray());// 将原来的消息内容替换为回包,命令码无需变化
	// commandList.add(msgEntity);// 加入到发送数组
	// if (!isExist) {
	// SayHelloResp helloResp = SayHelloResp.newBuilder().setContent("欢迎" + name
	// + "的到来").setSpeaker("系统").build();
	// MsgEntity helloMsg = new MsgEntity();
	// helloMsg.setCmdCode(CmdConstant.SAY_HELLO);
	// helloMsg.setData(helloResp.toByteArray());
	// ServerCache.sendToAll(helloMsg);// 此操作开销较大,一般不要如此或者分离到其它服务器
	// }
	//
	// }
	//
	// private void handleSayHello(MsgEntity msgEntity, List<MsgEntity>
	// commandList) {
	// SayHelloReq req = null;
	// try {
	// req = SayHelloReq.parseFrom(msgEntity.getData());
	// } catch (InvalidProtocolBufferException e) {
	// System.err.println("protobuf解码错误");
	// e.printStackTrace();
	// return;
	// }
	// // 关键词过滤
	// // 发言频率检测
	// int playerId = ServerCache.get(msgEntity.getChannel());
	// PlayerEntity pe = ServerCache.getPlayerById(playerId);
	// if (pe != null) {
	// SayHelloResp resp =
	// SayHelloResp.newBuilder().setContent(req.getContent()).setSpeaker(pe.getName()).build();
	// msgEntity.setData(resp.toByteArray());
	// ServerCache.sendToAll(msgEntity);
	// } else {
	// System.err.println("玩家不存在");
	// }
	//
	// }

}
