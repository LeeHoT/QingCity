package com.qingcity.test.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.netty.NettyMsgDecoder;
import com.qingcity.netty.NettyMsgEncoder;
import com.qingcity.proto.ChatProto.ChatMessage;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.proto.KeepAlive.KeepAliveMsg;
import com.qingcity.proto.PkMsg.PkInfo;
import com.qingcity.test.client.netty.ClientInboundHandler;
import com.qingcity.utils.MD5Util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ProtobufSocketClient {

	public static List<Channel> channelList = new ArrayList<Channel>();
	public static List<Channel> chatChannelList = new ArrayList<Channel>();

	private static final int READ_IDEL_TIME_OUT = 120; // 读超时
	private static final int WRITE_IDEL_TIME_OUT = 15;// 写超时
	private static final int ALL_IDEL_TIME_OUT = 60; // 所有超时

	private static EventLoopGroup workerGroup = new NioEventLoopGroup();

	private static int clientNum = 10;
	private static Bootstrap b;
	private static Channel ch;
	private static ScheduledExecutorService executorService;

	// 定义客户端没有收到服务端的pong消息的最大次数
	private static final int MAX_UN_REC_PONG_TIMES = 3;

	// 多长时间未请求后，发送心跳
	private static final int WRITE_WAIT_SECONDS = 5;

	// 隔N秒后重连
	private static final int RE_CONN_WAIT_SECONDS = 5;

	// 客户端连续N次没有收到服务端的pong消息 计数器
	private static int unRecPongTimes = 0;
	// 是否停止
	private static boolean isStop = false;

	public void connectServer() {
		b = new Bootstrap();
		b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast("frameDecoder", new NettyMsgDecoder());
				ch.pipeline().addLast("frameEncoder", new NettyMsgEncoder());
				ch.pipeline().addLast("heartbeat", new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT,
						ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
				ch.pipeline().addLast("handler", new ClientInboundHandler());
			}
		});

		for (int i = 0; i < clientNum; i++) {
			ChannelFuture future = b.connect("127.0.0.1", 8080);
			channelList.add(future.channel());
		}
		for (int i = 0; i < clientNum; i++) {
			ChannelFuture chatFuture = b.connect("127.0.0.1", 8090);
			chatChannelList.add(chatFuture.channel());
		}
	}

	public static void main(String[] args) {
		ProtobufSocketClient client = new ProtobufSocketClient();
		client.connectServer();
		ProtobufSocketClient.testLogin();

	}

	private static void connServer() {
		isStop = false;
		if (executorService != null) {
			executorService.shutdown();
		}
		executorService = Executors.newScheduledThreadPool(1);
		executorService.scheduleWithFixedDelay(new Runnable() {
			boolean isConnSucc = true;

			@Override
			public void run() {
				try {
					// 重置计数器
					unRecPongTimes = 0;
					// 连接服务端
					if (ch != null && ch.isOpen()) {
						ch.close();
					}
					ch = b.connect("127.0.0.1", 8080).sync().channel();
					// 此方法会阻塞
					// ch.closeFuture().sync();
					System.out.println("connect server finish");
				} catch (Exception e) {
					e.printStackTrace();
					isConnSucc = false;
				} finally {
					if (isConnSucc) {
						if (executorService != null) {
							executorService.shutdown();
						}
					}
				}
			}
		}, RE_CONN_WAIT_SECONDS, RE_CONN_WAIT_SECONDS, TimeUnit.SECONDS);
	}

	/**
	 * 登录测试
	 * 
	 * @throws InterruptedException
	 */

	public static List<byte[]> testLogin() {
		List<byte[]> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			LoginCheck.Builder login = LoginCheck.newBuilder();
			login.setUsername("lalala" + i);
			login.setPassword(MD5Util.getMD5Str("lht19941009"));
			byte[] bytes = login.build().toByteArray();
			MsgEntity test = new MsgEntity();
			test.setMsgLength((short) bytes.length);
			test.setCmdCode(CmdConstant.USER_LOGIN);
			test.setData(bytes);
			channelList.get(i).writeAndFlush(test);

		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 发送ping消息
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
				keepAlive.setContent("ping" + i + " " + j);
				byte[] pb = keepAlive.build().toByteArray();
				MsgEntity pmsg = new MsgEntity();
				pmsg.setCmdCode(CmdConstant.PING);
				pmsg.setData(pb);
				pmsg.setMsgLength(pb.length);
				channelList.get(i).writeAndFlush(pmsg);
			}
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// for (int i = 0; i < 10; i++) {
		// int tar = 9 - i;
		// ChatMessage.Builder chat = ChatMessage.newBuilder();
		// chat.setUId(61 + i);
		// chat.setUsername("李慧婷" + i);
		// chat.setTarget(70 - i);
		// chat.setContent("你好，李慧婷 " + tar + " ,我是李慧婷 " + i);
		// byte[] chatByte = chat.build().toByteArray();
		// MsgEntity test = new MsgEntity();
		// test.setMsgLength((short) chatByte.length);
		// test.setCmdCode(CmdConstant.USER_REGISTER);
		// test.setData(chatByte);
		// chatChannelList.get(i).writeAndFlush(test);
		// chatChannelList.get(i).closeFuture();
		// }

		return list;
	}

	/**
	 * 注册测试
	 */
	public static void register() {
		for (int i = 0; i < 10; i++) {
			RegisterCheck.Builder register = RegisterCheck.newBuilder();
			register.setUsername("lalala" + i);
			register.setPassword(MD5Util.getMD5Str("lht19941009"));
			register.setPassword2(MD5Util.getMD5Str("lht19941009"));
			register.setEmail("871s9fdsffsdfa" + i + "@qq.com");
			byte[] bytes = register.build().toByteArray();

			MsgEntity test = new MsgEntity();
			test.setMsgLength((short) bytes.length);
			test.setCmdCode(CmdConstant.USER_REGISTER);
			test.setData(bytes);
			channelList.get(i).writeAndFlush(test);
			channelList.get(i).closeFuture();
		}
	}

	/**
	 * pk测试
	 */
	public static List<byte[]> testPk() {
		List<byte[]> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			PkInfo.Builder pk = PkInfo.newBuilder();
			pk.setMusicId(1);
			pk.setPlayerId(17);
			byte[] bTest = pk.build().toByteArray();
			list.add(bTest);
		}
		return list;
	}

}
