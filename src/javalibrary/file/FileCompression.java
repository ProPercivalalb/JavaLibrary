package javalibrary.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileCompression {

	public static byte[] compressString(String str) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream(str.length());
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes());
			gzip.close();
		    return out.toByteArray();
		}
	    catch(Exception e) {
	    	e.printStackTrace();
	       	return null;
	    }
	}
	
	public static String decompressString(InputStream stream) {
		try {
			GZIPInputStream gis = new GZIPInputStream(stream);
			BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
	        String outStr = "";
	        String line;
	        while ((line = bf.readLine()) != null) {
	          outStr += line + "\n";
	        }
	        return outStr;
		}
	    catch(Exception e) {
	    	e.printStackTrace();
	       	return null;
	    }
	}
	
	public static byte[] compressTextFile(File file) {
        try {
		
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
	        DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
	        return bytearrayoutputstream.toByteArray();
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }

	}
}
