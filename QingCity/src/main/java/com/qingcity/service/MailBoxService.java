package com.qingcity.service;

import java.util.List;

import com.qingcity.entity.MailBox;

public interface MailBoxService {

	/**
	 * 根据邮箱id删除邮件，邮件过期后即删除邮件。 登录需要检查邮件是否过期
	 * 
	 * @param id
	 *            邮件id
	 * @param userId
	 *            玩家id
	 * @return 为-1即小于0则删除失败 >0 则删除成功
	 */
	int deleteById(Integer id, int userId);

	/**
	 * 从对应的邮件中领取邮件中的物品，此处使用了存储过程，只需传入邮件id即可
	 * 
	 * @param mailId
	 *            邮件id
	 */
	void getItemFromMailBox(int mailId);

	/**
	 * 查看邮件详情
	 * 
	 * @param id
	 *            查看的邮件id
	 * @return 查看的邮件内容,当返回结果为null的时候表明邮件不存在
	 */
	MailBox selectMailDetail(int id);

	/**
	 * 根据用户id查询用户邮件
	 * 
	 * @param id
	 *            玩家id
	 * @return 返回邮件列表，但List大小为0的时候表明没有邮件
	 */
	List<MailBox> selectMail(Integer userId);

	/**
	 * 更新邮件的状态，当玩家点击了邮件之后，则将其状态设置为已读
	 * 
	 * @param id
	 *            邮件id
	 */
	void updateMailReaded(int id);

	/**
	 * 发送邮件，即给好友发送体力(实际上还是可以赠送金币的哦，但是这个地方做的不好，增加新的赠品是需要更改大量的内容)
	 * 
	 * @param mailBox
	 *            邮件内容
	 * @return 更新行数
	 */
	int sendMail(MailBox mailBox);

}
