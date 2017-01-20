package com.qingcity.entity.pk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
	private int playerNum;
	private String roomId;// 默认为当前系统时间
	private List<Integer> player = new ArrayList<Integer>();

	public Room() {
		this.playerNum = 2;
		this.roomId = UUID.randomUUID().toString();
	}

	public int getplayerNum() {
		return playerNum;
	}

	public void setplayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public List<Integer> getPlayer() {
		return player;
	}

	public void setPlayer(List<Integer> player) {
		this.player = player;
	}

}
