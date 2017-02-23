package com.qingcity.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.Gift;

/**
 * 
 * @author leehotin
 * @Date 2017年2月22日 下午4:37:55
 * @Description 礼品Mapper
 */
@Repository
public interface GiftMapper {
	/**
	 * 根据id奖品总数量
	 * 
	 * @param id
	 *            奖品id
	 * @return
	 */
	int selectGiftCount(int id);

	/**
	 * 更新当前奖品数量(每次数量-1)
	 * 
	 * @param id
	 *            奖品id
	 * @param count
	 *            奖品变化数量
	 * @return
	 */
	int updatGiftCount(@Param("id") int id, @Param("count") int count);

	/**
	 * 查询奖品信息
	 * 
	 * @param id
	 *            奖品id
	 * @return
	 */
	Gift selectGiftById(Integer id);

	/**
	 * 删除意见奖品
	 * 
	 * @param id
	 *            奖品id
	 * @return
	 */
	int deleteGiftById(Integer id);

	/**
	 * 插入奖品信息(包含产品id)
	 * 
	 * @param gift
	 * @return
	 */
	int insertWithId(Gift gift);

	/**
	 * 插入产品信息(产品id由数据库自己生成)
	 * 
	 * @param gift
	 * @return
	 */
	int insertWithoutId(Gift gift);

	/**
	 * 更新奖品信息
	 * 
	 * @param gift
	 * @return
	 */
	int updateById(Gift gift);

}