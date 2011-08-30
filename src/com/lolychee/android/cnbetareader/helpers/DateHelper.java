package com.lolychee.android.cnbetareader.helpers;

import java.util.Date;

public class DateHelper {

	public static String xTimesAgo(Date date) {
		Date now = new Date();
		
		int times = (int) Math.ceil((now.getTime() - date.getTime()) / 1000);
		
		String string;
		
		if (times < 60) {
			string = times + "秒";
		}else if (times >= 60 && times < 3600) {
			string = ((int) Math.ceil(times/60)) + "分钟";
		}else if (times >= 3600 && times < 86400) {
			string = ((int)Math.ceil(times/3600)) + "小时";
		}else if (times >= 86400 && times < 604800) {
			string = ((int)Math.ceil(times/86400)) + "天";
		}else if (times >= 604800 && times < 2592000) {
			string = ((int)Math.ceil(times/604800)) + "周";
		}else if (times >= 2592000 && times < 31536000) {
			string = ((int)Math.ceil(times/2592000)) + "月";
		}else {
			string = ((int)Math.ceil(times/31536000)) + "年";
		}
		
		return string + "前";
		
	}

}
