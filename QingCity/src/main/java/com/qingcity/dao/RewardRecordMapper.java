package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.reward.RewardRecord;

@Repository
public interface RewardRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardRecord record);

    int insertSelective(RewardRecord record);

    RewardRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RewardRecord record);

    int updateByPrimaryKey(RewardRecord record);
}