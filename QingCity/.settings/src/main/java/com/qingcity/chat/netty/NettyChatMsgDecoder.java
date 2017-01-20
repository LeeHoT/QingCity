package com.qingcity.chat.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * 定长解码器
 */
public class NettyChatMsgDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * @param byteOrder
	 * @param maxFrameLength
	 *            字节最大长度,大于此长度则抛出异常
	 * @param lengthFieldOffset
	 *            开始计算长度位置,这里使用0代表放置到最开始
	 * @param lengthFieldLength
	 *            描述长度所用字节数 encode时使用的是short型
	 * @param lengthAdjustment
	 *            长度补偿,这里由于命令码使用2个字节.需要将原来长度计算加2
	 * @param initialBytesToStrip
	 *            开始计算长度需要跳过的字节数
	 * @param failFast
	 */
	public NettyChatMsgDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip,
				failFast);
	}

	public NettyChatMsgDecoder() {
		this(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 2, 0, 2, false);
	}

	/**
	 * 根据构造方法自动处理粘包,半包.然后调用此decode
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
		System.out.println("before decoder readableBytes :" + byteBuf.readableBytes());
		ByteBuf frame = (ByteBuf) super.decode(ctx, byteBuf);
		if (frame == null) {
			System.out.println("frame is " + frame);
			return null;
		}
		System.out.println("after decoder readableBytes :" + frame.readableBytes());
		// 聊天需要有的格式，
		short cmd = frame.readShort();// 先读取两个字节长度命令码
		byte protoType = frame.readByte();// 一个字节长度协议类型
		int msgLen = frame.readInt();// 再读取四个字节长度消息长
		System.out.println("协议号: " + cmd);// 目标id
		System.out.println("消息长度: " + msgLen);
		System.out.println("消息类型: " + protoType);
		System.out.println("数据消息的实际接收长度: " + frame.readableBytes());
		byte[] data = new byte[frame.readableBytes()];// 其它数据为实际数据
		frame.readBytes(data);
		if (msgLen != data.length) {
			System.out.println("消息实际长度和原始长度不一致。。请重新发送");
			return null;
		}
		// for (int i = 0; i < data.length; i++) {
		// System.out.print(data[i] + " ");
		// }
		return data;
	}

}
