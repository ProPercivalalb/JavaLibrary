package javalibrary.file;

import java.io.File;
import java.util.HashMap;

public class IterateDirectory {
	
	public static interface Sieve {
		public void found(File file);  
	}

	public static void execute(String pathname, Sieve sieve) {
		execute(new File(pathname), sieve);
	}
	
	public static void execute(File parent, Sieve sieve) {
		if(!parent.isDirectory())
			sieve.found(parent);
		else
			for(File child : parent.listFiles())
				execute(child, sieve);
	}
	
	public static void examine(String pathname) {
		examine(new File(pathname));
	}
	
	public static void examine(File parent) {
		HashMap<String, Integer> types = new HashMap<String, Integer>();
		
		Sieve sieve = new Sieve() {
			@Override
			public void found(File file) {
				String extension = FileUtil.getExtension(file);
				
				types.put(extension, types.containsKey(extension) ? types.get(extension) + 1 : 1);
			}
		};
		
		execute(parent, sieve);
		System.out.println(types);
	}
}
