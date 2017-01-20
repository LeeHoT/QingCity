package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.task.Task;

@Repository
public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}