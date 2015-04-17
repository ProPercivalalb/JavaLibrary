package javalibrary.file;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author Alex Barter (10AS)
 */
public class FileCompression {

	public static byte[] compressTextFile(File file) {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        //DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
		
        try {
            //write(par0NBTTagCompound, dataoutputstream);
        }
        finally {
        	//dos.close();
        }
        return bytearrayoutputstream.toByteArray();
	}
}
