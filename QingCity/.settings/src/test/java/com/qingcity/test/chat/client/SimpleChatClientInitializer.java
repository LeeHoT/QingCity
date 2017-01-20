package com.qingcity.test.chat.client;

import com.qingcity.chat.netty.NettyChatMsgDecoder;
import com.qingcity.chat.netty.NettyChatMsgEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端 ChannelInitializer
 * 
 * @author waylau.com
 * @date 2015-2-26
 */
public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("protobufDecoder", new NettyChatMsgDecoder());
		ch.pipeline().addLast("protobufEncoder", new NettyChatMsgEncoder());
		ch.pipeline().addLast("handler", new SimpleChatClientHandler());
	}
}
