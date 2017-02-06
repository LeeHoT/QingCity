package com.qingcity.chat.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.qingcity.chat.domain.ChatMessageReq;
import com.qingcity.constants.ChatConstant;
import com.qingcity.data.manager.SocietyGroup;
import com.qingcity.entity.MsgEntity;
import com.qingcity.handler.HandlerMsg;
import com.qingcity.proto.ChatProto.ChatMessage;
import com.qingcity.util.ExceptionUtils;

import io.netty.channel.group.ChannelGroup;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:23:40
 * @Description 处理公会内部消息
 */
@Controller
public class SocietyMessageHandler extends HandlerMsg implements ChatMessageHandler {

	private static Logger logger = LoggerFactory.getLogger(SocietyMessageHandler.class);

	@Override
	public void process(ChatMessageReq msgReq) {
		// 主要做一些验证工作
		// 验证公会频道是否存在
		ChannelGroup channels = SocietyGroup.getInstance().getChannels(msgReq.getChatMessage().getTarget());
		if (channels == null) {
			// 公会频道不存在
			logger.error("在发布公会消息的时候，玩家已登录，却不存在公会频道");
			return;
		}
		sendMessage(msgReq);
	}

	@Override
	public void sendMessage(ChatMessageReq msgReq) {
		// 查到当前公会对应的所有玩家的channels
		try {
			ChannelGroup channels = SocietyGroup.getInstance().getChannels(msgReq.getChatMessage().getTarget());
			// 获取发送过来的消息
			ChatMessage chatMessage = msgReq.getChatMessage();
			ChatMessage.Builder chat = ChatMessage.newBuilder();
			chat.setContent(chatMessage.getContent());
			chat.setUId(chatMessage.getUId());
			chat.setTarget(chatMessage.getTarget());
			chat.setUsername(chatMessage.getUsername());
			
			byte[] chatByte = chat.build().toByteArray();
			MsgEntity test = new MsgEntity();
			test.setMsgLength(chatByte.length);
			test.setCmdCode((short) ChatConstant.SOCIETY_MSG);
			test.setData(chatByte);
			channels.writeAndFlush(test);
		} catch (Exception e) {
			logger.error("在转发公会消息的过程中发生了异常：" + ExceptionUtils.getStackTrace(e));
		}

	}

}
