package com.qingcity.data.manager;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午5:32:03
 * @Description Channel频道管理接口
 */
public interface Groups {

	/**
	 * 将成员添加进频道Channel
	 * 
	 * @param identify
	 *            标识码，世界频道为世界频道码，公会频道为工会id
	 * @param channel
	 *            玩家channel
	 * @return -1 添加失败 1成功
	 */
	public int addToGroup(int identify, Channel channel);

	/**
	 * 玩家掉线时将玩家移除频道列表
	 * 
	 * @param channel
	 *            玩家channel
	 * @return -1 添加失败 1成功
	 */
	public int removeFromGroup(Channel channel);

	/**
	 * 获取当前在线人数，世界频道可获得所有玩家在线总数，公会频道则可获取公会在线总人数
	 * 
	 * @return 总人数
	 */
	public int getOnlineNum();

	/**
	 * 玩家更换频道
	 * 
	 * @param old_identify
	 *            旧频道，目的是为了将玩家从旧频道中移除
	 * @param new_identify
	 *            新频道，目的是为了将玩家加入新的频道
	 * @param channel
	 *            玩家channel
	 * @return
	 */
	public int changeChannel(int old_identify, int new_identify, Channel channel);

	/**
	 * 获取指定标识下的channel列表
	 * 
	 * @param identify
	 *            标识
	 * @return
	 */
	public ChannelGroup getChannels(int identify);

}
