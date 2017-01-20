package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.reward.Reward;;

@Repository
public interface RewardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reward record);

    int insertSelective(Reward record);

    Reward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reward record);

    int updateByPrimaryKey(Reward record);
}