package com.qingcity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.GiftRecord;

/**
 * 
 * @author leehotin
 * @Date 2017年2月22日 下午4:51:26
 * @Description 抽奖记录Mapper
 */
@Repository
public interface GiftRecordMapper {

	/**
	 * 插入新的抽奖记录
	 * 
	 * @param record
	 * @return
	 */
	int insertRecord(GiftRecord record);

	/**
	 * 查询某类抽奖获奖玩家列表
	 * 
	 * @param id
	 * @return
	 */
	List<GiftRecord> selectWinByType(Integer id);

	/**
	 * 查询某类抽奖已获奖人数
	 * 
	 * @param type
	 * @return
	 */
	int selectWinNumberByType(Integer type);

	/**
	 * 查询玩家某类抽奖次数
	 * 
	 * @param userId
	 *            玩家id
	 * @param type
	 *            查询类型
	 * @return 抽奖次数，有异常表明未抽过
	 */
	int selectLotteryTimes(@Param("userId") int userId, @Param("type") int type);

	/**
	 * 更新抽奖信息
	 * 
	 * @param giftRecord
	 * @return
	 */
	int updateByUserIdAndType(GiftRecord giftRecord);
}