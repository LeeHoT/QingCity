package com.qingcity.chat.domain;

import java.util.Queue;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:30:46
 * @Description 聊天消息队列
 */
public final class ChatMsgQueue {

	private Queue<ChatMessageReq> msg;
	private boolean running = false;

	public ChatMsgQueue(Queue<ChatMessageReq> msg) {
		this.msg = msg;
	}

	public Queue<ChatMessageReq> getRequestQueue() {
		return msg;
	}

	/**
	 * 清除消息队列
	 */
	public void clear() {
		msg.clear();
		msg = null;
	}

	/**
	 * 获取消息队列长度
	 * 
	 * @return
	 */
	public int size() {
		return msg != null ? msg.size() : 0;
	}

	/**
	 * 向消息队列中添加请求消息
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(ChatMessageReq request) {
		return this.msg.add(request);
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