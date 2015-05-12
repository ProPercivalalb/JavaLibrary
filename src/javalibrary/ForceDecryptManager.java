package javalibrary;

import java.util.ArrayList;
import java.util.List;

import javalibrary.cipher.auto.AMSCOAuto;
import javalibrary.cipher.auto.AffineAuto;
import javalibrary.cipher.auto.BeaufortAuto;
import javalibrary.cipher.auto.CadenusTranspositionAuto;
import javalibrary.cipher.auto.CaesarAuto;
import javalibrary.cipher.auto.HillCipherAuto;
import javalibrary.cipher.auto.KeywordAuto;
import javalibrary.cipher.auto.RailFenceAuto;
import javalibrary.cipher.auto.TranspositionAuto;
import javalibrary.cipher.auto.VigenereAuto;

/**
 * @author Alex Barter (10AS)
 */
public class ForceDecryptManager {

	public static AffineAuto affine = new AffineAuto();
	public static AMSCOAuto amsco = new AMSCOAuto();
	public static BeaufortAuto beaufort = new BeaufortAuto();
	public static CadenusTranspositionAuto cadenus = new CadenusTranspositionAuto();
	public static CaesarAuto caesar = new CaesarAuto();
	public static HillCipherAuto hill = new HillCipherAuto();
	public static KeywordAuto keyword = new KeywordAuto();
	public static RailFenceAuto railfence = new RailFenceAuto();
	public static TranspositionAuto transposition = new TranspositionAuto();
	public static VigenereAuto vigenere = new VigenereAuto();
	
	public static List<IForceDecrypt> ciphers = new ArrayList<IForceDecrypt>();
	
	public static String[] getNames() {
		String[] names = new String[ciphers.size()];
		for(int i = 0; i < ciphers.size(); ++i)
			names[i] = ciphers.get(i).getName();
		return names;
	}
	
	public static IForceDecrypt[] getObjects() {
		IForceDecrypt[] names = new IForceDecrypt[ciphers.size()];
		for(int i = 0; i < ciphers.size(); ++i)
			names[i] = ciphers.get(i);
		return names;
	}
	
	static {
		ciphers.add(affine);
		ciphers.add(amsco);
		ciphers.add(beaufort);
		ciphers.add(cadenus);
		ciphers.add(caesar);
		ciphers.add(hill);
		ciphers.add(keyword);
		ciphers.add(railfence);
		ciphers.add(transposition);
		ciphers.add(vigenere);
	}
}
