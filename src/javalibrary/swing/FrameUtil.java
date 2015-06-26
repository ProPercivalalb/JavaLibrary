package javalibrary.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * @author Alex Barter (10AS)
 */
public class FrameUtil {

	public static final Dimension SCREEN_DIM = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Sets the frame to the centre of the screen, works on all devices
	 * @param frame The frame wanted to be repositioned
	 */
	public static void repostionToCentre(JFrame frame) {
		Dimension windowDim = frame.getSize();
		frame.setLocation(SCREEN_DIM.width / 2 - windowDim.width / 2, SCREEN_DIM.height / 2 - windowDim.height / 2);
	}
	
	public static void repostionToTopLeft(JFrame frame) {
		frame.setLocation(0, 0);
	}
	
	public static void repostionToTopRight(JFrame frame) {
		Dimension windowDim = frame.getSize();
		frame.setLocation(SCREEN_DIM.width - windowDim.width, 0);
	}
	
	public static void repostionToBottomLeft(JFrame frame) {
		Dimension windowDim = frame.getSize();
		frame.setLocation(0, SCREEN_DIM.height - windowDim.height);
	}
	
	public static void repostionToBottonRight(JFrame frame) {
		Dimension windowDim = frame.getSize();
		frame.setLocation(SCREEN_DIM.width - windowDim.width, SCREEN_DIM.height - windowDim.height);
	}
	
	public static void show(JFrame frame) {
		frame.pack();
		frame.setVisible(true);
	}
}
