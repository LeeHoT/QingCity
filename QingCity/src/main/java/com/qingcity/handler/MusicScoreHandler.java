package com.qingcity.handler;

import org.springframework.stereotype.Controller;

import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;

@Controller("musicSocreHandler")
public class MusicScoreHandler implements CmdHandler {

	@Override
	public void handleMsg(MsgEntity msgEntity, GameResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

}
