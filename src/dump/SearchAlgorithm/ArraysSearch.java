package dump.SearchAlgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This <Skripsi_003> project in package <dump.SearchAlgorithm> created by :
 * Name         : syafiq
 * Date / Time  : 17 May 2016, 8:54 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ArraysSearch
{
    public static void main(String[] args)
    {
        int[] a = new int[100];
        for(int i = -1, is = a.length; ++i < is; )
        {
            a[i] = i;
        }

        System.out.println(Arrays.toString(a));
        shuffleArray(a);
        System.out.println(Arrays.toString(a));

        for(int i = -1, is = a.length; ++i < is; )
        {
            System.out.print(Arrays.binarySearch(a, i) >= 0 ? 'T' : 'F');
        }
        System.out.println();
        System.out.println(Arrays.toString(a));
    }

    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for(int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}