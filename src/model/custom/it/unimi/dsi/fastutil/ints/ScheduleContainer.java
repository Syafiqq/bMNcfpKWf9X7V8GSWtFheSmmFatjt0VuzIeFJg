package model.custom.it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrayList;

/**
 * This <Skripsi_003> project in package <model.custom.it.unimi.dsi.fastutil.ints> created by :
 * Name         : syafiq
 * Date / Time  : 18 May 2016, 3:31 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ScheduleContainer extends IntArrayList
{
    private int sizesks;

    public ScheduleContainer(int initialCapacity)
    {
        super(initialCapacity);
        this.sizesks = 0;
    }

    public boolean addSchedule(final int k, final int sks)
    {
        boolean bool = super.add(k);
        if(bool)
        {
            this.sizesks += sks;
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getSizeSKS()
    {
        return this.sizesks;
    }
}
