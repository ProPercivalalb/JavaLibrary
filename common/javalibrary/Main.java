package javalibrary;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javalibrary.cipher.AMSCOTransposition;
import javalibrary.cipher.AMSCOTranspositionAuto;
import javalibrary.cipher.CadenusTransposition;
import javalibrary.cipher.CadenusTranspositionAuto;
import javalibrary.cipher.HillCipherAuto;
import javalibrary.cipher.Playfair;
import javalibrary.cipher.PlayfairAuto;
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
		System.out.println(PlayfairAuto.tryDecode("RABQGLMLMYFMEPKLLKATBMAYLMLIBRXBEFBQMPNAINXBSUULSZKMXBMKGAKSRAKZCFMKRMGMNMFLIMLTANLKPDRSPDKMFKQAAFKSSXHMACGAKCXGPDGXGMNVOSQPDQCFGMDMHQSKNAMZULTLMFMYNDQLPDFLSMGALKZSMKONUFRAHQWMDLXMSKNANMHYRMMBLKATPDRSRMGMRPTOILGMSKRQCFEFBQLWRMKLBFLANBCLRATQLUSEYQMVQRMCCIATLVMYLZRSFAMGLDKBGMSKGHRABCMVCSLUKLLKATGAPDFLSMGALKZSRAKDFLSMELPDILLENAHRBUZMGMELXGPDRAUIFYROCLODROUIAKKPCFQPBSRQIUPDBXRARHYRMCCIMNEHBOMRDLKMXBSVGMBRAWPRPDIURYPROREFKSFUGMDAIMTZCFQPBSRQIUSKRQHVBSATHPULSZKMLIFYROUIODROUIDZCFMSFNAKPDOANVVNDTPAMGMBFHKPCFULSZKMSOKATPMHRSKLCFULSZKMXSRQCFEFBQLWRMKLCFIUROQPILIKUFARMKXLGAKQCFEFBQLWRMIMHAUFKGPDLUKLLKATRMFKRYKGGFMKRQMNXQRACHMPMCAQMKQPMBKLCFSOKAXGPDPDLUKLLKATNAPDILROKMNVMKTQLESKUFQCCXSRSZCFRPCFOTBSMNEHNMMGATHPPDKMLESRYQULCKGKMGCDDQCFNMKIGAMSSOKAPDFMDKKMSXEAVHDZRASZCFGKATLKMYNDQLKCULSZKMHPPDFLBSAKTLCFNAKLBOLPKFPARQCFXBCLMNXNTLCFGKATLQSMGALKZSULSZKMBOHAILESKGPDLUKLLKATOSLFRMNAPDILROKMNVHPPDKLBIULMKQPMBKLCFNUKSDPCFULSZKMTLRPCFKAEACLBKRSKMKIDPMKTQLESKUFQCZNOSSFYQRMMVRYPRPDLUFGLTKBFMKPCFMNXGIOULSZKMGAPDFMAKIKARPQBSNZBXNAPDKMKIDPXSCKHPPDKMNVORNMPQQSKGPDLUKLLKATOSLFRMNAPDILROLEMPMCANKPCFSRCSKMFLSMELPDLCXGPDPDLUKLLKATEACLBKRSLUBDLUNVMKTQLESKUFQCZNOSSFYQRMMVRYPRPDKLHVXSCKHPPDLEMPMCAGIOULSZKMGAPDFMAKIKARPQBSNZBXNAPDICRPPRALKBFMKPCFHMUMOADTKZVOOH"));
		timer.displayTime();
	}

}
