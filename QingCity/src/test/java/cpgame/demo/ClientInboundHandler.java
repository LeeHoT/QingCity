package cpgame.demo;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.GameMessage.UsersCheckResp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

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
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MsgEntity message = (MsgEntity) msg;
		UsersCheckResp uCheck = UsersCheckResp.parseFrom(message.getData());
		System.out.println(message.getCmdCode());
		if (message.getCmdCode() == CmdConstant.LOGIN_FAIL) {
			System.out.println(uCheck.getContent());
		} else if(message.getCmdCode()==CmdConstant.REGISTER_FAIL){
			System.out.println(uCheck.getContent());
		}else if(message.getCmdCode()==CmdConstant.REGISTER_SUCCESS){
			
			System.out.println(uCheck.getContent());
			System.err.println("注册成功啦");
		}else{
			System.out.println(msg);
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
