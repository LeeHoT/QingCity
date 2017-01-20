package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.Backpack;

@Repository
public interface BackpackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Backpack record);

    int insertSelective(Backpack record);

    Backpack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Backpack record);

    int updateByPrimaryKey(Backpack record);
}