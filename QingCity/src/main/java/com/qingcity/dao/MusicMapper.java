package com.qingcity.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.MusicEntity;

@Repository
public interface MusicMapper {
	/**
	 * 根据id删除音乐
	 * 
	 * @param musicid
	 *            歌曲id
	 * @return 返回删除的行数
	 */
	public int deleteByMusicId(Integer musicid);

	/**
	 * 新加入歌曲信息
	 * 
	 * @param music
	 *            歌曲实体
	 * @return 返回添加的歌曲数
	 */
	public int insert(MusicEntity music);

	/**
	 * 可选择性的添加音乐信息
	 * 
	 * @param music
	 *            歌曲实体
	 * @return 返回添加的歌曲数
	 */
	public int insertSelective(MusicEntity music);

	/**
	 * 根据id选择音乐
	 * @param musicId  歌曲ID
	 * @return   返回歌曲实体
	 */
	public MusicEntity selectByMusicId(Integer musicId);

	//为
	public List<MusicEntity> selectMusic();
	
	
	public int updateByMusicIdSelective(MusicEntity music);

	public int updateByMusicId(MusicEntity music);
}