package com.qingcity.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.data.manager.PlayerChannelManager;
import com.qingcity.data.manager.PlayerManager;

import io.netty.channel.socket.SocketChannel;

public class CheckChannelStatusTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(CheckChannelStatusTask.class);
	public static final Long CLIENT_OUTLINE_TIME = 30000L;// 5分钟

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		/**
		 * 轮询lastPingTime,时间超过一定值之后则视为掉线。直接踢掉。
		 */
		for (Map.Entry entry : PlayerManager.lastPingTime.entrySet()) {
			long time = (long) entry.getValue();
			if (System.currentTimeMillis() - time >= CLIENT_OUTLINE_TIME) {
				logger.warn("channel:[{}] had outline,it's time to kick off", entry.getKey());
				// 将玩家移出ping装置
				PlayerManager.lastPingTime.remove(entry.getKey());
				// 将玩家移出channel列表
				PlayerChannelManager.removeChannel((SocketChannel) entry.getKey());
			}
			
		}
	}

	// public static void main(String[] args) {
	// ExecutorServiceUtil.run(new CheckChannelStatusTask(), 0,
	// CheckChannelStatusTask.CLIENT_OUTLINE_TIME,
	// TimeUnit.MILLISECONDS);
	// }

}
