package model.dataset.core;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import model.dataset.component.Timeoff;

/*
 * This <Skripsi_003> project in package <model.dataset.core> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 7:39 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class LessonPoolSet
{
    public LessonGroupSet[]         merge;
    public int[]                    lessons;
    public int[]                    lesson_null;
    public int[]                    classrooms;
    public Timeoff[]                classroom_timeoff;
    public int[][][]                classroom_available_time;
    public Int2IntLinkedOpenHashMap clustered_classroom_encoder;
    public Int2IntLinkedOpenHashMap clustered_classroom_decoder;

    public LessonPoolSet()
    {
    }
}
