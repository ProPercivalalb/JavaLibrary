package javalibrary.swing.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import javax.swing.border.Border;

import javalibrary.swing.graphic.CircleRenderer;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public class JPieChart extends JChartBase {
	
	private static final long serialVersionUID = 1L;
	
	public JPieChart() {
		this(new ChartList());
	}
	
	public JPieChart(ChartList values) {
		super(values);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		//Gets the size of component in which to fit the graph
		Dimension dimension = this.getSize();
		int componentWidth = dimension.width;
		int componentHeight = dimension.height;
		
		Border border = this.getBorder();
		int borderInsetsBottom = border.getBorderInsets(this).bottom;
		int borderInsetsTop = border.getBorderInsets(this).top;
		int borderInsetsLeft = border.getBorderInsets(this).left;
		int borderInsetsRight = border.getBorderInsets(this).right;
		
	    //Creates the parameters in which the graph will be drawn within
	    int graphX = borderInsetsLeft;
	    int graphY = borderInsetsTop;
	    int graphWidth = componentWidth - borderInsetsRight - borderInsetsLeft;
	    int graphHeight = componentHeight - borderInsetsBottom - borderInsetsTop;
	    
	    if(this.paintEmptyText(graphics, graphX, graphY, graphWidth, graphHeight, "Empty Pie Chart"))
	    	return;
	    
	    Font toolTipFont = new Font("SansSerif", Font.BOLD, 12);
	    FontMetrics toolTipFontMetrics = graphics.getFontMetrics(toolTipFont);
	    
	    ArrayList<Arc2D> arcs = CircleRenderer.drawPieChart(graphics, this.values, graphX + graphWidth / 2, graphY + graphHeight / 2, Math.min(graphWidth, graphHeight) - 6);
	    int index = 0;
	    for(Arc2D arc : arcs) {
	    	if(arc.contains(this.mouseX, this.mouseY)) {
	    		graphics.setColor(Color.white);
	    		int labelWidth = toolTipFontMetrics.stringWidth(this.values.get(index).getToolTip() + " [" + (int)(double)values.get(index).getValue() + "]");
	    		int offSet = 0;
	    		
	    		if(graphWidth <= this.mouseX + labelWidth + 6)
	    			offSet = -labelWidth - 10 - 6;
	    		
	    		graphics.fillRect(this.mouseX + 7 + offSet, this.mouseY - 10, labelWidth + 6, toolTipFontMetrics.getHeight() + 2);
	    		graphics.setColor(Color.black);
	    		graphics.setFont(toolTipFont);
	    		graphics.drawString(this.values.get(index).getToolTip() + " [" + (int)(double)this.values.get(index).getValue() + "]", this.mouseX + 10 + offSet, this.mouseY + 2);
	    	}
	    	
	    	index++;
	    }
	}
}
