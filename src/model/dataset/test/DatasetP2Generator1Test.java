package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator1;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset;
import model.dataset.core.DatasetConverter;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 08 May 2016, 4:34 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator1Test
{
    @Test public void TestLessonAvailableClassroom()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator1                        gen = new DatasetP2Generator1(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.lessons.length);
        System.out.println();
        int idx = 0;
        for(Lesson les : dts.lessons)
        {
            System.out.printf("%-4d\t%s", ++idx, Arrays.toString(les.available_classroom));
            System.out.println();
        }
    }

    @Test public void TestLessonLink()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator1                        gen = new DatasetP2Generator1(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.lessons.length);
        System.out.println();
        int idx = 0;
        for(Lesson les : dts.lessons)
        {
            System.out.printf("%-4d\t%s", ++idx, Arrays.toString(les.link));
            System.out.println();
        }
    }

    @Test public void TestLessonWithoutDebug()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator1                        gen = new DatasetP2Generator1(dts, enc, dcd);
        gen.generateDataset();
    }
}