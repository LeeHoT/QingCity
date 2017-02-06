package com.qingcity.handler;

import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;

/**
 * CmdHandler 处理消息的Controller,根据gameHandlerMap抉择处理消息的类型。
 * 
 * @author leehot
 *
 */
public interface CmdHandler {
	public void handleMsg(MsgEntity msgEntity, GameResponse response) throws Exception;
}
