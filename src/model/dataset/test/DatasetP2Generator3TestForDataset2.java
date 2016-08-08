package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 14 June 2016, 9:11 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3TestForDataset2
{
    @Test public void testSKSDistribution()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println("SKS Distribution");
        System.out.println(Arrays.toString(dataset.sks_distribution));
    }

    @Test public void testClassroomAvailableTime()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println("Classroom Available Time");

        int counter_classroom = -1;
        for(int classrooms[][] : dataset.classroom_available_time)
        {
            System.out.printf("Classroom : [%d]\n", ++counter_classroom);
            int counter_day = -1;
            for(int days[] : classrooms)
            {
                System.out.printf("\tDay [%d] : %s\n", ++counter_day, Arrays.toString(days));
            }

        }
    }
}