package model.pso.core.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import model.pso.component.Setting;
import model.pso.core.PSOP2;
import org.junit.Test;

/*
 * This <Skripsi_003> project in package <model.pso.core.test> created by :
 * Name         : syafiq
 * Date / Time  : 08 August 2016, 3:47 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2SystemTestWithoutBenchmark
{
    final Setting setting = Setting.getInstance();
    private Dataset2<Timeoff, Lesson>                  dataset;
    private WorkingSet                                 workingset;
    private DatasetConverter<Int2IntLinkedOpenHashMap> encoder;
    private DatasetConverter<Int2IntLinkedOpenHashMap> decoder;
    private DatasetP2Generator3                        gen;
    private PSOP2                                      pso;

    public void generateDataset()
    {
        Main.getMyDatabaseAccount();
        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
    }

    @Test
    public void testSystemWithBenchmark()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 10000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.doCalculateWithMultithread(pso);
        System.out.println(pso.gBest.fitness);
        //pso.doExchange();
        //System.out.println(pso.gBest.fitness);
    }

    @Test
    public void testSystemWithZeroGlobOneLoc()
    {
        this.setting.bglob_min = 0.0;
        this.setting.bglob_max = 0.0;
        this.setting.bloc_min = 1;
        this.setting.bloc_max = 1;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 10000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.doCalculateWithMultithread(pso);
        System.out.println(pso.gBest.fitness);
        pso.doExchange();
    }

    @Test
    public void testSystemWithZeroGlobMultithread()
    {
        this.setting.bglob_min = 0.0;
        this.setting.bglob_max = 0.0;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 40;
        this.setting.max_epoch = 10000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.doCalculateWithMultithread(pso);
        System.out.println(pso.gBest.fitness);
        pso.doExchange();
    }

    @Test
    public void testSystemWithZeroGlobNoMultithread()
    {
        this.setting.bglob_min = 0.0;
        this.setting.bglob_max = 0.0;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 40;
        this.setting.max_epoch = 10000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.doCalculateWithoutMultithread(pso);
        System.out.println(pso.gBest.fitness);
        pso.doExchange();
    }

    private void doCalculateWithoutMultithread(final PSOP2 pso)
    {
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.evaluateAllParticle();
            pso.updateStoppingCondition();
        }
    }

    private void doCalculateWithMultithread(final PSOP2 pso)
    {
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBestWithMultiProcessor();
            pso.assignGBest();
            pso.evaluateAllParticleWithMultiProcessor();
            pso.updateStoppingCondition();
        }
    }

    @Test
    public void testConflict()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 1;
        this.setting.max_epoch = 1;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.doCalculateWithMultithread(pso);
        System.out.println(pso.gBest.fitness);
    }
}