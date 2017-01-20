package com.qingcity.test.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.sound.sampled.Port;

import com.qingcity.constants.ChatConstant;
import com.qingcity.constants.CmdConstant;
import com.qingcity.constants.PortConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.ChatProto.ChatMessage;

/**
 * 简单聊天服务器-客户端
 *
 * @author waylau.com
 * @date 2015-2-26
 */
public class SimpleChatClient {

	public static void main(String[] args) throws Exception {
		new SimpleChatClient("localhost", PortConstant.CHAT_PORT).run();
	}

	private final String host;
	private final int port;

	public SimpleChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
					.handler(new SimpleChatClientInitializer());
			Channel channel = bootstrap.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				ChatMessage.Builder chat = ChatMessage.newBuilder();

				chat.setUId(39);
				chat.setUsername("圣诞老人");
				String msg = in.readLine();
				chat.setContent(msg);
				chat.setTarget(ChatConstant.WORLD_MSG_CHANNEL);
				byte[] chatByte = chat.build().toByteArray();
				MsgEntity test = new MsgEntity();
				test.setMsgLength((short) chatByte.length);
				test.setCmdCode((short) ChatConstant.WORLD_MSG_CHANNEL);
				test.setData(chatByte);
				channel.writeAndFlush(test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}

}
