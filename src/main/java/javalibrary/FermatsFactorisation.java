package javalibrary;

public class FermatsFactorisation {

    public static void main(String[] args) {
        process(2146754568);

        /**
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < 40000; i++) {
            int n = 2146754366 + i;
            int c = process(n);

            if(c < min) {
                System.out.println("c: " + c + " n: " + n);
                min = c;
            }
        }**/
    }

    public static int process(int n) {

        int c = 1;
        while(n % 2 == 0) {
            n /= 2;
            c *= 2;
        }

        int x = (int)Math.ceil(Math.sqrt(n));
        int r;

        int count = 0;
        do {
            x = x + 1;
            r = (int)Math.pow(x, 2) - n;
            count++;
        }
        while(!isSquare(r));

        int a = x + (int)Math.sqrt(r);
        int b = x - (int)Math.sqrt(r);
        System.out.println("" + count);
        System.out.println("a: " + a + " b: " + b + " c: " + c);
        return count;
    }

    public static boolean isSquare(int n) {
        return Math.floor(Math.sqrt(n)) == Math.ceil(Math.sqrt(n));
    }
}
