package com.qingcity.test.userTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qingcity.entity.Gift;
import com.qingcity.util.LotteryUtil;

import sun.net.www.content.image.gif;

/**
 * 不同概率抽奖
 * 
 * @author:rex
 * @date:2014年10月20日
 * @version:1.0
 */
public class LotteryTest {

	private static Integer totalNum = 10000;
	private static Integer totalPeoPle = 10000;
	private static Integer none = totalNum - 50;

	public static void main(String[] args) {

		List<Gift> gifts = LotteryUtil.getGiftList(1);
		// List<Gift> gifts = new ArrayList<Gift>();
		// 序号==物品Id==物品名称==概率
		// gifts.add(new Gift(1, "P1", "王青演唱会门票", (20 / totalNum)));
		// gifts.add(new Gift(2, "P2", "王青新专辑", (100 / totalNum)));
		// gifts.add(new Gift(3, "P3", "王青合影", (50 / totalNum)));
		// gifts.add(new Gift(4, "P4", "王青签名照", (200 / totalNum)));
		// gifts.add(new Gift(5, "P5", "其他奖品", (none / totalNum)));
		for (Gift gift : gifts){
			System.out.println("奖品名称"+gift.getName());
		}
		
		gifts.add(new Gift(1, "未中奖", none, "很遗憾您未中奖"));
		
		List<Double> orignalRates = new ArrayList<Double>((gifts.size()));
		for (Gift gift : gifts) {
			double probability = (double)gift.getCount() / totalNum;
			System.out.println("概率"+probability);
			if (probability < 0) {
				probability = 0;
			}
			none = totalNum - gift.getCount();
			orignalRates.add(probability);
		}

		// statistics
		Map<Integer, Integer> count = new HashMap<Integer, Integer>();
		double num = totalPeoPle;
		for (int i = 0; i < num; i++) {
			int orignalIndex = LotteryUtil.lottery(orignalRates);
			System.out.println(orignalIndex);
			Integer value = count.get(orignalIndex);
			System.out.println(value);
			count.put(orignalIndex, value == null ? 1 : value + 1);
		}

		for (Entry<Integer, Integer> entry : count.entrySet()) {
			System.out.println(gifts.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability="
					+ entry.getValue() / num);
		}
	}

}
