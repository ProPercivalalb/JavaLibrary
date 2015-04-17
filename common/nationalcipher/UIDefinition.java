package nationalcipher;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javalibrary.ForceDecryptManager;
import javalibrary.IForceDecrypt;
import javalibrary.Output;
import javalibrary.fitness.ChiSquared;
import javalibrary.fitness.IndexCoincidence;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.ILanguage;
import javalibrary.language.Languages;
import javalibrary.lib.Timer;
import javalibrary.math.MathHelper;
import javalibrary.math.Units.Time;
import javalibrary.string.LetterCount;
import javalibrary.string.StringAnalyzer;
import javalibrary.swing.FrameUtil;
import javalibrary.swing.ImageUtil;
import javalibrary.swing.chart.ChartData;
import javalibrary.swing.chart.ChartList;
import javalibrary.swing.chart.JBarChart;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

/**
 * @author Alex Barter (10AS)
 */
public class UIDefinition extends JFrame {

	public Timer threadTimer;
	public Thread thread;
	
	public JTextArea output;
	public JTextArea input;
	
	public JMenuBar menuBar;
	public JMenu file;
	public JMenu language;
	public JMenuItem exit;
	public JMenu analyzer;
	public JMenuItem letterCount;
	public JMenuItem unknowncipher;
	public JMenuItem wordCount;
	public JMenuItem letterCombinationCount;
	public JMenu helper;
	public JMenuItem modular;
	public JMenuItem multiplicativeFactors;
	
	public JProgressBar progressBar;
	
	public UIDefinition() {
		super("Cryptography Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setUIFont(new FontUIResource(new Font("Courier New", 0, 20)));
		this.threadTimer = new Timer();
		this.thread = null;
	}
	
	public void initializeObjects() {
		JPanel contentPane = new JPanel();
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setOpaque(true); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		final JComboBox cipher = new JComboBox(ForceDecryptManager.getNames());
		cipher.setMaximumSize(new Dimension(150, 20));
		//cipher.setFont(new Font("Courier New", 10));
		topPanel.add(cipher);
		final JButton decode = new JButton("Force Decrypt");
		final JButton cancel = new JButton("Cancel");
		cancel.setEnabled(false);
		final Output outputObj = new Output() {

			@Override
			public void print(String text, Object... format) {
				addText(text, format);
			}

			@Override
			public void println(String text, Object... format) {
				addText(text + "\n", format);
			}
			
		};
		decode.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println(input.getText());
				if(input.getText() == null || input.getText().isEmpty()) {
					return;
				}
				
				thread = new Thread(new Runnable() {

					@Override
					public void run() {
						threadTimer.restart();
						IForceDecrypt force = ForceDecryptManager.ciphers.get(cipher.getSelectedIndex());
						outputObj.println("Cipher: " + force.getName());
						try {
							force.tryDecode(input.getText(), force.getEncryptionData(), Main.instance.language, outputObj, progressBar);
						}
						catch(Exception e) {
							outputObj.println(e.toString());
							e.printStackTrace();
						}
						
						DecimalFormat df = new DecimalFormat("#.#");
						outputObj.println("Time Running: %sms - %ss", df.format(threadTimer.getTimeRunning(Time.MILLISECOND)), df.format(threadTimer.getTimeRunning(Time.SECOND)));
						outputObj.println("");
						decode.setEnabled(true);
						cancel.setEnabled(false);
						language.setEnabled(true);
						try {
							Thread.sleep(1000L);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						progressBar.setValue(0);
					}
					
				});
				thread.start();
				decode.setEnabled(false);
				cancel.setEnabled(true);
				language.setEnabled(false);
			}
		});
		cancel.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(thread != null) {
					thread.stop();
				}
				DecimalFormat df = new DecimalFormat("#.#");
				outputObj.println("Time Running: %sms - %ss", df.format(threadTimer.getTimeRunning(Time.MILLISECOND)), df.format(threadTimer.getTimeRunning(Time.SECOND)));
				outputObj.println("");
				decode.setEnabled(true);
				cancel.setEnabled(false);
				language.setEnabled(true);
				
				progressBar.setValue(0);
			}
		});
		
		topPanel.add(decode);
		topPanel.add(cancel);
		
		contentPane.add(topPanel);
		
		final JPanel varsPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		final CardLayout cardLayout = new CardLayout();
		varsPanel.setLayout(cardLayout); 
		for(IForceDecrypt ifd : ForceDecryptManager.getObjects())
			varsPanel.add(ifd.getVarsPanel(), ifd.getName());
		
		cipher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				cardLayout.show(varsPanel, ForceDecryptManager.getNames()[cipher.getSelectedIndex()]);
			}
			
		});
		
		
		final JButton test = new JButton("Test");
		varsPanel.add(test);
		contentPane.add(varsPanel);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		inputPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		input = new JTextArea();
		JScrollPane inputScrollPanel = new JScrollPane(input);
		inputScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputScrollPanel.setPreferredSize(new Dimension(500, 200));
		input.setLineWrap(true);
		input.setBackground(new Color(255, 255, 220));
		inputPanel.add(inputScrollPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 0));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setLayout(new GridLayout(3, 1));
		JButton uppercase = new JButton("Uppercase");
		JButton nospaces = new JButton("Remove _");
		JButton nonletters = new JButton(" Keep A-Z ");
		uppercase.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent event) {
				String s = input.getText();
				input.setText(s.toUpperCase());
			}
		});
		
		nospaces.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent event) {
				String s = input.getText();
				input.setText(s.replaceAll("\\s+",""));
			}
		});
		
		nonletters.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent event) {
				String s = input.getText();
				input.setText(s.replaceAll("[^a-zA-Z]+", ""));
			}
		});
		
		buttonPanel.add(uppercase);
		buttonPanel.add(nospaces);
		buttonPanel.add(nonletters);
		inputPanel.add(buttonPanel);
		contentPane.add(inputPanel);
		
		this.output = new JTextArea();
		JScrollPane outputScrollPanel = new JScrollPane(this.output);
		outputScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outputScrollPanel.setPreferredSize(new Dimension(500, 200));
		this.output.setEditable(false);
		contentPane.add(outputScrollPanel);
		
		this.progressBar = new JProgressBar(0, 10);
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		/**this.progressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				DecimalFormat df = new DecimalFormat("#.#");
				double timeRemaining = 100 / progressBar.getPercentComplete() * threadTimer.getTimeRunning(Time.MILLISECOND);
				System.out.println("" + timeRemaining);
				progressBar.setString(df.format(progressBar.getPercentComplete() * 100) + "% - Time Remaining: " + df.format(timeRemaining / 1000));
			}
	    });**/
		contentPane.add(this.progressBar);
		
		this.menuBar = new JMenuBar();
		this.file = new JMenu("File");
		this.menuBar.add(this.file);
		this.language = new JMenu("Language");
		this.language.setIcon(ImageUtil.createImageIcon("/image/globe.png", "Language"));
		final JMenuItem currentLanguage = new JMenuItem("Current: English");
		
		this.language.add(currentLanguage);
		this.language.addSeparator();
		
		for(final ILanguage language : Languages.languages) {
			JMenuItem jmi = new JMenuItem(language.getName(), ImageUtil.createImageIcon(language.getImagePath(), language.getName()));
			
			jmi.addActionListener(new ActionListener () {
				

				@Override
				public void actionPerformed(ActionEvent event) {
					Main.instance.language = language;
					currentLanguage.setText("Current: " + language.getName());
					outputObj.println("Set language to " + language.getName());
				}
				
			});
			this.language.add(jmi);
		}
		this.file.add(this.language);
		
		
		
		this.exit = new JMenuItem("Exit");
	    this.exit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		System.exit(128);
	    	}
	    });
	    this.file.add(this.exit);
	    
	    this.analyzer = new JMenu("Analyzer");
	    this.menuBar.add(this.analyzer);
		
		this.unknowncipher = new JMenuItem("Identify Unknown Cipher");
		this.unknowncipher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				DecimalFormat df = new DecimalFormat("#.#");
				double chisquared = ChiSquared.calculate(input.getText(), Main.instance.language);
				double fitness = QuadgramStats.scoreFitness(input.getText(), Main.instance.language);
				double ioc = IndexCoincidence.calculate(input.getText(), Main.instance.language);
				
			    JLabel chisquaredLabel = new JLabel("Chi-Squared: " + df.format(chisquared));
			    panel.add(chisquaredLabel);
			    
			    JLabel fitnessLabel = new JLabel("Fitness: " + df.format(fitness));
			    panel.add(fitnessLabel);
			    
			    JLabel iocLabel = new JLabel("Index of Coincidence: " + df.format(ioc));
			    panel.add(iocLabel);
			    
			    String possibleCipher = "Unknown";
			    
			    if(chisquared < 100)
			    	possibleCipher = "Transposition";
			    else if(Math.abs(ioc - (Main.instance.language.getNormalCoincidence() / 0.0385D)) < 0.3) {
			    	possibleCipher = "Mono-alphabetic";
			    }
			    
			    JLabel possibleLabel = new JLabel("Possible Cipher " + possibleCipher);
			    panel.add(possibleLabel);
			    
			    
			    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			    JDialog dialog = optionPane.createDialog(Main.instance.definition, "Letter Combination Analyzer");
			    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    dialog.setModal(true);
			    dialog.setVisible(true);
			    dialog.dispose();
			}
		});
		this.analyzer.add(this.unknowncipher);
	    
	    
	    this.letterCount = new JMenuItem("Letter Count");
	    this.letterCount.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent e) {
		         JPanel panel = new JPanel();
		         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		          
		         final JBarChart chart = new JBarChart(new ChartList());
		         chart.setMinimumSize(new Dimension(400, 200));
		         chart.setMaximumSize(new Dimension(400, 200));
		         chart.setPreferredSize(new Dimension(400, 200));
		         chart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ordered by Size"));
		         panel.add(chart);
		         
		          
		         final JBarChart chartAlphabeticly = new JBarChart(new ChartList());
		         chartAlphabeticly.setMinimumSize(new Dimension(400, 200));
		         chartAlphabeticly.setMaximumSize(new Dimension(400, 200));
		         chartAlphabeticly.setPreferredSize(new Dimension(400, 200));
		         chartAlphabeticly.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ordered Alphabeticly"));
		         panel.add(chartAlphabeticly);
		         
		         Hashtable<Character, LetterCount> letters = StringAnalyzer.countLetters(input.getText());
		         ArrayList<LetterCount> alphabeticlySorted = StringAnalyzer.alphabeticOrder((Hashtable<Character, LetterCount>)letters.clone());
		         ArrayList<LetterCount> sortedItems = StringAnalyzer.sizeOrder(letters);
		         
		         ChartList values1 = new ChartList();
				 int i = 0;
				 for(LetterCount letterCount : alphabeticlySorted) {
					 values1.add(new ChartData("" + letterCount.ch, (double)letterCount.count));
					 i++;
				 }
			  		
			  		
				 ChartList values = new ChartList();
		         i = 0;
		         for(LetterCount letterCount : sortedItems) {
		        	 values.add(new ChartData("" + letterCount.ch, (double)letterCount.count));
		        	 i++;
		         }
		          
		         chart.values = values;
		         chart.updateUI();
		          
		         chartAlphabeticly.values = values1;
		         chartAlphabeticly.updateUI();
		          
		         JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
		         JDialog dialog = optionPane.createDialog(Main.instance.definition, "Letter Analyzer");
		         dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		         dialog.setModal(true);
		         dialog.setVisible(true);
		         dialog.dispose();
		     }
		});
		this.analyzer.add(this.letterCount);
		
		
		this.letterCombinationCount = new JMenuItem("Letter Combination Analyzer");
		this.letterCombinationCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			          
			    final JBarChart chart = new JBarChart(new ChartList()).setHasBarText(false);
			    chart.setMinimumSize(new Dimension(1000, 200));
			    chart.setMaximumSize(new Dimension(1000, 200));
			    chart.setPreferredSize(new Dimension(1000, 200));
			    chart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ordered by Size"));
			    panel.add(chart);
			    
				Hashtable<String, Integer> wordCount = StringAnalyzer.analyzeLetterCombination(input.getText(), 2, 16);
						
				ChartList sizeSorted = new ChartList();
				  		
				while(wordCount.size() > 0 && sizeSorted.size() < 89) {
				  	String largest = null;
				  	int lastLargest = 0;
				  			
				  	Iterator<String> ite = wordCount.keySet().iterator();
				  			
				  	while(ite.hasNext()) {
				  		String str = ite.next();
				  		if(largest == null) {
				  			largest = str;
				  			lastLargest = wordCount.get(str);
				  		}
				  		else if(wordCount.get(str) > lastLargest) {
				  			largest = str;
				  			lastLargest = wordCount.get(str);
				  		}
				  	}
				  			
				  	sizeSorted.add(new ChartData(largest, lastLargest));
				  	wordCount.remove(largest);	
				}

			    chart.values = sizeSorted;
			    chart.updateUI();
			          
			    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			    JDialog dialog = optionPane.createDialog(Main.instance.definition, "Letter Combination Analyzer");
			    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    dialog.setModal(true);
			    dialog.setVisible(true);
			    dialog.dispose();
			}
		});
		this.analyzer.add(this.letterCombinationCount);
		
		this.helper = new JMenu("Helpers");
	    this.menuBar.add(this.helper);
		
	    this.modular = new JMenuItem("Modular");
	    this.modular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			    JLabel encodedButton = new JLabel("Type text into the encoded textbox"); 
			    encodedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			    panel.add(encodedButton);

				JPanel textBoxPanel = new JPanel();
				textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.X_AXIS));
			    final JTextField inputTextBox = new JTextField();
			    final JTextField modularTextBox = new JTextField("26");
			    //TODO modularTextBox.setDocument(new CustomDocument());
			    textBoxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Input Text Area"));
			    final JLabel label = new JLabel("In modular 26: 0");      
			    label.setAlignmentX(Component.CENTER_ALIGNMENT);
			    
			 	inputTextBox.addKeyListener(new KeyListener() {
			 		@Override
					public void keyPressed(KeyEvent event) {}

					@Override
					public void keyReleased(KeyEvent event) {
						String newText = inputTextBox.getText();
						int modular = new Integer(modularTextBox.getText());
						int target = new Integer(newText);
						
						label.setText(String.format("In modular %d: %d", modular, MathHelper.wrap(target, 0, modular)));
					}

					@Override
					public void keyTyped(KeyEvent event) {}
				});
			 		  
			 	textBoxPanel.add(inputTextBox);
			 	textBoxPanel.add(modularTextBox);
			 	panel.add(textBoxPanel);
			 	panel.add(label);
			          
			    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			    JDialog dialog = optionPane.createDialog(Main.instance.definition, "Modular");
			    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    dialog.setModal(true);
			    dialog.setVisible(true);
			    //dialog.dispose();
			}
	    	
	    });
	    
		this.helper.add(this.modular);
		
	    this.multiplicativeFactors = new JMenuItem("Multiplicative Factors");
	    this.multiplicativeFactors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new JPanel();
			    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			    JLabel encodedButton = new JLabel("Type text into the encoded textbox"); 
			    encodedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			    panel.add(encodedButton);

				JPanel textBoxPanel = new JPanel();
				textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.X_AXIS));
			    final JTextField modularTextBox = new JTextField("26");
			    
			    
			    final DefaultListModel listModel = new DefaultListModel();
			    final JList list = new JList(listModel);
			    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			    list.setLayoutOrientation(JList.VERTICAL);
			    final JScrollPane scrollPane = new JScrollPane(list);
				scrollPane.getVerticalScrollBar().setUnitIncrement(16);
				//scrollPane.setPreferredSize(new Dimension(80, 250));
				//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane.setPreferredSize(new Dimension(80, 250));
			    //TODO modularTextBox.setDocument(new CustomDocument());
			    textBoxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Input Text Area"));   
			    
			    modularTextBox.addKeyListener(new KeyListener() {
			 		@Override
					public void keyPressed(KeyEvent event) {}

					@Override
					public void keyReleased(KeyEvent event) {
						int modular = new Integer(modularTextBox.getText());
						
						ArrayList<Integer[]> list = MathHelper.getMultiplicativeFactors(modular);
						listModel.removeAllElements();
						
						for(Integer[] array : list) {
							final JLabel label = new JLabel(String.format("%d %d", array[0], array[1]));      
							label.setAlignmentX(Component.CENTER_ALIGNMENT);
							listModel.addElement(String.format("%d     %d", array[0], array[1]));
						}
					}

					@Override
					public void keyTyped(KeyEvent event) {}
				});
			 		  
			 	panel.add(scrollPane);
			 	textBoxPanel.add(modularTextBox);
			 	panel.add(textBoxPanel);

			          
			    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
			    JDialog dialog = optionPane.createDialog(Main.instance.definition, "Multiplicative Factors");
			    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			    dialog.setModal(true);
			    dialog.setVisible(true);
			    //dialog.dispose();
			}
	    	
	    });
	    
		this.helper.add(this.multiplicativeFactors);
	    
		//Add the components pane to this panel.
		this.setJMenuBar(this.menuBar);
	}

	public void placeObjects() {
		
	}

	public void finalizeObjects() {
		
	}

	public void end() {
		this.pack();
		FrameUtil.repostionToCentre(this);
        this.setVisible(true);
		
	}
	
	public void setUIFont(FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
		        Font font = new Font(f.getFontName(), orig.getStyle(), orig.getSize());
		        UIManager.put(key, new FontUIResource(font));
			}
		}
	}
	
	public void addText(String text, Object... format) {
		this.output.append(String.format(text, format));
        this.output.setCaretPosition(this.output.getText().length());
	}
}
