package javalibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javalibrary.cipher.AMSCO;
import javalibrary.cipher.AMSCOAuto;
import javalibrary.cipher.CadenusTransposition;
import javalibrary.cipher.CadenusTranspositionAuto;
import javalibrary.cipher.HillCipherAuto;
import javalibrary.cipher.Redefence;
import javalibrary.cipher.RedefenceAuto;
import javalibrary.cipher.Transposition;
import javalibrary.cipher.VigenereAuto;
import javalibrary.cipher.wip.BifidAuto;
import javalibrary.cipher.wip.PortaAuto;
import javalibrary.fitness.FReader;
import javalibrary.fitness.QuadgramsStats;
import javalibrary.language.Languages;
import javalibrary.lib.Timer;
import javalibrary.math.MathHelper;
import javalibrary.math.Units;
import javalibrary.math.Units.Time;
import javalibrary.math.matrics.Matrix;
import javalibrary.util.Permunation;

/**
 * @author Alex Barter
 * @since 29 Oct 2013
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		//javalibrary.cipher.wip.TranspositionAuto.tryDecode("EVLNE ACDTK ESEAQ ROFOJ DEECU WIREE");
		timer.displayTime();
		
		timer.restart();
		//System.out.println(TranspositionCipher.decode("EVLNEACDTKESEAQROFOJDEECUWIREE", new int[] {5, 2, 1, 3, 0, 4}));
		javalibrary.cipher.TranspositionAuto.tryDecode("SSSATA NUELCL AENDEE HEEVRN HTAILS LTOCSO EOANUO DOEECA FERBET RTENOI IUCRWU RFAPRO EERCSS OEUATU LGTEMA TREMLI EAVEIE OGCELE SAEEEY YIUUOA IDAOSD MDECSS HTHUHA TCNXAE RERSEL TUNAGH ANRDTE VEPISY DTAEAM CINMRN WEORAM RVIBOD SDFDPA TIMRSS IETDAO SPECGR ACNETB LFIOEU SHSMEE IRLSHM ITTRLN ESEHMC LSSOSW FOTTWN BYTEYN GEYMTT GSTARI IXEEED RNASML TWGMIL DCRTSE OGOHRO LSSHMA WNDSST RABNDN ECFCAY EHOTDO RNONEN ECATNE AVOEAA TEHERC YRIGHS AYREFS OOATEM NCWTKA AAWNDA DMSLLN NNLUTF OEEENO YOEWTM ANRRSX HVOROL HISFUN NTHAEE OFOLPH EBAATM NORNOE ODNVTP HNOETE DEAEON PHPAEU RATVHN DETAHR AHPOOR SEFOVD DSTTPS VGRAAA TODSUR YIDOVT RELERL TMEMDH EOARSH OARRRE RXISGE IFAWFA IYIDUS IYIEEE SOTKEA ELATRE SNTIFE MTEIAI GHACEI ONDKTK ITTEAE ANECNN DICTNE DDDENS TSHEAN RTAMNE AHSHID AOCNUI SSCTEH SLNLEC THEETL LTIDLC TTNPNM CVSVNO SITDAE LXPIHS FATTYS FOEDCM WHTEBA ACHERT AIGRIU IRTNGI APHETR OWEHWS WAACMG COUWOO GOEGSM TARTEE IEMVAY INOGST ITAGBL NCSTCY COLRET EDAREH OPNEBY EGWCTE ETLTEY ETEENA NSAFMO");
		timer.displayTime();
	}

}
