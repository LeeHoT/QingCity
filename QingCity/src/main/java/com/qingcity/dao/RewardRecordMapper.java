package com.qingcity.dao;

import com.qingcity.entity.RewardRecord;

public interface RewardRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardRecord record);

    int insertSelective(RewardRecord record);

    RewardRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RewardRecord record);

    int updateByPrimaryKey(RewardRecord record);
}