package com.qingcity.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.MusicScoreEntity;

@Repository
public interface MusicScoreMapper {
    int deleteByMusicIdAndUserId(@Param("musicId")int musicId,@Param("userId")int userId);

    int insert(MusicScoreEntity musicScore);

    int insertSelective(MusicScoreEntity musicScore);

    MusicScoreEntity selectByMusicIdAndUserId(@Param("musicId")int musicId,@Param("userId")int userId);

    int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore);

    int updateByMusicIdAndUserId(MusicScoreEntity musicScore);
}