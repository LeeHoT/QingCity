package com.qingcity.util;

import java.util.LinkedList;
import java.util.List;

public class StringUtils {

	private static StringUtils instance = new StringUtils();

	public StringUtils getInstance() {
		return instance;
	}

	/**
	 * String 转换成 StringList
	 * 
	 * @param str
	 * @param sepKey
	 * @return
	 */
	public static List<String> splitToStringList(String str, String sepKey) {
		List<String> list = new LinkedList<String>();
		if ((isNotNull(str)) && (isNotNull(sepKey))) {
			String[] items = str.split(sepKey, -1);
			for (String item : items) {
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 将StringList拆分成String
	 * 
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	public static final boolean isNotNull(String source) {
		return !isNull(source);
	}

	public static final boolean isNull(String source) {
		if ((source != null) && ((!source.trim().equals("")) || (!source.trim().equalsIgnoreCase("null")))) {
			return false;
		}
		return true;
	}

}
