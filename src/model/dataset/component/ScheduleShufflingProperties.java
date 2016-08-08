package model.dataset.component;

/**
 * This <Skripsi_003> project in package <model.dataset.component> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 10:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ScheduleShufflingProperties implements Cloneable
{
    public final int[]       day_set;
    public final int[][][]   classrooms_set;
    public final int[][][]   lessons_set;
    public final int[][][][] classroom_current_time;

    public ScheduleShufflingProperties(int[] day_set, int[][][] classrooms_set, int[][][] lessons_set, int[][][][] classroom_current_time)
    {
        this.day_set = day_set;
        this.classrooms_set = classrooms_set;
        this.lessons_set = lessons_set;
        this.classroom_current_time = classroom_current_time;
    }

/*
    @Override public ScheduleShufflingProperties clone()
    {
        final int[] day_set_source      = this.day_set;
        final int[] day_set_destination = new int[this.day_set.length];

        final int[][][]   classroom_set_source               = this.classrooms_set;
        final int[][][]   classroom_set_destination          = new int[this.classrooms_set.length][][];
        final int[][][]   lesson_set_source                  = this.lessons_set;
        final int[][][]   lesson_set_destination             = new int[this.lessons_set.length][][];
        final int[][][][] classroom_current_time_source      = this.classroom_current_time;
        final int[][][][] classroom_current_time_destination = new int[this.classroom_current_time.length][][][];

        System.arraycopy(day_set_source, 0, day_set_destination, 0, day_set_source.length);

        for(int counter_entity = -1, entity_size = classroom_set_source.length; ++counter_entity < entity_size; )
        {
            classroom_set_destination[counter_entity] = new int[classroom_set_source[counter_entity].length][];
            lesson_set_destination[counter_entity] = new int[lesson_set_source[counter_entity].length][];
            classroom_current_time_destination[counter_entity] = new int[classroom_current_time_source[counter_entity].length][][];

            for(int counter_entity_child = -1, entity_child_size = classroom_set_source[counter_entity].length; ++counter_entity_child < entity_child_size; )
            {
                classroom_set_destination[counter_entity][counter_entity_child] = new int[classroom_set_source[counter_entity][counter_entity_child].length];
                lesson_set_destination[counter_entity][counter_entity_child] = new int[lesson_set_source[counter_entity][counter_entity_child].length];

                System.arraycopy(classroom_set_source[counter_entity][counter_entity_child], 0, classroom_set_destination[counter_entity][counter_entity_child], 0, classroom_set_source[counter_entity][counter_entity_child].length);
                System.arraycopy(lesson_set_source[counter_entity][counter_entity_child], 0, lesson_set_destination[counter_entity][counter_entity_child], 0, lesson_set_source[counter_entity][counter_entity_child].length);
            }

            for(int counter_classroom = -1, classroom_size = classroom_current_time_source[counter_entity].length; ++counter_classroom < classroom_size; )
            {
                classroom_current_time_destination[counter_entity][counter_classroom] = new int[day_set.length][2];
            }
        }

        return new ScheduleShufflingProperties(day_set_destination, classroom_set_destination, lesson_set_destination, classroom_current_time_destination);
    }
*/

    public static ScheduleShufflingProperties newInstance(ScheduleShufflingProperties properties)
    {
        final int[] day_set_source      = properties.day_set;
        final int[] day_set_destination = new int[properties.day_set.length];

        final int[][][]   classroom_set_source               = properties.classrooms_set;
        final int[][][]   classroom_set_destination          = new int[properties.classrooms_set.length][][];
        final int[][][]   lesson_set_source                  = properties.lessons_set;
        final int[][][]   lesson_set_destination             = new int[properties.lessons_set.length][][];
        final int[][][][] classroom_current_time_source      = properties.classroom_current_time;
        final int[][][][] classroom_current_time_destination = new int[properties.classroom_current_time.length][][][];

        System.arraycopy(day_set_source, 0, day_set_destination, 0, day_set_source.length);

        for(int counter_entity = -1, entity_size = classroom_set_source.length; ++counter_entity < entity_size; )
        {
            classroom_set_destination[counter_entity] = new int[classroom_set_source[counter_entity].length][];
            lesson_set_destination[counter_entity] = new int[lesson_set_source[counter_entity].length][];
            classroom_current_time_destination[counter_entity] = new int[classroom_current_time_source[counter_entity].length][][];

            for(int counter_entity_child = -1, entity_child_size = classroom_set_source[counter_entity].length; ++counter_entity_child < entity_child_size; )
            {
                classroom_set_destination[counter_entity][counter_entity_child] = new int[classroom_set_source[counter_entity][counter_entity_child].length];
                lesson_set_destination[counter_entity][counter_entity_child] = new int[lesson_set_source[counter_entity][counter_entity_child].length];

                System.arraycopy(classroom_set_source[counter_entity][counter_entity_child], 0, classroom_set_destination[counter_entity][counter_entity_child], 0, classroom_set_source[counter_entity][counter_entity_child].length);
                System.arraycopy(lesson_set_source[counter_entity][counter_entity_child], 0, lesson_set_destination[counter_entity][counter_entity_child], 0, lesson_set_source[counter_entity][counter_entity_child].length);
            }

            for(int counter_classroom = -1, classroom_size = classroom_current_time_source[counter_entity].length; ++counter_classroom < classroom_size; )
            {
                classroom_current_time_destination[counter_entity][counter_classroom] = new int[properties.day_set.length][2];
            }
        }

        return new ScheduleShufflingProperties(day_set_destination, classroom_set_destination, lesson_set_destination, classroom_current_time_destination);
    }


    public void reset_classroom_current_time()
    {
        for(int[][][] lesson_pool : this.classroom_current_time)
        {
            for(int[][] classrooms : lesson_pool)
            {
                for(int[] day : classrooms)
                {
                    for(int counter_property = -1, property_size = day.length; ++counter_property < property_size; )
                    {
                        day[counter_property] = 0;
                    }
                }
            }
        }
    }
}
