package model.dataset.core;

/**
 * This <Skripsi_003> project in package <model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:50 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Dataset<TTimeoff, TLesson>
{
    /**
     * Nomenclature :
     * <p>
     * SubType :
     * TTimeoff = Time-off
     * TLesson = Lesson
     * <p>
     * Global Helper Variable :
     * school : School
     * active_days : Active Days
     * active_periods : Active Periods
     * classes : Classes
     * classrooms : Classrooms
     * lecturers : Lectures
     * subjects : Subjects
     * lessons : Lessons
     */

    public final int        school;
    public       int[]      active_days;
    public       int[]      active_periods;
    public       TTimeoff[] classes;
    public       TTimeoff[] classrooms;
    public       TTimeoff[] lecturers;
    public       TTimeoff[] subjects;
    public       TLesson[]  lessons;

    public Dataset(final int school)
    {
        this.school = school;
    }
}
