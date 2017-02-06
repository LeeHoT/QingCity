package com.qingcity.service;

import com.qingcity.entity.Societys;

/**
 * 
 * @author leehotin
 *
 */
public interface SocietyService {

	/**
	 * 检查公会名字是否被占用
	 * 
	 * @param name
	 *            被检测的公会名字
	 * @return 被占用返回true,否则返回false
	 */
	public boolean checkSocietyName(String name);

	/**
	 * 查询公会玩家信息 当userId值大于0的时候，不考虑societyId的值，只选择当前玩家的公会并查询该玩家公会成员信息
	 * 当userId的值小于等于0的时候，则必须societyId的值大于0且在societys表中，即为一个存在的公会Id
	 * 接收时需要对Societys实体中的pList进行非空判定，否则当值为空的时候或传入数值有误是会报NullPointerException
	 * 
	 * @param userId
	 *            传入的玩家id,不跟据玩家Id查询是请设置为小于0
	 * @param societyId
	 *            传入的工会Id,当userId>0时,不考虑societyId的值,
	 *            只有当userId的值小于0时societyId的值有效
	 * @return
	 */
	public Societys selectSocietysMember(int userId, int societyId);

	/**
	 * 更新公会公告信息，最多50个汉字，所以在使用前一定检查新公告的长度，不可高于50, 更新前需检查权限
	 * 
	 * @param societyId
	 *            公会id
	 * @param notice
	 *            新的公告信息
	 */
	public void updateSocietyNotice(Integer societyId, String notice);

	/**
	 * 更新公会名字, 更新前需检查权限
	 * 
	 * @param societyId
	 *            公会id
	 * @param name
	 *            新的公告名字，不得超过五个字
	 */
	public void updateSocietyName(Integer societyId, String name);

	/**
	 * 创建公会，常见成功后会返回当前工会的Id
	 * 
	 * @param societys
	 *            新建公会对象
	 */
	public void createSociety(Societys societys);

}
