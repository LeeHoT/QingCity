
package com.qingcity.server;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.qingcity.constants.PortConstant;
import com.qingcity.netty.ServerInitializer;
import com.qingcity.util.NetUtil;

/**
 * NettyServer 启动程序，加载log4j和spring 配置文件,设定服务器端口,初始化Initializer
 * 
 * @author 李慧婷
 *
 */
public class NettyServerStart {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerStart.class);
	private static int port;

	public static ApplicationContext factory;

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {

		DOMConfigurator.configureAndWatch("src/main/resources/config/log4j.xml");
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		else {
			port = PortConstant.GAME_SERVER;

		}
		NetUtil net = new NetUtil();
		if (net.isLoclePortUsing(port)) {
			System.out.println("端口已被占用");
			return;
		}
		run();
	}

	private static void run() throws Exception {
		logger.info("load the spring config file");
		factory = new FileSystemXmlApplicationContext("classpath:config/applicationContext.xml");
		ServerInitializer initializer = (ServerInitializer) factory.getBean(ServerInitializer.class);
		// 设定Server 监听端口
		NettyServer server = new NettyServer(port);
		// 设定 initializer
		server.setInitializer(initializer);
		// 加载Pipeline 解码器 NettyMsgDecoder 编码器NettyMsgEncoder
		logger.info("GameServer Starting...");
		server.run();
	}
}