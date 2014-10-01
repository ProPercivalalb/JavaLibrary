package javalibrary.cipher;

import java.util.ArrayList;
import java.util.Random;

import javalibrary.fitness.QuadgramsStats;
import javalibrary.string.StringTransformer;

/**
 * @author Alex Barter (10AS)
 */
public class PlayfairAutoOld {

	private static Random rand = new Random();
	
	public static String tryDecode(String cipherText) {
		
		//Removes all characters except letters
		cipherText = StringTransformer.removeEverythingButLetters(cipherText).toLowerCase();
		
		String keySquare = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		int i=0;
	    double score,maxscore=-99e99;
	    // run until user kills it
	    
	    boolean running = true;
	    while(running){
	        i++;
	        ArrayList<Object> info = lookForBestKeySquare(cipherText, keySquare, 20.0D, 1000.0D, 0.1D);
	        keySquare = (String)info.get(0);
	        String deciphered = (String)info.get(2);
	        score = (double)info.get(1);
	        if(score > maxscore){
	            maxscore = score;
	            System.out.println((String.format("best score so far: %f, on iteration %d\n",score,i)));
	            System.out.println((String.format("    Key: '%s'\n",keySquare)));
	            System.out.println((String.format("    Deciphered: '%s'\n",deciphered)));
	            //playfairDecipher(key, cipher,out, len);
	            //System.out.println((String.format("    plaintext: '%s'\n", out)));
	        }
	    }
		
		String plainText = "";
		
		return plainText;
	}
	
	public static ArrayList<Object> lookForBestKeySquare(String cipherText, String bestKey, double TEMP, double COUNT, double STEP) {
		int i, j, count;
	    double t;
	    char temp;
	    String testKey = "";
	    String maxKey = "";
	    double prob, dF, maxscore, score;
	    double bestscore;
	    maxKey = bestKey;
	    String deciphered = Playfair.decode(cipherText, maxKey);
	    maxscore = QuadgramsStats.scoreFitness(deciphered);
	    bestscore = maxscore;
	    for(t = TEMP; t >= 0; t -= STEP){
	        for(count = 0; count < COUNT; count++){ 
	        	testKey = KeySquareManipulation.modifyKey(maxKey);    
	        	deciphered = Playfair.decode(cipherText, testKey);
	            score = QuadgramsStats.scoreFitness(deciphered);
	            dF = score - maxscore;
	            if (dF >= 0){
	                maxscore = score;
	                maxKey = testKey;
	            }
	            else if(t > 0){
	                prob = Math.exp(dF / t);
	                if(prob > 1.0*rand.nextInt(32767) / 32767){
	                    maxscore = score;
	                    maxKey = testKey;   
	                }
	            }
	            // keep track of best score we have seen so far
	            if(maxscore > bestscore){
	                bestscore = maxscore;
	                bestKey = maxKey;  
	            } 
	        }
	    }
	    ArrayList<Object> list = new ArrayList<Object>();
	    list.add(bestKey);
	    list.add(bestscore);
	    list.add(deciphered);
		return list;
	}
}
