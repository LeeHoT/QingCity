package com.qingcity.chat.server;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.qingcity.chat.netty.ChatServerInitializer;
import com.qingcity.constants.PortConstant;
import com.qingcity.server.NettyServerStart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatServer {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerStart.class);
	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	public static ApplicationContext factory;
	private ChatServerInitializer chatInitializer;
	private int port;

	public ChatServer(int port) {
		this.port = port;
	}

	public void setInitializer(ChatServerInitializer chatInitializer) {
		logger.info("setInitializer port :" + port);
		this.chatInitializer = chatInitializer;
	}

	public void run() throws Exception {
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(this.chatInitializer) // (4)
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			System.out.println("ChatServer 启动了");
			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync(); // (7)
			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("ChatServer 关闭了");
		}
	}

	public static void main(String[] args) throws Exception {
		DOMConfigurator.configureAndWatch("src/main/resources/config/log4j.xml");
		factory = new FileSystemXmlApplicationContext("classpath:config/applicationContext-chat.xml");
		ChatServerInitializer chatInitializer = (ChatServerInitializer) factory.getBean(ChatServerInitializer.class);
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = PortConstant.CHAT_PORT;
		}
		ChatServer chatServer = new ChatServer(port);
		chatServer.setInitializer(chatInitializer);
		logger.info("Starting ChatServer...");
		chatServer.run();

	}

}
