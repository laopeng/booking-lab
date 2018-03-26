package ltd.hlmr.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @version 1.0
 */
public class IDGenerator {
	private static List<Integer> array = new ArrayList<>(30);

	/**
	 * @return 生成主键id（23位），生成规则:17位时间戳（精确到毫秒）+6位随机数。
	 */
	public static String getId() {
		// 获取17位时间戳
		LocalDateTime localDateTime = LocalDateTime.now();
		String tempString = DateUtil.format(localDateTime, "yyyyMMddHHmmssSSS");
		int random = (int) ((Math.random() * 9 + 1) * 100000);
		while (array.contains(random)) {// 生成30个随机数字内基本耗时超过1毫秒，能保证1毫秒内随机数字不重复
			random = (int) ((Math.random() * 9 + 1) * 100000);
		}
		tempString += random;
		array.add(random);
		if (array.size() == 30) {// 数组长度达到30个就清空
			array.clear();
		}
		return tempString;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getId());
		}
	}
}
