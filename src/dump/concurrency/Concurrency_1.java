package dump.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This <Skripsi_003> project in package <dump.concurrency> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2016, 9:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Concurrency_1
{
    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newCachedThreadPool();

        int[] asw = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(final int i : asw)
        {
            Runnable task = () -> doSomething(i);

            executor.submit(task);
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
        System.out.println("end");
    }

    private static void doSomething(int i)
    {
        System.out.println(i);
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ignored)
        {
        }
    }
}
