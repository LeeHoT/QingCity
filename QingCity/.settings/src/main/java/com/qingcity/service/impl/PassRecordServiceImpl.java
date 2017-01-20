package com.qingcity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.constants.DifficultyConstant;
import com.qingcity.dao.MusicMapper;
import com.qingcity.dao.PassRecordMapper;
import com.qingcity.entity.music.PassRecord;
import com.qingcity.service.PassRecordService;

@Service("passRecordService")
public class PassRecordServiceImpl implements PassRecordService {

	private static final Logger logger = LoggerFactory.getLogger(PassRecordServiceImpl.class);

	@Autowired
	private PassRecordMapper passRecordMapper;
	@Autowired
	private MusicMapper musicMapper;

	@Override
	public List<PassRecord> queryForPassMusic(int userId, int city, int difficulty) {
		logger.info("玩家[{}]查询[{}]城下难度为[{}]的歌曲列表", userId, city, difficulty);
		return passRecordMapper.queryForPassMusic(userId, city);
	}

	@Override
	public int insertPassRecord(PassRecord passRecord) {
		logger.info("玩家[{}]插入一条记录", passRecord.getUserId());
		return passRecordMapper.insertSelective(passRecord);

	}

	@Override
	public int updatePassRecordToComplete(PassRecord passRecord) {
		logger.info("玩家[{}]将[{}]城下的歌曲[{}]通关", passRecord.getUserId(), passRecord.getCity(), passRecord.getMusicId());
		// 将一首歌曲设置为已通关
		int result = passRecordMapper.updatePassRecordToComplete(passRecord.getUserId(), passRecord.getMusicId(),
				passRecord.getDifficulty());
		if (result > 0) {
			// 更新成功，解锁下一首歌曲
			// 检查解锁本难度还是解锁下一难度
			// 查询是已解锁歌曲数量
			int passNum = selectPassNum(passRecord.getUserId(), passRecord.getCity(),
					passRecord.getDifficulty());
			if (passNum < musicMapper.selectMusicNum(passRecord.getCity())) {
				// 并没有完成当前难度下所有歌曲,查找哪首歌的解锁条件为该歌曲的id
				int preCondition = musicMapper.selectMusicIdByPreCondition(passRecord.getMusicId());
				if (preCondition > 0) {
					// 说明找到了对应的歌曲，将该歌曲设置为未通关
					logger.info("玩家[{}]将[{}]城下的歌曲[{}]解锁,目前该歌曲为通关", passRecord.getUserId(), passRecord.getCity(),
							preCondition);
					passRecord.setMusicId(preCondition);
					passRecord.setComplete(false);
					insertPassRecord(passRecord);
				}
			} else {
				if (passRecord.getDifficulty() == DifficultyConstant.HARD) {
					logger.info("玩家[{}]已通关所有难度的所有歌曲", passRecord.getUserId());
					// 做一些特定的处理
					return -2;
				}
				logger.info("玩家[{}]已将[{}]城下的难度为[{}]所有歌曲通关,将解锁下一难度第一首歌曲", passRecord.getUserId(), passRecord.getCity(),
						passRecord.getDifficulty());
				// 解锁下一下难度第一首歌曲
				passRecord.setDifficulty(passRecord.getDifficulty() + 1);
				passRecord.setComplete(false);
				passRecord.setMusicId(musicMapper.selectMusicIdByPreCondition(0));
				insertPassRecord(passRecord);
			}
		}
		return 1;
	}

	@Override
	public int selectPassNum(int userId, int city, int difficulty) {
		return passRecordMapper.selectPassNum(userId, city, difficulty);
	}

	@Override
	public int selectCompleteNum(int userId) {
		return passRecordMapper.selectCompleteNum(userId);
	}

}
