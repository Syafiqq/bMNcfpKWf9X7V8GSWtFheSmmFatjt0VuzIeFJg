package model.dataset.core;

/*
 * This <Skripsi_003> project in package <model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 4:50 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Dataset<Timeoff, Lesson>
{
    /*
     * Nomenclature :
     * <p>
     * SubType :
     * Timeoff = Time-off
     * Lesson = Lesson
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

    public final int       school;
    public       int[]     active_days;
    public       int[]     active_periods;
    public       Timeoff[] classes;
    public       Timeoff[] classrooms;
    public       Timeoff[] lecturers;
    public       Timeoff[] subjects;
    public       Lesson[]  lessons;

    public Dataset(final int school)
    {
        this.school = school;
    }
}
