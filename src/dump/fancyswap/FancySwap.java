package dump.fancyswap;

import org.junit.Test;

/**
 * This <Skripsi_003> project in package <dump.fancyswap> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 9:07 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class FancySwap
{
    public static int getItself(int itself, int dummy)
    {
        return itself;
    }

    public static void main(String[] args)
    {
        int a = 10;
        int b = 20;

        for(int i = -1, is = 0xFFFFFFFF; ++i < is; )
        {
            int temp = a;
            a = b;
            b = a;
        }

/*        System.out.println(a);
        System.out.println(b);
        a = getItself(b, b = a);
        System.out.println(a);
        System.out.println(b);*/
    }

    @SuppressWarnings("SuspiciousNameCombination") @Test public void f1()
    {
        int x = 10;
        int y = 20;

        for(long i = -1, is = Integer.MAX_VALUE * 10L; ++i < is; )
        {
            final int temp = x;
            x = y;
            y = temp;
            //x = getItself(y, y = x);
        }
    }
}
