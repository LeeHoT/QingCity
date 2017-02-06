package com.qingcity.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.qingcity.chat.domain.ChatMessageReq;
import com.qingcity.chat.domain.ChatMsgQueue;
import com.qingcity.chat.handler.ChatMessageHandler;
import com.qingcity.constants.ChatConstant;
import com.qingcity.util.ExceptionUtils;

import io.netty.channel.Channel;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:33:14
 * @Description 聊天消息拦截器，主要处理对世界消息和公会消息的分发
 */
@Controller
public class ChatMessageDispatcher implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageDispatcher.class);

	private static final Integer MESSAGE_QUEUE_LIMIT = 10;
	@Autowired
	private ChatMessageHandler societyMessageHandler;
	@Autowired
	private ChatMessageHandler worldMessageHandler;
	private final Map<Integer, ChatMsgQueue> sessionMsgQ;
	private Executor messageExecutor;
	private boolean running;
	private long sleepTime;
	private volatile int msgNum = 0;// 消息队列中未处理消息数

	public ChatMessageDispatcher() {
		this.sessionMsgQ = new ConcurrentHashMap<Integer, ChatMsgQueue>();
		this.running = true;
		this.sleepTime = 200L;
	}

	public void setMessageExecutor(Executor messageExecutor) {
		this.messageExecutor = messageExecutor;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void addMessageQueue(Integer channelId, ChatMsgQueue messageQueue) {
		this.sessionMsgQ.put(channelId, messageQueue);
	}

	public Map<Integer, ChatMsgQueue> getMessageQueue() {
		return this.sessionMsgQ;
	}

	public void removeMessageQueue(Channel channel) {
		ChatMsgQueue queue = (ChatMsgQueue) this.sessionMsgQ.remove(channel);
		if (queue != null)
			queue.clear();
	}

	/**
	 * 添加消息进入队列
	 * 
	 * @param request
	 *            游戏请求 com.qingcity.chat.domain.ChatMessageReq
	 */
	public void addMessage(ChatMessageReq request) {
		try {
			ChatMsgQueue chatQueue = (ChatMsgQueue) this.sessionMsgQ
					.get(Integer.valueOf(request.getChatMessage().getTarget()));
			if (chatQueue == null) {
				// 消息为空，创建新的消息队列
				chatQueue = new ChatMsgQueue(new ConcurrentLinkedQueue<ChatMessageReq>());
				this.sessionMsgQ.put(Integer.valueOf(request.getChatMessage().getTarget()), chatQueue);
				chatQueue.add(request);// 添加请求消息进入队列
				msgNum++;// 消息增加一条
				logger.debug("添加消息进入消息队列，当前队列中有" + msgNum + "条消息!");
			} else {
				if (chatQueue.size() > MESSAGE_QUEUE_LIMIT) {
					// 消息超过队列上限，，直接丢弃
					return;
				}
				chatQueue.add(request);// 添加消息进入队列
				msgNum++;
				logger.debug("添加消息进入消息队列，当前队列中有" + msgNum + "条消息!");
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				for (ChatMsgQueue chatQueue : sessionMsgQ.values())
					if ((chatQueue != null) && (chatQueue.size() > 0) && (!chatQueue.isRunning())) {
						MessageWorker messageWorker = new MessageWorker(chatQueue);
						this.messageExecutor.execute(messageWorker);
					}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
			// 等待200毫秒后继续
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	public void stop() {
		this.running = false;
	}

	public ChatMsgQueue getUserMessageQueue(Channel channel) {
		return (ChatMsgQueue) this.sessionMsgQ.get(channel);
	}

	private final class MessageWorker implements Runnable {
		private final ChatMsgQueue chatQueue;
		private ChatMessageReq request;

		private MessageWorker(ChatMsgQueue chatQueue) {
			chatQueue.setRunning(true);
			this.chatQueue = chatQueue;
			this.request = ((ChatMessageReq) chatQueue.getRequestQueue().poll());
		}

		public void run() {
			try {
				// 处理消息队列中的消息
				msgNum--;
				handMessageQueue();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				this.chatQueue.setRunning(false);
			}
		}

		private void handMessageQueue() {
			try {
				if (ChatConstant.WORLD_MSG == request.getChatMessage().getTarget()) {
					// 处理世界消息
					worldMessageHandler.process(request);
				} else if (ChatConstant.SOCIETY_MSG <= request.getChatMessage().getTarget()) {
					// 处理公会消息
					societyMessageHandler.process(request);
				} else {
					logger.warn("目标[{}]找不到", request.getChatMessage().getTarget());
				}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}
}
