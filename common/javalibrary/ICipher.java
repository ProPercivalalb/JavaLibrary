package javalibrary;

import java.util.Random;

/**
 * @author Alex Barter (10AS)
 */
public interface ICipher {

	public String encode(String plainText, EncryptionData data);

	public String decode(String cipherText, EncryptionData data);
	
	public EncryptionData getRandomEncryptionData(Random rand);
}
