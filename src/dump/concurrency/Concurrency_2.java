package dump.concurrency;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * This <Skripsi_003> project in package <dump.concurrency> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2016, 9:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Concurrency_2
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newCachedThreadPool();

        int[][] asw = new int[20][5];
        for(int i = -1, is = asw.length, c = 0; ++i < is; )
        {
            for(int j = -1, js = asw[i].length; ++j < js; )
            {
                asw[i][j] = ++c;
            }
        }
        for(int i[] : asw)
        {
            System.out.println(Arrays.toString(i));
        }
        for(final int i[] : asw)
        {
            doSomething(i);
            //executor.submit(() -> doSomething(i));
        }
        executor.shutdown();

        try
        {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        for(int i[] : asw)
        {
            System.out.println(Arrays.toString(i));
        }
    }

    private static void doSomething(int i[])
    {
        for(int j = -1, js = i.length; ++j < js; )
        {
            i[j] = i[j] * 10;
        }
        shuffleArray(i);
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ignored)
        {
        }
    }

    public static void shuffleArray(int[] array)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for(int counter_array = array.length - 1; counter_array > 0; counter_array--)
        {
            int index = rnd.nextInt(counter_array + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[counter_array];
            array[counter_array] = a;
        }
    }
}
