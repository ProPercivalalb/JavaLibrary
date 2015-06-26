package javalibrary.swing;

public class ToolTipUtil {

	public static String createMultiLined(Iterable lines) {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		for(Object line : lines)
			builder.append(line.toString() + "<br>");
		builder.replace(builder.length() - 4, builder.length(), "");
		
		builder.append("</html>");
		return builder.toString();
	}
	
}
