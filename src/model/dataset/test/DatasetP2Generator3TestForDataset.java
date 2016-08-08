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
import model.dataset.core.WorkingSet;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 14 June 2016, 9:10 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3TestForDataset
{
    @Test public void TestSchool()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(gen.getDataset().school);
    }

    @Test public void TestDays()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(Arrays.toString(dataset.active_days));
    }

    @Test public void TestPeriod()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(Arrays.toString(dataset.active_periods));
    }

    @Test public void TestClasses()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.classes.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dataset.classes)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestClassrooms()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.classrooms.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dataset.classrooms)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestLecturers()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lecturers.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dataset.lecturers)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestSubjects()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.subjects.length);
        System.out.println();
        int idx = 0;
        for(Timeoff tof : dataset.subjects)
        {
            System.out.println(++idx);
            tof.printTimeoff();
            System.out.println();
        }
    }

    @Test public void TestLessonProperty()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = -1;
        System.out.printf("%3s\t\t:\t%-5s\t%-5s\t%-5s\t%-5s\n", "no", "class", "lectr", "subjt", "sks");
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%3d\t\t:\t%-5d\t%-5d\t%-5d\t%-5d\n", ++idx, les.subject, les.sks, les.lecture, les.clazz);
        }
    }

    @Test public void TestLessonPropertyFoxExcel()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = -1;
        //System.out.printf("%3d\t\t:\t%-5s\t%-5s\t%-5s\t%-5s\n", ++idx, "class", "lectr", "subjt", "sks");
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%d\t%d\t%d\t%d\t%d\n", les.parent == -1 ? ++idx : les.parent, les.subject, les.sks, les.lecture, les.clazz);
        }
    }


    @Test public void TestLessonLink()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = 0;
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%3d\t\t:%s", ++idx, Arrays.toString(les.link));
            System.out.println();
        }
    }

    @Test public void TestLessonAvailableClassroom()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = -1;
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%3d\t\t:%s", ++idx, Arrays.toString(les.available_classroom));
            System.out.println();
        }
    }

    @Test public void TestLessonAllowedClassroom()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = -1;
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%3d\t\t:%s", ++idx, Arrays.toString(les.allowed_classroom));
            System.out.println();
        }
    }

    @Test public void TestLessonAvailableClassroomForExcel()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
        System.out.println(dataset.lessons.length);
        System.out.println();
        int idx = -1;
        for(Lesson les : dataset.lessons)
        {
            System.out.printf("%d\t", les.parent == -1 ? ++idx : les.parent);
            for(int acls : les.available_classroom)
            {
                System.out.printf("%d\t", acls);
            }
            System.out.println();
        }
    }

    @Test public void TestSubjectEncoderDecoder()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        System.out.println("Encoder");
        for(Int2IntMap.Entry i : encoder.subjects.int2IntEntrySet())
        {
            System.out.printf("%4d become %-4d\n", i.getIntKey(), i.getIntValue());
        }

        System.out.println("Decoder");
        for(Int2IntMap.Entry i : decoder.subjects.int2IntEntrySet())
        {
            System.out.printf("%4d to %-4d\n", i.getIntKey(), i.getIntValue());
        }
    }

    @Test public void TestLectureEncoderDecoder()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        System.out.println("Encoder");
        for(Int2IntMap.Entry i : encoder.lecturers.int2IntEntrySet())
        {
            System.out.printf("%4d become %-4d\n", i.getIntKey(), i.getIntValue());
        }

        System.out.println("Decoder");
        for(Int2IntMap.Entry i : decoder.lecturers.int2IntEntrySet())
        {
            System.out.printf("%4d to %-4d\n", i.getIntKey(), i.getIntValue());
        }
    }

    @Test public void TestClassEncoderDecoder()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        System.out.println("Encoder");
        for(Int2IntMap.Entry i : encoder.classes.int2IntEntrySet())
        {
            System.out.printf("%4d become %-4d\n", i.getIntKey(), i.getIntValue());
        }

        System.out.println("Decoder");
        for(Int2IntMap.Entry i : decoder.classes.int2IntEntrySet())
        {
            System.out.printf("%4d to %-4d\n", i.getIntKey(), i.getIntValue());
        }
    }

    @Test public void TestClassroomEncoderDecoder()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        System.out.println("Encoder");
        for(Int2IntMap.Entry i : encoder.classrooms.int2IntEntrySet())
        {
            System.out.printf("%4d become %-4d\n", i.getIntKey(), i.getIntValue());
        }

        System.out.println("Decoder");
        for(Int2IntMap.Entry i : decoder.classrooms.int2IntEntrySet())
        {
            System.out.printf("%4d to %-4d\n", i.getIntKey(), i.getIntValue());
        }
    }

    @Test public void TestLessonEncoderDecoder()
    {
        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        System.out.println("Encoder");
        for(Int2IntMap.Entry i : encoder.lessons.int2IntEntrySet())
        {
            System.out.printf("%4d become %-4d\n", i.getIntKey(), i.getIntValue());
        }

        System.out.println("Decoder");
        for(Int2IntMap.Entry i : decoder.lessons.int2IntEntrySet())
        {
            System.out.printf("%4d to %-4d\n", i.getIntKey(), i.getIntValue());
        }
    }
}