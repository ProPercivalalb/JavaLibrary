package javalibrary.cipher.permentate;

public class Permentations {
	
	public static void permutate(PermentateArray permen, int[] arr) {
		permutate(permen, arr, 0);
	}
	
	private static void permutate(PermentateArray permen, int[] arr, int pos) {
	    if(arr.length - pos == 1) {
	    	permen.onPermentate(arr);
	    }
	    else
		    for(int i = pos; i < arr.length; i++) {
		    	int h = arr[pos];
		        int j = arr[i];
		        arr[pos] = j;
		        arr[i] = h;
		            
		        permutate(permen, arr, pos + 1);
		        arr[pos] = h;
		    	arr[i] = j;
		    }
	}
}
