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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.core.test> created by :
 * Name         : syafiq
 * Date / Time  : 15 June 2016, 4:47 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2TestSwarmInitialization
{
    public int   total_particle;
    public PSOP2 pso;

    @Before public void Initialization()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 10;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        this.pso = new PSOP2(setting, gen);
        this.pso.initializeSwarm();

        this.total_particle = setting.MAX_PARTICLES;
    }

    @Test public void SwarmInitializationObjectReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                if(j != i)
                {
                    Assert.assertNotSame(this.pso.particles[i], this.pso.particles[j]);
                }
            }
        }
    }

    @Test public void SwarmInitializationObjectDataToDataReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                if(j != i)
                {
                    Assert.assertNotSame(this.pso.particles[i].data, this.pso.particles[j].data);
                }
            }
        }
    }

    @Test public void SwarmInitializationObjectPBestTOPBestReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                if(j != i)
                {
                    Assert.assertNotSame(this.pso.particles[i].pBest, this.pso.particles[j].pBest);
                }
            }
        }
    }

    @Test public void SwarmInitializationObjectVelocityToVelocityReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                if(j != i)
                {
                    Assert.assertNotSame(this.pso.particles[i].velocity, this.pso.particles[j].velocity);
                }
            }
        }
    }

    @Test public void SwarmInitializationObjectDataToPbestReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                Assert.assertNotSame(this.pso.particles[i].data, this.pso.particles[j].pBest);
            }
        }
    }

    @Test public void SwarmInitializationObjectDataToVelocityReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                Assert.assertNotSame(this.pso.particles[i].data, this.pso.particles[j].velocity);
            }
        }
    }

    @Test public void SwarmInitializationObjectPbestToVelocityReference()
    {
        for(int i = -1, is = this.total_particle; ++i < is; )
        {
            for(int j = -1, js = this.total_particle; ++j < js; )
            {
                Assert.assertNotSame(this.pso.particles[i].pBest, this.pso.particles[j].velocity);
            }
        }
    }
}