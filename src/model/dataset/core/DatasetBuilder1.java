package model.dataset.core;

import model.dataset.component.Lesson;
import model.dataset.component.ScheduleShufflingProperties;
import model.dataset.component.Timeoff;

/*
 * This <Skripsi_003> project in package <model.dataset.core> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 7:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class DatasetBuilder1<Dataset extends model.dataset.core.Dataset<? extends Timeoff, ? extends Lesson>, WorkingSet extends model.dataset.core.WorkingSet, DatasetConverter extends model.dataset.core.DatasetConverter<?>> extends DatasetBuilder<Dataset, DatasetConverter>
{
    protected final WorkingSet workingset;

    public DatasetBuilder1(WorkingSet workingset, Dataset dataset, DatasetConverter encoder, DatasetConverter decoder)
    {
        super(dataset, encoder, decoder);
        this.workingset = workingset;
    }

    protected abstract void generateLessonGroupSet();

    protected abstract void generateLessonPool();

    protected abstract int[][] generateRandomSchedule(final ScheduleShufflingProperties schedule_properties);

    public WorkingSet getWorkingset()
    {
        return this.workingset;
    }
}

