package model.dataset.component;

import java.util.Arrays;

/*
 * This <Skripsi_003> project in package <model.dataset.component> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 6:43 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Timeoff
{
    /*
     * Global Important Variable
     *
     * @param placement = Time-off
     */
    public double[][] timeoff;


    /**
     * @param active_day_size    = Active Day Size
     * @param active_period_size = Active Period Size
     */
    public Timeoff(int active_day_size, int active_period_size)
    {
        this.timeoff = new double[active_day_size][active_period_size];
    }

    /**
     * @param day    = Current Day
     * @param period = Current Period
     * @param status = Current Value
     */
    public void add(int day, int period, double status)
    {
        this.timeoff[day][period] = status;
    }

    /**
     * Print Time-off
     */
    public void printTimeoff()
    {
        for(int i = -1, is = this.timeoff.length; ++i < is; )
        {
            System.out.println(Arrays.toString(this.timeoff[i]));
        }
    }

    /**
     * @param day    = Current Day
     * @param period = Current Period
     * @return Current Day and Period Time-off
     */
    public double get(int day, int period)
    {
        return this.timeoff[day][period];
    }
}