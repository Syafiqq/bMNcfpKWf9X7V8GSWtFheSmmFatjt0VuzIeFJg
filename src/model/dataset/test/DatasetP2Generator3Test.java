package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
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
 * Date / Time  : 16 May 2016, 7:46 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3Test
{
    public static boolean compareArrays(int[] array1, int[] array2)
    {
        boolean b = true;
        if(array1 != null && array2 != null)
        {
            if(array1.length != array2.length)
            {
                b = false;
            }
            else
            {
                for(int i = 0; i < array2.length; i++)
                {
                    if(array2[i] != array1[i])
                    {
                        b = false;
                    }
                }
            }
        }
        else
        {
            b = false;
        }
        return b;
    }

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

/*        ScheduleShufflingProperties[] properties1 = new ScheduleShufflingProperties[200000];
        for(int i = -1; ++i < 200000;)
        {
            properties1[i] = properties.clone();
        }*/

        //ThreadPoolExecutor                executor   = new ThreadPoolExecutor(4, 1000000, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 200000, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        ExecutorService executor = Executors.newCachedThreadPool();
        //executor.allowCoreThreadTimeOut(true);
        for(int i = -1; ++i < 10000; )
        {
            executor.submit((Runnable) () -> gen.generateRandomSchedule(properties));
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

        final ScheduleShufflingProperties properties = gen.generateProperties();

        for(int i = 0; i < 10000000; ++i)
        {
            gen.generateRandomSchedule(properties);
        }
    }

    @Test public void TestGeneratorWithoutDebugWithParameterWithoutMultithreading()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties = gen.generateProperties();

        final int[][] a = gen.generateRandomSchedule(properties);

        for(int i = 0; i < 999999; ++i)
        {
            gen.generateRandomSchedule(properties, a);
        }
    }

    @Test public void TestGeneratorWithParameterWithStabilityCheck()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties = gen.generateProperties();
        final int[][]                     a          = gen.generateRandomSchedule(properties);
        ;

/*
        for(int[] c : a)
        {
            System.out.println(Arrays.toString(c));
        }

        System.out.println();
        for(int[] c : a)
        {
            System.out.println(c.length);
        }
*/

/*        for(int[] c : a)
        {
            int[] tmp = IntArrays.copy(c);
            IntArrays.mergeSort(tmp);
            System.out.println(Arrays.toString(tmp));
        }*/

        int[][] b = new int[a.length][];
        for(int i = -1, is = a.length; ++i < is; )
        {
            b[i] = new int[a[i].length];
            System.arraycopy(a[i], 0, b[i], 0, a[i].length);
            IntArrays.mergeSort(b[i]);
        }

/*        System.out.println();
        System.out.println();
        System.out.println();*/

        int counters = 0;

        for(int i = 0; i < 10000000; ++i)
        {
            int counter = 0;
            gen.generateRandomSchedule(properties, a);


            for(int j = -1, is = a.length; ++j < is; )
            {
                int[] c = new int[a[j].length];
                System.arraycopy(a[j], 0, c, 0, a[j].length);
                IntArrays.mergeSort(c);

                counter += compareArrays(c, b[j]) ? 1 : 0;
            }

            counters += (counter == 5 ? 1 : 0);
        }

        System.out.println(counters);




/*
        for(int[] c : a)
        {
            System.out.println(Arrays.toString(c));
        }
        System.out.println();
        for(int[] c : a)
        {
            System.out.println(c.length);
        }
*/

/*        for(int[] c : a)
        {
            int[] tmp = IntArrays.copy(c);
            IntArrays.mergeSort(tmp);
            System.out.println(Arrays.toString(tmp));
        }*/

    }

    @Test public void TestGeneratorWithDebugWithoutMultithreading()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties = gen.generateProperties();

        int[][] a = gen.generateRandomSchedule(properties);
        for(int i = 0; i < 1; ++i)
        {
            gen.generateRandomSchedule(properties, a);
        }

/*        for(int[][] b : properties.classrooms_set)
        {
            for(int[] c : b)
            {
                System.out.println(Arrays.toString(c));
            }

            System.out.println();
        }*/

        for(int[] d : a)
        {
            System.out.println(Arrays.toString(d));
        }
    }

    @Test public void TestGeneratorWithDebugAndSortWithoutMultithreading()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties = gen.generateProperties();

        int[][] a = new int[0][];
        for(int i = 0; i < 1; ++i)
        {
            a = gen.generateRandomSchedule(properties);
        }

        for(int[] d : a)
        {
            IntArrays.mergeSort(d);
            System.out.println(Arrays.toString(d));
        }
    }

    @SuppressWarnings("Duplicates") @Test public void TestSchedulingShufflingProperties()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties = gen.generateProperties();

        System.out.println("Day_Set");
        System.out.println(Arrays.toString(properties.day_set));
        System.out.println();
        System.out.println("Classroom");
        int cp = -1;
        for(int[][] pool : properties.classrooms_set)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cg = -1;
            for(int[] group : pool)
            {
                System.out.printf("\t\tgroup_set-%d (%d): %s\n", ++cg, group.length, Arrays.toString(group));
            }
        }
        System.out.println();
        System.out.println("Lesson");
        cp = -1;
        for(int[][] pool : properties.lessons_set)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cg = -1;
            for(int[] group : pool)
            {
                System.out.printf("\t\tgroup_set-%d (%d) : %s\n", ++cg, group.length, Arrays.toString(group));
            }
        }
        System.out.println();
        System.out.println("Classroom Current Time");
        cp = -1;
        for(int[][][] pool : properties.classroom_current_time)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cc = -1;
            for(int[][] classroom : pool)
            {
                System.out.printf("\t\tclassroom-%d (%d)\n", ++cc, workingset.lesson_pool[cp].clustered_classroom_decoder.get(cc));
                int cd = -1;
                for(int[] day : classroom)
                {
                    System.out.printf("\t\t\tday-%d (%d): %s\n", ++cd, cd, Arrays.toString(day));
                }
            }
        }
    }

    @SuppressWarnings("Duplicates") @Test public void TestSchedulingShufflingPropertiesClone()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final ScheduleShufflingProperties properties0 = gen.generateProperties();
        final ScheduleShufflingProperties properties  = ScheduleShufflingProperties.newInstance(properties0);

        System.out.println("Day_Set");
        System.out.println(Arrays.toString(properties.day_set));
        System.out.println();
        System.out.println("Classroom");
        int cp = -1;
        for(int[][] pool : properties.classrooms_set)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cg = -1;
            for(int[] group : pool)
            {
                System.out.printf("\t\tgroup_set-%d (%d): %s\n", ++cg, group.length, Arrays.toString(group));
            }
        }
        System.out.println();
        System.out.println("Lesson");
        cp = -1;
        for(int[][] pool : properties.lessons_set)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cg = -1;
            for(int[] group : pool)
            {
                System.out.printf("\t\tgroup_set-%d (%d) : %s\n", ++cg, group.length, Arrays.toString(group));
            }
        }
        System.out.println();
        System.out.println("Classroom Current Time");
        cp = -1;
        for(int[][][] pool : properties.classroom_current_time)
        {
            System.out.printf("\tpool_set-%d\n", ++cp);
            int cc = -1;
            for(int[][] classroom : pool)
            {
                System.out.printf("\t\tclassroom-%d (%d)\n", ++cc, workingset.lesson_pool[cp].clustered_classroom_decoder.get(cc));
                int cd = -1;
                for(int[] day : classroom)
                {
                    System.out.printf("\t\t\tday-%d (%d): %s\n", ++cd, cd, Arrays.toString(day));
                }
            }
        }
    }
}