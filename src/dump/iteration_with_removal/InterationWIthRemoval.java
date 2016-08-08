package dump.iteration_with_removal;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;

/**
 * This <Skripsi_003> project in package <dump.iteration_with_removal> created by :
 * Name         : syafiq
 * Date / Time  : 20 May 2016, 2:42 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class InterationWIthRemoval
{
    public static void main(String[] args)
    {
        int                  size = 20;
        IntLinkedOpenHashSet a    = new IntLinkedOpenHashSet(size);
        IntLinkedOpenHashSet ar   = new IntLinkedOpenHashSet(size);
        for(int i = -1, is = size; ++i < is; )
        {
            a.add(i);
        }

        for(int i : a)
        {
            //System.out.printf("%3d\t",i);
            ar.add(i);
            //System.out.printf("%s\n", Arrays.toString(a.toIntArray()));
        }
    }
}
