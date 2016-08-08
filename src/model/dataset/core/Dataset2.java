package model.dataset.core;

/**
 * This <Skripsi_003> project in package <model.dataset> created by :
 * Name         : syafiq
 * Date / Time  : 17 May 2016, 11:31 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Dataset2<TTimeoff, TLesson> extends Dataset<TTimeoff, TLesson>
{
    public int[][][] classroom_available_time;
    public int[]     sks_distribution;


    public Dataset2(int school)
    {
        super(school);
    }
}
