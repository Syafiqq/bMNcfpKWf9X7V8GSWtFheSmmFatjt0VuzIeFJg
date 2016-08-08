package dump.Locking;

/**
 * This <Skripsi_003> project in package <dump.Locking> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2016, 8:50 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Locking_1
{
    static int cok   = 0;
    static int cokes = 0;

    public static void main(String[] args)
    {
        final Object lock = new Object();

        Thread t = new Thread(() ->
        {
            synchronized(lock)
            {
                while(true)
                {
                    try
                    {
                        cok = (int) (Math.random() * 10);
                        System.out.printf("THREAD\tcok = %d\n", cok);
                        Thread.sleep(900);

                        cokes = cok;
                        lock.wait();
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            }
        });

        t.setDaemon(true);
        t.start();
        for(int i = 0, is = 10; ++i < is; )
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            synchronized(lock)
            {
                lock.notify();
            }
            System.out.println(cokes);
        }
    }

}
