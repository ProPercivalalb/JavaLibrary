package javalibrary.language;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alex Barter (10AS)
 */
public class Languages {

	public static English english = new English();
	public static French french = new French();
	public static German german = new German();
	public static List<ILanguage> languages = Arrays.asList(english, french, german);
	
	public static String[] getNames() {
		String[] names = new String[languages.size()];
		for(int i = 0; i < languages.size(); ++i)
			names[i] = languages.get(i).getName();
		return names;
	}
}
