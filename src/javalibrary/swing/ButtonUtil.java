package javalibrary.swing;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

public class ButtonUtil {

	public static JButton setButtonSizeToIconSize(JButton button, Icon icon) {
		Dimension dimension = new Dimension(icon.getIconWidth(), icon.getIconHeight());
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
