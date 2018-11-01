package javalibrary.swing.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javalibrary.swing.chart.ChartData;
import javalibrary.swing.chart.ChartList;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public class CircleRenderer {

	/**
	 * Draws a circle filled with the colour in the graphics instance
	 * @param graphics The {@link Graphics} which draws the shape
	 * @param xPosition The x position of the centre of the circle
	 * @param yPosition The y position of the centre of the circle
	 * @param diameter The diameter of the circle halved for the radius
	 * @return The shape instance it has drawn, can be used to figure out intersects etc...
	 */
	public static Shape drawFilledCircle(Graphics graphics, int xPosition, int yPosition, double diameter) {
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		Ellipse2D.Double hole = new Ellipse2D.Double();
		hole.width = diameter;
		hole.height = diameter;
		hole.x = xPosition - diameter / 2;
		hole.y = yPosition - diameter / 2;
		g2d.fill(hole);
		return hole;
    }
	
	/**
	 * Draws an outline of a circle in the colour in which the graphics has been set to
	 * @param graphics The {@link Graphics} which draws the shape
	 * @param xPosition The x position of the centre of the circle
	 * @param yPosition The y position of the centre of the circle
	 * @param diameter The diameter of the circle halved for the radius
	 * @return The shape instance it has drawn, can be used to figure out intersects etc...
	 */
	public static Shape drawOutlineCircle(Graphics graphics, int xPosition, int yPosition, double diameter) {
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		Ellipse2D.Double hole = new Ellipse2D.Double();
		hole.width = diameter;
		hole.height = diameter;
		hole.x = xPosition - diameter / 2;
		hole.y = yPosition - diameter / 2;
		g2d.draw(hole);
		return hole;
    }
	
	public static ArrayList<Arc2D> drawPieChart(Graphics graphics, ChartList pairs, int xPosition, int yPosition, double radius) {
		Graphics2D g2d = (Graphics2D)graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		ArrayList<Arc2D> arcs = new ArrayList<Arc2D>();
		
		double total = pairs.getTotalValues();
		
		double degreePerUnit = 360 / total;
		degreePerUnit = Math.abs(degreePerUnit);
		
		Color[] colours = {Color.gray, Color.lightGray, Color.darkGray};
		
		double last = 0D;
		for(int i = 0; i < pairs.size(); i++) {
			ChartData pair = pairs.get(i);
			
			if(pairs.size() > 1 && i == pairs.size() - 1 && colours[0] == colours[i % colours.length])
				g2d.setPaint(colours[1]);
			else
				g2d.setPaint(colours[i % colours.length]);
			
            double endDegree = pair.getValue() * degreePerUnit;
            Arc2D arc = new Arc2D.Double(xPosition - radius / 2, yPosition - radius / 2, radius, radius, last, endDegree, Arc2D.PIE);
            last = last + endDegree;
	        g2d.fill(arc);
	        g2d.setPaint(Color.black);
	        
			if(pairs.size() == 1)
				drawOutlineCircle(graphics, xPosition, yPosition, radius);
			else
		        g2d.draw(arc);
			
	        arcs.add(arc);
        }
		return arcs;
	}
}
