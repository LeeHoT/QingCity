package com.qingcity.netty;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qingcity.dispatcher.HandlerDispatcher;
import com.qingcity.domain.ERequestType;
import com.qingcity.utils.ExceptionUtils;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	private static final Logger logger = LoggerFactory.getLogger(ServerInitializer.class);
	private static final int READ_IDEL_TIME_OUT = 120; // 读超时
	private static final int WRITE_IDEL_TIME_OUT = 15;// 写超时
	private static final int ALL_IDEL_TIME_OUT = 60; // 所有超时
	// 设定超时时间
	// private int timeout = 3600;
	@Autowired
	private HandlerDispatcher handlerDispatcher;
	// 消息请求类型
	private String requestType = ERequestType.SOCKET.getValue();

	public void init() {
		logger.info("init the HandlerDispatcher");
		new Thread(this.handlerDispatcher).start();
	}

	/**
	 * 初始化channel
	 */
	public void initChannel(SocketChannel ch) {
		try {
			if (ERequestType.SOCKET.getValue().equals(this.requestType.trim().toLowerCase())) {
				logger.info("init the channel of type : " + this.requestType.trim().toLowerCase());
				ch.pipeline().addLast("protobufDecoder", new NettyMsgDecoder());
				ch.pipeline().addLast("protobufEncoder", new NettyMsgEncoder());
				// ch.pipeline().addLast("timeout", new
				// ReadTimeoutHandler(this.timeout));
//				ch.pipeline().addLast("heartbeat", new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT,
//						ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
				ch.pipeline().addLast("handler", new ServerAdapter(this.handlerDispatcher));
			} else {
				logger.info("cannot inti the channel ,the type : " + this.requestType.trim()
						+ "cannot be find,please check the request type whether it is " + ERequestType.SOCKET.getValue()
						+ " !");
				return;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}

	}

	// public void setTimeout(int timeout) {
	// this.timeout = timeout;
	// }

	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestType() {
		return this.requestType;
	}
}