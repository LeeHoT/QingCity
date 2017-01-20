package com.qingcity.netty;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.constants.CmdConstant;
import com.qingcity.data.manager.PlayerChannelManager;
import com.qingcity.data.manager.PlayerManager;
import com.qingcity.dispatcher.HandlerDispatcher;
import com.qingcity.domain.ERequestType;
import com.qingcity.domain.GameRequest;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.KeepAlive.KeepAliveMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerAdapter extends SimpleChannelInboundHandler<MsgEntity> {

	private static final Logger logger = LoggerFactory.getLogger(ServerAdapter.class);

	// 心跳丢失计数器
	private AtomicInteger counter = new AtomicInteger();
	private HandlerDispatcher handlerDispatcher;

	public void setHandlerDispatcher(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public ServerAdapter(HandlerDispatcher handlerDispatcher) {
		this.handlerDispatcher = handlerDispatcher;
	}

	public ServerAdapter() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("[Client  OnLine]:" + ctx.channel().remoteAddress() + "上线");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.error("[Client OutLine]:" + ctx.channel().remoteAddress() + "掉线并将其移出服务器");
		PlayerChannelManager.removeChannel((SocketChannel) ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
		if (msg == null) {
			// 无消息内容
			return;
		}
		if (CmdConstant.PING == msg.getCmdCode()) {
			System.out.println("收到ping消息");
			// 客户端发来的PING消息，处理PING消息,并返回给客户端PONG。
			repPing(ctx,msg);

		} else {
			socketRequest(ctx, msg);
		}
		// 客户端有数据传输到服务端，更新channel时间

	}

	/**
	 * 回应客户端心跳检测
	 * 
	 * @param ctx
	 */
	private void repPing(ChannelHandlerContext ctx,MsgEntity msg) {
		KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
		keepAlive.setContent("服务器收到ping");
		MsgEntity repMsg = new MsgEntity();
		byte[] pingB = keepAlive.build().toByteArray();
		repMsg.setCmdCode(CmdConstant.REP_PING);
		repMsg.setData(pingB);
		repMsg.setMsgLength(pingB.length);
		ctx.writeAndFlush(repMsg);
	}

	/**
	 * 发送ping消息
	 * 
	 * @param ctx
	 */
	private void sendPing(ChannelHandlerContext ctx) {
		KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
		MsgEntity msg = new MsgEntity();
		byte[] pingB = keepAlive.build().toByteArray();
		msg.setCmdCode(CmdConstant.PING);
		msg.setData(pingB);
		msg.setMsgLength(pingB.length);
		ctx.writeAndFlush(msg);
	}

//	@Override
//	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//		if (evt instanceof IdleStateEvent) {
//			IdleStateEvent event = (IdleStateEvent) evt;
//			if (event.state().equals(IdleState.READER_IDLE)) {
//				// 未进行读操作
//				System.out.println("READER_IDLE");
//				// 如果未连接次数超过三次
//				// 超时关闭channel,等待client重连
//				ctx.close().sync();
//			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
//				// 未进行写操作
//				System.out.println("WRITER_IDLE");
//				// TO DO SOMETHING
//			} else if (event.state().equals(IdleState.ALL_IDLE)) {
//				// 未进行读写操作
//				System.out.println("ALL_IDLE");
//				sendPing(ctx);
//				// 发送心跳消息
//			}
//		}
//	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("[Client]: " + ctx.channel().remoteAddress() + "在" + new Date() + "发生连接异常");
		cause.printStackTrace();
		ctx.close();
	}

	private void socketRequest(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
		this.handlerDispatcher.addMessage(new GameRequest(ctx, ERequestType.SOCKET, msg));
	}
}
