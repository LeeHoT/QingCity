package com.qingcity.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {

	private static NetUtil instance = new NetUtil();

	public static NetUtil getInstance() {
		return instance;
	}

	/***
	 * true:already in using false:not using
	 * 
	 * @param port
	 */
	public static boolean isLoclePortUsing(int port) {
		boolean flag = true;
		try {
			flag = isPortUsing("127.0.0.1", port);
		} catch (Exception e) {
		}
		return flag;
	}

	/***
	 * true:already in using false:not using
	 * 
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	public static boolean isPortUsing(String host, int port) throws UnknownHostException {
		boolean flag = false;
		InetAddress theAddress = InetAddress.getByName(host);
		Socket socket = null;
		try {
			socket = new Socket(theAddress, port);
			flag = true;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
