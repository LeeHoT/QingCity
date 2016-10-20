package com.qingcity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qingcity.constants.CmdConstant;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.proto.MessageError.Error;
import com.qingcity.service.PlayerService;
import com.qingcity.service.UserService;


public class PlayerHandler implements CmdHandler {

	protected Logger logger = LoggerFactory.getLogger(UserHandler.class);
	@Autowired
	private UserEntity userEntity;
	@Autowired
	private MsgEntity resEntity;// 回复信息
	@Autowired
	private PlayerService playerService;
	
	@Override
	public void handleMsg(MsgEntity msgEntity, GameResponse response) throws Exception {
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
				case CmdConstant.GET_PLAYSER_INFO:
					logger.info("一位玩家请求登录");
					//handleLoginCheck(msgEntity, response);
					break;
				case CmdConstant.PAY_DIAMOND:
					logger.info("有用户正在请求进行注册");
					System.out.println(msgEntity.getMsgLength());
					System.out.println(msgEntity.getData());
					System.out.println(msgEntity.getData().length);
					//handleRegisterCheck(msgEntity, response);
					break;
				case CmdConstant.PAY_GOLD:
					logger.info("有用户在添加邮箱信息");
				case CmdConstant.DIAMOND_BUY_GOLD:
					logger.info("有用户在进行实名制认证");
//				case CmdConstant.ADD_PHONE:
//					logger.info("有用户在添加电话信息");
				default:
					System.out.println("找不到对应的命令码");
				}
		
	}
	
	public void handlerGetPlayerInfo(MsgEntity msgEntity,GameResponse resopnse){
		
	}
	
	public void handlerUpDatePlayerInfo(MsgEntity msgEntity,GameResponse response){
		
	}

}
