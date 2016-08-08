package model.dataset.core;

import model.dataset.component.Lesson;
import model.dataset.component.ScheduleShufflingProperties;
import model.dataset.component.Timeoff;

/**
 * This <Skripsi_003> project in package <model.dataset.core> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 7:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class DatasetBuilder1<TDataset extends Dataset<? extends Timeoff, ? extends Lesson>, TWorkingSet extends WorkingSet, TDatasetConverter extends DatasetConverter<?>> extends DatasetBuilder<TDataset, TDatasetConverter>
{
    protected final TWorkingSet workingset;

    public DatasetBuilder1(TWorkingSet workingset, TDataset dataset, TDatasetConverter encoder, TDatasetConverter decoder)
    {
        super(dataset, encoder, decoder);
        this.workingset = workingset;
    }

    protected abstract void generateLessonGroupSet();

    protected abstract void generateLessonPool();

    protected abstract int[][] generateRandomSchedule(final ScheduleShufflingProperties schedule_properties);

    public TWorkingSet getWorkingset()
    {
        return this.workingset;
    }
}

