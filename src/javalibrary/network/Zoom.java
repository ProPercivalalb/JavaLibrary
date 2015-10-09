package javalibrary.network;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

import javalibrary.util.RandomUtil;

public class Zoom extends JFrame {

	private static DrawingPanel p;
	private double factor = 1;
	private int currentX, currentY, oldX, oldY;
	private int moveX, moveY;
	public NodeShape startNode;
	public int holdX, holdY, currentHoldX, currentHoldY;
	public static boolean controlDown = false;
	
	public NodeShape nodeAbove;
	
	public static JTable tbl;
	public static DefaultTableModel dtm;
	
	public int currentId = 0;
	public ArrayList<ArcShape> tableOrder = new ArrayList<ArcShape>();
	
	public Zoom() {
		super("ZOOM");

		p = new DrawingPanel();
		add(p, BorderLayout.CENTER);

		Box box = Box.createVerticalBox();

		//box.add(Box.createHorizontalGlue());
		
		
		

		// create object of table and table model
		tbl = new JTable() {
			@Override
            public TableCellEditor getCellEditor(final int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                
                final JTextField textfield = new JTextField();
                ((AbstractDocument) textfield.getDocument()).setDocumentFilter(new DocumentFilter() {

                	 public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                	      String text, AttributeSet attr) throws BadLocationException {
                		 if(textfield.getText().indexOf('.') != -1 && text.contains(".")) {
                			 text = text.replaceAll(".", "");
                		 }
                	           fb.insertString(offset, text.replaceAll("[^0-9.]", ""), attr);   
                	 }
          
                	
                });
                
                textfield.getDocument().addDocumentListener(new DocumentListener() {
                	@Override
                	public void changedUpdate(DocumentEvent event) {
                		
                	}
                	@Override
                	public void removeUpdate(DocumentEvent event) {
                		try {
	                		double v = Double.parseDouble(textfield.getText());
							tableOrder.get(row).distance = v;
							p.repaint();
                		}
                		catch(NumberFormatException e) {
                			tableOrder.get(row).distance = 0;
							p.repaint();
                		}
                	}
                	@Override
                	public void insertUpdate(DocumentEvent event) {
                		try {
	                		double v = Double.parseDouble(textfield.getText());
							tableOrder.get(row).distance = v;
							p.repaint();
                		}
                		catch(NumberFormatException e) {
                			tableOrder.get(row).distance = 0;
							p.repaint();
                		}
                	}
                });
                
                if (modelColumn == 2)
                    return new DefaultCellEditor(textfield);
                else
                    return super.getCellEditor(row, column);
  
            }
		};
		
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.getTableHeader().setResizingAllowed(false);
		tbl.setRowHeight(20);
		tbl.setAutoCreateColumnsFromModel(true);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane scrollPane = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setPreferredSize(new Dimension(225, 500));
		dtm = new DefaultTableModel(0, 0) {
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 2;
			}
		};
		
		dtm.setColumnIdentifiers(new String[] {"Node", "Node", "Distance"});
		tbl.setModel(dtm);
		
		box.add(scrollPane);
		box.add(Box.createVerticalStrut(15));
		JButton shortestPath = new JButton("Shortest Path");
		shortestPath.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				JButton source = (JButton)event.getSource();
				NetworkBase base = p.getNetworkBase();
				SpanningTree spanningTree = SpanningTree.findMinSpanningTree(base, Algorithm.PRIM);
				for(Arc arc : spanningTree.CONNECTIONS) {
					ArcShape finalShape = null;
					for(ArcShape shape : p.arcs) {
						if((shape.n1.id == arc.id1 && shape.n2.id == arc.id2) || (shape.n1.id == arc.id2 && shape.n2.id == arc.id1)) {
							finalShape = shape;
							break;
						}
					}
					finalShape.pathHighlight = true;
				}
				p.repaint();
				spanningTree.print();
		    }
		});
		box.add(shortestPath);
		add(box, BorderLayout.SOUTH);

		add(scrollPane, BorderLayout.EAST);
		
		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
		    public void valueChanged(ListSelectionEvent lse) {
		        for(ArcShape arc : p.arcs) {
					boolean foundArc = false;
					for(int row : tbl.getSelectedRows()) {
						int id1 = (int)tbl.getValueAt(row, 0);
						int id2 = (int)tbl.getValueAt(row, 1);
							
						if(arc.n1.id == id1 && arc.n2.id == id2 || arc.n1.id == id2 && arc.n2.id == id1) {
							arc.highlight = true;
							foundArc = true;
						}
					}
						
					if(!foundArc)
						arc.highlight = false;
				}
					
				p.repaint();
		    }
		});

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
			    moveX += (int)((endX - startX) * factor);
			    moveY += (int)((endY - startY) * factor);
			    currentHoldX = (int)((currentX - moveX) / factor);
				currentHoldY = (int)((currentY - moveY) / factor);
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
				else if(SwingUtilities.isRightMouseButton(event) && controlDown && nodeAbove != null) {
					int newX = (int)((currentX - moveX) / factor);
					int newY = (int)((currentY - moveY) / factor);
					
					boolean tooClose = false;
					
					for(NodeShape node : p.nodes) {
						if(nodeAbove == node) continue;
						if(Math.pow(node.x - newX, 2) + Math.pow(node.y - newY, 2) <= Math.pow(6 * 2, 2)) {
							tooClose = true;
							break;
						}
					}
					
					if(!tooClose) {
						nodeAbove.x = newX;
						nodeAbove.y = newY;
					}
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
					
					for(NodeShape node : p.nodes) {
						if(Math.abs(node.x - scaledX) < 6 && Math.abs(node.y - scaledY) < 6) {
							startNode = node;
							break;
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
					
					for(NodeShape node : p.nodes) {
						if(Math.pow(node.x - scaledX, 2) + Math.pow(node.y - scaledY, 2) <= Math.pow(6 * 2, 2)) {
							tooClose = true;
							break;
						}
					}
					
					if(!tooClose) {
						p.nodes.add(new NodeShape(scaledX, scaledY, currentId++));
						p.repaint();
					}
				}
				else if(SwingUtilities.isRightMouseButton(event) && controlDown) {
					int scaledX = (int)((oldX - moveX) / factor);
					int scaledY = (int)((oldY - moveY) / factor);
					
					for(NodeShape node : p.nodes) {
						if(Math.pow(node.x - scaledX, 2) + Math.pow(node.y - scaledY, 2) <= Math.pow(6, 2)) {
							nodeAbove = node;
							break;	
						}
					}
				}
			}


			@Override
			public void mouseReleased(MouseEvent event) {
				if(SwingUtilities.isLeftMouseButton(event) && controlDown) {
					NodeShape endNode = null;
					
			
					
					for(NodeShape node : p.nodes) {
						if(Math.pow(node.x - currentHoldX, 2) + Math.pow(node.y - currentHoldY, 2) <= Math.pow(6, 2)) {
							endNode = node;
							break;
						}
					}
					
					if(startNode != null && endNode != null && startNode != endNode) {
						ArcShape arc = new ArcShape(startNode, endNode, 10);
						p.arcs.add(arc);
						
						tableOrder.add(arc);
					    dtm.addRow(new Object[] {startNode.id, endNode.id, "10"});
					    //dtm.fireTableDataChanged();
					}
					
					startNode = null;
					holdX = 0;
					holdY = 0;
					currentHoldX = 0;
					currentHoldY = 0;
					p.repaint();
				}
				else if(SwingUtilities.isRightMouseButton(event) && controlDown) {
					nodeAbove = null;
				}
				else if(SwingUtilities.isMiddleMouseButton(event)) {
					int scaledX = (int)((oldX - moveX) / factor);
					int scaledY = (int)((oldY - moveY) / factor);
					
					NodeShape nodeDelete = null;
					
					for(NodeShape node : p.nodes) {
						if(Math.pow(node.x - scaledX, 2) + Math.pow(node.y - scaledY, 2) <= Math.pow(6, 2)) {
							nodeDelete = node;
							break;
						}
					}
					
					if(nodeDelete != null) {
						p.nodes.remove(nodeDelete);
						List<ArcShape> toRemove = new ArrayList<ArcShape>();
						for(ArcShape arc : p.arcs) {
							if(arc.n1 == nodeDelete || arc.n2 == nodeDelete) {
								toRemove.add(arc);
								int row = 0;
								for(; row < tbl.getRowCount(); row++) {
									int id1 = (int)tbl.getValueAt(row, 0);
									int id2 = (int)tbl.getValueAt(row, 1);
									
									if(arc.n1.id == id1 && arc.n2.id == id2 || arc.n1.id == id2 && arc.n2.id == id1) {
										dtm.removeRow(row);
		
										break;
									}
								}
							}
						}
						p.arcs.removeAll(toRemove);
						tableOrder.removeAll(toRemove);
						p.repaint();
					}
				}
			}
		});
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				
				boolean before = controlDown;
				controlDown = e.isShiftDown();
				if(before != controlDown)
					p.repaint();
		    	return false;
		    }
		});
		
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		//this.pack();
		this.setVisible(true);
	}

	public class DrawingPanel extends JPanel {

		private List<NodeShape> nodes = new ArrayList<NodeShape>();
		private List<ArcShape> arcs = new ArrayList<ArcShape>();
		
		public DrawingPanel() {
			this.setBorder(BorderFactory.createEtchedBorder());
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//g2.drawLine(moveX, moveY - 1000, moveX, moveY + 1000);
			//g2.drawLine(moveX - 1000, moveY, moveX + 1000, moveY);
			
			if(startNode != null)
				g2.drawLine((int)(startNode.x * factor) + moveX, (int)(startNode.y * factor) + moveY, (int)(currentHoldX * factor) + moveX, (int)(currentHoldY * factor) + moveY);
			
			Dimension dim = this.getSize();
			int width = dim.width;
			int height = dim.height;
			
			Double d = this.getTotalDistance();
			String s = d.longValue() == d ? "" + d.longValue() : "" + d; 
			
			g2.drawString(s, 10, 20);
			
			for(ArcShape arc : p.arcs) {
				g2.setColor(Color.black);
				arc.drawShape(this, g2, factor, moveX, moveY);
			}
			
			for(NodeShape node : p.nodes) {
				g2.setColor(Color.black);
				node.drawShape(this, g2, factor, moveX, moveY);
			}
		}
		
		public double getTotalDistance() {
			double distance = 0;
			for(ArcShape arc : this.arcs)
				distance += arc.distance;
			return distance;
		}
		
		public NetworkBase getNetworkBase() {
			NetworkBase base = new NetworkBase();
			for(NodeShape node : this.nodes)
				base.addNode(new Node(node.id));
			
			for(ArcShape arc : this.arcs)
				base.addArc(new Arc(arc.n1.id, arc.n2.id, arc.distance));
			
			return base;
		}
	}
	
	public static interface GraphShape {
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY);
	}
	
	public static class NodeShape implements GraphShape {
		
		public int x, y;
		public int id;
		
		public NodeShape(int x, int y, int id) {
			this.x = x;
			this.y = y;
			this.id = id;
		}

		@Override
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY) {
			Dimension dim = panel.getSize();
			int width = dim.width;
			int height = dim.height;
			int squareWidth = (int)(12 * scaleFactor);
			int renderX = (int)(this.x * scaleFactor) + moveX - squareWidth / 2;
			int renderY = (int)(this.y * scaleFactor) + moveY - squareWidth / 2;
			if(controlDown) {
				//graphics.setColor(Color.green);
			}
			graphics.fillOval(renderX, renderY, squareWidth, squareWidth);
			graphics.setColor(Color.black);
			graphics.setFont(graphics.getFont().deriveFont((float) (12.0F * scaleFactor)));
			graphics.drawString("" + this.id, renderX - (int)(2 * scaleFactor), renderY - (int)(1 * scaleFactor));
		}
	}
	
	public static class ArcShape implements GraphShape {
		
		public NodeShape n1, n2;
		public double distance;
		public boolean highlight;
		public boolean pathHighlight;
		
		public ArcShape(NodeShape n1, NodeShape n2, int distance) {
			this.n1 = n1;
			this.n2 = n2;
			this.distance = distance;
		}

		@Override
		public void drawShape(DrawingPanel panel, Graphics2D graphics, double scaleFactor, int moveX, int moveY) {
			Dimension dim = panel.getSize();
			graphics.setColor(Color.black);
			int width = dim.width;
			int height = dim.height;
		
			int x1 = (int)(n1.x * scaleFactor) + moveX;
			int y1 = (int)(n1.y * scaleFactor) + moveY;
			int x2 = (int)(n2.x * scaleFactor) + moveX;
			int y2 = (int)(n2.y * scaleFactor) + moveY;
			
			Stroke stroke = graphics.getStroke();
			if(pathHighlight) {
				graphics.setStroke(new BasicStroke((int)(4 * scaleFactor)));
				graphics.setColor(Color.yellow);
			}
			else if(highlight) {
				graphics.setStroke(new BasicStroke((int)(4 * scaleFactor)));
				graphics.setColor(Color.red);
			}
			graphics.drawLine(x1, y1, x2, y2);
			graphics.setStroke(stroke);
			
			graphics.setColor(Color.black);
			graphics.setFont(graphics.getFont().deriveFont((float) (6.0F * scaleFactor)));
			Double d = this.distance;
			String s = d.longValue() == d ? "" + d.longValue() : "" + d; 
			graphics.drawString("" + s, Math.min(x1, x2) + Math.abs(x1 - x2) / 2 - (int)(6 * scaleFactor), Math.min(y1, y2) + Math.abs(y1 - y2) / 2 +  (int)(2 * scaleFactor));
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Zoom(); 
	}

}