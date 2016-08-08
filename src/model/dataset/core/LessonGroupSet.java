package model.dataset.core;

/**
 * This <Skripsi_003> project in package <model.dataset.core> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 7:07 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class LessonGroupSet
{
    public int[] classrooms;
    public int[] lessons;
    public int[] local_sks_distribution;

    public LessonGroupSet(int classroom_size, int lesson_size, int sks_distribution_size)
    {
        this.classrooms = new int[classroom_size];
        this.lessons = new int[lesson_size];
    }

    public LessonGroupSet()
    {
        this.classrooms = this.lessons = null;
    }
}
