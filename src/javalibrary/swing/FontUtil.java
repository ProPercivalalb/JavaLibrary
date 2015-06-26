package javalibrary.swing;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class FontUtil {

	public static void setGlobalUIFont(String name) {
		Enumeration keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource)value;
		        Font font = new Font(name, orig.getStyle(), orig.getSize());
		        UIManager.put(key, new FontUIResource(font));
			}
		}
	}
}
