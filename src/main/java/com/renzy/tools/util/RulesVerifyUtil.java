/** 
 * Project Name: usercomm_project 
 * File Name: RulesVerifyUtil.java 
 * Package Name: com.huifenqi.usercomm.utils 
 * Date: 2015年12月23日下午5:12:00 
 * Copyright (c) 2015, www.huizhaofang.com All Rights Reserved. 
 * 
 */
package com.renzy.tools.util;

/**
 * ClassName: RulesVerifyUtil date: 2015年12月23日 下午5:12:00 Description:
 * 规则校验工具：参数长度、电话号码等
 * 
 * @author xiaozhan
 * @version
 * @since JDK 1.8
 */
public class RulesVerifyUtil {
	/**
	 * 校验参数替换模板:接口
	 *
	 * @param cs
	 * @return
	 */
	public static boolean verifyPathTemplate(String cs) {
		String regex = ".*\\$\\{(path.[a-z]+)\\}.*";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验参数替换模板:参数
	 *
	 * @param cs
	 * @return
	 */
	public static boolean verifyPathParameterTemplate(String cs) {
		String regex = ".*\\$\\{(path.parameter.+?)\\}.*";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}


	/**
	 * 校验逗号分隔数字
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifySeparatedNumber(String cs) {
		String regex = "\\d*(\\,\\d*)*";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验坐标
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyPosition(String cs) {
		String regex = "-?\\d+\\.\\d+\\,-?\\d+\\.\\d+";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验坐标值
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyPositionValue(String cs) {
		String regex = "-?\\d+\\.\\d+";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验数字区间
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyNumberRegion(String cs) {
		String regex = "^\\[(\\*|\\d+)\\,(\\*|\\d+)\\]$";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验金额字符串，以元为单位，最多两位小数
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyMoney(String cs) {
		String regex = "^(([1-9][0-9]*)+|0)(.[0-9]{1,2})?$";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验设置字段
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifySetting(String cs) {
		String regex = "\\w+:\\d+(\\,\\w+:\\d+)*";
		boolean verifyString = StringUtil.verifyString(cs, regex);
		return verifyString;
	}

	/**
	 * 校验名字 最大长度默认是50,只能包含汉字和(·)
	 * 
	 * @return
	 */
	public static boolean verifyName(String cs) {
		return verifyName(cs, 50);
	}

	/**
	 * 校验名字 只能包含汉字和(·)
	 * 
	 * @param cs
	 * @param length
	 * @return
	 */
	public static boolean verifyName(String cs, int length) {
		if (StringUtil.isBlank(cs)) {
			return false;
		}
		if (cs.length() > length) {
			return false;
		}
		String regex = "^[\u4E00-\u9FA5]+(?:·|[\u4E00-\u9FA5]+)*([\u4E00-\u9FA5])$";
		return StringUtil.verifyString(cs, regex);
	}

	/**
	 * 校验QQ号 长度为5-11
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyQQNo(String cs) {
		if (StringUtil.isBlank(cs)) {
			return false;
		}
		String regex = "^[1-9]\\d{4,10}$";
		return StringUtil.verifyString(cs, regex);
	}

	/**
	 * 校验身份证号
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyIdNo(String cs) {
		if (StringUtil.isBlank(cs)) {
			return false;
		}
		cs = cs.toLowerCase();
		String regex = "((^\\d{15}$)|(^\\d{17}(?:\\d|x)$))";
		return StringUtil.verifyString(cs, regex);
	}

	/**
	 * 校验电话码
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyPhone(String cs) {
		if (StringUtil.isBlank(cs)) {
			return false;
		}
		String regex = "^1((3[0-9])|(4[0-9])|(5[0-9])|(7[0-9])|(8[0-9]))\\d{8}$";
		return StringUtil.verifyString(cs, regex);
	}

	/**
	 * 验证字符串的最小和最大长度, 包含边界值
	 * 
	 * @param cs
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean verifyCsLength(String cs, int minLength, int maxLength) {
		if (cs == null) {
			return false;
		}
		if (cs.length() >= minLength && cs.length() <= maxLength)
			return true;

		return false;
	}

	/**
	 * 验证字符串的最小值, 包含边界值
	 * 
	 * @param cs
	 * @param minLength
	 * @return
	 */
	public static boolean verifyCsMinLength(String cs, int minLength) {
		if (cs == null) {
			return false;
		}
		if (cs.length() >= minLength)
			return true;

		return false;
	}

	/**
	 * 验证字符串的最大长度, 包含边界值
	 * 
	 * @param cs
	 * @param maxLength
	 * @return
	 */
	public static boolean verifyCsMaxLength(String cs, int maxLength) {
		if (cs == null) {
			return false;
		}
		if (cs.length() <= maxLength)
			return true;

		return false;
	}

	/**
	 * 验证数字的大小,包含边界值
	 * 
	 * @param cs
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean verifyNumberScale(String cs, long min, long max) {
		if (verifyNumber(cs) && Long.valueOf(cs) >= min && Long.valueOf(cs) <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 验证数字的最小值，包含边界值
	 * 
	 * @param cs
	 * @param min
	 * @return
	 */
	public static boolean verifyNumberMin(String cs, long min) {
		if (verifyNumber(cs) && Long.valueOf(cs) >= min) {
			return true;
		}
		return false;
	}

	/**
	 * 验证数字的最大值，包含边界值
	 * 
	 * @param cs
	 * @param min
	 * @return
	 */
	public static boolean verifyNumberMax(String cs, long max) {
		if (verifyNumber(cs) && Long.valueOf(cs) <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否是数字
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean verifyNumber(String cs) {
		String regex = "(0)|([1-9]\\d*)";
		return StringUtil.verifyString(cs, regex);
	}

	public static boolean verifyEmail(String cs) {
		// String regex =
		// "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
		String regex = "^\\w(?:(.\\w+)|(-\\w+))*\\w+@\\w+(?:(.\\w+)|(-\\w+))*(\\.\\w{2,3})$";
		return StringUtil.verifyString(cs, regex);
	}

	// public static void main(String args[]) {
	// // System.out.println(verifyName("阿沛·阿旺晋美·阿利乌"));
	// // System.out.println(verifyQQNo("123456789101"));
	// // System.out.println(verifyIdNo("610526198710154"));
	// // System.out.println(verifyEmail("18578444@qq.xinwei.com"));
	// System.out.println(verifyPhone("17182707716"));
	// }
}
