package model.pso.component;

import model.dataset.component.TimeoffPlacement;
import model.helper.IntHList;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 16 June 2016, 9:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PlacementProperties
{
    public  TimeoffPlacement[] lecture_placement;
    public  TimeoffPlacement[] class_placement;
    public  IntHList           lecture_fill;
    public  IntHList           class_fill;
    private int                classroom;
    private int                day;
    private int                period;

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
        for(int counter_classroom = -1, classroom_size = this.classroom; ++counter_classroom < classroom_size; )
        {
            for(int counter_day = -1, day_size = this.day; ++counter_day < day_size; )
            {
                for(int counter_period = -1, period_size = this.period; ++counter_period < period_size; )
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
/*        for(TimeoffPlacement placement : this.lecture_placement)
        {
            placement.resetPlacement();
        }

        for(TimeoffPlacement placement : this.class_placement)
        {
            placement.resetPlacement();
        }*/
    }
}
