package com.qingcity.test.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import com.qingcity.constants.ChatConstant;
import com.qingcity.constants.CmdConstant;
import com.qingcity.constants.PortConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.netty.NettyMsgDecoder;
import com.qingcity.netty.NettyMsgEncoder;
import com.qingcity.proto.ChatProto.ChatMessage;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.proto.KeepAlive.KeepAliveMsg;
import com.qingcity.proto.Lottery.C2S_Lottery;
import com.qingcity.proto.PkMsg.PkInfo;
import com.qingcity.test.client.netty.ClientInboundHandler;
import com.qingcity.util.MD5Utils;

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

	private static int clientNum = 20;
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
			ChannelFuture future = b.connect("127.0.0.1", PortConstant.GAME_SERVER);
			channelList.add(future.channel());
		}
	}

	public static void main(String[] args) {
		ProtobufSocketClient client = new ProtobufSocketClient();
		client.connectServer();
<<<<<<< HEAD
		ProtobufSocketClient.lottery();
=======
		ProtobufSocketClient.register();
>>>>>>> 5c0e5bc843bdb11b1826aeb31ab6f881df5aeb17

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
		System.out.println(System.currentTimeMillis() / 1000);
		for (int j = 0; j < 1000; j++) {
			for (int i = 0; i < 10; i++) {
				LoginCheck.Builder login = LoginCheck.newBuilder();
				login.setUsername("李慧婷" + i);
				login.setPassword(MD5Utils.getMD5("lht19941009"));
				byte[] bytes = login.build().toByteArray();
				MsgEntity test = new MsgEntity();
				test.setMsgLength((short) bytes.length);
				test.setCmdCode(CmdConstant.USER_LOGIN);
				test.setData(bytes);
				channelList.get(i).writeAndFlush(test);
			}
		}
		System.out.println(System.currentTimeMillis() / 1000);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// // 发送ping消息
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 10; j++) {
		// KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
		// keepAlive.setContent("ping" + i + " " + j);
		// byte[] pb = keepAlive.build().toByteArray();
		// MsgEntity pmsg = new MsgEntity();
		// pmsg.setCmdCode(CmdConstant.PING);
		// pmsg.setData(pb);
		// pmsg.setMsgLength(pb.length);
		// channelList.get(i).writeAndFlush(pmsg);
		// }
		// }
		for (int i = 1; i <= 2; i++) {
			System.out.println("------------");
			ChatMessage.Builder chat = ChatMessage.newBuilder();
			chat.setUId(60 + i);
			chat.setUsername("李慧婷" + i);
			chat.setTarget(i);
			chat.setContent("公会里的各位你们好,我是李慧婷 " + i);
			byte[] chatByte = chat.build().toByteArray();
			MsgEntity test = new MsgEntity();
			test.setMsgLength(chatByte.length);
			test.setCmdCode((short) ChatConstant.SOCIETY_MSG);
			test.setData(chatByte);
			channelList.get(i).writeAndFlush(test);
		}
		// 发送ping消息
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 10; j++) {
		// KeepAliveMsg.Builder keepAlive = KeepAliveMsg.newBuilder();
		// keepAlive.setContent("ping" + i + " " + j);
		// byte[] pb = keepAlive.build().toByteArray();
		// MsgEntity pmsg = new MsgEntity();
		// pmsg.setCmdCode(CmdConstant.PING);
		// pmsg.setData(pb);
		// pmsg.setMsgLength(pb.length);
		// channelList.get(i).writeAndFlush(pmsg);
		// }
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// }
		// for (int i = 1; i <= 2; i++) {
		// System.out.println("------------");
		// ChatMessage.Builder chat = ChatMessage.newBuilder();
		// chat.setUId(60 + i);
		// chat.setUsername("李慧婷" + i);
		// chat.setTarget(ChatConstant.WORLD_MSG);
		// chat.setContent("世界里的各位你们好,我是李慧婷 " + i);
		// byte[] chatByte = chat.build().toByteArray();
		// MsgEntity test = new MsgEntity();
		// test.setMsgLength(chatByte.length);
		// test.setCmdCode((short) ChatConstant.WORLD_MSG);
		// test.setData(chatByte);
		// channelList.get(i).writeAndFlush(test);
		// }
		return list;
	}

	public static void lottery() {
		C2S_Lottery.Builder lottery = C2S_Lottery.newBuilder();
		lottery.setUserId(39);
		lottery.setType(3);
		byte[] bytes = lottery.build().toByteArray();
		MsgEntity test = new MsgEntity();
		test.setMsgLength((short) bytes.length);
		test.setCmdCode(CmdConstant.DRAW_A_LOTTERY);
		test.setData(bytes);
		channelList.get(1).writeAndFlush(test);
		channelList.get(1).closeFuture();
	}

	/**
	 * 注册测试
	 */
	public static void register() {
		for (int i = 0; i < 2; i++) {
			RegisterCheck.Builder register = RegisterCheck.newBuilder();
			register.setUsername("llal李慧婷5");
<<<<<<< HEAD
			register.setPassword(MD5Utils.getMD5("lht19941009"));
			register.setPassword2(MD5Utils.getMD5("lht19941009"));
=======
			register.setPassword(MD5Util.getMD5Str("lht19941009"));
			register.setPassword2(MD5Util.getMD5Str("lht19941009"));
>>>>>>> 5c0e5bc843bdb11b1826aeb31ab6f881df5aeb17
			register.setEmail("871s9sqw545efd" + i + "@qq.com");
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
