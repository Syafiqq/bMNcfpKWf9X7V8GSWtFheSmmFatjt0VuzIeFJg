package model.pso.component;

import java.util.Arrays;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 28 June 2016, 9:12 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class RepairProperties
{
    public boolean[][] absent;
    public int[][]     index;

    public RepairProperties(int classroom_length, int day_length)
    {
        this.absent = new boolean[classroom_length][day_length];
        this.index = new int[classroom_length][day_length];
    }

    public void resetAbsent()
    {
        for(boolean[] absent : this.absent)
        {
            Arrays.fill(absent, false);
        }
    }
}
