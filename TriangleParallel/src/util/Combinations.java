package util;

public class Combinations {

    /**
     * Gets the x-th lexicographically ordered p-combination of {1,...,n}
     * @param n Size of numbers to choose from
     * @param p Size of combinations
     * @param x Index of combination desired
     * @return The x-th lexicographically ordered p-combination of {1,...,n}
     */
    public static int[] getCombination(int n, int p, int x){
        int i,r,k = 0;
        int[] c = new int[p];
        for(i=0;i<p-1;i++){
            c[i] = (i != 0) ? c[i-1] : 0;
            do {
                c[i]++;
                r = choose(n-c[i],p-(i+1));
                k = k + r;
            } while(k < x);
            k = k - r;
        }
        c[p-1] = c[p-2] + x - k;

        return c;
    }

    /**
     * Computes n choose k
     * @param n n in n choose k
     * @param k k in n choose k
     * @return n choose k
     */
    public static int choose(int n, int k){
        if(k == 0) return 1;
        return (n * choose(n-1, k-1)) / k;
    }
}
