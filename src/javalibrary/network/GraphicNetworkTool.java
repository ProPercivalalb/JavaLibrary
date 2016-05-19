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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;

import javalibrary.math.Rounder;
import javalibrary.network.algorithm.Algorithm;
import javalibrary.network.algorithm.ChinesePostman;
import javalibrary.network.algorithm.ShortestPath;
import javalibrary.network.algorithm.SpanningTree;
import javalibrary.network.algorithm.TravellingSalesman;
import javalibrary.string.ValueFormat;
import javalibrary.swing.DocumentUtil;
import javalibrary.swing.ExitAction;
import javalibrary.swing.ImageUtil;
import javalibrary.thread.Threads;
import javalibrary.util.RandomUtil;

public class GraphicNetworkTool extends JFrame {

	private static NetworkDisplay display;
	private double factor = 1;
	private int currentX, currentY, oldX, oldY;
	private int moveX, moveY;
	public Node startNode;
	public int holdX, holdY, currentHoldX, currentHoldY;
	public static boolean controlDown = false;
	
	public Node nodeAbove;
	
	public JDialog arcListDialog;
	public static JTable tbl;
	public static DefaultTableModel dtm;
	
	public final JSlider showSpeedSlider;
	public final JMenuItem showNodeIdItem;
	public final JMenuItem showArcLengthItem;
	
	public int currentId = 0;
	public ArrayList<Arc> tableOrder = new ArrayList<Arc>();
	public ArrayList<Arc> highlightSelected = new ArrayList<Arc>();
	public ArrayList<Arc> highlightRoute = new ArrayList<Arc>();
	public ArrayList<Arc> highlightArrowRoute = new ArrayList<Arc>();
	public HashMap<Arc, List<Boolean>> arrow = new HashMap<Arc, List<Boolean>>();
	public Arc lastest;
	
	public GraphicNetworkTool() {
		super("Graphic Network Tool");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("javalibrary/network/brick.png")));
		
		
		display = new NetworkDisplay();
		add(display, BorderLayout.CENTER);
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newFile = new JMenuItem("New", ImageUtil.createImageIcon("/javalibrary/network/page_white.png", "New"));
		newFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				display.clearAll();
				
				display.repaint();
			}
		});
		JMenuItem load = new JMenuItem("Load", ImageUtil.createImageIcon("/javalibrary/network/folder_explore.png", "Edit"));
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				
				fc.setApproveButtonText("Load Network");
				int returnVal = fc.showDialog(GraphicNetworkTool.this, "Load");
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println(file);
					try {
						if(file.exists()) {
							FileInputStream fIn = new FileInputStream(file);
						    BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
			
						    display.clearAll();
		
							HashMap<String, List<Map<String, Object>>> map = new Gson().fromJson(myReader, new TypeToken<HashMap<String, List<Map<String, Object>>>>(){}.getType());
							HashMap<Integer, Node> nodeMap = new HashMap<Integer, Node>();

							List<Map<String, Object>> nodes = map.get("nodes");
							for(Map<String, Object> nodeData : nodes) {
								Node node = new Node(((Double)nodeData.get("id")).intValue(), ((Double)nodeData.get("x")).intValue(), ((Double)nodeData.get("y")).intValue());
								nodeMap.put(((Double)nodeData.get("id")).intValue(), node);
								display.getNetworkBase().addNode(node);
							}
							
							List<Map<String, Object>> edges = map.get("edges");
							for(Map<String, Object> edgeData : edges) {
								Arc arc = new Arc(((Double)edgeData.get("source")).intValue(), ((Double)edgeData.get("target")).intValue(), (Double)edgeData.get("distance"));
								display.getNetworkBase().addArc(arc);
								tableOrder.add(arc);
								dtm.addRow(new Object[] {arc.id1, arc.id2, arc.getTotalDistance()});
							}
							
							display.repaint();
						      
						    myReader.close();
						}	
					}
					catch(Exception e) {
						e.printStackTrace();
					}
			     }
			}
			
		});
		
		JMenuItem save = new JMenuItem("Save", ImageUtil.createImageIcon("/javalibrary/network/disk.png", "Save"));
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
				File workingDirectory = new File(System.getProperty("user.dir"));
				fc.setCurrentDirectory(workingDirectory);
				
				fc.setApproveButtonText("Save Network");
				int returnVal = fc.showDialog(GraphicNetworkTool.this, "Save");
				
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						if(!file.toString().endsWith(".json"))
							file = new File(file.toString() + ".json"); 
						if(!file.exists())
							file.createNewFile();
						
						BufferedWriter writer = new BufferedWriter(new FileWriter(file));
						
						HashMap<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();

						
						List<Map<String, Object>> nodeData = new ArrayList<Map<String, Object>>();
						for(Node node : display.base.NODES.values()) {
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("id", node.getId());
							data.put("x", node.x);
							data.put("y", node.y);
							nodeData.add(data);
						}
						map.put("nodes", nodeData);
						
						List<Map<String, Object>> edgesData = new ArrayList<Map<String, Object>>();
						for(Arc arc : display.base.CONNECTIONS) {
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("source", arc.id1);
							data.put("target", arc.id2);
							data.put("distance", arc.getTotalDistance());
							edgesData.add(data);
						}
						map.put("edges", edgesData);
							
						String jsonString = new Gson().toJson(map);
					        
					   	writer.append(jsonString);
					   	writer.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
			    }
			}
			
		});
		
		fileMenu.add(newFile);
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(ExitAction.createExit("Exit"));
		menuBar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem showNodeItem = new JMenuItem("Node Table");
		
		showNodeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				//arcListDialog.setVisible(true);
			}
		});
			
			
		JMenuItem showArcsItem = new JMenuItem("Arc Table");
		showArcsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				arcListDialog.setVisible(true);
			}
		});
		
		JMenuItem assignDistancesItem = new JMenuItem("Deduce Distances", ImageUtil.createImageIcon("/javalibrary/network/calculator_link.png", "Calculate"));
		assignDistancesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				
				for(Arc arc : display.base.CONNECTIONS) {
					arc.distances.clear();
					Node node1 = display.base.NODES.get(arc.id1);
					Node node2 = display.base.NODES.get(arc.id2);
					double distance = Rounder.round(Math.sqrt(Math.pow(node1.x - node2.x, 2) + Math.pow(node1.y - node2.y, 2)), 2);
					
					arc.distances.add(distance);
					tbl.setValueAt(distance, tableOrder.indexOf(arc), 2);
				}
				
				tbl.repaint();
				display.repaint();
			}
		});
		
		JMenuItem deleteLoneNodesItem = new JMenuItem("Delete Lone Nodes", ImageUtil.createImageIcon("/javalibrary/network/cross.png", "Delete"));
		deleteLoneNodesItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				for(int nodeId : new ArrayList<Integer>(display.base.NODES.keySet())) {
					if(display.base.getAllPathsConnectedToNode(nodeId).size() <= 0) {
						display.base.NODES.remove(nodeId);
					}
				}
				display.repaint();
			}
		});
		
		editMenu.add(showNodeItem);
		editMenu.add(showArcsItem);
		editMenu.addSeparator();
		editMenu.add(assignDistancesItem);
		editMenu.add(deleteLoneNodesItem);
		menuBar.add(editMenu);
		
		JMenu algorithmMenu = new JMenu("Algorithms");
		JMenuItem chinesePostmanItem = new JMenuItem("Chinese Postman");
		JMenuItem shortestPathItem = new JMenuItem("Shortest Path");
		JMenu spanningTreeItem = new JMenu("Spanning Tree");
		JMenuItem spanningTreeItemKruskal = new JMenuItem("Kruskal");
		JMenuItem spanningTreeItemPrim = new JMenuItem("Prim");
		JMenuItem travellingSalesmanItem = new JMenuItem("Travelling Salesman");
		
		spanningTreeItemPrim.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				NetworkBase base = display.getNetworkBase();
				SpanningTree spanningTree = SpanningTree.findMinSpanningTree(base, Algorithm.KRUSKAL);
				display.displayBase = base;
				for(Arc arc : spanningTree.CONNECTIONS) {
					Arc finalShape = null;
					for(Arc shape : display.base.CONNECTIONS) {
						if(shape.equals(arc)){
							finalShape = shape;
							break;
						}
					}
					highlightRoute.add(finalShape);
				}
				
				display.repaint();
		    }
		});
		
		spanningTreeItemPrim.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				NetworkBase base = display.getNetworkBase();
				SpanningTree spanningTree = SpanningTree.findMinSpanningTree(base, Algorithm.PRIM);
				display.displayBase = base;
				for(Arc arc : spanningTree.CONNECTIONS) {
					Arc finalShape = null;
					for(Arc shape : display.base.CONNECTIONS) {
						if(shape.equals(arc)){
							finalShape = shape;
							break;
						}
					}
					highlightRoute.add(finalShape);
				}
				
				display.repaint();
		    }
		});
	
		shortestPathItem.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				NetworkBase base = display.getNetworkBase();
				
				ShortestPath shortestPath = ShortestPath.findShortestPath(base, base.getSmallestNode(), base.getLargestNode(), Algorithm.DIJKSTRA);
				display.displayBase = base;
				
				List<Integer> routeIds = shortestPath.getRouteIds();
				
				for(int i = 0; i < routeIds.size() - 1; i++) {
					int id1 = routeIds.get(i);
					int id2 = routeIds.get(i + 1);
					
					Arc finalShape = null;
					boolean direction = false;
					for(Arc shape : display.base.CONNECTIONS) {
						if((shape.id1 == id1 && shape.id2 == id2)) {
							finalShape = shape;
							direction = true;
							break;
						}
						else if(shape.id1 == id2 && shape.id2 == id1) {
							finalShape = shape;
							break;
						}
					}
					if(finalShape != null) {
						highlightArrowRoute.add(finalShape);
						arrow.put(finalShape, Arrays.asList(direction));
					}
				}
				display.repaint();
				shortestPath.print();
		    }
		});
		
		chinesePostmanItem.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				Threads.runTask(new Runnable() {

					@Override
					public void run() {
						highlightRoute.clear();
						highlightArrowRoute.clear();
						arrow.clear();
						lastest = null;
						NetworkBase base = display.getNetworkBase();

						ChinesePostman chinesePostman = ChinesePostman.findRouteAll(base, base.getSmallestNodeId(), base.getSmallestNodeId());
						display.displayBase = chinesePostman;
						List<Integer> routeIds = chinesePostman.getRouteIds();
						System.out.println(routeIds);
						for(int i = 0; i < routeIds.size() - 1; i++) {
							int id1 = routeIds.get(i);
							int id2 = routeIds.get(i + 1);
							
							Arc finalShape = null;
							boolean direction = false;
							for(Arc shape : display.base.CONNECTIONS) {
								if((shape.id1 == id1 && shape.id2 == id2)) {
									finalShape = shape;
									direction = true;
									break;
								}
								else if(shape.id1 == id2 && shape.id2 == id1) {
									finalShape = shape;
									break;
								}
							}
							highlightArrowRoute.add(finalShape);
							if(!arrow.containsKey(finalShape))
								arrow.put(finalShape, new ArrayList<Boolean>());
							arrow.get(finalShape).add(direction);
							lastest = finalShape;
							display.repaint();
							
							int value = showSpeedSlider.getValue();
							
								try {
									Thread.sleep(1000 - value);
								}
								catch(InterruptedException e) {
									e.printStackTrace();
								}
							
						}
	
						
					}
					
				});
		    }
		});
		
		travellingSalesmanItem.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent event) {
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				NetworkBase base = display.getNetworkBase();
				
				TravellingSalesman travellingSalesman = TravellingSalesman.findRouteAll(base, base.getSmallestNodeId());
				display.displayBase = travellingSalesman;
				
				/**
				List<Integer> routeIds = shortestPath.getRouteIds();
				
				for(int i = 0; i < routeIds.size() - 1; i++) {
					int id1 = routeIds.get(i);
					int id2 = routeIds.get(i + 1);
					
					Arc finalShape = null;
					boolean direction = false;
					for(Arc shape : p.base.CONNECTIONS) {
						if((shape.id1 == id1 && shape.id2 == id2)) {
							finalShape = shape;
							direction = true;
							break;
						}
						else if(shape.id1 == id2 && shape.id2 == id1) {
							finalShape = shape;
							break;
						}
					}
					if(finalShape != null) {
						highlightArrowRoute.add(finalShape);
						arrow.put(finalShape, Arrays.asList(direction));
					}
				}**/
				display.repaint();
				travellingSalesman.print();
		    }
		});
		
		algorithmMenu.add(chinesePostmanItem);
		algorithmMenu.add(shortestPathItem);
		spanningTreeItem.add(spanningTreeItemKruskal);
		spanningTreeItem.add(spanningTreeItemPrim);
		algorithmMenu.add(spanningTreeItem);
		algorithmMenu.add(travellingSalesmanItem);
		algorithmMenu.addSeparator();
		this.showSpeedSlider = new JSlider(JSlider.HORIZONTAL);
	    Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
	    labelTable.put(0, new JLabel("1000ms"));
	    labelTable.put(500, new JLabel("500ms"));
	    labelTable.put(1000, new JLabel("0ms"));
	    this.showSpeedSlider.setLabelTable(labelTable);
	    this.showSpeedSlider.setValue(200);
	    this.showSpeedSlider.setMinimum(0);
	    this.showSpeedSlider.setMaximum(1000);
	    this.showSpeedSlider.setMinorTickSpacing(250);
	    this.showSpeedSlider.setMajorTickSpacing(500);
	    this.showSpeedSlider.setPaintTicks(true);
	    this.showSpeedSlider.setPaintLabels(true);
	    this.showSpeedSlider.setFocusable(false);
	    algorithmMenu.add(this.showSpeedSlider);
	    algorithmMenu.addSeparator();
	    
		menuBar.add(algorithmMenu);
		
		
		
		JMenu displayMenu = new JMenu("Display");
		JMenuItem findNode = new JMenuItem("Find", ImageUtil.createImageIcon("/javalibrary/network/find.png", "Find"));
		this.showNodeIdItem = new JCheckBoxMenuItem("Show Node Ids", true);
		this.showArcLengthItem = new JCheckBoxMenuItem("Show Arc Length", true);
		
		this.showNodeIdItem.addActionListener(new UpdateNetworkDisplayAction());
		this.showArcLengthItem.addActionListener(new UpdateNetworkDisplayAction());
		
	    JMenuItem resetItem = new JMenuItem("Reset Highlighting", ImageUtil.createImageIcon("/javalibrary/network/pencil_delete.png", "Highlighting"));
	    resetItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				highlightRoute.clear();
				highlightArrowRoute.clear();
				arrow.clear();
				lastest = null;
				display.repaint();
			}
		});
	    algorithmMenu.add(resetItem);
		
		displayMenu.add(findNode);
		displayMenu.addSeparator();
		displayMenu.add(this.showNodeIdItem);
		displayMenu.add(this.showArcLengthItem);
		displayMenu.add(resetItem);
		menuBar.add(displayMenu);
		
		
		this.setJMenuBar(menuBar);

		
		this.arcListDialog = new JDialog();
		//this.dialog.addWindowListener(new JDialogCloseEvent(this.dialog));
		this.arcListDialog.setTitle("Arc List");
		this.arcListDialog.setAlwaysOnTop(true);
		this.arcListDialog.setModal(false);
		this.arcListDialog.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("javalibrary/network/brick.png")));
		
		// create object of table and table model
		tbl = new JTable() {
			@Override
            public TableCellEditor getCellEditor(final int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                
                final JTextField textfield = new JTextField();
                ((AbstractDocument)textfield.getDocument()).setDocumentFilter(new DocumentUtil.DocumentDoubleInput(textfield));
               
                
                textfield.getDocument().addDocumentListener(new DocumentListener() {
                	@Override
                	public void changedUpdate(DocumentEvent event) {
                		
                	}
                	@Override
                	public void removeUpdate(DocumentEvent event) {
                		try {
	                		double v = Double.parseDouble(textfield.getText());
	                		display.base.CONNECTIONS.get(display.base.CONNECTIONS.indexOf(tableOrder.get(row))).distances.set(0, v);
							display.displayBase = display.base;
	                		display.repaint();
                		}
                		catch(NumberFormatException e) {
                			display.base.CONNECTIONS.get(display.base.CONNECTIONS.indexOf(tableOrder.get(row))).distances.set(0, 0.0D);
							display.displayBase = display.base;
							display.repaint();
                		}
                	}
                	@Override
                	public void insertUpdate(DocumentEvent event) {
                		try {
	                		double v = Double.parseDouble(textfield.getText());
	                		display.base.CONNECTIONS.get(display.base.CONNECTIONS.indexOf(tableOrder.get(row))).distances.set(0, v);
							display.displayBase = display.base;
	                		display.repaint();
                		}
                		catch(NumberFormatException e) {
                			display.base.CONNECTIONS.get(display.base.CONNECTIONS.indexOf(tableOrder.get(row))).distances.set(0, 0.0D);
							display.displayBase = display.base;
							display.repaint();
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
		tbl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent event) {
				if(SwingUtilities.isMiddleMouseButton(event)) {
					int row = tbl.rowAtPoint(event.getPoint());
			        if (row >= 0) {
			        	dtm.removeRow(row);
			        	display.base.CONNECTIONS.remove(tableOrder.get(row));
			        	display.displayBase = display.base;
			        	tableOrder.remove(row);
			        	display.repaint();
			        }
				}
			}
			
		});
		
	
		arcListDialog.add(scrollPane);
		arcListDialog.pack();
		
		tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
		    public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					highlightSelected.clear();
					for(int row : tbl.getSelectedRows()) {
						Arc arc = tableOrder.get(row);
						highlightSelected.add(arc);
					}
				}
		
				display.repaint();
		    }
		});

		display.addMouseWheelListener(new MouseWheelListener() {

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
                display.repaint();
			}
			
		});
		
		display.addMouseMotionListener(new MouseMotionAdapter() {
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
					
					for(Node node : display.base.NODES.values()) {
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

				display.repaint();
	        }
		});
		
		display.addMouseListener(new MouseAdapter() {
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
					
					for(Node node : display.base.NODES.values()) {
						if(Math.abs(node.x - scaledX) < 6 && Math.abs(node.y - scaledY) < 6) {
							startNode = node;
							break;
						}
					}

					holdX = (int)((holdX - moveX) / factor);
					holdY = (int)((holdY - moveY) / factor);
					currentHoldX = holdX;
					currentHoldY = holdY;
					display.repaint();
				}
				else if(SwingUtilities.isLeftMouseButton(event)) {
					int scaledX = (int)((event.getX() - moveX) / factor);
					int scaledY = (int)((event.getY() - moveY) / factor);
					
					boolean tooClose = false;
					
					for(Node node :  display.base.NODES.values()) {
						if(Math.pow(node.x - scaledX, 2) + Math.pow(node.y - scaledY, 2) <= Math.pow(6 * 2, 2)) {
							tooClose = true;
							break;
						}
					}
					
					if(!tooClose) {
						int smallestId = 0;
						while(display.base.NODES.containsKey(smallestId))
							smallestId += 1;
						
						if(display.base.addNode(new Node(smallestId, scaledX, scaledY))) {
							display.displayBase = display.base;
							display.repaint();
						}
					}
				}
				else if(SwingUtilities.isRightMouseButton(event) && controlDown) {
					int scaledX = (int)((oldX - moveX) / factor);
					int scaledY = (int)((oldY - moveY) / factor);
					
					for(Node node :  display.base.NODES.values()) {
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
					Node endNode = null;
					
			
					
					for(Node node :  display.base.NODES.values()) {
						if(Math.pow(node.x - currentHoldX, 2) + Math.pow(node.y - currentHoldY, 2) <= Math.pow(6, 2)) {
							endNode = node;
							break;
						}
					}
					
					if(startNode != null && endNode != null && startNode != endNode) {
						Arc arc = new Arc(startNode.getId(), endNode.getId(), RandomUtil.pickRandomInt(99) + 1);
						if(display.base.addArc(arc)) {
							display.displayBase = display.base;
							tableOrder.add(arc);
						    dtm.addRow(new Object[] {arc.id1, arc.id2, "" + arc.getTotalDistance()});
						}
					}
					
					startNode = null;
					holdX = 0;
					holdY = 0;
					currentHoldX = 0;
					currentHoldY = 0;
					display.repaint();
				}
				else if(SwingUtilities.isRightMouseButton(event) && controlDown) {
					nodeAbove = null;
				}
				else if(SwingUtilities.isMiddleMouseButton(event)) {
					int scaledX = (int)((oldX - moveX) / factor);
					int scaledY = (int)((oldY - moveY) / factor);
					
					Node nodeDelete = null;
					
					for(Node node : display.base.NODES.values()) {
						if(Math.pow(node.x - scaledX, 2) + Math.pow(node.y - scaledY, 2) <= Math.pow(6, 2)) {
							nodeDelete = node;
							break;
						}
					}
					
					if(nodeDelete != null) {
		
						List<Arc> connectedArcs = display.base.getArcsConnectedToNode(nodeDelete);
						display.base.NODES.remove(nodeDelete.getId());
						display.base.CONNECTIONS.removeAll(connectedArcs);
						
						for(Arc arc : connectedArcs) {
							int index = tableOrder.indexOf(arc);
							if(index != -1) {
								tableOrder.remove(index);
								dtm.removeRow(index);
							}
						}
						
						display.repaint();
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
					display.repaint();
		    	return false;
		    }
		});
		
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		//this.pack();
		this.setVisible(true);
	}

	public class NetworkDisplay extends JPanel {
		
		public NetworkBase base;
		public NetworkBase displayBase;
		
		public NetworkDisplay() {
			this.setBorder(BorderFactory.createEtchedBorder());
			this.base = new NetworkBase();
			this.displayBase = this.base;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphics = (Graphics2D)g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			//g2.drawLine(moveX, moveY - 1000, moveX, moveY + 1000);
			//g2.drawLine(moveX - 1000, moveY, moveX + 1000, moveY);
			
			if(startNode != null)
				graphics.drawLine((int)(startNode.x * factor) + moveX, (int)(startNode.y * factor) + moveY, (int)(currentHoldX * factor) + moveX, (int)(currentHoldY * factor) + moveY);

			graphics.drawString(ValueFormat.getNumber(this.getTotalDistance()), 10, 20);
			
			double scaleFactor = factor;
			
			for(Arc arc : this.displayBase.CONNECTIONS) {
				int count = 0;
				List<Boolean> arrows = arrow.get(arc);
				
				for(double distance : arc.getDistances()) {
					graphics.setColor(Color.gray);
	
					Node node1 = this.displayBase.NODES.get(arc.id1);
					Node node2 = this.displayBase.NODES.get(arc.id2);
					
					int x1 = (int)(node1.x * scaleFactor) + moveX;
					int y1 = (int)(node1.y * scaleFactor) + moveY;
					int x2 = (int)(node2.x * scaleFactor) + moveX;
					int y2 = (int)(node2.y * scaleFactor) + moveY;
					
					Stroke stroke = graphics.getStroke();
					if(highlightArrowRoute.contains(arc) && arrows != null && arrows.size() > count) {
						graphics.setStroke(new BasicStroke((int)(4 * scaleFactor)));
						if(lastest == arc && count + 1 == arrows.size())
							graphics.setColor(Color.green);
						else
							graphics.setColor(new Color(202, 0, 2));
					}
					else if(highlightRoute.contains(arc)) {
						graphics.setStroke(new BasicStroke((int)(4 * scaleFactor)));
						graphics.setColor(new Color(202, 0, 2));
					}
					else if(highlightSelected.contains(arc) && highlightArrowRoute.isEmpty() && highlightRoute.isEmpty()) {
						graphics.setStroke(new BasicStroke((int)(4 * scaleFactor)));
						graphics.setColor(Color.red);
					}
					if(count == 0)
						graphics.drawLine(x1, y1, x2, y2);
					else if(count == 1) {
						double dy = node2.y - node1.y;
					    double dx = node2.x - node1.x;
						int lineLength = (int) (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) * factor);
						
						double theta = Math.atan2(dy, dx);
						  
						AffineTransform affine = graphics.getTransform();
						if(node1.getId() < node2.getId()) {
							graphics.translate(x1, y1);
							graphics.rotate(theta);
						}
						else {
							graphics.translate(x2, y2);
							graphics.rotate(theta + Math.PI);
						}
		
						
						   
						graphics.drawArc(0, 0 - (int)(20 * scaleFactor / 2), lineLength, (int)(20 * scaleFactor), 0, 180);
						graphics.setTransform(affine);
					}
					else if(count == 2) {
						graphics.drawArc(x1, y1, x2 - x1, y2 - y1, 0, 180);
					}
					graphics.setStroke(stroke);
					
					graphics.setColor(Color.black);
					graphics.setFont(graphics.getFont().deriveFont((float) (6.0F * scaleFactor)));
	
					if(showArcLengthItem.isSelected())
						graphics.drawString(ValueFormat.getNumber(distance), Math.min(x1, x2) + Math.abs(x1 - x2) / 2 - (int)(6 * scaleFactor), Math.min(y1, y2) + Math.abs(y1 - y2) / 2 +  (int)(2 * scaleFactor));
					if(arrows != null && arrows.size() > count) {
						if(arrows.get(count))
							drawArrowHead(graphics, x2, y2, x1, y1, Color.black, count, node1.getId() < node2.getId());
						else
							drawArrowHead(graphics, x1, y1, x2, y2, Color.black, count, node1.getId() > node2.getId());
					}
					count += 1;
				}
			}
			
			for(Node node : this.displayBase.NODES.values()) {
				graphics.setColor(Color.gray);

				int squareWidth = (int)(12 * scaleFactor);
				int renderX = (int)(node.x * scaleFactor) + moveX - squareWidth / 2;
				int renderY = (int)(node.y * scaleFactor) + moveY - squareWidth / 2;
				if(controlDown) {
					//graphics.setColor(Color.green);
				}
				graphics.fillOval(renderX, renderY, squareWidth, squareWidth);
				Stroke stroke = graphics.getStroke();

				graphics.setStroke(new BasicStroke((float)Math.max(scaleFactor / 2, 1)));
					
				
				graphics.setColor(Color.black);
				graphics.drawOval(renderX, renderY, squareWidth, squareWidth);
				graphics.setStroke(stroke);
				graphics.setFont(graphics.getFont().deriveFont((float) (12.0F * scaleFactor)));

				if(showNodeIdItem.isSelected())
					graphics.drawString("" + node.getId(), renderX - (int)(2 * scaleFactor), renderY - (int)(1 * scaleFactor));
			}
		}
		
		public void clearAll() {
			this.base = new NetworkBase();
			this.displayBase = this.base;
			tableOrder.clear();
			highlightRoute.clear();
			highlightSelected.clear();
			highlightArrowRoute.clear();
			arrow.clear();
			lastest = null;
			int rowCount = dtm.getRowCount();
			for(int i = rowCount - 1; i >= 0; i--)
				dtm.removeRow(i);
		}
	
		private void drawArrowHead(Graphics2D g2, int x1, int y1, int x2, int y2, Color color, int count, boolean rightDirection) {
			double phi = Math.toRadians(40);
			int barb = (int) (10 * factor);
	        g2.setPaint(color);
	        double dy = y2 - y1;
	        double dx = x2 - x1;
	        double lineLength = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) / factor;
	        
	        double theta = Math.atan2(dy, dx);
	        
	        double x, y, rho = theta + phi;
	        for(int j = 0; j < 2; j++) {
	            x = x2 + barb * Math.cos(rho);
	            y = y2 + barb * Math.sin(rho);
	            double distance = (3 * lineLength / 4) * factor;
	            double xTrans = 0;
	            double yTrans = 0;
	            if(count == 1) {
		            xTrans = (rightDirection ? -1 : 1) * 9 * factor * Math.cos(theta + Math.PI / 2D);
		            yTrans = (rightDirection ? -1 : 1) * 9 * factor * Math.sin(theta + Math.PI / 2D);
	            }
	            else if(count == 2) {
		            xTrans = -9 * factor * Math.cos(theta - Math.PI / 2D);
		            yTrans = -9 * factor * Math.sin(theta - Math.PI / 2D);
	            }
	            g2.draw(new Line2D.Double(x2 - distance * Math.cos(theta)- xTrans, y2 - distance * Math.sin(theta) - yTrans, x - distance * Math.cos(theta) - xTrans, y - distance * Math.sin(theta)  - yTrans ));
	            rho = theta - phi;
	        }
	    }
		
		/**
		 * Gets the change in x and y that need to be performed to move 
		 * @return

		public Point getTranslation(int theta, int distance) {
			Point point = new Point();
			point.translate(distance * Math.cos(theta), distance * Math.sin(theta));
		}*/
		
		public double getTotalDistance() {
			return this.displayBase.getTotalDistance();
		}
		
		public NetworkBase getNetworkBase() {
			return this.base;
		}
	}
	
	public class UpdateNetworkDisplayAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			display.repaint();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new GraphicNetworkTool(); 
	}

}