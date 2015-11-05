package javalibrary.swing;

import javax.swing.Icon;
import javax.swing.JButton;

public class ButtonUtil {

	public static JButton createIconButton(Icon icon) {
		JButton button = new JButton(icon);
		button.setFocusPainted(false);
		return button;
	}
}
