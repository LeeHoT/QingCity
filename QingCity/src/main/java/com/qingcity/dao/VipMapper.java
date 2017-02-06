package com.qingcity.dao;

import com.qingcity.entity.Vip;

public interface VipMapper {
    int deleteByPrimaryKey(Integer playerid);

    int insert(Vip record);

    int insertSelective(Vip record);

    Vip selectByPrimaryKey(Integer playerid);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}