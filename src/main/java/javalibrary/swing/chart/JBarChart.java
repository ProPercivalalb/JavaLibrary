package javalibrary.swing.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

import javalibrary.math.MathUtil;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public class JBarChart extends JChartBase {
 
	private static final long serialVersionUID = 1L;
	
	private boolean hasBarText = true;
	private Color barFillColor = Color.gray;
	private Color barOutlineColor = Color.black;
	public List<Integer> selected = new ArrayList<Integer>();
	
	public JBarChart() {
		this(new ChartList());
	}
	
	public JBarChart(ChartList values) {
		super(values);
	}

	 public BufferedImage getScreenShot() {

			    BufferedImage image = new BufferedImage(
			      this.getWidth(),
			      this.getHeight(),
			      BufferedImage.TYPE_INT_RGB
			      );
			    // call the Component's paint method, using
			    // the Graphics object of the image.
			    this.paint( image.getGraphics() ); // alternately use .printAll(..)
			    return image;
			  }
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		//Gets the size of component in which to fit the graph
		Dimension dimension = this.getSize();
		int componentWidth = dimension.width;
		int componentHeight = dimension.height;
		
		Border border = this.getBorder();
		int borderInsetsBottom = 0, borderInsetsTop = 0, borderInsetsLeft = 0, borderInsetsRight = 0;
		
		if(border != null) {
			borderInsetsBottom = border.getBorderInsets(this).bottom;
			borderInsetsTop = border.getBorderInsets(this).top;
			borderInsetsLeft = border.getBorderInsets(this).left;
			borderInsetsRight = border.getBorderInsets(this).right;
		}
		
	    //Creates the parameters in which the graph will be drawn within
	    int graphX = borderInsetsLeft;
	    int graphY = borderInsetsTop;
	    int graphWidth = componentWidth - borderInsetsRight - borderInsetsLeft;
	    int graphHeight = componentHeight - borderInsetsBottom - borderInsetsTop;
		
	    //If the data in the bar chart is empty draw a string and end this method
	    if(this.paintEmptyText(graphics, graphX, graphY, graphWidth, graphHeight, "Empty Bar Chart"))
	    	return;
	    
	    double maxValue = MathUtil.findLargestDouble(this.values.getAllValues());
	    
	    int barWidth = graphWidth / this.values.size();
	    int barGap = 4; //Should be a multiple of 2 to work well
	    int labelGap = this.hasBarText ? 14 : 2;
	    double barHeightScale = graphHeight / maxValue;
	    
	    //Tries to centre the graph
	    int graphCentreOffset = 0;
	    if(barWidth * this.values.size() < graphWidth) {
	    	int graphOffset = graphWidth - barWidth * this.values.size();
	    	if(graphOffset % 2 == 1)
	    		graphOffset -= 1;
	    	graphCentreOffset = graphOffset / 2;
	    }
	    
	    //The font for the label below the bar
	    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
	    FontMetrics labelFontMetrics = graphics.getFontMetrics(labelFont);
	    Font toolTipFont = new Font("SansSerif", Font.BOLD, 12);
	    FontMetrics toolTipFontMetrics = graphics.getFontMetrics(toolTipFont);
	    
	    //Draws the bar with the label if said so
	    for (int i = 0; i < this.values.size(); i++) {
	    	int barX = graphX + (i * barWidth) + (barGap / this.values.size()) + graphCentreOffset;
	    	int barY = graphY + graphHeight - (int)(values.get(i).getValue() * barHeightScale);
	    	int barTall = (int)((this.values.get(i).getValue()) * barHeightScale) - labelGap;
	    	
	    	//Limits how small the bar is to 4 pixels
	    	if(barTall < 4) {
	    		barY = graphY + graphHeight - labelGap - 4;
	    		barTall = 4;
	    	}
	    	
	    	graphics.setColor(this.barOutlineColor);

	    	graphics.fillRect(barX, barY, barWidth - (barGap / 2), barTall);
	    	graphics.setColor(this.barFillColor);
	    	if(selected.contains(i))
	    		graphics.setColor(new Color(202, 0, 2));
	    	graphics.fillRect(barX + 1, barY + 1, barWidth - (barGap / 2) - 2, barTall - 2);
	    
	    	if(this.hasBarText) {
	    		graphics.setFont(labelFont);
	    		int labelWidth = labelFontMetrics.stringWidth(this.values.get(i).getName());
	    		graphics.drawString(this.values.get(i).getName(), barX + (barWidth - 1) / 2 - labelWidth / 2, graphY + graphHeight - (labelGap / 2) + (labelFontMetrics.getHeight() / 2) - 1);
		    }
	    }
	    
	    if(UIUpdater.isMousePositionValid(this.mouseX, this.mouseY)) {
	    	for (int i = 0; i < this.values.size(); i++) {
		    	int barX = graphX + (i * barWidth) + (barGap / this.values.size()) + graphCentreOffset;
		    	int barY = graphY + graphHeight - (int)(values.get(i).getValue() * barHeightScale);
		    	int barTall = (int)((this.values.get(i).getValue()) * barHeightScale) - labelGap;
		    	
		    	//Limits how small the bar is to 4 pixels
		    	if(barTall < 4) {
		    		barY = graphY + graphHeight - labelGap - 4;
		    		barTall = 4;
		    	}
		    	
		    	if(this.mouseX >= barX && this.mouseX < barX + barWidth - (barGap / 2) && this.mouseY >= barY && this.mouseY < barY + barTall) {
		    		graphics.setColor(Color.white);
		    		int labelWidth = toolTipFontMetrics.stringWidth(this.values.get(i).getToolTip() + " [" + this.values.get(i).getValue() + "]");
		    		int offSet = 0;
		    		
		    		if(graphWidth <= this.mouseX + labelWidth + 6)
		    			offSet = -labelWidth - 10 - 6;
		    		
		    		graphics.fillRect(this.mouseX + 7 + offSet, this.mouseY - 10, labelWidth + 6, toolTipFontMetrics.getHeight() + 2);
		    		graphics.setColor(Color.black);
		    		graphics.setFont(toolTipFont);
		    		graphics.drawString(this.values.get(i).getToolTip() + " [" + this.values.get(i).getValue() + "]", this.mouseX + 10 + offSet, this.mouseY + 2);
		    	}
	    	}
	    }
	}
	
	/**
	 * Sets the color that will be painted inside the bar
	 * @param color The new color overriding the old one
	 * @return This instance so you can use just after the constructor
	 */
	public JBarChart setBarFillColor(Color color) {
		this.barFillColor = color;
		return this;
	}
	
	/**
	 * Sets the color that will be painted on the outside of bar
	 * @param color The new color overriding the old one
	 * @return This instance so you can use just after the constructor
	 */
	public JBarChart setBarOutlineColor(Color color) {
		this.barOutlineColor = color;
		return this;
	}
	
	/**
	 * Sets whether there is text below the bar
	 * @param hasBarText The boolean whether bar text is enabled
	 * @return This instance so you can use just after the constructor
	 */
	public JBarChart setHasBarText(boolean hasBarText) {
		this.hasBarText = hasBarText;
		return this;
	}
	
	public void setSelected(int index) {
		this.selected.add(index);
	}
	
	public void unselectAll() {
		this.selected.clear();
	}
	
	public void resetAll() {
		this.unselectAll();
		this.values.clear();
	}
}