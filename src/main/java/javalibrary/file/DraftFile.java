package javalibrary.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DraftFile {

	public BufferedWriter bw;
	public FileWriter fw;
	
	public DraftFile(String fileName) {
		try {
			this.fw = new FileWriter(fileName);
			this.bw = new BufferedWriter(this.fw);
		} 
		catch (IOException e) {
			e.printStackTrace();
			this.close();
		} 
	}
	
	public void write(String line) {
		try {
			this.bw.write(line);
			this.bw.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(char[] line) {
		try {
			this.bw.write(line, 0, line.length);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newLine() {
		try {
			this.bw.newLine();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean close() {
		try {
			if(this.bw != null) this.bw.close();
			if(this.fw != null) this.fw.close();
			
			return true;
		} 
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}