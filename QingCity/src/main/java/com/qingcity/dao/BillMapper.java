package com.qingcity.dao;

<<<<<<< HEAD
import java.util.List;

import org.apache.ibatis.annotations.Param;
=======
>>>>>>> 5c0e5bc843bdb11b1826aeb31ab6f881df5aeb17
import org.springframework.stereotype.Repository;

import com.qingcity.entity.Bill;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:13:55
 * @Description 订单Mapper接口
 */
@Repository
public interface BillMapper {
<<<<<<< HEAD
	/**
	 * 根据订单id 查询 订单信息
	 * 
	 * @param orderid
	 * @return 订单信息
	 */
	Bill selectByOrderId(String orderId);

	/**
	 * 根据订单号删除订单信息
	 * 
	 * @param orderid
	 * @return 成功返回1 否则返回null
	 */
	int deleteByOrderId(String orderId);

	/**
	 * 插入一个订单记录，
	 * 
	 * @param record
	 * @return
	 */
	int insertBill(Bill record);

	/**
	 * 根据订单id 更新订单信息
	 * 
	 * @param record
	 * @return
	 */
	int updateByOrderId(Bill record);

	/**
	 * 查询某个订单的状态
	 * 
	 * @param orderId
	 * @return 订单状态
	 */
	int selectStatusById(String orderId);

	/**
	 * 根据订单号查询订单中的商品id
	 * 
	 * @param orderId
	 * @return
	 */
	int selectPidByOrderId(String orderId);

	/**
	 * 根据玩家id 查询当前玩家的所有订单信息
	 * 
	 * @param userId
	 * @return
	 */
	List<Bill> selectBillByUserId(int userId);
	
	int updateStatusByOrderId(@Param("status")int status,@Param("orderId") String orderId);

=======
	int deleteByPrimaryKey(Integer orderid);

	int insert(Bill record);

	int insertSelective(Bill record);

	Bill selectByPrimaryKey(Integer orderid);

	int updateByPrimaryKeySelective(Bill record);

	int updateByPrimaryKey(Bill record);
>>>>>>> 5c0e5bc843bdb11b1826aeb31ab6f881df5aeb17
}