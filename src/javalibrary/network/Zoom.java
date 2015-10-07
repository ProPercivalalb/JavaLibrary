package javalibrary.network;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javalibrary.util.RandomUtil;

public class Zoom extends JFrame {

	private DrawingPanel p;
	private double factor = 1;
	private int currentX, currentY, oldX, oldY;
	private int moveX, moveY;
	public NodeShape startNode;
	public int holdX, holdY, currentHoldX, currentHoldY;
	public boolean controlDown = false;
	
	
	public Zoom() {
		super("ZOOM");

		p = new DrawingPanel();
		add(p, BorderLayout.CENTER);

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		
		
		JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 0, 30, 10);
		framesPerSecond.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    
			        int fps = (int)source.getValue();
			        factor = fps;
			        p.repaint();
			}
			
		});
		    
		framesPerSecond.setMajorTickSpacing(10);

		//Create the label table
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("Zoom Out") );
		labelTable.put( new Integer( 30 ), new JLabel("Zoom In") );
		framesPerSecond.setLabelTable( labelTable );
		framesPerSecond.setMajorTickSpacing(10);
		framesPerSecond.setMinorTickSpacing(1);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);

		box.add(framesPerSecond);
		box.add(Box.createHorizontalGlue());
		
		
		
		add(box, BorderLayout.SOUTH);

		add(new JLabel("\n"), BorderLayout.NORTH);
		add(new JLabel("     "), BorderLayout.EAST);
		add(new JLabel("     "), BorderLayout.WEST);

		p.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
			    int notches = event.getWheelRotation();
			    double startX = (event.getX() - moveX) / factor;
			    double startY = (event.getY() - moveY) / factor;
		
			    if(notches < 0)
			    	factor += factor / 10;
			    else
			        factor -= factor / 10;
        
			    double endX = (event.getX() - moveX) / factor;
			    double endY = (event.getY() - moveY) / factor;
			    moveX +=  (int)((endX - startX) * factor);
			    moveY += (int)((endY - startY) * factor);
                p.repaint();
			}
			
		});
		
		p.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
	        public void mouseDragged(MouseEvent event) {
				currentX = event.getX();
				currentY = event.getY();
				
				if(SwingUtilities.isLeftMouseButton(event) && controlDown) {
					currentHoldX = (int)((currentX - moveX) / factor);
					currentHoldY = (int)((currentY - moveY) / factor);
				}
				else if(SwingUtilities.isRightMouseButton(event) && !controlDown) {
					moveX += (currentX - oldX) * 1;
					moveY += (currentY - oldY) * 1;
				}
				else {
					//p.squareSize.add(new ArcShape((int)((oldX - moveX) / factor), (int)((oldY - moveY) / factor), 
					//		(int)((currentX - moveX) / factor), (int)((currentY - moveY) / factor)));
				}
				oldX = currentX;
				oldY = currentY;

				p.repaint();
	        }
		});
		
		p.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				System.out.println(event.getButton());
				
				oldX = event.getX();
				oldY = event.getY();

				if(SwingUtilities.isLeftMouseButton(event) && controlDown) {
					System.out.println(oldX + " " + oldY);
					holdX = event.getX();
					holdY = event.getY();
			
					int scaledX = (int)((holdX - moveX) / factor);
					int scaledY = (int)((holdY - moveY) / factor);
					
					for(GraphShape graphShape : p.squareSize) {
						if(graphShape instanceof NodeShape) {
							NodeShape node = (NodeShape)graphShape;
							if(Math.abs(node.x - scaledX) < 6 && Math.abs(node.y - scaledY) < 6) {
								startNode = node;
								break;
							}
								
						}
					}

					holdX = (int)((holdX - moveX) / factor);
					holdY = (int)((holdY - moveY) / factor);
					currentHoldX = holdX;
					currentHoldY = holdY;
					p.repaint();
				}
				else if(SwingUtilities.isLeftMouseButton(event)) {
					int scaledX = (int)((event.getX() - moveX) / factor);
					int scaledY = (int)((event.getY() - moveY) / factor);
					
					boolean tooClose = false;
					
					for(GraphShape graphShape : p.squareSize) {
						if(graphShape instanceof NodeShape) {
							NodeShape node = (NodeShape)graphShape;
							if(Math.abs(node.x - scaledX) <= 12 && Math.abs(node.y - scaledY) <= 12) {
								System.out.println("tooclose");
								tooClose = true;
								break;
							}
								
						}
					}
					
					if(!tooClose) {
						p.squareSize.add(new NodeShape(scaledX, scaledY));
						p.repaint();
					}
				}
			}


			@Override
			public void mouseReleased(MouseEvent event) {
				if(SwingUtilities.isLeftMouseButton(event) && controlDown) {
					NodeShape endNode = null;
					
			
					
					for(GraphShape graphShape : p.squareSize) {
						if(graphShape instanceof NodeShape) {
							NodeShape node = (NodeShape)graphShape;
							if(Math.abs(node.x - currentHoldX) < 6 && Math.abs(node.y - currentHoldY) < 6) {
								endNode = node;
								break;
							}
								
						}
					}
					
					if(endNode != null)
						p.squareSize.add(new ArcShape(startNode, endNode));
					
					startNode = null;
					holdX = 0;
					holdY = 0;
					currentHoldX = 0;
					currentHoldY = 0;
					p.repaint();
				}
				
			}
		});
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				controlDown = e.isShiftDown();
		    	return false;
		    }
		});
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setVisible(true);
	}

	public class DrawingPanel extends JPanel {

		private List<GraphShape> squareSize = new ArrayList<GraphShape>();
		
		public DrawingPanel() {
			this.setBorder(BorderFactory.createEtchedBorder());
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2.drawLine(moveX, moveY - 1000, moveX, moveY + 1000);
			g2.drawLine(moveX - 1000, moveY, moveX + 1000, moveY);
			
			if(startNode != null)
				g2.drawLine((int)(startNode.x * factor) + moveX, (int)(startNode.y * factor) + moveY, (int)(currentHoldX * factor) + moveX, (int)(currentHoldY * factor) + moveY);
			
			for(int i = 0; i < this.squareSize.size(); ++i) {
				GraphShape graphShape = this.squareSize.get(i);
				
				graphShape.drawShape(this, g2, factor, moveX, moveY);
				
				g2.drawString("" + factor + " " + moveX + " " +moveY, 10, 20);
				//int squareWidth = (int)(this.squareSize.get(i) * factor);
			//	int x = (width - squareWidth) / 2 + moveX;
				//int y = (height - squareWidth) / 2 + moveY;
				//g.drawOval(x, y, squareWidth, squareWidth);
			}
		}
	}
	
	public static interface GraphShape {
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY);
	}
	
	public static class NodeShape implements GraphShape {
		
		public int x, y;
		
		public NodeShape(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY) {
			Dimension dim = panel.getSize();
			int width = dim.width;
			int height = dim.height;
			int squareWidth = (int)(12 * scaleFactor);
			int renderX = (int)(this.x * scaleFactor) + moveX - squareWidth / 2;
			int renderY = (int)(this.y * scaleFactor) + moveY - squareWidth / 2;
			graphics.fillOval(renderX, renderY, squareWidth, squareWidth);
		}
	}
	
	public static class ArcShape implements GraphShape {
		
		public NodeShape n1, n2;
		
		public ArcShape(NodeShape n1, NodeShape n2) {
			this.n1 = n1;
			this.n2 = n2;

		}

		@Override
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY) {
			Dimension dim = panel.getSize();
			int width = dim.width;
			int height = dim.height;
			graphics.drawLine((int)(n1.x * scaleFactor) + moveX, (int)(n1.y * scaleFactor) + moveY, (int)(n2.x * scaleFactor) + moveX, (int)(n2.y * scaleFactor) + moveY);
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Zoom(); 
	}

}