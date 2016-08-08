package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.LessonGroupSet;
import model.dataset.core.LessonPoolSet;
import model.dataset.core.WorkingSet;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 14 June 2016, 1:53 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3TestForWorkingSet
{
    @Test public void testLessonSets()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        LessonGroupSet[] lesson_set = workingset.lesson_set;
        System.out.printf("Lesson Group Set %d", lesson_set.length);

        for(int i = -1, is = lesson_set.length; ++i < is; )
        {
            System.out.printf("%s [%d]\n", "Lesson Group", i);
            System.out.printf("\t%20s : %s\n", "Classroom", Arrays.toString(lesson_set[i].classrooms));
            System.out.printf("\t%20s : %s\n", "Lesson", Arrays.toString(lesson_set[i].lessons));
            System.out.printf("\t%20s : %s\n", "SKS Distribution", Arrays.toString(lesson_set[i].local_sks_distribution));
            System.out.println();
        }
    }

    @Test public void testLessonPoolProperty()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        LessonPoolSet[] lesson_pool = workingset.lesson_pool;

        System.out.printf("Lesson Group Set %d", lesson_pool.length);

        for(int i = -1, is = lesson_pool.length; ++i < is; )
        {
            System.out.printf("%s [%d]\n", "Lesson Pool", i);
            //System.out.printf("\t%20s : [%4d] %s\n", "Classroom", lesson_pool[i].classrooms.length, Arrays.toString(lesson_pool[i].classrooms));
            System.out.printf("\t%20s : [%4d] %s\n", "Lesson", lesson_pool[i].lessons.length, Arrays.toString(lesson_pool[i].lessons));
            System.out.printf("\t%20s : [%4d] %s\n", "Lesson Null", lesson_pool[i].lesson_null.length, Arrays.toString(lesson_pool[i].lesson_null));
            System.out.println();
        }
    }

    @Test public void testLessonPoolAvailableTime()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        LessonPoolSet[] lesson_pool = workingset.lesson_pool;
        System.out.println("Classroom Available Time");
        System.out.printf("Lesson Group Set %d\n", lesson_pool.length);
        int cp = -1;
        for(LessonPoolSet lv1 : lesson_pool)
        {
            System.out.printf("pool-%d\n", ++cp);
            int cc = -1;
            for(int[][] set : lv1.classroom_available_time)
            {
                System.out.printf("\tclassroom-%d (%d)\n", ++cc, lv1.clustered_classroom_decoder.get(cc));
                int cd = -1;
                for(int[] availabiliy : set)
                {
                    System.out.printf("\t\tday-%d :\t%s\n", ++cd, Arrays.toString(availabiliy));
                }
            }
        }
    }

    @Test public void testLessonPoolClassroomIDConverter()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        LessonPoolSet[] lesson_pool = workingset.lesson_pool;
        System.out.println("Clustered Classroom Encoder");
        int cp = -1;
        for(LessonPoolSet lv1 : lesson_pool)
        {
            System.out.printf("pool-%d\n", ++cp);
            int cc = -1;
            for(Int2IntMap.Entry set : lv1.clustered_classroom_encoder.int2IntEntrySet())
            {
                System.out.printf("\tclassroom : %d to %d\n", set.getIntKey(), set.getIntValue());
            }
        }

        System.out.println();
        System.out.println("Clustered Classroom Decoder");
        cp = -1;
        for(LessonPoolSet lv1 : lesson_pool)
        {
            System.out.printf("pool-%d\n", ++cp);
            int cc = -1;
            for(Int2IntMap.Entry set : lv1.clustered_classroom_decoder.int2IntEntrySet())
            {
                System.out.printf("\tclassroom : %d become %d\n", set.getIntKey(), set.getIntValue());
            }
        }
    }

    @Test public void testLessonPoolClassroomTimeoff()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        LessonPoolSet[] lesson_pool  = workingset.lesson_pool;
        int             pool_counter = -1;
        for(LessonPoolSet pool : lesson_pool)
        {
            System.out.printf("Pool [%d]\n", ++pool_counter);
            int tof_counter = -1;
            for(Timeoff tof : pool.classroom_timeoff)
            {
                System.out.printf("Classroom [%d] (%d)\n", ++tof_counter, pool.clustered_classroom_decoder.get(tof_counter));
                tof.printTimeoff();
                System.out.println();
            }
        }

    }
}