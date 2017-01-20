package com.qingcity.chat.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qingcity.chat.domain.ChatMessageDispatcher;
import com.qingcity.chat.netty.NettyChatMsgDecoder;
import com.qingcity.chat.netty.NettyChatMsgEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
	private static final Logger logger = LoggerFactory.getLogger(ChatServerInitializer.class);
	@Autowired
	private ChatMessageDispatcher chatMessageDispatcher;
	@Autowired
	private String chatRequestType;

	public void init() {
		logger.info("init the ChatMessageDispatcher");
		new Thread(this.chatMessageDispatcher).start();
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		logger.info(" initChannel start ");
		ch.pipeline().addLast("protobufDecoder", new NettyChatMsgDecoder());
		ch.pipeline().addLast("protobufEncoder", new NettyChatMsgEncoder());
		ch.pipeline().addLast("chatHandler", new ChatServerHandler(this.chatMessageDispatcher));
		System.out.println("ChatClient:" + ch.remoteAddress() + "连接上");
	}

	public void setChatMessageDispatcher(ChatMessageDispatcher chatMessageDispatcher) {
		this.chatMessageDispatcher = chatMessageDispatcher;
	}

	public void setChatRequestType(String chatRequestType) {
		this.chatRequestType = chatRequestType;
	}

	public String getChatRequestType() {
		return chatRequestType;
	}
}
