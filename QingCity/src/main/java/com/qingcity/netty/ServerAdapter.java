package com.qingcity.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.constants.CmdConstant;
import com.qingcity.constants.ProtocalType;
import com.qingcity.dispatcher.HandlerDispatcher;
import com.qingcity.domain.ERequestType;
import com.qingcity.domain.GameRequest;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.Login;
import com.qingcity.proto.Login.LoginRequest;
import com.qingcity.proto.UserProto.User;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerAdapter extends SimpleChannelInboundHandler<MsgEntity> {

	private static final Logger logger = LoggerFactory.getLogger(ServerAdapter.class);

	private HandlerDispatcher handlerDispatcher;
	@SuppressWarnings("unused")
	private int onLineNum = 0;

	/**
	 * 设置handlerDispatcher
	 * 
	 * @param handlerDispatcher
	 */
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
		// 在线人数加1
		onLineNum++;
		System.out.println(ctx + "连接服务器成功");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
		logger.info("服务器已收到消息");
		if (msg == null) {
			// 无消息内容
			return;
		}

		User user = User.parseFrom(msg.getData());
		System.out.println("用户id: "+user.getUId());
		System.out.println("用户name "+user.getUName());
		System.out.println("用户mima "+user.getUPassword());
		System.out.println("用户tili "+user.getUPower());
		System.out.println("用户zuanshi "+user.getUDiamond());
		System.out.println("用户jinbi "+user.getUGold());
		
		User.Builder users = user.newBuilder();
		users.setUId(56);
		users.setUName("mengrenjie");
		users.setUPassword("123123");
		users.setUDiamond(676);
		users.setUGold(456);
		users.setUPower(70);
		User us =users.build();
		byte[] b = us.toByteArray();
		MsgEntity remsg = new MsgEntity();
		remsg.setCmdCode(msg.getCmdCode());
		remsg.setMsgLength(b.length);
		remsg.setProtocalType(ProtocalType.PB_LUA);
		remsg.setData(b);
		
		// LoginRequest login;
		// System.out.println(msg.getData().length);
		// login = LoginRequest.parseFrom(msg.getData());
		// System.out.println(login);
		// System.out.println("id : " + login.getId());
		// System.out.println("email : " + login.getEmail());
		// System.out.println("name : " + login.getName());
		// LoginRequest.Builder repLogin = LoginRequest.newBuilder();
		// repLogin.setId(2000);
		// repLogin.setName("game");
		// repLogin.setEmail("jarjin@163.com");
		// LoginRequest lr = repLogin.build();
		// byte[] blr = lr.toByteArray();
		//
		// MsgEntity repMsg = new MsgEntity();
		// repMsg.setCmdCode(CmdConstant.LOGIN_CHECK);
		// repMsg.setProtocalType(msg.getProtocalType());
		// repMsg.setMsgLength(blr.length);
		// repMsg.setData(blr);
		ctx.writeAndFlush(remsg);
		//socketRequest(ctx, msg);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace();
	}

	private void socketRequest(ChannelHandlerContext ctx, MsgEntity msg) throws Exception {
		this.handlerDispatcher.addMessage(new GameRequest(ctx, ERequestType.SOCKET, msg));
	}
}
