package com.qingcity.service;

import com.qingcity.entity.MailBox;

public interface MailBoxService {
	
	
	/**
	 * 根据邮箱id删除邮件，邮件过期后即删除邮件。
	 * 登录需要检查邮件是否过期
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id);

	/**
	 * 添加邮件 此方法必须保证 MailBox 所有不为空的属性全都有值 
	 * @param mailbox
	 * @return
	 */
    public int insert(MailBox mailbox);
    
    /**
     * 有选择性的的添加邮箱，
     * @param mailbox
     * @return
     */
    public int insertSelective(MailBox mailbox);
    
    /**
     * 根据用户id查询用户邮件
     * @param id
     * @return
     */
    public MailBox selectByPlayerId(Integer id);

    public int updateByPlayerIdSelective(MailBox mailbox);

    public int updateByPlayerIdWithBLOBs(MailBox mailbox);

    public int updateByPlayerId(MailBox mailbox);

}
