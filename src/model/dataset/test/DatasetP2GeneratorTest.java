package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset;
import model.dataset.core.DatasetConverter;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 05 May 2016, 7:48 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2GeneratorTest
{
    @Test public void TestDays()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(Arrays.toString(dts.active_days));
    }

    @Test public void TestPeriod()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(Arrays.toString(dts.active_periods));
    }

    @Test public void TestClasses()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.classes.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dts.classes)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestClassrooms()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.classrooms.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dts.classrooms)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestLecturers()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.lecturers.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dts.lecturers)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestSubjects()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.println(dts.subjects.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dts.subjects)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestLesson()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
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

    @Test public void TestAllWithDebug()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
        System.out.printf("%-20s\t= %s\n", "Days", Arrays.toString(dts.active_days));
        System.out.printf("%-20s\t= %s\n", "Periods", Arrays.toString(dts.active_periods));
        System.out.printf("%-20s\t= %d\n", "Class Size", dts.classes.length);
        int idx = 0;
        for(Timeoff tof : dts.classes)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
        System.out.printf("%-20s\t= %d\n", "Classroom Size", dts.classrooms.length);
        idx = 0;
        for(Timeoff tof : dts.classrooms)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
        System.out.printf("%-20s\t= %d\n", "Lecturers Size", dts.lecturers.length);
        idx = 0;
        for(Timeoff tof : dts.lecturers)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
        System.out.printf("%-20s\t= %d\n", "Subject Size", dts.subjects.length);
        idx = 0;
        for(Timeoff tof : dts.subjects)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestAll()
    {
        Main.getMyDatabaseAccount();
        Dataset<Timeoff, Lesson>                   dts = new Dataset<>(1);
        DatasetConverter<Int2IntLinkedOpenHashMap> enc = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> dcd = new DatasetConverter<>();
        DatasetP2Generator                         gen = new DatasetP2Generator(dts, enc, dcd);
        gen.generateDataset();
    }
}