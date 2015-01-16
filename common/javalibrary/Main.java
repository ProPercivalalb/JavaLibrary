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
import javalibrary.cipher.Caesar;
import javalibrary.cipher.HillCipherAuto;
import javalibrary.cipher.Redefence;
import javalibrary.cipher.RedefenceAuto;
import javalibrary.cipher.Transposition;
import javalibrary.cipher.Vigenere;
import javalibrary.cipher.VigenereAuto;
import javalibrary.cipher.VigenereAutoKey;
import javalibrary.cipher.wip.BifidAuto;
import javalibrary.cipher.wip.PortaAuto;
import javalibrary.fitness.ChiSquared;
import javalibrary.fitness.FReader;
import javalibrary.fitness.QuadgramStats;
import javalibrary.language.English;
import javalibrary.language.Languages;
import javalibrary.lib.Timer;
import javalibrary.math.GCD;
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
		StartUp.load();
		
		Timer timer = new Timer();
		String s = "ANWAECNNDRTWTANIREOAHSRDNTIEERDCTEWAYEREVAAARPIOEROBSEDESCREHLAOITITHSDTRINOSEPEPLTHTNIDTWSDULEDLHOTPEATERHAAREDEOAIODTAHREGEROTHWHTSURETEEINWIGIRESETPOWICOOONITSEUDSEECHACTEOFIILEONEWIOTACTEODTEUNEAHSEDAPTRYEMRONMOMLEXONTKEMEOYVITSTEENHANTRERTIEINEOTNDLHPOPLYITEWITRSEDEEVDYCFRMNSNAYPINMTEENEEINAHINEPRITEDEHVELEAORELLLVMOCNAHNCEPHEROHJUUVEHAUTATHTTETOASIPOWWNEEDSELOPGDESLFEDTERFCAATEHJASDTRPRLESEERETETECNEORRSGCKAMIIWAEFUTIASHAONGCTHSRENREHTRSTHHACEINWTPRLOGHETODAERALOEEDEATSOMLDWEDTWEFSBELEVPDTEONOIGNSAVFTEGSEBOMCEHATIETNRSPTONTHUSORECREDENDCETLWAEHESEDRNCVERACUHOIHALEINAEMEUNHERDESTTSTASIPEDEYLEEEMTIISIIGOTHNTSSFOLNITRETRWHEMTKHHSWTORCSSNEREROHTEENCSAPEBLIRCNTHULEOFENRSROMRNSHMADDAODEATIHIOROUTWEVTHAPECEETOVTERDLEADITEWDOTTTTCATKMBHTHEICWANTNISEDWATXANKEABOVOANMSWLPRGAISPODFOGNDPEDESWRDACTTREFHDESYABERRETLAVOD";
		System.out.println("" + ChiSquared.calculate(s, new English()));
		
		timer.displayTime();
	}

}
