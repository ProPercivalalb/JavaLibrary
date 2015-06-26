package javalibrary.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	public static String format(long epoch) {
		return format(new Date(epoch));
	}
	
	public static String format(long epoch, String format) {
		return format(new Date(epoch), format);
	}
	
	public static String format(Date date) {
		return format(date, "dd/MM/yyyy hh:mm:ss a");
	}
	
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
}
