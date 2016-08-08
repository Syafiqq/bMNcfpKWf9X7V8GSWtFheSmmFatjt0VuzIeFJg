package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.ScheduleShufflingProperties;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 30 May 2016, 12:26 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3RandomGeneratorCompareTest
{
    @Test public void TestGeneratorWithoutDebugWithMultithreading()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        final ScheduleShufflingProperties properties = gen.generateProperties();
        int                               size       = 150000;

        ScheduleShufflingProperties[] properties1 = new ScheduleShufflingProperties[size];
        for(int i = -1; ++i < size; )
        {
            properties1[i] = ScheduleShufflingProperties.newInstance(properties);
        }

        //ThreadPoolExecutor executor = new ThreadPoolExecutor(4, size, 2, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(size));
        //executor.allowCoreThreadTimeOut(true);
        //ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i = -1; ++i < size; )
        {
            int finalI = i;
            executor.submit((Runnable) () -> gen.generateRandomSchedule(properties1[finalI]));
        }
        executor.shutdown();
        while(!executor.isTerminated())
        {
        }
    }


    @Test public void TestGeneratorWithoutDebugWithoutMultithreading()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        int                               size       = 150000;
        final ScheduleShufflingProperties properties = gen.generateProperties();

        ScheduleShufflingProperties[] properties1 = new ScheduleShufflingProperties[size];
        for(int i = -1; ++i < size; )
        {
            properties1[i] = ScheduleShufflingProperties.newInstance(properties);
        }

        for(int i = 0; i < size; ++i)
        {
            gen.generateRandomSchedule(properties);
        }
    }
}
