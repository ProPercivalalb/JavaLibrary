package javalibrary;

import java.util.ArrayList;

import javalibrary.cipher.AMSCOTransposition;
import javalibrary.cipher.AMSCOTranspositionAuto;
import javalibrary.cipher.HillCipherAuto;
import javalibrary.cipher.VigenereAuto;
import javalibrary.cipher.wip.BifidAuto;
import javalibrary.cipher.wip.PortaAuto;
import javalibrary.language.Languages;
import javalibrary.lib.Timer;
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
		VigenereAuto.tryDecode("kwocxpcnpptddnrvrerjsruptltlkexqrtpldpniperlqplmfesjwxltkxrpctvflohoewkwomhtfjtnqfewnpzptndfcouxcterosolwnnolymfywovtxlndymizrijfetlpujixmemnlzxjwtipwtmdppthtjtpkertqtjamhstqmmtjwxanwxdexieomheouxypjxilufcjxyxrwesmhumztpthseqtopmehwnvmyewnrxdantemmhxepttjwxuxidjzxjnnejtlupespxhsxkmdenturcievmnlodentznjzmrfoerelapxexdvahmutjexcdfljwxepxnopbewnlmtsmgnpvmhveqtpcihmoertapqutxfblernmtvmoneeentrfnkohhxkwjwxspkertamakwosodeqtpxldegnxxhetbevtaetiertolencitvexflsdifcyxfwxcenijrnpcwrjtldeqtipstaimztmdxjcoaxpenspmlpnuertvwpipzexcdjmjwxrxgwocatikuecawtwqegxanwxdexierd", Languages.english);
		System.out.println("Seconds: " + timer.getTimeRunning(Time.SECOND));
	}

}
