package com.qingcity.domain;

import com.qingcity.entity.MsgEntity;

import io.netty.handler.codec.http.FullHttpRequest;

public class Command {
	private int id;
//    private List<String> commandData;
	@SuppressWarnings("unused")
	private MsgEntity messageData;
	@SuppressWarnings("unused")
	private ERequestType requestType;
	FullHttpRequest request;

	public Command(ERequestType requestType, MsgEntity msg) {
		this.requestType = requestType;
		if (requestType == ERequestType.SOCKET) {
			this.messageData = msg;
			this.id = msg.getCmdCode();
		} else {
			System.out.println("Command default");
		}
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRequestType(ERequestType requestType) {
		this.requestType = requestType;
	}

//	String readString() {
//		switch (requestType) {
//		case HTTP:
//		case WEBSOCKET_TEXT:
//			return (String) this.commandData.get(this.readIndex++);
//		case SOCKET:
//		case WEBSOCKET_BINARY:
//			int length = this.messageData.readInt();
//			byte[] c = new byte[length];
//			this.messageData.readBytes(c);
//			return new String(c);
//		}
//
//		return null;
//	}
//
//	int readInt() {
//		switch (requestType) {
//		case HTTP:
//		case WEBSOCKET_TEXT:
//			return Integer.parseInt((String) this.commandData.get(this.readIndex++));
//		case SOCKET:
//		case WEBSOCKET_BINARY:
//			return this.messageData.readInt();
//		}
//
//		return -1;
//	}
//
//	short readShort() {
//		switch (requestType) {
//		case HTTP:
//		case WEBSOCKET_TEXT:
//			return Short.parseShort((String) this.commandData.get(this.readIndex++));
//		case SOCKET:
//		case WEBSOCKET_BINARY:
//			return this.messageData.readShort();
//		}
//
//		return -1;
//	}
//
//	long readLong() {
//		switch (requestType) {
//		case HTTP:
//		case WEBSOCKET_TEXT:
//			return Long.parseLong((String) this.commandData.get(this.readIndex++));
//		case SOCKET:
//		case WEBSOCKET_BINARY:
//			return this.messageData.readLong();
//		}
//
//		return -1L;
//	}
//
//	float readFloat() {
//		switch (requestType) {
//		case HTTP:
//		case WEBSOCKET_TEXT:
//			return Float.parseFloat((String) this.commandData.get(this.readIndex++));
//		case SOCKET:
//		case WEBSOCKET_BINARY:
//			return this.messageData.readFloat();
//		}
//
//		return -1.0F;
//	}
}
