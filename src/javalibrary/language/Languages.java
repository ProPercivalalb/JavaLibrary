package javalibrary.language;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Barter (10AS)
 */
public class Languages {

	public static Danish danish = new Danish();
	public static English english = new English();
	public static Finnish finnish = new Finnish();
	public static French french = new French();
	public static German german = new German();
	public static Icelandic icelandic = new Icelandic();
	public static Polish polish = new Polish();
	public static Spanish spanish = new Spanish();
	public static Swedish swedish = new Swedish();
	
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
		languages.add(english);
		//languages.add(finnish);
		//languages.add(french);
		//languages.add(german);
		//languages.add(icelandic);
		//languages.add(polish);
		//languages.add(spanish);
		//languages.add(swedish);
	}
}
