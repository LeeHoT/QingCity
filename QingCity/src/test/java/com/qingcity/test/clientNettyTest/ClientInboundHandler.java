package com.qingcity.test.clientNettyTest;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.GameMessage.UsersCheckResp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		System.out.println("客户端接收到服务器端的消息啦");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("怎么没有消息呢");
		MsgEntity message = (MsgEntity) msg;
		System.out.println(message.getCmdCode());
		UsersCheckResp uCheck ;
		try {
			uCheck = UsersCheckResp.parseFrom(message.getData());
			System.out.println(message.getCmdCode());
			if (message.getCmdCode() == CmdConstant.ANY_FAIL) {
				System.out.println("好可惜。。。。失败了。");
				System.out.println("************"+uCheck.getContent());
			} else if (message.getCmdCode() == CmdConstant.ANY_SUCCESS) {
				System.err.println("成功啦");
				System.out.println("************"+uCheck.getContent());
			} else {
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
}
