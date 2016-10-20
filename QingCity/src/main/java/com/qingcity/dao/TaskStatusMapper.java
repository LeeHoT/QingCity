package com.qingcity.dao;

import com.qingcity.entity.TaskStatus;

public interface TaskStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskStatus record);

    int insertSelective(TaskStatus record);

    TaskStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskStatus record);

    int updateByPrimaryKey(TaskStatus record);
}