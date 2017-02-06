package com.qingcity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.MailBox;

@Repository
public interface MailBoxMapper {

	/**
	 * 查询一个玩家的所有邮件
	 * 
	 * @param userId
	 *            玩家id
	 * @return 当前玩家邮件列表
	 */
	public List<MailBox> selectMail(int userId);

	/**
	 * 查看邮件详情
	 * 
	 * @param id
	 *            查看的邮件id
	 * @return 查看的邮件内容
	 */
	public MailBox selectMailDetail(int id);

	/**
	 * 删除邮件，当邮件到达过期时间的时候则将其删除
	 * 
	 * @param id
	 * @param userId
	 */
	public int deleteMailById(@Param("id") int id, @Param("userId") int userId);

	/**
	 * 从对应的邮件中获取邮件中的物品，此处使用了存储过程，只需传入邮件id即可,领取完毕之后邮件将被删除。
	 * 
	 * 
	 * @param mailId
	 *            邮件id
	 */
	public void getItemFromMailBox(int mailId);

	/**
	 * 更新邮件的状态，当玩家点击了邮件之后，则将其状态设置为已读
	 * 
	 * @param id
	 *            邮件id
	 */
	public void updateMailReaded(int id);

	/**
	 * 发送邮件，即给好友发送体力(实际上还是可以赠送金币的哦，但是这个地方做的不好，增加新的赠品是需要更改大量的内容)
	 * 
	 * @param mailBox
	 *            邮件内容
	 * @return 更新行数
	 */
	public int sendMail(MailBox mailBox);
}