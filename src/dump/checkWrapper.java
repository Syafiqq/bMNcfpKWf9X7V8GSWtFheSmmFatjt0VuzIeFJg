package dump;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This <Skripsi_003> project in package <dump> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 7:43 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
class Wrapper
{
    public int a;

    public Wrapper(int i)
    {
        this.set(i);
    }

    public String toString()
    {
        return String.format("%d", this.a);
    }

    public void set(int i)
    {
        this.a = i;
    }
}

public class checkWrapper
{
    public static void main(String[] args)
    {
        int       size = 5;
        Wrapper[] b    = new Wrapper[size];
        for(int i = -1; ++i < size; )
        {
            b[i] = new Wrapper(i);
        }

        for(final Wrapper tmp : b)
        {
            System.out.println(tmp);
        }

        for(final Wrapper tmp : b)
        {
            tmp.set(ThreadLocalRandom.current().nextInt(10));
        }

        for(final Wrapper tmp : b)
        {
            System.out.println(tmp);
        }
    }
}
