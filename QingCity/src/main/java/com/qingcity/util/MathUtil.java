package com.qingcity.util;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class MathUtil {

	private static MathUtil instance = new MathUtil();

	public static MathUtil getInstance() {
		return instance;
	}

	public int getRandomNum(int startOffset, int endOffset) {
		int number = 0;
		Object[] values = new Object[20];

		Random random = new Random();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

		// 生成随机数字并存入HashMap
		for (int i = startOffset; i < values.length; i++) {
			number = random.nextInt(endOffset) + 1;// 生成的随机数
			hashMap.put(number, i);
		}
		return number;
	}

	public static void main(String[] args) {
		String rad = UUID.randomUUID().toString();
		System.out.println(rad);
	}
}
