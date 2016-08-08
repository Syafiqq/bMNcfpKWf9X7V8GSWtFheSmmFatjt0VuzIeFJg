package dump.DataType;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;

/**
 * This <Skripsi_003> project in package <dump.DataType> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 10:06 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class LinkedHashSetDuplicates
{
    public static void main(String[] args)
    {
        IntLinkedOpenHashSet a = new IntLinkedOpenHashSet(10);
        for(int i = -1, is = 10; ++i < is; )
        {
            a.add(i);
        }
        System.out.println(a);

        for(int i = -1, is = 10; ++i < is; )
        {
            a.add(i);
        }
        System.out.println(a);
    }
}
