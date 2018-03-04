package com.easyconnect.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUseUtil {
	/**
	 * @author Ice
	 */
	public static Timestamp nowTime() {
		Timestamp nowTimestamp = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		Date date = new Date();
		nowTimestamp = Timestamp.valueOf(df.format(date));

		return nowTimestamp;
	}

}
