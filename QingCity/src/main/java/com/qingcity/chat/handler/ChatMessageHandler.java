package com.qingcity.chat.handler;

import com.qingcity.chat.domain.ChatMessageReq;
/**
 * @author leehotin
 * @Date 2017年2月5日 下午3:15:01
 * @Description 聊天消息处理接口
 */
public abstract interface ChatMessageHandler{
	
	public void process(ChatMessageReq msgReq);
	
	public void sendMessage(ChatMessageReq msgReq);

}
