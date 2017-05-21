package com.explorer2.strategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {
	public static String toTime(String time) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(time.startsWith("\"")&&time.endsWith("\"")){
			time=time.substring(1, time.length()-1);
		}
		if (time.endsWith("前")) {
			if (time.substring(time.length() - 3, time.length()).equals("小时前")) {
				if ((date.getHours() - Integer.parseInt(time.substring(0, time.length() - 3))) >= 0)
					calendar.add(Calendar.DAY_OF_MONTH, 0);
				else
					calendar.add(Calendar.DAY_OF_MONTH, -1);
			} else {
				if ((date.getMonth() - Integer.parseInt(time.substring(0, time.length() - 3))) > 0)
					calendar.add(Calendar.DAY_OF_YEAR, 0);
				else
					calendar.add(Calendar.DAY_OF_YEAR, -1);
			}

		} else if (time.equals("今天")) {
			calendar.add(Calendar.DAY_OF_MONTH, 0);
		} else if (time.equals("昨天")) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		} else if (time.equals("前天")) {
			calendar.add(Calendar.DAY_OF_MONTH, -2);
		} else if (time.equals("刚刚")) {
			calendar.add(Calendar.DAY_OF_MONTH, 0);
		} else {
			return time;
		}
		date = calendar.getTime();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(date);
	}
}
