package com.qingcity.test.smsdk;

import com.qingcity.sdk.smsdk.MobClient;
import com.qingcity.sdk.smsdk.SmsWebApiKit;

/**
 * 
 * 测试
 * @author Administrator
 */
public class TestCase {

	public static void main(String[] args) throws Exception {
		System.out.println(new SmsWebApiKit("1b136566856fe").sendMsg("13552154299", "86"));
		String result = status();
		System.out.println(result);
		
	}
	
	
	/**
	 * 服务端运行状态
	 * @return
	 * @throws Exception
	 */
	public static String status() throws Exception{
		
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		try {
			client = new MobClient(address);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			return result;
		} finally {
			client.release();
		}
	}
	
	
}
