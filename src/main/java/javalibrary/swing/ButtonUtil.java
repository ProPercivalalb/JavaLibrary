package javalibrary.swing;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

public class ButtonUtil {

	public static JButton setButtonSizeToIconSize(JButton button, Icon icon) {
		return setButtonSizeToIconSize(button, icon, 0);
	}
	
	public static JButton setButtonSizeToIconSize(JButton button, Icon icon, int border) {
		Dimension dimension = new Dimension(icon.getIconWidth() + border, icon.getIconHeight() + border);
		button.setMinimumSize(dimension);
		button.setMaximumSize(dimension);
		button.setPreferredSize(dimension);
		return button;
	}
	
	public static JButton createIconButton(Icon icon) {
		JButton button = new JButton(icon);
		button.setFocusPainted(false);
		return button;
	}
}
