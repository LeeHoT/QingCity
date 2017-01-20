package com.qingcity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.qingcity.constants.CmdConstant;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.service.PlayerService;

@Controller("playerHandler")
public class PlayerHandler extends HandlerMsg implements CmdHandler {
	private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);

	@Autowired
	private UserEntity userEntity;
	@Autowired
	private MsgEntity resEntity;// 回复信息
	@Autowired
	private PlayerService playerService;

	@Override
	public void handleMsg(MsgEntity msgEntity, GameResponse response) throws Exception {
		if (isErrorMsg(msgEntity, response)) {
			return;
		}
		switch (msgEntity.getCmdCode()) {// 根据命令码对应找到对应处理方法
		case CmdConstant.GET_PLAYSER_INFO:
			logger.info("获取玩家信息");
			// handleLoginCheck(msgEntity, response);
			break;
		case CmdConstant.PAY_DIAMOND:
			logger.info("有用户正在请求(软妹币)购买钻石)");
			System.out.println(msgEntity.getMsgLength());
			System.out.println(msgEntity.getData());
			System.out.println(msgEntity.getData().length);
			// handleRegisterCheck(msgEntity, response);
			break;
		case CmdConstant.PAY_GOLD:
			logger.info("有用户正在请求(软妹币)购买金币");
		case CmdConstant.DIAMOND_BUY_GOLD:
			logger.info("有用户在请求钻石购买金币");
		default:
			System.out.println("找不到对应的命令码" + msgEntity.getCmdCode());
		}

	}

	public void handlerGetPlayerInfo(MsgEntity msgEntity, GameResponse resopnse) {

	}

	public void handlerUpDatePlayerInfo(MsgEntity msgEntity, GameResponse response) {

	}
}
