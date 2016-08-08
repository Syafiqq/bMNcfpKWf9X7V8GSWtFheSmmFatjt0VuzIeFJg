package dump.arrayIntialization;

/**
 * This <Skripsi_003> project in package <dump.dump_0001> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 6:51 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Main
{
    public static void main(String[] args)
    {
        int[][] tof = new int[10][10];
        for(int i = -1, is = tof.length; ++i < is; )
        {
            for(int j = -1, js = tof[i].length; ++j < js; )
            {
                tof[i][j] = 0;
            }
        }
    }
}
