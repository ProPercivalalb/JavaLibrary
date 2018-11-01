package javalibrary.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class ExitAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		System.exit(128);
	}
	
	public static JMenuItem createExit(String text) {
		JMenuItem exit = new JMenuItem(text);
		exit.addActionListener(new ExitAction());
		return exit;
	}
}