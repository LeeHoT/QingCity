package com.qingcity.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.Societys;

@Repository
public interface SocietysMapper {

	/**
	 * 查询公会玩家信息 当userId_in值大于0的时候，不考虑societyId_in的值，只选择当前玩家的公会并查询该玩家公会成员信息
	 * 当userId_in的值小于等于0的时候，则必须societyId_in的值大于0且在societys表中，即为一个存在的公会Id
	 * 接收时需要对Societys实体中的pList进行非空判定，否则当值为空的时候或传入数值有误是会报NullPointerException
	 * 
	 * @param userId_in
	 *            传入的玩家id,不跟据玩家Id查询是请设置为小于0
	 * @param societyId_in
	 *            传入的工会Id,当userId_in>0时,不考虑societyId_in的值,
	 *            只有当userId_in的值小于0时societyId_in的值有效
	 * @return
	 */
	 Societys queryForMemberList(@Param("userId_in") Integer userId_in,
			@Param("societyId_in") Integer societyId_in);

	/**
	 * 更新公会公告信息
	 * 
	 * @param societyId
	 *            公会id
	 * @param notice
	 *            新的公告信息，最多50个汉字，所以在使用前一定检查新公告的长度，不可高于50
	 */
	 void updateSocietyNotice(@Param("societyId") Integer societyId, @Param("notice") String notice);

	/**
	 * 更新公会名字
	 * 
	 * @param societyId
	 *            公会id
	 * @param name
	 *            新的公告名字，不得超过五个字
	 */
	 void updateSocietyName(@Param("societyId") Integer societyId, @Param("name") String name);

	/**
	 * 创建公会，常见成功后会返回当前工会的Id
	 * 
	 * @param societys
	 *            新建公会对象
	 */
	 void createSociety(Societys societys);

	/**
	 * 根据名字查询该名字是否已经被占用，因为在公会名不存在是，返回的结果为空，会抛出异常，所以使用时需要捕获异常
	 * 
	 * @param name
	 *            新的公会名
	 * @return 已经存在的公会id
	 */
	 int selectSocietyName(String name) throws Exception;
}