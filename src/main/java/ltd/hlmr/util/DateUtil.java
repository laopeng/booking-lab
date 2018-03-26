package ltd.hlmr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created on 2017/1/5 8:59
 *
 * @author ChenZhiqiang
 */
public final class DateUtil {

	private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static final String DEFAULT_DATE2_PATTERN = "yyyyMMdd";
	private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private DateUtil() {
		// private construct
	}

	/**
	 * 获取当前格式化后的时间("yyyy-MM-dd HH:mm:ss")
	 * 
	 * @return
	 */
	public static String now() {
		return format(LocalDateTime.now());
	}

	/**
	 * 获取当前格式化后的时间
	 * 
	 * @return
	 */
	public static String now(String pattern) {
		return format(LocalDateTime.now(), pattern);
	}

	public static String nowDate() {
		return formatDate(LocalDate.now());
	}

	public static String nowDate(String pattern) {
		return formatDate(LocalDate.now(), pattern);
	}

	public static String nowTime() {
		return formatTime(LocalTime.now());
	}

	public static String nowTime(String pattern) {
		return formatTime(LocalTime.now(), pattern);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String format(LocalDateTime dateTime) {
		return format(dateTime, DEFAULT_DATETIME_PATTERN);
	}

	public static String formatSimple(LocalDateTime dateTime) {
		return format(dateTime, DEFAULT_DATE2_PATTERN);
	}

	public static String format(LocalDateTime dateTime, String pattern) {
		return dateTime.format(formatter(pattern));
	}

	public static String format(Date date, String pattern) {
		return format(toLocalDateTime(date), pattern);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(LocalDate date) {
		return formatDate(date, DEFAULT_DATE_PATTERN);
	}

	public static String formatDate(LocalDate date, String pattern) {
		return date.format(formatter(pattern));
	}

	public static String formatTime(LocalTime time) {
		return formatTime(time, DEFAULT_TIME_PATTERN);
	}

	public static String formatTime(LocalTime time, String pattern) {
		return time.format(formatter(pattern));
	}

	public static LocalDateTime parse(String text, String pattern) {
		return LocalDateTime.parse(text, formatter(pattern));
	}

	public static LocalDate parseDate(String text, String pattern) {
		return LocalDate.parse(text, formatter(pattern));
	}

	public static LocalTime parseTime(String text, String pattern) {
		return LocalTime.parse(text, formatter(pattern));
	}

	public static Date parseAndToDate(String text, String pattern) {
		return toDate(parse(text, pattern));
	}

	public static Date parseDateAndToDate(String text, String pattern) {
		return toDate(parseDate(text, pattern));
	}

	public static Date parseTimeAndToDate(String text, String pattern) {
		return toDate(parseTime(text, pattern));
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static LocalDate toLocalDate(Date date) {
		return toLocalDateTime(date).toLocalDate();
	}

	public static LocalTime toLocalTime(Date date) {
		return toLocalDateTime(date).toLocalTime();
	}

	public static Date toDate(LocalDateTime dateTime) {
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalDate date) {
		return toDate(date.atStartOfDay());
	}

	public static Date toDate(LocalTime time) {
		return toDate(LocalDateTime.of(LocalDate.now(), time));
	}

	private static DateTimeFormatter formatter(String pattern) {
		return DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
	}

	/**
	 * 判断两个时间是否同一周
	 * 
	 * @param day
	 * @param other
	 * @return
	 */
	public static boolean inSameWeek(LocalDate day, LocalDate other) {
		if (day.isEqual(other)) {
			return true;
		}
		LocalDate before;
		LocalDate after;
		if (day.isBefore(other)) {
			before = day;
			after = other;
		} else {
			before = other;
			after = day;
		}

		int diff = 0;
		switch (before.getDayOfWeek()) {
		case MONDAY:
			diff = 7;
			break;
		case TUESDAY:
			diff = 6;
			break;
		case WEDNESDAY:
			diff = 5;
			break;
		case THURSDAY:
			diff = 4;
			break;
		case FRIDAY:
			diff = 3;
			break;
		case SATURDAY:
			diff = 2;
			break;
		case SUNDAY:
			diff = 1;
			break;
		}
		return before.plusDays(diff).isAfter(after);
	}

	/**
	 * 当前月份的第一天日期
	 * 
	 * @return
	 */
	public static LocalDate firstDayOfCurrentMonth() {
		return LocalDate.now().withDayOfMonth(1);
	}

	/**
	 * 当前周的第一天日期
	 * 
	 * @return
	 */
	public static LocalDate firstDayOfCurrentWeek() {
		return dayOfCurrentWeek(1);
	}

	/**
	 * 当前周的最后一天日期
	 * 
	 * @return
	 */
	public static LocalDate lastDayOfCurrentWeek() {
		return dayOfCurrentWeek(7);
	}

	public static LocalDate dayOfCurrentWeek(int dayOfWeek) {
		return LocalDate.now().with(WeekFields.ISO.dayOfWeek(), dayOfWeek);
	}

	/**
	 * 获取时间的最后一刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateLastTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 返回岁数（月数）
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int getAgeMonths(String birthDay) {
		int ageMonths = 0;
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dateOfBirth = sdf.parse(birthDay);
			if (dateOfBirth != null) {
				now.setTime(new Date());
				born.setTime(dateOfBirth);
				if (born.after(now)) {
					// throw new IllegalArgumentException("年龄不能超过当前日期");
					return 0;
				}
				age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
				int nowMonth = now.get(Calendar.MONTH) + 1;
				int bornMonth = born.get(Calendar.MONTH) + 1;
				int nowDay = now.get(Calendar.DAY_OF_MONTH);
				int bornDay = born.get(Calendar.DAY_OF_MONTH);

				if (nowMonth < bornMonth) {
					if (nowDay < bornDay)
						ageMonths = (age - 1) * 12 + (nowMonth + 12 - bornMonth - 1);
					else
						ageMonths = (age - 1) * 12 + (nowMonth + 12 - bornMonth);
				} else if (nowMonth >= bornMonth) {
					if (nowDay < bornDay)
						ageMonths = age * 12 + (nowMonth - bornMonth - 1);
					else
						ageMonths = age * 12 + (nowMonth - bornMonth);
				}

			} else {
				return -1;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		return ageMonths;
	}

	/**
	 * 显示年龄 >=18岁，显示*岁 1岁=<X<18岁，显示*岁*月 大约等于1个月小于1岁，显示*月*天 小于1个月，显示*天
	 * 
	 * @param birthDay
	 * @return
	 */
	public static String getAgeView(String birthDay) {
		String retVal = "";
		int ageMonths = 0;
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dateOfBirth = sdf.parse(birthDay);
			if (dateOfBirth != null) {
				now.setTime(new Date());
				born.setTime(dateOfBirth);
				if (born.after(now)) {
					throw new IllegalArgumentException("年龄不能超过当前日期");
				}
				age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
				int nowMonth = now.get(Calendar.MONTH) + 1;
				int bornMonth = born.get(Calendar.MONTH) + 1;
				int nowDay = now.get(Calendar.DAY_OF_MONTH);
				int bornDay = born.get(Calendar.DAY_OF_MONTH);
				int preMonthDays = 0;

				if (nowMonth < bornMonth) {
					if (nowDay < bornDay)
						ageMonths = (age - 1) * 12 + (nowMonth + 12 - bornMonth - 1);
					else
						ageMonths = (age - 1) * 12 + (nowMonth + 12 - bornMonth);
				} else if (nowMonth >= bornMonth) {
					if (nowDay < bornDay)
						ageMonths = age * 12 + (nowMonth - bornMonth - 1);
					else
						ageMonths = age * 12 + (nowMonth - bornMonth);
				}

				// 大于等于18岁的显示岁数，不显示月数
				if (ageMonths >= 12 * 18)
					retVal = (int) Math.floor(ageMonths / 12) + "岁";
				// 大于等于1岁小于18岁
				if (ageMonths >= 12 && ageMonths < 12 * 18)
					if (ageMonths % 12 == 0)
						retVal = (int) Math.floor(ageMonths / 12) + "岁";
					else
						retVal = (int) Math.floor(ageMonths / 12) + "岁" + ageMonths % 12 + "月";

				// 大于等于1个月，小于1岁
				if (ageMonths < 12)
					if (ageMonths > 0) {
						if (nowDay == bornDay)
							retVal = ageMonths + "月";
						else {
							if (nowDay < bornDay) {
								if (nowMonth == 1) {
									// 取出生月的天数
									preMonthDays = getDaysByYearMonth(born.get(Calendar.YEAR), 12);
									retVal = ageMonths + "月" + (preMonthDays - bornDay + 1 + nowDay) + "天";
								} else {
									preMonthDays = getDaysByYearMonth(born.get(Calendar.YEAR), bornMonth);
									retVal = ageMonths + "月" + (preMonthDays - bornDay + 1 + nowDay) + "天";
								}
							} else
								retVal = ageMonths + "月" + (nowDay - bornDay) + "天";
						}
					} else {
						if (bornMonth == nowMonth)
							retVal = (nowDay - bornDay + 1) + "天";
						else if (nowMonth == 1) {
							// 取出生月的天数
							preMonthDays = getDaysByYearMonth(born.get(Calendar.YEAR), 12);
							retVal = (preMonthDays - bornDay + 1 + nowDay) + "天";
						} else {
							preMonthDays = getDaysByYearMonth(born.get(Calendar.YEAR), bornMonth);
							retVal = (preMonthDays - bornDay + 1 + nowDay) + "天";
						}
					}
			} else {
				return "";
			}
		} catch (ParseException e) {
			// e.printStackTrace();
			return "";
		}
		return retVal;
	}

	/**
	 * 月份转化为岁数
	 * 
	 * @param ageMonths
	 * @return
	 */
	public static int getAge(int ageMonths) {
		int retVal = (int) Math.floor(ageMonths / 12);
		return retVal;
	}

	/**
	 * 获取当月的 天数
	 */
	public static int getCurrentMonthDay() {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据日期 找到对应日期的 星期
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;

		} catch (Exception e) {
			System.out.println("错误!");
		}
		return dayOfweek;
	}
}
