package javalibrary;

import java.util.ArrayList;
import java.util.List;

import javalibrary.cipher.auto.ADFGVXAuto;
import javalibrary.cipher.auto.ADFGXAuto;
import javalibrary.cipher.auto.AMSCOAuto;
import javalibrary.cipher.auto.AffineAuto;
import javalibrary.cipher.auto.BazeriesAuto;
import javalibrary.cipher.auto.BeaufortAuto;
import javalibrary.cipher.auto.BifidAuto;
import javalibrary.cipher.auto.CadenusAuto;
import javalibrary.cipher.auto.CaesarAuto;
import javalibrary.cipher.auto.ColumnarAuto;
import javalibrary.cipher.auto.ColumnarRowAuto;
import javalibrary.cipher.auto.FourSquareAuto;
import javalibrary.cipher.auto.HillAuto;
import javalibrary.cipher.auto.KeywordAuto;
import javalibrary.cipher.auto.MyszkowskiAuto;
import javalibrary.cipher.auto.NicodemusAuto;
import javalibrary.cipher.auto.PlayfairAuto;
import javalibrary.cipher.auto.PortaAuto;
import javalibrary.cipher.auto.RailFenceAuto;
import javalibrary.cipher.auto.RedefenceAuto;
import javalibrary.cipher.auto.RouteAuto;
import javalibrary.cipher.auto.TwoSquareAuto;
import javalibrary.cipher.auto.VariantAuto;
import javalibrary.cipher.auto.VigenereAuto;
import javalibrary.cipher.auto.VigenereAutokeyAuto;
import javalibrary.cipher.auto.VigenereProgressiveKeyAuto;

/**
 * @author Alex Barter (10AS)
 */
public class ForceDecryptManager {

	public static ADFGXAuto adfgx = new ADFGXAuto();
	public static ADFGVXAuto adfgvx = new ADFGVXAuto();
	public static AffineAuto affine = new AffineAuto();
	public static AMSCOAuto amsco = new AMSCOAuto();
	public static BazeriesAuto bazeries = new BazeriesAuto();
	public static BeaufortAuto beaufort = new BeaufortAuto();
	public static BifidAuto bifid = new BifidAuto();
	public static CadenusAuto cadenus = new CadenusAuto();
	public static CaesarAuto caesar = new CaesarAuto();
	public static ColumnarAuto columnar = new ColumnarAuto();
	public static ColumnarRowAuto columnarRow = new ColumnarRowAuto();
	public static FourSquareAuto fourSquare = new FourSquareAuto();
	public static HillAuto hill = new HillAuto();
	public static KeywordAuto keyword = new KeywordAuto();
	public static MyszkowskiAuto myszkowski = new MyszkowskiAuto();
	public static NicodemusAuto nicodemus = new NicodemusAuto();
	public static PlayfairAuto playfair = new PlayfairAuto();
	public static PortaAuto porta = new PortaAuto();
	public static RailFenceAuto railfence = new RailFenceAuto();
	public static RedefenceAuto redefence = new RedefenceAuto();
	public static RouteAuto route  = new RouteAuto();
	public static TwoSquareAuto twoSquare = new TwoSquareAuto();
	public static VariantAuto variant = new VariantAuto();
	public static VigenereAuto vigenere = new VigenereAuto();
	public static VigenereAutokeyAuto vigenereAutokey = new VigenereAutokeyAuto();
	public static VigenereProgressiveKeyAuto vigenereProgressiveKey = new VigenereProgressiveKeyAuto();
	
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
		ciphers.add(adfgx);
		ciphers.add(adfgvx);
		ciphers.add(affine);
		ciphers.add(amsco);
		ciphers.add(bazeries);
		ciphers.add(beaufort);
		ciphers.add(bifid);
		ciphers.add(cadenus);
		ciphers.add(caesar);
		ciphers.add(columnar);
		ciphers.add(columnarRow);
		ciphers.add(fourSquare);
		ciphers.add(hill);
		ciphers.add(keyword);
		ciphers.add(myszkowski);
		ciphers.add(nicodemus);
		ciphers.add(railfence);
		ciphers.add(redefence);
		ciphers.add(route);
		ciphers.add(twoSquare);
		ciphers.add(playfair);
		ciphers.add(porta);
		ciphers.add(variant);
		ciphers.add(vigenere);
		ciphers.add(vigenereAutokey);
		ciphers.add(vigenereProgressiveKey);
	}
}
