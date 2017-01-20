package com.qingcity.test.client.netty;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.GameMessage.UsersCheckResp;
import com.qingcity.proto.KeepAlive.KeepAliveMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @project: nettygame
 * @Title: ClientInboundHandler.java
 * @Package: cpgame.nettygame
 * @author: chenpeng
 * @email: 46731706@qq.com
 * @date: 2015年8月27日 上午9:48:49
 * @description:
 * @version:
 */
public class ClientInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		MsgEntity message = (MsgEntity) msg;
		UsersCheckResp uCheck;
		try {
			uCheck = UsersCheckResp.parseFrom(message.getData());
			if (message.getCmdCode() == CmdConstant.ANY_FAIL) {
				System.out.println("************" + uCheck.getContent());
			} else if (message.getCmdCode() == CmdConstant.ANY_SUCCESS) {
				System.out.println("++++++++++++" + uCheck.getContent());
			} else if (message.getCmdCode() == CmdConstant.REP_PING) {
				System.out.println("客户端收到服务器PING回应消息");
				System.out.println(msg);
			}
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if (msg instanceof HttpContent) {
		// HttpContent content = (HttpContent) msg;
		// ByteBuf buf = content.content();
		// System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
		// buf.release();
		// }
		// if (msg instanceof ByteBuf) {
		// ByteBuf messageData = (ByteBuf) msg;
		// int commandId = messageData.readInt();
		// int length = messageData.readInt();
		// byte[] c = new byte[length];
		// messageData.readBytes(c);
		// System.out.println("commandId:"+commandId+"\tmessage:"+new
		// String(c));
		// }
	}

	/**
	 * 回应客户端心跳检测
	 * 
	 * @param ctx
	 */
	private void repPing(ChannelHandlerContext ctx) {
		KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
		MsgEntity msg = new MsgEntity();
		byte[] pingB = keepAlive.build().toByteArray();
		msg.setCmdCode(CmdConstant.REP_PING);
		msg.setData(pingB);
		msg.setMsgLength(pingB.length);
		ctx.writeAndFlush(msg);
	}
}
