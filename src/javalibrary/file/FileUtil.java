package javalibrary.file;

import java.io.File;

public class FileUtil {

	public static String getExtension(File file) {
		int i = file.getName().lastIndexOf('.');
		int p = Math.max(file.getName().lastIndexOf('/'), file.getName().lastIndexOf('\\'));

		if(i > p)
			return file.getName().substring(i + 1);
		
		return "";
	}
}
