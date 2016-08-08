package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator2;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset1;
import model.dataset.core.DatasetConverter;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 08 May 2016, 5:46 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator2Test
{
    @Test public void TestAll()
    {
        Main.getMyDatabaseAccount();
        Dataset1<Timeoff, Lesson>                  dts = new Dataset1<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator2                        gen = new DatasetP2Generator2(dts, enc, dcd);
        gen.generateDataset();
    }

    @Test public void TestLessonLink()
    {
        Main.getMyDatabaseAccount();
        Dataset1<Timeoff, Lesson>                  dts = new Dataset1<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator2                        gen = new DatasetP2Generator2(dts, enc, dcd);
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

    @Test public void TestLessonSubject()
    {
        Main.getMyDatabaseAccount();
        Dataset1<Timeoff, Lesson>                  dts = new Dataset1<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator2                        gen = new DatasetP2Generator2(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.lessons.length);
        System.out.println();
        int idx = 0;
        for(Lesson les : dts.lessons)
        {
            System.out.printf("%-4d\t%d", ++idx, les.subject);
            System.out.println();
        }
    }

    @Test public void TestLessonData()
    {
        Main.getMyDatabaseAccount();
        Dataset1<Timeoff, Lesson>                  dts = new Dataset1<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator2                        gen = new DatasetP2Generator2(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.lessons.length);
        System.out.println();
        int idx = 0;
        for(int les : dts.data)
        {
            System.out.printf("%-4d\t%d", ++idx, les);
            System.out.println();
        }
    }
}