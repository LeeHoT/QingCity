package com.qingcity.chat.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.utils.ExceptionUtils;

import io.netty.channel.Channel;

public class ChatMessageDispatcher implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageDispatcher.class);

	private static final Integer MESSAGE_QUEUE_LIMIT = 10;

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
	 *            游戏请求 com.qingcity.domain.GameRequest
	 */
	public void addMessage(ChatMessageReq request) {
		try {
			logger.info("添加消息进入消息队列，当前队列中有" + msgNum + "条消息!");
			ChatMsgQueue chatQueue = (ChatMsgQueue) this.sessionMsgQ
					.get(Integer.valueOf(request.getChatMessage().getTarget()));
			if (chatQueue == null) {
				// 消息为空，创建新的消息队列
				chatQueue = new ChatMsgQueue(new ConcurrentLinkedQueue<ChatMessageReq>());
				this.sessionMsgQ.put(Integer.valueOf(request.getChatMessage().getTarget()), chatQueue);
				chatQueue.add(request);// 添加请求消息进入队列
				msgNum++;// 消息增加一条
			} else {
				if (chatQueue.size() > MESSAGE_QUEUE_LIMIT) {
					// 消息超过队列上限，，直接丢弃
					return;
				}
				chatQueue.add(request);// 添加消息进入队列
				msgNum++;
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
						logger.info("messageExecutir[{}]", this.messageExecutor);
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
				logger.info("当前队列中还有[{}]条消息", msgNum);
				//handMessageQueue();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				this.chatQueue.setRunning(false);
			}
		}

//		private void handMessageQueue() {
//			if (handler != null)
//				try {
//					System.out.println(request.getChannel());
//					handler.handleMsg(this.request.getMsg(), response);
//				} catch (Exception e) {
//					logger.error(ExceptionUtils.getStackTrace(e));
//				}
//			else {
//				logger.warn("指令 [{}]找不到", messageId);
//			}
//		}
	}
}
