package javalibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javalibrary.cipher.AMSCO;
import javalibrary.cipher.AMSCOAuto;
import javalibrary.cipher.AffineAuto;
import javalibrary.cipher.Beaufort;
import javalibrary.cipher.BeaufortAutoKey;
import javalibrary.cipher.CadenusTransposition;
import javalibrary.cipher.CadenusTranspositionAuto;
import javalibrary.cipher.HillCipherAuto;
import javalibrary.cipher.Redefence;
import javalibrary.cipher.RedefenceAuto;
import javalibrary.cipher.Transposition;
import javalibrary.cipher.Vigenere;
import javalibrary.cipher.VigenereAuto;
import javalibrary.cipher.VigenereAutoKey;
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
		AffineAuto.tryDecode("SXDK LDKB,UMDQBP CVK UMX GDUXPU KXAVKU CKVL UMX VQ-PRUX UXDL. RU PMVJP UMDU UMX PMRAIVDKS HAP PTPUXL JDP NVLAGXUXGT PNKDLIGXS PV JX DKX QVU HVRQH UV IX DIGX UV UKDNX MXK LVEXLXQUP CKVL UMDU. SV JX MDEX DQT VSS UKDNXP CKVL VQPMVKX KDSDK UMDU HREX D MRQU VC JMXKX PMX LRHMU MDEX IXXQ? UMX NVLLXQU RQ UMX GDPU LXPPDHX UMDU UMX ARKDUXP NVLAGXUXS UMX PZKEXT XEXQ UMVZHM UMXT MDS LVEXS PVZUM UV DEVRS SXUXNURVQ PMVZGS MDEX UVGS LX UMDU UMX PZKEXT JDP QVU HXVHKDAMRN. DU CRKPU R UMVZHMU RU LRHMU MDEX IXXQ KXCXKKRQH UV D UXGXNVLP PZKEXT PRQNX TVZ LXQURVQXS UMX GVQH DXKRDG, IZU DNUZDGGT UMX DUUDNMXS LXPPDHX RP EXKT KXEXDGRQH. PURGG QVU PZKX JMDU UMX PZKEXT JDP CVK UMVZHM, DQS MVJ UMDU RP NVQQXNUXS UV UMX LRPPRQH PZAXKPUKZNUZKX. NDQ TVZ HXU LX DQT ARNUZKXP? MDKKT ");
		timer.displayTime();
	}

}
