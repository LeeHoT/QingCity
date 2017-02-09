package com.qingcity.service;

import com.qingcity.entity.music.MusicEntity;

public interface MusicService {

	/**
	 * 根据前置条件查询歌曲id,主要用于解锁打歌歌曲
	 * 
	 * @param preCondition
	 *            前置条件
	 * @return 符合条件的歌曲id
	 */
	int selectMusicIdByPreCondition(int preCondition);

	/**
	 * 查询当前城下歌曲的数量
	 * 
	 * @param city
	 * @return
	 */
	int selectMusicNum(int city);

	/**
	 * 根据id删除音乐
	 * 
	 * @param musicid
	 *            歌曲id
	 * @return 返回删除的行数
	 */
	int deleteByMusicId(Integer musicid);

	/**
	 * 新加入歌曲信息
	 * 
	 * @param music
	 *            歌曲实体
	 * @return 返回添加的歌曲数
	 */
	int insertMusic(MusicEntity music);

	/**
	 * 可选择性的添加音乐信息
	 * 
	 * @param music
	 *            歌曲实体
	 * @return 返回添加的歌曲数
	 */
	int insertMusicSelective(MusicEntity music);

	/**
	 * 根据id选择音乐
	 * 
	 * @param musicId
	 *            歌曲ID
	 * @return 返回歌曲实体
	 */
	MusicEntity selectByMusicId(Integer musicId);

	/**
	 * 根据歌曲id更新歌曲的前置解锁条件
	 * 
	 * @param musicId
	 *            音乐id
	 * @param preCondition
	 *            解锁前置条件
	 * @return 更新的音乐id
	 */
	int updatePreCondition(int musicId, int preCondition);

	/**
	 * 根据音乐名称更改音乐信息
	 * 
	 * @param music
	 *            音乐信息实体对象
	 * @return 更新行数/音乐id
	 */
	int updateByMusicName(MusicEntity music);

	/**
	 * 根据音乐id更改音乐信息
	 * 
	 * @param music
	 *            音乐信息实体对象
	 * @return 更新行数/音乐id
	 */
	int updateByMusicId(MusicEntity music);
}
