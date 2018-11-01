package javalibrary.swing;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author Alex Barter (10AS)
 */
public class ImageUtil {

	public static ImageIcon createScaledImageIcon(String path, double scale) {
		Image img = ImageUtil.createImageIcon(path).getImage();  
		Image newImg = img.getScaledInstance((int)(img.getWidth(null) * scale), (int)(img.getHeight(null) * scale),  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
	
	public static ImageIcon createImageIcon(String path, String description) {
		URL imgURL = ImageUtil.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL, description);
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public static ImageIcon createImageIcon(String path) {
		URL imgURL = ImageUtil.class.getResource(path);
		if(imgURL != null)
			return new ImageIcon(imgURL);
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
}
