package com.fc.pay.bill.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类
 * 
 * @author zhanjq
 *
 */
public class DateUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
	
	private static final SimpleDateFormat date_format_hyphen = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat date_format_default = new SimpleDateFormat("yyyyMMdd");
	
	private static final SimpleDateFormat date_format_year = new SimpleDateFormat("yyyy");
	
	private static final SimpleDateFormat date_format_MMdd = new SimpleDateFormat("MMdd");
	
	private static final SimpleDateFormat time_format_default = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final SimpleDateFormat time_format_hyphen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat time_format_unionpay = new SimpleDateFormat("MMDDhhmmss");

	private DateUtil() {
		super();
	}

	/***
	 * 查询当前小时
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.HOUR_OF_DAY); // 获取当前小时
	}

	/***
	 * 查询当前分钟
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MINUTE); // 获取当前分钟
	}

	/***
	 * 获取几天前的日期格式
	 * 
	 * @param dayNum
	 * @return
	 */
	public static String getDateByDayNum(int dayNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -dayNum);
		String result = date_format_hyphen.format(cal.getTime());
		return result;
	}

	/**
	 * 计算 day天后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int intervalDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 不同年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}
			return timeDistance + (day2 - day1);
		}
		//同一年 
		else {
			//System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
	
	/**
	 * 日期格式化：yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String makeHyphenDateFormat(Date date){
		return date_format_hyphen.format(date);
	}
	
	/**
	 * 日期格式化：yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String makeDefaultDateFormat(Date date){
		return date_format_default.format(date);
	}
	
	/**
	 * 日期格式化：MMdd
	 * @param date
	 * @return
	 */
	public static String makeMMddDateFormat(Date date){
		return date_format_MMdd.format(date);
	}

	/**
	 * 时间格式化：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String makeHyphenTimeFormat(Date date){
		return time_format_hyphen.format(date);
	}
	
	/**
	 * 时间格式化：yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String makeDefaultTimeFormat(Date date){
		return time_format_default.format(date);
	}
	
	/**
	 * 解析时间值
	 * @param timeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseHyphenTimeContent(String timeStr) throws ParseException{
		return time_format_hyphen.parse(timeStr);
	}
	
	/**
	 * 解析时间值
	 * @param timeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDefaultTimeContent(String timeStr) throws ParseException{
		return time_format_default.parse(timeStr);
	}
	
	/**
	 * 解析日期值
	 * @param yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDefaultDateContent(String yyyyMMdd) throws ParseException {
		return date_format_default.parse(yyyyMMdd);
	}
	
	/**
	 * 解析时间值
	 * @param timeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseUnionpayTime(String timeStr) throws ParseException{		
		int length = timeStr.length();
		//eg: 12342050316
		int dayEnd = length - 6;
		Calendar calendar=Calendar.getInstance(); 
		//log.debug("yyyy>>>"+Integer.parseInt(date_format_year.format(new Date())));
		//log.debug("DD>>>"+timeStr.substring(2, dayEnd));
		//log.debug("hh>>>"+timeStr.substring(dayEnd, dayEnd+2));
		//log.debug("mm>>>"+timeStr.substring(dayEnd+2, dayEnd+4));
		//log.debug("ss>>>"+timeStr.substring(dayEnd+4));
		calendar.set(Calendar.YEAR, Integer.parseInt(date_format_year.format(new Date())));//设置当前年份
		calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(timeStr.substring(2, dayEnd)));
		calendar.set(Calendar.HOUR, Integer.parseInt(timeStr.substring(dayEnd, dayEnd+2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeStr.substring(dayEnd+2, dayEnd+4)));
		calendar.set(Calendar.SECOND, Integer.parseInt(timeStr.substring(dayEnd+4)));
		return calendar.getTime();
	}
	
	
	public static final void main(String[] args) throws ParseException{
		String timeStr = time_format_unionpay.format(new Date());
		log.info("当前时间[MMDDhhmmss]>>>"+timeStr);		
		log.info("默认时间[yyyyMMddHHmmss]>>>"+time_format_default.format(parseUnionpayTime(timeStr)));
		String dateStr = "20161230";
		Date date = parseDefaultDateContent(dateStr);
		log.info("解析日期>>>"+date);
		log.info("格式化日期>>>"+makeHyphenDateFormat(date));
	}
	
	
}
