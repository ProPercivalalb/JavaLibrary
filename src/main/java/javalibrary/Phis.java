package javalibrary;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.DatatypeConverter;

import javalibrary.file.DraftFile;
import javalibrary.file.FileUtil;
import javalibrary.file.IterateDirectory;
import javalibrary.streams.FileReader;
import javalibrary.string.StringTransformer;

public class Phis {

	public static DraftFile draft = new DraftFile("C:\\Users\\alexl\\Documents\\PHP Phishing\\output.txt");
	public static String PATH = "C:\\Users\\alexl\\Documents\\PHP Phishing\\phishingkits\\";
	
	/**
	 * 
	 */
	public static String decodeHex(String hex) {
		String output = "";
		for(String split : StringTransformer.splitInto(hex, 4))
			output += (char)Integer.valueOf(split.substring(2, 4), 16).intValue();
		
		return output;
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File(PATH);
		
		String test = "%66%67%36%73%62%65%68%70%72%61%34%63%6f%5f%74%6e%64";
		IterateDirectory.examine(PATH);
		String hex = "\\x67\\x7a\\x69\\x6e\\x66\\x6c\\x61\\x74\\x65";
		System.out.println(decodeHex(hex));
		byte[] bytes = DatatypeConverter.parseBase64Binary("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");
		String result= new String(bytes, "UTF-8");
		//System.out.println(new String(decompress(bytes)));

		IterateDirectory.Sieve sieve = new IterateDirectory.Sieve() {

			@Override
			public void found(File file) {
				
				
				List<String> strings = FileReader.compileTextFromFile(file);
				String ext = FileUtil.getExtension(file);
				if(ext.equals("php") || ext.equals("txt") || ext.equals("css")|| ext.equals("js")) {
					
					
					int lineNo = 0;
					//if(!file.getAbsolutePath().endsWith(".php"))
					//	continue;
					for(String line : strings) {
						if(line.contains(".txt"))
							System.out.println(line);
						
						Matcher matcher = RegexLib.EMAIL_ADDRESS.matcher(line);
						while(matcher.find()) {
						
							String email = matcher.group();
					
							draft.write(file.getAbsolutePath() + " " + lineNo + " " + email);
							draft.newLine();
					    }	
						
						lineNo++;
					}
				}
			}
			
		};
		
		IterateDirectory.execute(PATH, sieve);
		draft.close();
	}
	
	public static byte[] compress(final String str) throws IOException {
        if ((str == null) || (str.length() == 0)) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        return obj.toByteArray();
    }
	
	 public static String decompress(final byte[] compressed) throws IOException {
	        String outStr = "";
	        if ((compressed == null) || (compressed.length == 0)) {
	            return "";
	        }
	        if (isCompressed(compressed)) {
	            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, "UTF-8"));

	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                outStr += line;
	            }
	        } else {
	            outStr = new String(compressed);
	        }
	        return outStr;
	    }

	    public static boolean isCompressed(final byte[] compressed) {
	        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	    }
}
