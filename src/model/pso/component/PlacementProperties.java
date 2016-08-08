package model.pso.component;

import model.dataset.component.TimeoffPlacement;
import model.helper.IntHList;

/*
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 16 June 2016, 9:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PlacementProperties
{
    public final  TimeoffPlacement[] lecture_placement;
    public final  TimeoffPlacement[] class_placement;
    public final  IntHList           lecture_fill;
    public final  IntHList           class_fill;
    private final int                classroom;
    private final int                day;
    private final int                period;

    public PlacementProperties(int lecture_total, int class_total, int classroom_total, int day_total, int period_total, int active_classroom)
    {
        this.lecture_placement = new TimeoffPlacement[lecture_total];
        this.class_placement = new TimeoffPlacement[class_total];
        for(int counter_lecture_placement = -1, lecture_placement_size = this.lecture_placement.length; ++counter_lecture_placement < lecture_placement_size; )
        {
            this.lecture_placement[counter_lecture_placement] = new TimeoffPlacement(day_total, period_total, classroom_total);
        }
        for(int counter_class_placement = -1, class_placement_size = this.class_placement.length; ++counter_class_placement < class_placement_size; )
        {
            this.class_placement[counter_class_placement] = new TimeoffPlacement(day_total, period_total, classroom_total);
        }
        lecture_fill = new IntHList(active_classroom * day_total * period_total);
        class_fill = new IntHList(active_classroom * day_total * period_total);
        this.classroom = active_classroom;
        this.period = period_total;
        this.day = day_total;
    }

    public void resetPlacement()
    {
        int counter_reset = -1;
        for(int counter_classroom = -1; ++counter_classroom < this.classroom; )
        {
            for(int counter_day = -1; ++counter_day < this.day; )
            {
                for(int counter_period = -1; ++counter_period < this.period; )
                {
                    ++counter_reset;
                    try
                    {
                        this.lecture_placement[this.lecture_fill.list[counter_reset]].placement[counter_day][counter_period].counter = -1;
                    }
                    catch(ArrayIndexOutOfBoundsException ignored)
                    {

                    }
                    try
                    {
                        this.class_placement[this.class_fill.list[counter_reset]].placement[counter_day][counter_period].counter = -1;
                    }
                    catch(ArrayIndexOutOfBoundsException ignored)
                    {

                    }
                }
            }
        }
        this.lecture_fill.reset();
        this.class_fill.reset();
    }
}
