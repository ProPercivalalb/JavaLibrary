package nationalcipher;

import javalibrary.ForceDecryptManager;
import javalibrary.Output;
import javalibrary.fitness.IndexCoincidence;
import javalibrary.language.Languages;

import javax.swing.UIManager;

/**
 * @author Alex Barter (10AS)
 */
public class Main {

	public static NationalCipher instance;
	
	public static void main(String[] args) {

		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
		instance = new NationalCipher();
	}
	
}
