package javalibrary.swing;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author Alex Barter (10AS)
 */
public class ImageUtil {

	public static ImageIcon createImageIcon(String path, String description) {
		URL imgURL = ImageUtil.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL, description);
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
}
