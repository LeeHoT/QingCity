package com.qingcity.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.qingcity.domain.GameRequest;
import com.qingcity.domain.GameResponse;
import com.qingcity.domain.MessageQueue;
import com.qingcity.handler.CmdHandler;
import com.qingcity.util.ExceptionUtils;

import io.netty.channel.Channel;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:35:15
 * @Description HandlerDispatcher 根据handlerMap 抉择处理方式， 同时可处理消息，包括添加队列、移除队列等等
 */
@Controller
public class HandlerDispatcher implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(HandlerDispatcher.class);

	private final Map<Integer, MessageQueue> sessionMsgQ;
	private Executor messageExecutor;
	private Map<Integer, CmdHandler> handlerMap;
	private boolean running;
	private long sleepTime;
	private volatile int msgNum = 0;// 消息队列中未处理消息数

	public HandlerDispatcher() {
		this.sessionMsgQ = new ConcurrentHashMap<Integer, MessageQueue>();
		this.running = true;
		this.sleepTime = 200L;
	}

	public void setHandlerMap(Map<Integer, CmdHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}

	public void setMessageExecutor(Executor messageExecutor) {
		this.messageExecutor = messageExecutor;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void addMessageQueue(Integer channelId, MessageQueue messageQueue) {
		this.sessionMsgQ.put(channelId, messageQueue);
	}

	public Map<Integer, MessageQueue> getMessageQueue() {
		return this.sessionMsgQ;
	}

	public void removeMessageQueue(Channel channel) {
		MessageQueue queue = (MessageQueue) this.sessionMsgQ.remove(channel);
		if (queue != null)
			queue.clear();
	}

	/**
	 * 添加消息进入队列
	 * 
	 * @param request
	 *            游戏请求 com.qingcity.domain.GameRequest
	 */
	public void addMessage(GameRequest request) {
		try {
			logger.info("添加消息进入消息队列，当前队列中有" + msgNum + "条消息!");
			MessageQueue messageQueue = (MessageQueue) this.sessionMsgQ
					.get(Integer.valueOf(request.getChannel().hashCode()));
			if (messageQueue == null) {
				// 消息为空，创建新的消息队列
				messageQueue = new MessageQueue(new ConcurrentLinkedQueue<GameRequest>());
				this.sessionMsgQ.put(Integer.valueOf(request.getChannel().hashCode()), messageQueue);
				logger.info("hashCode[{}]", Integer.valueOf(request.getChannel().hashCode()));
				messageQueue.add(request);// 添加请求消息进入队列
				msgNum++;// 消息增加一条
			} else {
				messageQueue.add(request);// 添加消息进入队列
				msgNum++;
			}
		} catch (Exception e) {
			HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	public void run() {
		while (this.running) {
			try {
				for (MessageQueue messageQueue : sessionMsgQ.values())
					if ((messageQueue != null) && (messageQueue.size() > 0) && (!messageQueue.isRunning())) {
						MessageWorker messageWorker = new MessageWorker(messageQueue);
						this.messageExecutor.execute(messageWorker);
						logger.info("messageExecutir[{}]", this.messageExecutor);
					}
			} catch (Exception e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			}
			// 等待200毫秒后继续
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	public void stop() {
		this.running = false;
	}

	public MessageQueue getUserMessageQueue(Channel channel) {
		return (MessageQueue) this.sessionMsgQ.get(channel);
	}

	private final class MessageWorker implements Runnable {
		private final MessageQueue messageQueue;
		private GameRequest request;

		private MessageWorker(MessageQueue messageQueue) {
			messageQueue.setRunning(true);
			this.messageQueue = messageQueue;
			this.request = ((GameRequest) messageQueue.getRequestQueue().poll());
		}

		public void run() {
			try {
				// 处理消息队列中的消息
				msgNum--;
				logger.info("当前队列中还有[{}]条消息", msgNum);
				handMessageQueue();
			} catch (Exception e) {
				HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
			} finally {
				this.messageQueue.setRunning(false);
			}
		}

		private void handMessageQueue() {
			int messageId = this.request.getCommandId();
			GameResponse response = new GameResponse(this.request.getChannel(), this.request.getCommand(),
					this.request.getRequestType());
			CmdHandler handler = (CmdHandler) HandlerDispatcher.this.handlerMap.get(Integer.valueOf(messageId));
			if (handler != null)
				try {
					System.out.println(request.getChannel());
					handler.handleMsg(this.request.getMsg(), response);
				} catch (Exception e) {
					HandlerDispatcher.logger.error(ExceptionUtils.getStackTrace(e));
				}
			else {
				HandlerDispatcher.logger.warn("指令 [{}]找不到", messageId);
			}
		}
	}
}
