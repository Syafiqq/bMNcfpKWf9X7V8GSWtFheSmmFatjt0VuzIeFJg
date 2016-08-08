package model.dataset.component;

import java.util.Arrays;
import model.helper.IntHList;

/*
 * This <Skripsi_003> project in package <model.dataset.component> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 6:43 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TimeoffPlacement
{
    /*
     * Global Important Variable
     *
     * @param placement = Time-off
     */
    public IntHList[][] placement;


    /**
     * @param active_day_size    = Active Day Size
     * @param active_period_size = Active Period Size
     * @param classroom_size     = Classroom Size
     */
    public TimeoffPlacement(int active_day_size, int active_period_size, int classroom_size)
    {
        this.placement = new IntHList[active_day_size][active_period_size];
        for(int counter_day = -1; ++counter_day < active_day_size; )
        {
            for(int counter_period = -1; ++counter_period < active_period_size; )
            {
                this.placement[counter_day][counter_period] = new IntHList(classroom_size);
            }
        }
    }

    /**
     * Print Time-off
     */
    public void printTimeoff()
    {
        for(int i = -1, is = this.placement.length; ++i < is; )
        {
            System.out.println(Arrays.toString(this.placement[i]));
        }
    }

    public void resetPlacement()
    {
        for(IntHList[] day : this.placement)
        {
            for(IntHList period : day)
            {
                period.reset();
            }
        }
    }

    public boolean putPlacementIfAbset(int day_index, int period_index, int lesson)
    {
        this.placement[day_index][period_index].add(lesson);
        return this.placement[day_index][period_index].counter == 0;
    }

    public boolean isNotTheSameDay(int day, int[] link)
    {
        for(IntHList period : this.placement[day])
        {
            for(int lesson : link)
            {
                for(int counter_lessons = -1, lessons_size = period.counter; ++counter_lessons <= lessons_size; )
                {
                    if(period.list[counter_lessons] == lesson)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}