package model.dataset.component;

/*
 * This <Skripsi_003> project in package <model.dataset.component> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 7:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Lesson
{
    /*
     * Nomenclature :
     *
     * @param classes = Class
     * @param subject = Subject
     * @param lecture = Lecture
     * @param sks = Sks
     * @param link = Link
     * @param available_classroom = Available Classroom
     */
    public int       clazz;
    public int       subject;
    public int       lecture;
    public int       sks;
    public int       parent;
    public int[]     link;
    public int[]     available_classroom;
    public boolean[] allowed_classroom;

    /**
     * @param subject                  = Subject
     * @param lecture                  = Lecture
     * @param sks                      = Sks
     * @param clazz                    = Class
     * @param link_size                = Link Size
     * @param available_classroom_size = Available Classroom Size
     */
    public Lesson(int subject, int lecture, int sks, int clazz, int link_size, int available_classroom_size)
    {
        this(subject, lecture, sks, clazz, link_size, available_classroom_size, -1);
    }

    public Lesson(int subject, int lecture, int sks, int clazz, int link_size, int available_classroom_size, int lesson_parent)
    {
        this.subject = subject;
        this.lecture = lecture;
        this.clazz = clazz;
        this.sks = sks;
        this.link = new int[link_size];
        this.available_classroom = new int[available_classroom_size];
        this.parent = lesson_parent;
    }
}

