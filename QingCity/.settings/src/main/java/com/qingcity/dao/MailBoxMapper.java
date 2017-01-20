package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.MailBox;

@Repository
public interface MailBoxMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(MailBox record);

    public int insertSelective(MailBox record);

    public MailBox selectByPlayerId(Integer id);

    public int updateByPrimaryKeySelective(MailBox record);

    public int updateByPrimaryKeyWithBLOBs(MailBox record);

    public int updateByPrimaryKey(MailBox record);
}