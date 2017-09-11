package javalibrary.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javalibrary.lib.Timer;
import javalibrary.streams.FileReader;

public class FileTest {

	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		String magic = "split,me,up,so,badly,i,want,to,eat,cheese";
		File file = new File("C:\\Users\\alexl\\Desktop\\read.txt");
		
		
		
		timer.restart();
		for(int i = 0; i < 10000; i++) {
			String[] split = magic.split(",");
		}
		timer.displayTime();
		
		
		
		timer.restart();
		char[] array = magic.toCharArray();
		for(int i = 0; i < 10000; i++) {
			int[] indexArray = new int[array.length];

			
			int count = 0;
			for(int j = 0; j < array.length; j++) {
				if(array[j] == ',')
					indexArray[count++] = j;
			}
			
			
			char[][] finalAr = new char[count][];
			
			for(int j = 0; j < count; j++) {
				finalAr[j] = new char[indexArray[j]];
				
				for(int k = 0; k < indexArray[j]; k++) {
					
				}
			}
			
			
		}
		
		
		timer.displayTime();
		

	}

}
