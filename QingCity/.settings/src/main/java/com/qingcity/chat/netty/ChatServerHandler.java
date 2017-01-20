package com.qingcity.chat.netty;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.chat.domain.ChatMessageDispatcher;
import com.qingcity.chat.domain.ChatMessageReq;
import com.qingcity.chat.group.WorldGroup;
import com.qingcity.constants.ChatConstant;
import com.qingcity.proto.ChatProto.ChatMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatServerHandler extends SimpleChannelInboundHandler<byte[]> {

	private static final Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);

	private ChatMessageDispatcher chatMessageDispatcher;

	public ChatServerHandler(ChatMessageDispatcher chatMessageDispatcher) {
		this.chatMessageDispatcher = chatMessageDispatcher;
	}

	/**
	 * 当有玩家发送消息时，
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();
		for (Channel channel : WorldGroup.worldChannels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		for (Channel channel : WorldGroup.worldChannels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("[ChatClient]: " + incoming.remoteAddress() + "在线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("[ChatClient]: " + incoming.remoteAddress() + "掉线");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
		logger.error("[ChatClient]: " + ctx.channel().remoteAddress() + "在" + new Date() + "发生连接异常");
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
		// 取出消息内容
		ChatMessage chatMsg = ChatMessage.parseFrom(bytes);
		System.out.println("发起聊天者的id " + chatMsg.getUId());
		System.out.println("发起聊天者姓名 " + chatMsg.getUsername());
		System.out.println("聊天目标 " + chatMsg.getTarget());
		System.out.println("聊天内容 " + chatMsg.getContent());
		if (ChatConstant.WORLD_MSG_CHANNEL == chatMsg.getTarget()) {
			System.out.println(chatMsg.getUsername() + "发起了世界频道聊天");

		} else if (ChatConstant.SOCIETY_MSG <= chatMsg.getTarget()) {
			System.out.println(chatMsg.getUsername() + "发起了公会频道聊天");
		} else {
			System.out.println("目标不存在,消息错误。");
		}
		sendMessage(ctx, chatMsg);
	}

	public void sendMessage(ChannelHandlerContext ctx, ChatMessage chatMsg) {
		this.chatMessageDispatcher.addMessage(new ChatMessageReq(ctx, chatMsg));
	}
}
