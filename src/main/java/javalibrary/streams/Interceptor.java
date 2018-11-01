package javalibrary.streams;

import java.awt.Color;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Interceptor extends PrintStream {
	
	public JTextPane textArea;
	public Color color;
	
    public Interceptor(OutputStream out, JTextPane textArea, Color color) {
        super(out, true);
        this.textArea = textArea;
		this.color = color;
    }
    
    @Override
    public void print(String s) {
        super.print(s);
        if(this.textArea != null) {
        	this.textArea.setEditable(true);
        	
        	if(this.color == Color.red) {
	        	int index = -1;
	        	if((index = s.indexOf(": ")) != -1) {
	        		this.appendText(s.substring(0, index), new Color(65, 105, 225), true);
	        		this.appendText(s.substring(index, s.length()) + "\n", this.color, false);
	        	}
	        	else if((index = s.indexOf("(")) != -1) {
	 
	        		int lastIndex = s.indexOf(")");
	        		String className = s.substring(index + 1, lastIndex);
	        		this.appendText(s.substring(0, index + 1), this.color, false);
	        		this.appendText(className, "Unknown Source".equals(className) ? this.color : new Color(65, 105, 225), !"Unknown Source".equals(className));
	        		this.appendText(")" + "\n", this.color, false);

	        	}
	        	else
	        		this.appendText(s + "\n", this.color, false);
        	}
        	else
        		this.appendText(s + "\n", this.color, false);
        	
        	this.textArea.setEditable(false);
        }
    }
    
    public void appendText(String s, Color color, boolean underlined) {
    	StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset1 = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        AttributeSet aset2 = sc.addAttribute(aset1, StyleConstants.Underline, underlined);
        
        int len = this.textArea.getDocument().getLength();
        this.textArea.setCaretPosition(len);
        this.textArea.setCharacterAttributes(aset2, false);
        this.textArea.replaceSelection(s);
    }
}