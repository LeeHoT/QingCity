package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.RechargeRecord;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:14:22
 * @Description 充值记录Mapper接口
 */
@Repository
public interface RechargeRecordMapper {
	int deleteByPrimaryKey(Integer rechargeid);

	int insert(RechargeRecord record);

	int insertSelective(RechargeRecord record);

	RechargeRecord selectByPrimaryKey(Integer rechargeid);

	int updateByPrimaryKeySelective(RechargeRecord record);

	int updateByPrimaryKey(RechargeRecord record);
}