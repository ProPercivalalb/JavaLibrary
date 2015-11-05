package javalibrary;

import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

/**
 * @author Alex Barter (10AS)
 */
public interface Output {

	public void print(String text, Object... format);
	
	public void println(String text, Object... format);
	
	public static class Default implements Output {

		@Override
		public void print(String text, Object... format) {
			System.out.print(String.format(text, format));
		}

		@Override
		public void println(String text, Object... format) {
			System.out.println(String.format(text, format));
			
		}
	}
	
	public static class TextComponent implements Output {

		public JTextArea textComponent;
		
		public TextComponent(JTextArea textComponent) {
			this.textComponent = textComponent;
		}
		
		@Override
		public void print(String text, Object... format) {
			this.textComponent.append(String.format(text, format));
	        this.textComponent.setCaretPosition(this.textComponent.getText().length());
		}

		@Override
		public void println(String text, Object... format) {
			this.print(text + "\n", format);
			
		}
	}
}
