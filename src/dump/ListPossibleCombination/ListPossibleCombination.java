package dump.ListPossibleCombination;

/**
 * This <Skripsi_003> project in package <dump.ListPossibleCombination> created by :
 * Name         : syafiq
 * Date / Time  : 30 June 2016, 10:47 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ListPossibleCombination
{
    static void permute(int[] a, int k)
    {
        if(k == a.length)
        {
            //for (int i = 0; i < a.length; i++)
            //{
            //    System.out.print(" [" + a[i] + "] ");
            //}
            //System.out.println();
        }
        else
        {
            for(int i = k; i < a.length; i++)
            {
                int temp = a[k];
                a[k] = a[i];
                a[i] = temp;

                permute(a, k + 1);

                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
    }

    public static void main(String[] args)
    {
        int[] a = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        permute(a, 0);
    }
}
