package uml.hotel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class PCalendar {

	private Calendar calendar = Calendar.getInstance();
	private CalendarFrame view;
	
	public PCalendar() {
		// TODO Auto-generated constructor stub
	}

	public void setView(CalendarFrame calendarFrame) {
		// TODO Auto-generated method stub
		this.view = calendarFrame;
	}

	public String getYearAndMonth() {
		Date date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	public int getFirstDayOfWeek() {
		//设置为当前月的第一天
		calendar.set(Calendar.DATE, 1);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	/**
	 * 获得当前月的最大天数
	 * @return
	 */
	public int getMaxDay() {
		return calendar.getActualMaximum(Calendar.DATE);
	}

	public int getShowingMonth(String yearAndMonth) {
		
		String[] ss = yearAndMonth.split("-");
		int month = -1;
		//单月份
		if (ss[1].indexOf("0") == 0) {
			month = Integer.parseInt(ss[1].substring(1));
		} else {
			//双月份
			month = Integer.parseInt(ss[1]);
		}
		
		return month;
	}

	
	/**
	 * 获得上一个月的日期
	 */
	public void lastMonth() {
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		view.showCalendar();
	}
	
	public void nextMonth() {
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + 1);
		view.showCalendar();
	}
}
