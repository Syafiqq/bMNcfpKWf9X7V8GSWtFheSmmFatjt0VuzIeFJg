package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.LessonPoolSet;
import model.dataset.core.WorkingSet;
import model.pso.component.Setting;
import org.junit.Before;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 17 June 2016, 1:57 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3TestConverterForExcel
{
    private Dataset2<Timeoff, Lesson>                  dataset;
    private WorkingSet                                 workingset;
    private DatasetConverter<Int2IntLinkedOpenHashMap> encoder;
    private DatasetConverter<Int2IntLinkedOpenHashMap> decoder;
    private DatasetP2Generator3                        gen;
    private Setting                                    setting;

    @Before public void initialization()
    {
        this.setting = Setting.getInstance();
        setting.bglob_min = 0.5;
        setting.bloc_min = 0.5;
        setting.brand_min = 0.5;
        setting.max_particle = 10;
        setting.max_epoch = 1;

        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);

        Main.getMyDatabaseAccount();
        this.gen.generateDataset();
    }

    @Test public void testActiveDaysConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.active_days.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.active_days.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testActivePeriodConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.active_periods.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.active_periods.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testClassConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.classes.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.classes.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testLocalClusteredClassroomConverter()
    {
        for(LessonPoolSet pool : this.workingset.lesson_pool)
        {
            ObjectIterator<Int2IntMap.Entry> encoder = pool.clustered_classroom_encoder.int2IntEntrySet().fastIterator();
            ObjectIterator<Int2IntMap.Entry> decoder = pool.clustered_classroom_decoder.int2IntEntrySet().fastIterator();
            while(encoder.hasNext() && decoder.hasNext())
            {
                Int2IntMap.Entry encoder_value = encoder.next();
                Int2IntMap.Entry decoder_value = decoder.next();
                System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
            }
        }
    }

    @Test public void testClassroomConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder       = this.encoder.classrooms.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder       = this.decoder.classrooms.int2IntEntrySet().fastIterator();
        int[]                            local_encoder = new int[this.dataset.classrooms.length];
        int[]                            local_decoder = new int[this.dataset.classrooms.length];

        for(LessonPoolSet pool : this.workingset.lesson_pool)
        {
            for(Int2IntMap.Entry counter : pool.clustered_classroom_encoder.int2IntEntrySet())
            {
                local_encoder[counter.getIntKey()] = counter.getIntValue();
            }

            for(Int2IntMap.Entry counter : pool.clustered_classroom_decoder.int2IntEntrySet())
            {
                local_decoder[counter.getIntValue()] = counter.getIntKey();

            }
        }

        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), encoder_value.getIntValue(), local_encoder[encoder_value.getIntValue()], local_decoder[decoder_value.getIntKey()], decoder_value.getIntKey(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testLectureConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.lecturers.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.lecturers.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testSubjectConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.subjects.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.subjects.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }

    @Test public void testLessonConverter()
    {
        ObjectIterator<Int2IntMap.Entry> encoder = this.encoder.lessons.int2IntEntrySet().fastIterator();
        ObjectIterator<Int2IntMap.Entry> decoder = this.decoder.lessons.int2IntEntrySet().fastIterator();
        while(encoder.hasNext() && decoder.hasNext())
        {
            Int2IntMap.Entry encoder_value = encoder.next();
            Int2IntMap.Entry decoder_value = decoder.next();
            System.out.printf("%d\t%d\t%d\t%d\n", encoder_value.getIntKey(), encoder_value.getIntValue(), decoder_value.getIntKey(), decoder_value.getIntValue());
        }
    }
}