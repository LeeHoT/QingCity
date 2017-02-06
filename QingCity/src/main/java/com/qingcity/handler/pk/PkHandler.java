package com.qingcity.handler.pk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.qingcity.constants.CmdConstant;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.MsgEntity;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.pk.Room;
import com.qingcity.handler.CmdHandler;
import com.qingcity.handler.HandlerMsg;
import com.qingcity.proto.MessageError.Error;
import com.qingcity.proto.PkMsg.PkInfo;
import com.qingcity.service.MusicScoreService;
import com.qingcity.service.PlayerService;
import com.qingcity.util.TimeUtil;

/**
 * 如果玩家开始pk,则首先进入pk队列，保存方式为map<level,playerId>的形式保存
 * 进入队列后开始搜索队列中玩家，队列为先进先出原则，所以匹配事找到最先进入队列的第一位 等级相同的玩家
 * 若匹配到最后并没有找到对应的玩家。则随机从数据库中选择一位等级相同的玩家，取得该首歌的最佳成绩后与当前玩家进行PK, 成绩高者获胜，并获得相应的奖励
 * 
 * @author Administrator
 *
 */
@Controller("pkHandler")
public class PkHandler extends HandlerMsg implements CmdHandler {
	private static final Logger logger = LoggerFactory.getLogger(PkHandler.class);
	private Room room;

	private int count = 0; // 未满房间数
	private Map<String, String> roomQ;// map<musicId_level,playerId>
										// 未满的房间Map
	private Map<String, Room> listRoom = new HashMap<>();// 所有房间列表

	@Autowired
	private PlayerEntity playerEntity;
	@Autowired
	private MusicScoreEntity musicScoreEntity;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private MusicScoreService musicScoreService;

	@Override
	public void handleMsg(MsgEntity msgEntity, GameResponse response) {
		if (isErrorMsg(msgEntity, response)) {
			logger.error("实际消息和原消息不符!长度不一致。");
			return;
		}
		switch (msgEntity.getCmdCode()) {
		case CmdConstant.PK_SEARCH:
			System.out.println("一个新用户开始了。");
			handlerSearch(msgEntity, response);
			break;
		case CmdConstant.PK_COMPLETE:
			HandlerPk(msgEntity, response);
			break;
		default:
			logger.error("找不到命令码 " + msgEntity.getCmdCode());
			;
			break;
		}
	}

	public void handlerSearch(MsgEntity msgEntity, GameResponse response) {
		if (roomQ == null) {// 如果map为空，则新建map
			roomQ = new HashMap<>();
		}
		int playerId = 0;// 玩家id
		int musicId = 0;// 所选音乐id
		int level = 0;// 玩家等级
		try {
			PkInfo player = PkInfo.PARSER.parseFrom(msgEntity.getData());
			playerId = player.getPlayerId();// 当前pk玩家id
			musicId = player.getMusicId();// 当前进行pk的音乐
			// 查询玩家等级level
			playerEntity = playerService.selectByUserId(playerId);
			level = playerEntity.getLevel(); // 当前pk玩家的等级
			System.out.println("当前" + playerId + "玩家等级为" + level);
			// 玩家开始匹配,并获得房间id
			int randomId = 0;
			boolean isFull = false;
			String roomId = null;
			int num = 0;
			do {
				// 匹配次数
				System.out.println("匹配次数" + num);
				if (num < 3) {
					roomId = matchRoom(playerId, musicId, level);// 参数分别是
																	// pk音乐id
																	// 玩家等级
				} else if (num == 3) {
					// 匹配次数过多。。当前匹配玩家过少。无法正常匹配
					// 从数据库中选取数据
					System.out.println("实在没办法。。只能从数据库中取值了。。。哎  ");
					randomId = SearchRandomPlayer(level, musicId);
					if (randomId != playerId) {
						// 数据库中数据匹配成功。。
						listRoom.get(roomId).getPlayer().add(randomId);
					}
				} else {
					Error.Builder error = Error.newBuilder();
					error.setContent("匹配失败");
					handlerResMsg(error.build(), CmdConstant.ANY_FAIL, response);
					return;
				}
				// 等待五秒钟
				System.out.println("玩家" + playerId + "休息5秒钟，，等等玩家");
				Thread.sleep(20000); // 音乐id以及当前玩家等级
				// 检查房间是否满 ，满了说明已经匹配 成功，否则说明只是创建了房间
				isFull = isFullRoom(roomId);
				num++;
			} while (!isFull && roomId != "");
			System.out.println("房间满了，，房间id为" + roomId + "，可以开始比赛了。");
			// 将对手信息返回给当前玩家，，开始pk
			roomQ.remove(musicId + "_" + level, roomId);
			System.out.println("匹配成功，需要返回给玩家信息");
			// *********返会对手信息********
			Error.Builder error = Error.newBuilder();
			error.setContent("匹配成功");
			handlerResMsg(error.build(), CmdConstant.ANY_SUCCESS, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据房间id找到对应的两个人，只有两个人的数据都收到之后再进行存储， 此处不做任何战斗处理，只是对客户端传来的数据进行存储。
	 * 需要使用到musicScoreService 如果该玩家成绩高于其最高成绩，则更新最高成绩， 以及playerService
	 * 对玩家的金币和钻石进行更新，添加对玩家PK的奖励
	 * 
	 * 保证数据库中每个玩家每天的成绩不会出现重复，但是不同日期同一首歌会出现重复的成绩
	 * 
	 * @param msgEntity
	 * @param response
	 */
	public void HandlerPk(MsgEntity msgEntity, GameResponse response) {
		int musicId = 1;
		int musicGrade = 3;
		int normalHighScore = 10000;
		int userId = 88;
		musicScoreEntity = musicScoreService.selectByMusicIdAndUserId(userId, musicId);
		Date date = new Date();
		// 若查询结果为空，说明该玩家没有该首歌的成绩，直接存储并将本次成绩作为最终成绩
		// 如果赛季 周数 和 日期有一者不相同，则将数据直接插入
		if (null == musicScoreEntity || !musicScoreEntity.getDay().equals(date)
				|| musicScoreEntity.getWeek() != TimeUtil.getWeek(date)
				|| musicScoreEntity.getSeason() != TimeUtil.getSeason(date)) {
			// 从msgEntity 中取出相应的额数据,直接作为新数据插入
			musicScoreEntity.setMusicId(musicId);
			musicScoreEntity.setMusicGrade(musicGrade);
			musicScoreEntity.setNormalHighScore(normalHighScore);
			musicScoreEntity.setUserId(userId);
			musicScoreEntity.setSeason(TimeUtil.getSeason(date));
			musicScoreEntity.setWeek(TimeUtil.getWeek(date));
			musicScoreEntity.setDay(TimeUtil.Date2Timestamp(date));
			musicScoreService.insert(musicScoreEntity);
		} else {
			// 否则成绩属于当天的成绩，应对今日成绩进行更新，
			if (musicScoreEntity.getMusicGrade() > musicGrade
					&& musicScoreEntity.getNormalHighScore() > normalHighScore) {
				// 两项均需要更新
				musicScoreEntity.setMusicGrade(musicGrade);
				musicScoreEntity.setNormalHighScore(normalHighScore);
				musicScoreService.updateByMusicIdAndUserIdSelective(musicScoreEntity);
			} else if (musicScoreEntity.getMusicGrade() > musicGrade
					&& musicScoreEntity.getNormalHighScore() <= normalHighScore) {
				// 只需更新musicGrade
				musicScoreEntity.setMusicGrade(musicGrade);
				musicScoreService.updateByMusicIdAndUserIdSelective(musicScoreEntity);
			} else if (musicScoreEntity.getMusicGrade() <= musicGrade
					&& musicScoreEntity.getNormalHighScore() > normalHighScore) {
				// 只需更新normalHighScore
				musicScoreEntity.setNormalHighScore(normalHighScore);
				musicScoreService.updateByMusicIdAndUserIdSelective(musicScoreEntity);
			} else {
				System.out.println("什么都不需要更新，成绩没有刷新");
			}
			// 返回消息

		}
		System.out.println("房间" + room + "已经创建,当前时间为" + System.currentTimeMillis() + ",房间id为 :" + room.getRoomId());
	}

	/**
	 * 判断房间是否已满，如果满了，则匹配成功，，否则为防止玩家等待时间过长，选择其他更加快速的方式
	 * 
	 * @param roomId
	 *            玩家房间id
	 * @return true 房间已满 否则 房间未满
	 */
	public boolean isFullRoom(String roomId) {
		int roomNum = 0;// 房间人数
		roomNum = listRoom.get(roomId).getPlayer().size();
		System.out.println("房间里有" + roomNum + "个人");
		if (roomNum == listRoom.get(roomId).getplayerNum()) {
			// 房间人数已满
			System.out.println("你的房间已经满了。。可以开始玩了");
			return true;
		}
		// 房间未满，匹配不成功,此时需要另想它法了。。。。
		System.out.println("你的房间还没有满哦");
		return false;
	}

	/**
	 * 匹配对手，搜索满足要求的房间
	 * 
	 * @param playerId
	 * @param musicId
	 * @param level
	 * @return 匹配成功或成功创建的房间Id
	 */
	public String matchRoom(int playerId, int musicId, int level) {
		String key = musicId + "_" + level;
		String roomId = "";
		// 获取该玩家需要的房间信息
		// roonQ中是否存在这个key
		System.out.println("玩家" + playerId + "开始匹配房间了");
		if (!roomQ.containsKey(key)) {
			// 不存在key,则新建房间
			System.out.println("未满房间队列中没有等级和音乐匹配的玩家，玩家" + playerId + "新建房间");
			roomId = createRoom(playerId, musicId, level);
			return roomId;
		}
		// 有对应的的房间，获取房间列表
		System.out.println("队列中有键值为" + key + "的房间");
		roomId = roomQ.get(key);
		// 判断房间中的人是不是他自己，，如果不是。。怎匹配成功

		if (listRoom.get(roomId).getPlayer().get(0) == playerId) {
			// 房间是他自己的。。接着下一个
			System.out.println("房间是他自己的。");
		} else {
			System.out.println("你所找到的房间id为" + roomId);
			// 去所有房间列表中找到对应的房间，
			// 获取房间内的玩家列表，并将当前玩家添加进去，此时房间内玩家数量为2，即房间已满
			listRoom.get(roomId).getPlayer().add(playerId);
			System.out.println(
					"添加新的玩家" + playerId + "进入房间，此时房间" + roomId + "中有" + listRoom.get(roomId).getPlayer().size() + "个人");
		}

		// 房间满了则开始PK,并将未满房间列表中的数据删除,此时所有房间列表中仍然存在对应的房间
		return roomId;
	}

	/**
	 * 创建房间 将玩家添加进玩家列表 将房间添加进roomQ 中
	 * 
	 * @param player
	 *            玩家id
	 * @param musicId
	 *            选择音乐的id
	 * @param level
	 *            玩家等级
	 * @return 新建房间的Id
	 */
	public String createRoom(int player, int musicId, int level) {
		// 新建房间
		room = new Room();
		count++;
		System.out.println("房间" + room + "已经创建,当前时间为" + System.currentTimeMillis() + ",房间id为 :" + room.getRoomId());
		// 将房间保存在房间队列中，
		// 将玩家添加进房间
		List<Integer> listP = new ArrayList<>();
		listP.add(player);
		room.setPlayer(listP);
		// 将房间加入已满房间列表
		listRoom.put(room.getRoomId(), room);// 添加房间进入房间列表，
		String key = musicId + "_" + level;// 房间列表的Key
		// 将房间id放入待搜索队列，
		roomQ.put(key, room.getRoomId());// 将列表存进map,其中key值为 musicId_level
		// 返回房间id
		return room.getRoomId();
	}

	/**
	 * 移出map中的玩家，当玩家匹配成功后，立刻将玩家移出
	 * 
	 * @param key
	 *            roomQ中的key,即 musicId_level
	 * @param roomId
	 *            已经匹配成功的房间Id
	 */
	public void removeRoom(String key, String roomId) {
		if (roomQ.remove(key, roomId)) {
			System.out.println("成功移除一个房间<key,value>");
		} else {
			roomQ.remove(key);
			System.out.println("成功移除一个房间key");
		}
		count--;
		System.out.println("当前roomQ队列中有" + count + "个未满的房间");
	}

	/**
	 * 当匹配不到玩家时， 则从数据库中随机选出一名等级相同的玩家进行匹配 匹配方式则根据玩家的等级和所选择的歌曲进行匹配
	 * 只有找到一个符合条件的玩家时才推出
	 */
	public int SearchRandomPlayer(int level, int musicId) {
		// 根据音乐id找到前五条对应玩家的id找到对应的玩家
		List<Integer> list = new ArrayList<>();
		System.out.println("从数据库中匹配玩家的开始时间" + System.currentTimeMillis());
		// 锁定playerEntity,防止返回的数据出错。
		synchronized (playerEntity) {
			do {
				// 查出数据库中的随机1000以内两个数之间的数据
				// 根据玩家最高成绩进行匹配,选出最高成绩上下1000分的范围，随机取任何一个人的成绩
				list = musicScoreService.selectPlayerByMusicIdAndLevel(musicId, level - 2, level + 2);
				if (list != null) {
					// 找到所有符合要求的玩家，随机从中取一个人出来,此处取出本次符合要求玩家的中间的一个
					System.out.println("从数据库中匹配玩家的结束时间" + System.currentTimeMillis());
					return list.get(list.size() / 2);
				} else {
					return -1;
				}
			} while (true);
		}
	}
}
