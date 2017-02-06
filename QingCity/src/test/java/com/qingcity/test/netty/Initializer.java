package com.qingcity.test.netty;

import com.qingcity.netty.NettyMsgDecoder;
import com.qingcity.netty.NettyMsgEncoder;
import com.qingcity.test.clientNettyTest.ClientInboundHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Initializer {

	private static Initializer instance = new Initializer();

	public static Initializer getInstance() {
		return instance;
	}

	private static EventLoopGroup workerGroup = new NioEventLoopGroup();

	public ChannelFuture initializer(Object obj) throws Exception {
		try {

			Bootstrap b = new Bootstrap();
			b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast("frameDecoder", new NettyMsgDecoder());
					ch.pipeline().addLast("frameEncoder", new NettyMsgEncoder());
					ch.pipeline().addLast("handler", new ClientInboundHandler());
				}
			});
			ChannelFuture future = b.connect("127.0.0.1", 8080);
			future.channel().writeAndFlush(obj).sync();
			future.channel().close();
			return future;
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
