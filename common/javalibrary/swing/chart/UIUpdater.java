package javalibrary.swing.chart;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public class UIUpdater {

	public static final int INVALID_MARKER = -1;
	
	/**
	 * Checks whether the mouse position is within the component
	 * @param mouseX The x position of the mouse relative to the component
	 * @param mouseY The y position of the mouse relative to the component
	 * @return Whether both given values are not equal to {@link #INVALID_MARKER}
	 */
	public static boolean isMousePositionValid(int mouseX, int mouseY) {
		return mouseX != INVALID_MARKER && mouseY != INVALID_MARKER;
	}
	

	public static <T extends JComponent & IUIUpdater> void registerInstance(T jBarChart) {
		jBarChart.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent event) {}

			@Override
			public void mouseEntered(MouseEvent event) {}

			@Override
			public void mouseExited(MouseEvent event) {
				IUIUpdater updater = (IUIUpdater)event.getComponent();
  				JComponent component = (JComponent)event.getComponent();
  				updater.setMousePosition(UIUpdater.INVALID_MARKER, UIUpdater.INVALID_MARKER);
  				component.updateUI();
			}

			@Override
			public void mousePressed(MouseEvent event) {}

			@Override
			public void mouseReleased(MouseEvent event) {}
        	  
          });
		
		jBarChart.addMouseMotionListener(new MouseMotionListener() {
  			@Override
  			public void mouseDragged(MouseEvent event) { this.updateMousePoint(event); }

  			@Override
  			public void mouseMoved(MouseEvent event) { this.updateMousePoint(event); }
  			
  			public void updateMousePoint(MouseEvent event) {
  				IUIUpdater updater = (IUIUpdater)event.getComponent();
  				JComponent component = (JComponent)event.getComponent();
  				
  				Point point = component.getMousePosition();
  				if(point != null) {
  				    updater.setMousePosition(point.x, point.y);
  				    component.updateUI();
  				}
  			}
          });
	}
	
	public interface IUIUpdater {
		
		/**
		 * Called to update the instances mouse x & y values 
		 * @param mouseX The new x position of the mouse
		 * @param mouseY The new y position of the mouse
		 */
		public void setMousePosition(int mouseX, int mouseY);
	}
}
