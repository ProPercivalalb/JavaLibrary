package javalibrary.swing.chart;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javalibrary.swing.chart.UIUpdater.IUIUpdater;

import javax.swing.JComponent;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public abstract class JChartBase extends JComponent implements IUIUpdater {

	private static final long serialVersionUID = 1L;
	public int mouseX = UIUpdater.INVALID_MARKER;
	public int mouseY = UIUpdater.INVALID_MARKER;
	public ChartList values;
	
	public JChartBase(ChartList values) {
		this.values = values;
		UIUpdater.registerInstance(this);
	}
	
	public boolean paintEmptyText(Graphics graphics, int graphX, int graphY, int graphWidth, int graphHeight, String text) {
		//If there are no values draw a string saying so
	    if(this.values.size() <= 0) {
		    Font messageFont = new Font("SansSerif", Font.ITALIC, 20);
		    FontMetrics messageFontMetrics = graphics.getFontMetrics(messageFont);
	    	
	    	String message = text;
    		int messageWidth = messageFontMetrics.stringWidth(message);
	    	
	    	int x = graphX + graphWidth / 2 - messageWidth / 2;
	    	int y = graphY + graphHeight / 2;
	    	graphics.setFont(messageFont);
	    	graphics.drawString(message, x, y);
	    	return true;
	    }
	    return false;
	}
	
	@Override
	public void updateUI() {
		this.repaint();
	}

	@Override
	public void setMousePosition(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
}
