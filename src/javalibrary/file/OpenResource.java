package javalibrary.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OpenResource {

	private InputStream inputStream;
	private BufferedReader bufferedReader;
	
	public OpenResource(String resourcePath) {
		this.inputStream = OpenResource.class.getResourceAsStream(resourcePath);
		this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
	}
	
	public String nextLine() {
		try {
			return this.bufferedReader.readLine();
		} 
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean close() {
		try {
			if(this.inputStream != null) this.inputStream.close();
			if(this.bufferedReader != null) this.bufferedReader.close();
			return true;
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
