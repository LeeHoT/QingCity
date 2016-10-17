package cpgame.demo;

import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.netty.NettyMsgDecoder;
import com.qingcity.netty.NettyMsgEncoder;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.utils.MD5Util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProtobufSocketClient {

	private static EventLoopGroup workerGroup = new NioEventLoopGroup();

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		try {

			Bootstrap b = new Bootstrap();
			b.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {

					ch.pipeline().addLast("frameDecoder", new NettyMsgDecoder());
					ch.pipeline().addLast("frameEncoder", new NettyMsgEncoder());
					ch.pipeline().addLast("handler", new ClientInboundHandler());

				}
			});
			ChannelFuture future = b.connect("127.0.0.1", 8080);
			MD5Util md = new MD5Util();
			
			/**
			 * 登录测试
			*/
//			LoginCheck.Builder login = LoginCheck.newBuilder();
//			login.setUsername("李慧婷2");
//			login.setPassword(md.getMD5Str("lht19941009"));
//			LoginCheck lo = login.build();
//			byte[] bTest = lo.toByteArray();
//			 
			/**
			 * 注册测试
			 */
//			
			RegisterCheck.Builder register = RegisterCheck.newBuilder();
			register.setUsername("李慧婷2");
			register.setPassword(md.getMD5Str("lht19941009"));
			register.setPassword2(md.getMD5Str("lht19941009"));
			register.setEmail("875269031@qq.com");
			RegisterCheck reg = register.build();
			byte[] bTest = reg.toByteArray();
			System.out.println(bTest.length);
			MsgEntity test = new MsgEntity();
			test.setMsgLength((short)bTest.length);
			test.setCmdCode(CmdConstant.USER_REGISTER_CHECK);
			test.setData(bTest);
			future.channel().writeAndFlush(test).sync();
			future.channel().closeFuture().sync();

		} finally {
			workerGroup.shutdownGracefully();

		}

	}

}
