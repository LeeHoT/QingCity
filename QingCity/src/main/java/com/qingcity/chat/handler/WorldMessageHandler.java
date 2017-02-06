package com.qingcity.chat.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.qingcity.chat.domain.ChatMessageReq;
import com.qingcity.constants.ChatConstant;
import com.qingcity.data.manager.WorldGroup;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.ChatProto.ChatMessage;
import com.qingcity.util.ExceptionUtils;

import io.netty.channel.group.ChannelGroup;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:23:24
 * @Description 处理世界消息
 */
@Controller
public class WorldMessageHandler implements ChatMessageHandler {

	private static Logger logger = LoggerFactory.getLogger(WorldMessageHandler.class);

	@Override
	public void process(ChatMessageReq msgReq) {
		ChannelGroup channels = WorldGroup.getInstance().getChannels(msgReq.getChatMessage().getTarget());
		if (channels == null) {
			// 世界频道不存在
			logger.error("在发布世界消息的时候，玩家已登录，却不存在世界频道");
			return;
		}
		sendMessage(msgReq);

	}

	@Override
	public void sendMessage(ChatMessageReq msgReq) {
		try {
			// 查到当前世界对应的所有玩家的channels
			ChannelGroup channels = WorldGroup.getInstance().getChannels(msgReq.getChatMessage().getTarget());
			// 获取发送过来的消息
			ChatMessage chatMessage = msgReq.getChatMessage();
			ChatMessage.Builder chat = ChatMessage.newBuilder();
			chat.setContent(chatMessage.getContent());
			chat.setUId(chatMessage.getUId());
			chat.setTarget(chatMessage.getTarget());
			chat.setUsername(chatMessage.getUsername());

			byte[] chatMsg = chat.build().toByteArray();
			MsgEntity worldRes = new MsgEntity();
			worldRes.setMsgLength(chatMsg.length);
			worldRes.setCmdCode((short) ChatConstant.WORLD_MSG);
			worldRes.setData(chatMsg);
			channels.writeAndFlush(worldRes);
		} catch (Exception e) {
			logger.error("在转发世界消息的时候发生了异常：" + ExceptionUtils.getStackTrace(e));
		}

	}

}
