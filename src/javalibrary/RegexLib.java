package javalibrary;

import java.util.regex.Pattern;

public class RegexLib {
	
	public static final Pattern EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
	public static final Pattern MAIL_FUNCTION = Pattern.compile("mail\\(.*\\)");
	public static final Pattern EVAL_FUNCTION = Pattern.compile("eval\\(.*\\)");
	public static final Pattern WRITE_FUNCTION = Pattern.compile("fputs\\(.*\\)");
}
