package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.Dress;

@Repository
public interface DressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dress record);

    int insertSelective(Dress record);

    Dress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dress record);

    int updateByPrimaryKey(Dress record);
}