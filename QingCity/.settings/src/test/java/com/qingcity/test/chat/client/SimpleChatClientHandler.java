package com.qingcity.test.chat.client;

import com.qingcity.proto.ChatProto.ChatMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端 channel
 * 
 * @author waylau.com
 * @date 2015-2-26
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
		System.out.println("客户端收到服务器发送的消息了。");
		ChatMessage chatMsg = (ChatMessage) obj;
		System.out.println("消息是由" + chatMsg.getUsername() + "发来的");
		System.out.println("消息内容是 " + chatMsg.getContent());
	}
}
