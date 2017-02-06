package com.qingcity.test.userTest;

import org.junit.Test;

import com.qingcity.constants.CmdConstant;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.GameMessage.LoginCheck;
import com.qingcity.proto.GameMessage.RegisterCheck;
import com.qingcity.test.netty.Initializer;
import com.qingcity.util.MD5Util;

import io.netty.channel.ChannelFuture;

public class ClientUserTest {

	public void init(Object obj) {
		Initializer init = Initializer.getInstance();
		try {
			init.initializer(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 登录测试
	 */

	
	public MsgEntity loginTest() {
		LoginCheck.Builder login = LoginCheck.newBuilder();
		login.setUsername("李慧婷");
		login.setPassword(MD5Util.getMD5Str("lht19941009"));
		LoginCheck lo = login.build();
		byte[] bTest = lo.toByteArray();
		MsgEntity test = new MsgEntity();
		test.setCmdCode(CmdConstant.USER_LOGIN);
		test.setData(bTest);
		init(bTest);
		return test;
	}

	/**
	 * 注册测试
	 */

	
	public MsgEntity registerTest() {
		RegisterCheck.Builder register = RegisterCheck.newBuilder();
		register.setUsername("金鸿轩");
		register.setPassword(MD5Util.getMD5Str("lht19950125"));
		register.setPassword2(MD5Util.getMD5Str("lht19950125"));
		register.setEmail("875269035@qq.com");
		RegisterCheck reg = register.build();
		byte[] bTest = reg.toByteArray();
		MsgEntity test = new MsgEntity();
		test.setCmdCode(CmdConstant.USER_REGISTER);
		test.setData(bTest);
		init(bTest);
		return test;
	}

	public static void main(String[] args) {
		ClientUserTest cut = new ClientUserTest();
		cut.registerTest();

	}
}
