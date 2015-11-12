package javalibrary.swing;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class DocumentUtil {

	public static class DocumentUpperCaseInput extends DocumentFilter {
		
    	@Override
    	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    		text = text.toUpperCase();
    		 
    		super.replace(fb, offset, length, text, attrs);
    	}
    }
	
	public static class DocumentIntegerInput extends DocumentFilter {
		
    	@Override
    	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    		text = text.replaceAll("[^0-9]", "");
    		 
    		super.replace(fb, offset, length, text, attrs);
    	}
    }
	
	public static class DocumentDoubleInput extends DocumentFilter {

		private JTextComponent textComponent;
		
		public DocumentDoubleInput(JTextComponent textComponent) {
			this.textComponent = textComponent;
		}
		
    	@Override
    	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    		//if(this.textComponent.getText().indexOf('.') != -1 && text.contains("."))
    		//	text = text.replaceAll(".", "");
    		text = text.replaceAll("[^0-9.]", "");
    		 
    		super.replace(fb, offset, length, text, attrs);
    	}
    }
	
	public static abstract class DocumentChangeAdapter implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent event) {
			this.onUpdate(event);
		}

		@Override
		public void insertUpdate(DocumentEvent event) {
			this.onUpdate(event);
		}

		@Override
		public void removeUpdate(DocumentEvent event) {
			this.onUpdate(event);
		}
		
		public abstract void onUpdate(DocumentEvent event);
		
	}
	
	public static void addUndoManager(JTextArea textArea) {
		final UndoManager undoManager;

		// In the constructor

		undoManager = new UndoManager();
		Document doc = textArea.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
			
		    @Override
		    public void undoableEditHappened(UndoableEditEvent e) {
		        undoManager.addEdit(e.getEdit());
		    }
		});

		InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = textArea.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

		am.put("Undo", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canUndo()) {
		                undoManager.undo();
		            }
		        } catch (CannotUndoException exp) {
		            exp.printStackTrace();
		        }
		    }
		});
		am.put("Redo", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canRedo()) {
		                undoManager.redo();
		            }
		        } catch (CannotUndoException exp) {
		            exp.printStackTrace();
		        }
		    }
		});
	}
}
