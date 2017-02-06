package com.qingcity.chat.domain;

import com.qingcity.proto.ChatProto.ChatMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:31:07
 * @Description 聊天消息请求整理
 */
public class ChatMessageReq {

	private ChatMessage chatMsg;
	private Channel channel;
	private ChannelHandlerContext ctx;

	public ChatMessageReq(ChannelHandlerContext ctx, ChatMessage chatMsg) {
		try {
			this.chatMsg = chatMsg;
			this.ctx = ctx;
			this.channel = ctx.channel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChatMessage getChatMessage() {
		return chatMsg;
	}

	public void setChatMessage(ChatMessage obj) {
		this.chatMsg = obj;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

}
