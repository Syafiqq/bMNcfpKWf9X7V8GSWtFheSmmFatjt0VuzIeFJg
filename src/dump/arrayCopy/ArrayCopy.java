package dump.arrayCopy;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;

/**
 * This <Skripsi_003> project in package <dump.arrayCopy> created by :
 * Name         : syafiq
 * Date / Time  : 20 May 2016, 7:12 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ArrayCopy
{
    public static void main(String[] args)
    {
        int   size = 20;
        int[] a    = new int[size];

        for(int i = -1; ++i < size; )
        {
            a[i] = i;
        }

        System.out.println(Arrays.toString(a));

        int[] b = new int[size];
        System.arraycopy(a, 0, b, 0, size);

        System.out.println(Arrays.toString(b));
        System.out.println(a == b);

        int[] c = a.clone();
        System.out.println(Arrays.toString(c));
        System.out.println(a == c);
        System.out.println(b == c);

        int[] d = IntArrays.copy(a, 0, size);
        System.out.println(Arrays.toString(d));
        System.out.println(a == d);
        System.out.println(b == d);
        System.out.println(c == d);
        System.out.println(d == d);
    }
}
