package com.qingcity.netty;

import com.qingcity.entity.MsgEntity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 服务端这里继承<code>MessageToByteEncoder</code>更加方便
 */
public class NettyMsgEncoder extends MessageToByteEncoder<MsgEntity> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgEntity msg, ByteBuf byteBuf) throws Exception {
		int dataLength = msg.getData() == null ? 0 : msg.getData().length;
		byteBuf.ensureWritable(7+dataLength);	
		byteBuf.writeShort(7+dataLength);//写入消息总产度，为消息体长度+协议号长度+下次长度  占用2字节	
		byteBuf.writeShort(msg.getCmdCode());//写入协议号  有CmdConstants.CmdConstant指定  占用2字节	
		byteBuf.writeByte(msg.getProtocalType());
		byteBuf.writeInt(dataLength);//写入消息长度   占用2字节
		if (dataLength > 0) {
			byteBuf.writeBytes(msg.getData());//写入消息体，长度为dataLength
		}
	}

}
