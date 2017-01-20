package com.qingcity.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.qingcity.domain.ERequestType;
import com.qingcity.netty.ServerInitializer;
import com.qingcity.task.CheckChannelStatusTask;
import com.qingcity.utils.ExecutorServiceUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	private Logger logger = LoggerFactory.getLogger(NettyServer.class);

	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
	/** 业务出现线程大小 */
	protected static final int BIZTHREADSIZE = 4;

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

	private ServerInitializer initializer;
	private final int port;

	public NettyServer(int port) {
		this.logger.info("register port :" + port);
		this.port = port;
	}

	public void setInitializer(ServerInitializer initializer) {
		this.logger.info("setInitializer port :" + port);
		this.initializer = initializer;
	}

	public void run() throws Exception {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(this.initializer)
					.option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.SO_BACKLOG, 128);
			ChannelFuture cf = null;
			this.logger.info(ERequestType.parse(this.initializer.getRequestType()).getValue()
					+ " server started at port " + this.port + '.');

			if (ERequestType.HTTP.equals(ERequestType.parse(this.initializer.getRequestType()))) {
				this.logger.info("the server initialize request method is : " + this.initializer.getRequestType());
				// 绑定端口 port
				cf = b.bind(this.port).sync();
			} else {
				this.logger
						.info("the socketServer initialize request method is : " + this.initializer.getRequestType());
				// 绑定端口 port
				cf = b.bind(new InetSocketAddress(this.port)).sync();
			}
			ExecutorServiceUtil.run(new CheckChannelStatusTask(), 0, CheckChannelStatusTask.CLIENT_OUTLINE_TIME,
					TimeUnit.MILLISECONDS);
			cf.channel().closeFuture().sync();
		} finally {
			// 温柔的关闭 boss worker
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
