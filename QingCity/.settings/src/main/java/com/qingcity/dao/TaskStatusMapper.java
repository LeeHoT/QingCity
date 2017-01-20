package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.task.TaskStatus;

@Repository
public interface TaskStatusMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(TaskStatus record);

    int insertSelective(TaskStatus record);

    TaskStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskStatus record);

    int updateByPrimaryKey(TaskStatus record);
}