package javalibrary.language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Barter (10AS)
 */
public class Languages {

	public static Danish DANISH = new Danish();
	public static English ENLGISH = new English();
	public static Finnish FINNISH = new Finnish();
	public static French FRENCH = new French();
	public static German GERMAN = new German();
	public static Icelandic ICELANDIC = new Icelandic();
	public static Polish POLISH = new Polish();
	public static Spanish SPANISH = new Spanish();
	public static Swedish SWEDISH = new Swedish();
	
	public static List<ILanguage> languages = new ArrayList<ILanguage>();
	
	public static String[] getNames() {
		String[] names = new String[languages.size()];
		for(int i = 0; i < languages.size(); ++i)
			names[i] = languages.get(i).getName();
		return names;
	}
	
	public static void loadNGgramData() {
		for(ILanguage language : languages)
			language.loadNGramData();
	}
	
	static {
		//languages.add(danish);
		languages.add(ENLGISH);
		//languages.add(finnish);
		//languages.add(french);
		//languages.add(german);
		//languages.add(icelandic);
		//languages.add(polish);
		//languages.add(spanish);
		//languages.add(swedish);
	}
}
