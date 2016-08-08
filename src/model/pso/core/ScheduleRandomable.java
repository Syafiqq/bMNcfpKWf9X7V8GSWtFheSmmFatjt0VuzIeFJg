package model.pso.core;

import model.dataset.component.ScheduleShufflingProperties;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:55 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public interface ScheduleRandomable<Data_Type>
{
    Data_Type random(ScheduleShufflingProperties properties);

    void random(ScheduleShufflingProperties properties, Data_Type data);
}
