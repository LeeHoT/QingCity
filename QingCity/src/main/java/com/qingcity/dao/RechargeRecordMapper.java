package com.qingcity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	/**
	 * 查询订单详情
	 * 
	 * @param rechargeid
	 * @return
	 */
	RechargeRecord selectByRechargeId(String rechargeid);

	/**
	 * 删除订单
	 * 
	 * @param rechargeid
	 * @return
	 */
	int deleteByRechargeId(String rechargeid);

	/**
	 * 插入一条新的订单记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(RechargeRecord record);

	/**
	 * 更新订单信息
	 * 
	 * @param record
	 * @return
	 */
	int updateByRechargeId(RechargeRecord record);

	/**
	 * 根据id查询订单状态
	 * 
	 * @param rechargeId
	 * @return
	 */
	int selectStatusById(String rechargeId);

	/**
	 * 查询某位玩家的所有订单
	 * 
	 * @param userId
	 * @return
	 */
	List<RechargeRecord> selectByUserId(int userId);

	/**
	 * 根据订单id 查询订单中商品id
	 * 
	 * @param rechargeId
	 * @return
	 */
	int selectPidById(String rechargeId);

	/**
	 * 更新订单状态
	 * 
	 * @param rechargeId
	 * @param status
	 * @return
	 */
	int updateStatusById(@Param("rechargeId") String rechargeId, @Param("status") int status);

}