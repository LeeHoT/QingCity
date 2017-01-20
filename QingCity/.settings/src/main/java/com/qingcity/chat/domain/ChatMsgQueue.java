package com.qingcity.chat.domain;

import java.util.Queue;

public final class ChatMsgQueue {

	private Queue<ChatMessageReq> chatQueue;
	private boolean running = false;

	public ChatMsgQueue(Queue<ChatMessageReq> chatQueue) {
		this.chatQueue = chatQueue;
	}

	public Queue<ChatMessageReq> getRequestQueue() {
		return chatQueue;
	}

	/**
	 * 清除消息队列
	 */
	public void clear() {
		chatQueue.clear();
		chatQueue = null;
	}

	/**
	 * 获取消息队列长度
	 * 
	 * @return
	 */
	public int size() {
		return chatQueue != null ? chatQueue.size() : 0;
	}

	/**
	 * 向消息队列中添加请求消息
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(ChatMessageReq request) {
		return this.chatQueue.add(request);
	}

	/**
	 * 设置消息队列运行状态
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 消息队列是否正在被轮询
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

}