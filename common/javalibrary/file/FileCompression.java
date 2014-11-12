package javalibrary.file;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

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
