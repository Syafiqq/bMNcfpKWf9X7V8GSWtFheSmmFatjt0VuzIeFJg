package model.pso.core.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.LessonPoolSet;
import model.dataset.core.WorkingSet;
import model.pso.component.Setting;
import model.pso.core.PSOP2;
import org.junit.Before;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.core.test> created by :
 * Name         : syafiq
 * Date / Time  : 28 June 2016, 7:54 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2TestFoFitness
{
    public PSOP2 pso;

    @Before public void Initialization()
    {
        Setting setting = Setting.getInstance();
        setting.bglob_min = 0.5;
        setting.bloc_min = 0.5;
        setting.brand_min = 0.5;
        setting.max_particle = 10;
        setting.max_epoch = 10;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        this.pso = new PSOP2(setting, gen);
        this.pso.initializeSwarm();
    }

    @Test public void testAllowedClassroomSKS()
    {
        for(LessonPoolSet pool : pso.lesson_pool)
        {
            int sks = 0;
            for(int lesson : pool.lessons)
            {
                sks += pso.lessons[lesson].sks;
            }
            System.out.println(sks);
        }
    }
}