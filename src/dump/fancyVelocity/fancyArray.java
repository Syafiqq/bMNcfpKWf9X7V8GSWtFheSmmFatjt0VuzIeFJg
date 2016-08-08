package dump.fancyVelocity;

import java.util.Arrays;

/**
 * This <Skripsi_003> project in package <dump.fancyVelocity> created by :
 * Name         : syafiq
 * Date / Time  : 23 May 2016, 7:08 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class fancyArray
{
    public static void main(String[] args)
    {
        int[] a = new int[10];
        int[] b = new int[10];

        for(int i = -1, is = 10; ++i < is; )
        {
            a[i] = i;
        }
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.arraycopy(a, 0, b, 0, 10);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        a[5] = 50;
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }
}
