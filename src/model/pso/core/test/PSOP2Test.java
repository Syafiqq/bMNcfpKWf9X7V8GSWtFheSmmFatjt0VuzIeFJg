package model.pso.core.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import model.pso.component.ParticleP2;
import model.pso.component.Position;
import model.pso.component.Setting;
import model.pso.core.PSOP2;
import org.junit.Assert;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.core.test> created by :
 * Name         : syafiq
 * Date / Time  : 12 June 2016, 1:29 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2Test
{
    //private static final Logger logger = LogManager.getLogger(PSOP2Test.class);
    @Test public void testSettingOnly()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;


        PSOP2 pso = new PSOP2(setting, null);
        Assert.assertEquals(0.5, pso.setting.bglob, 0.1);
        Assert.assertEquals(0.5, pso.setting.bloc, 0.1);
        Assert.assertEquals(0.5, pso.setting.brand, 0.1);
    }

    @Test public void testDatasetGenerator()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 10;

        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        Assert.assertArrayEquals(new int[] {0, 1, 2, 3, 4}, pso.active_days);
        Assert.assertArrayEquals(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, pso.active_periods);
    }

    @Test public void testGBestInstantiation()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 10;

        Main.getMyDatabaseAccount();
        Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        WorkingSet                                 workingset = new WorkingSet();
        DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);

        for(Position position : pso.gBest.positions)
        {
            System.out.println(position.position.length);
        }
    }

    @Test public void testConstructor()
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

        PSOP2 pso = new PSOP2(setting, gen);

        System.out.printf("Setting : @see model.pso.component.Setting\n");
        System.out.printf("\t%-30s : %s\n", "Max Particle", String.valueOf(pso.setting.MAX_PARTICLES));
        System.out.printf("\t%-30s : %s\n", "Max Epoch", String.valueOf(pso.setting.MAX_EPOCHS));
        System.out.printf("\t%-30s : %s\n", "B-loc", String.format("%-6.2g", pso.setting.bloc));
        System.out.printf("\t%-30s : %s\n", "B-glob", String.format("%-6.2g", pso.setting.bglob));
        System.out.printf("\t%-30s : %s\n", "B-rand", String.format("%-6.2g", pso.setting.brand));
        System.out.printf("End Of Setting : @see model.pso.component.Setting\n");
        System.out.println();
        System.out.printf("Data Set\n");
        System.out.printf("\tDataSet : @see model.pso.core.Dataset\n");
        System.out.printf("\t\t%-30s : %s\n", "Active Days", String.format("%s", pso.school == 0 ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestSchool"));
        System.out.printf("\t\t%-30s : %s\n", "Active", String.format("%s", pso.active_days == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestDays"));
        System.out.printf("\t\t%-30s : %s\n", "Active Period", String.format("%s", pso.active_periods == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestPeriod"));
        System.out.printf("\t\t%-30s : %s\n", "Classes", String.format("%s", pso.classes == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestClasses"));
        System.out.printf("\t\t%-30s : %s\n", "Classrooms", String.format("%s", pso.classrooms == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestClassrooms"));
        System.out.printf("\t\t%-30s : %s\n", "Lecturers", String.format("%s", pso.lecturers == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestLecturers"));
        System.out.printf("\t\t%-30s : %s\n", "Subjects", String.format("%s", pso.subjects == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestSubjects"));
        System.out.printf("\t\t%-30s : %s\n", "Lessons", String.format("%s", pso.lessons == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestLessonProperty"));
        System.out.printf("\t\t%-30s   %s\n", "", String.format("%s", pso.lessons == null ? "" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestLessonLink"));
        System.out.printf("\t\t%-30s   %s\n", "", String.format("%s", pso.lessons == null ? "" : "@see model.dataset.test.DatasetP2Generator3TestForDataset.TestLessonAvailableClassroom"));
        System.out.printf("\tEnd Of DataSet : @see model.pso.core.Dataset\n");
        System.out.println();
        System.out.printf("\tDataSet2 : @see model.pso.core.Dataset2\n");
        System.out.printf("\t\t%-30s : %s\n", "SKS Distribution", String.format("%s", pso.sks_distribution == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset2.testSKSDistribution"));
        System.out.printf("\t\t%-30s : %s\n", "Classroom Available Time", String.format("%s", pso.classroom_available_time == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForDataset2.testClassroomAvailableTime"));
        System.out.printf("\tEnd Of DataSet2 : @see model.pso.core.Dataset2\n");
        System.out.printf("Data Set\n");
        System.out.println();
        System.out.printf("WorkingSet : @see model.dataset.core.WorkingSet\n");
        System.out.printf("\t%-30s : %s\n", "Lesson Group Set", String.format("%s", pso.sks_distribution == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForWorkingSet.testLessonSets"));
        System.out.printf("\t%-30s : %s\n", "Lesson Pool Set", String.format("%s", pso.lesson_set == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForWorkingSet.testLessonPoolProperty"));
        System.out.printf("\t%-30s   %s\n", "", String.format("%s", pso.lesson_pool == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForWorkingSet.testLessonPoolAvailableClassroom"));
        System.out.printf("\t%-30s   %s\n", "", String.format("%s", pso.lesson_pool == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForWorkingSet.testLessonPoolClassroomIDConverter"));
        System.out.printf("\t%-30s   %s\n", "", String.format("%s", pso.lesson_pool == null ? "NULL" : "@see model.dataset.test.DatasetP2Generator3TestForWorkingSet.testLessonPoolClassroomTimeoff"));
        System.out.printf("End Of WorkingSet : @see model.dataset.core.WorkingSet\n");


        //System.out.printf("%-20s : %d", "school", pso.school);
    }

    @Test public void testSwarmInitialization()
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

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        System.out.println("Particle");
        System.out.println(pso.particles[0].toString(0));
    }

    @Test public void testSwarmInitializationAllParticle()
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

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();

        for(int i = -1, is = setting.MAX_PARTICLES; ++i < is; )
        {
            System.out.printf("Particle [%d]\n", i);
            System.out.println(pso.particles[i].toString(0));
            System.out.println();
            System.out.println();
        }
    }

    @Test public void testSwarmInitializationAllParticleData()
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

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();

        for(int i = -1, is = setting.MAX_PARTICLES; ++i < is; )
        {
            System.out.printf("Particle [%d]\n", i);
            System.out.printf("%s\n", (String.format("%s\t%s : \n%s", "", "Data", pso.particles[i].data.toString(1))));
            System.out.println();
        }
    }

    @Test public void testUpdateSwarmFitnessWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        for(int i = 0, is = 50000; ++i < is; )
        {
            pso.updateSwarmFitness();
        }
    }

    @Test public void testUpdateSwarmFitnessWithoutBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        pso.calculateFitness(pso.particles[0]);
        //System.out.println(pso.particles[0].data.fitness);
    }

    @Test public void testUpdateSwarmFitnessExecutionTime()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 1;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();

        for(int i = -1, is = 1000; ++i < is; )
        {
            long a = System.currentTimeMillis();
            pso.calculateFitness(pso.particles[0]);
            //System.out.println((System.nanoTime() - a) * 1f / 1000);
            System.out.println((System.currentTimeMillis() - a));
        }
    }

    @Test public void testResetPlacementWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        for(int i = 0, is = 10000; ++i < is; )
        {
            for(int j = -1; ++j < setting.MAX_PARTICLES; )
            {
                pso.particles[j].placement_properties.resetPlacement();
            }
        }
    }

    @Test public void testGBestAfterSwarmInitialization()
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

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();

        System.out.println(pso.gBest);
    }

    @Test public void testUpdateAllParticlePbestWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        for(int i = 0, is = 50000; ++i < is; )
        {
            pso.updateAllParticlePBest();
            for(ParticleP2 p2 : pso.particles)
            {
                p2.pBest.fitness = 0.0;
            }
        }
    }

    @Test public void testEvaluateAllParticleWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        pso.updateAllParticlePBest();
        pso.assignGBest();
        for(int i = 0, is = 100000; ++i < is; )
        {
            pso.evaluateAllParticle();
        }
    }

    @Test public void testRepairWithoutBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 1;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        pso.calculateFitness(pso.particles[0]);
        //pso.updateSwarmFitness();
        //pso.updateAllParticlePBest();
        //pso.assignGBest();
        //pso.particles[0].calculateVelocity(pso.gBest);
        //pso.particles[0].updateData();
        //Position[] p = new Position[5];
        /*
        p[0] = new Position(new int[]{585, 686, 586, 649, 583, 650, 0, 0, 0, 548, 688, 690, 653, 645, 588, 0, 0, 0, 0, 0, 646, 584, 689, 687, 0, 648, 0, 0, 0, 546, 587, 652, 651, 549, 550, 0, 0, 0, 644, 691, 0, 0, 547, 647, 0, 0, 0});
        p[1] = new Position(new int[]{580, 582, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 682, 0, 683, 579, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 0, 685, 684, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        p[2] = new Position(new int[]{255, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 258, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 250, 259, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        p[3] = new Position(new int[]{5, 84, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 305, 306, 314, 309, 307, 311, 0, 0, 0, 9, 75, 26, 17, 20, 3, 0, 0, 0, 302, 308, 310, 23, 25, 7, 0, 0, 0, 301, 304, 0, 300, 313, 312, 303, 0, 16, 14, 13, 70, 78, 73, 0, 0, 0, 19, 22, 15, 11, 18, 10, 0, 0, 0, 2, 81, 83, 12, 4, 76, 0, 0, 0, 24, 82, 72, 8, 27, 6, 0, 0, 0, 77, 21, 0, 74, 71, 79, 80, 0});
        p[4] = new Position(new int[]{571, 450, 134, 439, 407, 342, 534, 659, 410, 572, 554, 670, 69, 540, 271, 386, 62, 361, 330, 164, 0, 132, 669, 290, 169, 632, 143, 574, 604, 387, 521, 427, 261, 478, 608, 533, 657, 655, 535, 228, 391, 222, 61, 416, 665, 159, 172, 517, 510, 455, 509, 444, 404, 341, 133, 392, 467, 673, 226, 0, 413, 335, 55, 383, 639, 326, 0, 375, 415, 171, 146, 229, 365, 174, 87, 633, 385, 0, 130, 642, 384, 212, 364, 279, 417, 157, 295, 219, 131, 211, 466, 570, 475, 618, 276, 88, 151, 520, 357, 399, 178, 112, 225, 283, 95, 373, 408, 389, 56, 414, 339, 231, 666, 516, 400, 658, 518, 368, 468, 165, 476, 0, 185, 145, 0, 372, 274, 409, 206, 347, 567, 138, 91, 85, 672, 94, 563, 363, 641, 543, 445, 527, 166, 405, 152, 218, 123, 344, 0, 163, 454, 0, 291, 401, 144, 98, 366, 489, 566, 377, 296, 117, 184, 155, 397, 656, 449, 39, 158, 434, 147, 615, 557, 661, 660, 675, 402, 99, 121, 374, 451, 378, 376, 40, 382, 370, 141, 362, 113, 282, 556, 149, 676, 173, 452, 428, 52, 662, 403, 0, 553, 446, 0, 371, 333, 479, 448, 293, 483, 58, 343, 57, 367, 109, 630, 369, 116, 492, 329, 506, 470, 0, 46, 216, 197, 465, 0, 0, 0, 246, 270, 539, 578, 189, 612, 629, 0, 0, 32, 47, 214, 515, 351, 51, 0, 0, 0, 635, 680, 0, 299, 336, 525, 0, 320, 599, 45, 464, 611, 201, 0, 531, 34, 628, 502, 198, 269, 487, 0, 44, 631, 315, 53, 317, 504, 0, 0, 0, 636, 493, 606, 190, 137, 285, 0, 0, 421, 35, 500, 48, 601, 575, 318, 0, 0, 437, 507, 474, 37, 497, 0, 541, 280, 0, 505, 447, 0, 0, 213, 210, 150, 667, 0, 0, 589, 0, 0, 0, 0, 412, 422, 0, 0, 443, 0, 0, 0, 0, 381, 569, 423, 0, 0, 0, 0, 441, 603, 262, 272, 519, 453, 327, 0, 390, 0, 0, 0, 292, 0, 0, 0, 0, 0, 0, 0, 0, 395, 0, 380, 328, 432, 60, 388, 227, 0, 104, 63, 345, 100, 677, 233, 179, 0, 235, 419, 50, 355, 490, 170, 0, 0, 0, 208, 89, 624, 183, 605, 0, 0, 0, 66, 671, 294, 67, 398, 110, 334, 111, 209, 0, 297, 0, 86, 394, 118, 42, 0, 0, 0, 224, 429, 223, 393, 0, 321, 0, 103, 359, 97, 162, 425, 0, 0, 0, 568, 148, 595, 160, 526, 607, 469, 0, 288, 523, 0, 473, 623, 0, 0, 0, 0, 248, 277, 436, 242, 0, 0, 0, 0, 602, 115, 192, 0, 536, 0, 442, 122, 350, 181, 0, 0, 0, 59, 396, 0, 0, 331, 0, 0, 0, 0, 356, 477, 0, 438, 93, 0, 0, 0, 418, 511, 435, 0, 0, 0, 0, 0, 0, 654, 0, 643, 0, 0, 440, 199, 129, 0, 538, 512, 678, 0, 0, 0, 524, 247, 0, 480, 620, 0, 0, 0, 0, 128, 590, 0, 488, 194, 0, 0, 0, 0, 241, 481, 0, 591, 0, 0, 0, 0, 36, 462, 522, 530, 621, 598, 0, 485, 668, 284, 0, 0, 0, 0, 68, 551, 0, 542, 0, 0, 0, 221, 555, 0, 0, 332, 0, 0, 0, 0, 573, 346, 484, 0, 558, 0, 0, 0, 182, 560, 154, 0, 0, 0, 191, 215, 0, 323, 196, 0, 0, 0, 0, 324, 127, 0, 576, 627, 594, 0, 0, 0, 175, 30, 338, 267, 0, 0, 0, 239, 176, 0, 278, 0, 0, 0, 0, 622, 193, 458, 514, 613, 0, 0, 681, 195, 249, 0, 0, 0, 0, 508, 0, 0, 266, 124, 0, 0, 0, 204, 354, 0, 244, 0, 0, 0, 0, 186, 472, 0, 268, 352, 0, 0, 0, 316, 461, 426, 31, 0, 217, 263, 0, 616, 0, 0, 0, 0, 0, 220, 0, 0, 552, 0, 0, 0, 0, 119, 273, 0, 0, 0, 0, 0, 96, 0, 433, 64, 0, 0, 0, 0, 102, 0, 663, 0, 0, 286, 459, 360, 0, 0, 0, 0, 0, 287, 528, 0, 240, 0, 0, 0, 0, 0, 114, 577, 0, 54, 496, 0, 0, 0, 0, 337, 610, 0, 245, 202, 0, 0, 0, 0, 90, 120, 617, 0, 107, 275, 565, 0, 281, 232, 0, 0, 0, 379, 156, 142, 153, 625, 0, 0, 0, 230, 349, 638, 234, 0, 0, 0, 105, 0, 0, 609, 564, 168, 0, 0, 0, 0, 348, 0, 664, 325, 167, 411, 65, 0, 0, 0, 0, 0, 0, 139, 0, 0, 562, 207, 0, 0, 0, 0, 544, 0, 0, 529, 180, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 406, 619, 0, 264, 101, 0, 0, 0, 0, 140, 0, 92, 491, 0, 0, 0, 0, 161, 561, 0, 559, 358, 0, 0, 0, 340, 260, 0, 640, 0, 0, 0, 41, 106, 0, 0, 674, 0, 532, 126, 0, 460, 0, 0, 0, 0, 0, 0, 637, 0, 237, 513, 0, 0, 0, 0, 0, 614, 0, 431, 634, 0, 0, 0, 0, 498, 49, 0, 457, 319, 0, 0, 0, 0, 33, 456, 482, 596, 0, 600, 593, 205, 38, 499, 0, 0, 0, 0, 494, 200, 592, 471, 29, 0, 0, 0, 322, 0, 0, 238, 679, 0, 0, 0, 0, 265, 289, 108, 486, 0, 0, 0, 353, 503, 236, 177, 298, 243, 0, 0, 188, 136, 0, 0, 0, 0, 463, 43, 501, 420, 0, 0, 0, 0, 424, 187, 0, 626, 537, 0, 0, 0, 0, 495, 545, 0, 135, 28, 0, 0, 0, 430, 597, 125, 203, 0, 0, 0});
        */
/*        p[0] = new Position(new int[]{});
        p[1] = new Position(new int[]{});
        p[2] = new Position(new int[]{});
        p[3] = new Position(new int[]{});
        p[4] = new Position(new int[]{});*/
        //p[0] = new Position(new int[] {548, 652, 648, 651, 583, 649, 0, 0, 0, 585, 647, 686, 547, 584, 587, 0, 0, 0, 689, 650, 646, 645, 0, 687, 0, 0, 586, 0, 0, 546, 588, 0, 0, 549, 550, 690, 0, 0, 644, 691, 0, 0, 653, 688, 0, 0, 0});
        //p[1] = new Position(new int[] {682, 582, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 580, 0, 683, 0, 579, 0, 0, 0, 0, 0, 0, 0, 0, 581, 0, 685, 684, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        //p[2] = new Position(new int[] {254, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 258, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 250, 259, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        //p[3] = new Position(new int[] {1, 300, 314, 303, 301, 312, 308, 0, 0, 0, 305, 310, 5, 84, 2, 78, 0, 25, 4, 12, 11, 0, 6, 0, 82, 0, 10, 83, 73, 79, 0, 313, 0, 302, 0, 309, 72, 0, 304, 307, 311, 0, 0, 0, 0, 0, 76, 15, 13, 70, 23, 3, 0, 0, 0, 19, 22, 14, 0, 18, 0, 26, 0, 0, 71, 81, 0, 9, 7, 16, 0, 0, 0, 24, 17, 0, 0, 8, 27, 0, 0, 0, 77, 21, 0, 74, 75, 20, 80, 0, 306});
        //p[3] = new Position(new int[] {9, 3, 79, 75, 16, 14, 72, 0, 0, 312, 305, 0, 308, 2, 84, 309, 0, 0, 303, 300, 0, 307, 313, 24, 82, 0, 10, 81, 8, 13, 301, 25, 12, 0, 304, 78, 0, 76, 306, 6, 311, 0, 83, 0, 0, 0, 0, 15, 11, 70, 23, 0, 0, 0, 0, 19, 0, 73, 22, 18, 0, 26, 0, 0, 71, 0, 0, 7, 0, 0, 0, 0, 5, 0, 17, 27, 0, 0, 0, 77, 21, 0, 74, 0, 4, 20, 310, 314, 80, 302, 1});
        //p[3] = new Position(new int[] {309, 314, 305, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 5, 71, 78, 25, 12, 0, 0, 0, 83, 6, 10, 82, 79, 73, 0, 0, 0, 313, 310, 302, 306, 303, 304, 0, 0, 0, 307, 311, 0, 301, 300, 312, 308, 0, 76, 15, 13, 70, 23, 3, 0, 0, 0, 19, 22, 14, 11, 18, 26, 0, 0, 0, 2, 81, 9, 7, 4, 16, 0, 0, 0, 24, 17, 72, 8, 27, 1, 0, 0, 0, 77, 21, 0, 74, 84, 20, 80, 0});
        //p[4] = new Position(new int[]{92, 280, 232, 479, 469, 156, 440, 168, 369, 353, 380, 659, 0, 517, 381, 0, 666, 454, 0, 279, 672, 331, 333, 297, 342, 360, 276, 448, 263, 182, 151, 0, 227, 234, 398, 376, 180, 164, 0, 655, 410, 0, 165, 367, 566, 161, 510, 455, 94, 328, 526, 0, 0, 0, 159, 0, 123, 152, 639, 146, 348, 413, 334, 281, 228, 442, 326, 184, 564, 397, 271, 226, 141, 0, 174, 505, 544, 138, 625, 407, 273, 405, 50, 482, 417, 389, 264, 371, 235, 219, 139, 395, 343, 0, 400, 55, 363, 349, 662, 374, 366, 478, 0, 224, 373, 157, 387, 563, 438, 0, 40, 98, 119, 664, 520, 671, 142, 391, 406, 370, 57, 399, 669, 372, 565, 0, 274, 409, 206, 347, 62, 91, 85, 132, 0, 0, 0, 0, 0, 364, 261, 112, 518, 0, 541, 445, 0, 162, 467, 392, 446, 609, 355, 619, 275, 291, 401, 330, 361, 143, 0, 489, 0, 0, 172, 339, 382, 117, 155, 375, 0, 0, 0, 0, 0, 0, 368, 158, 617, 384, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 439, 59, 402, 571, 121, 451, 327, 231, 416, 415, 429, 296, 260, 208, 145, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 39, 393, 385, 0, 0, 0, 665, 483, 535, 0, 299, 394, 32, 616, 0, 515, 0, 511, 533, 140, 88, 641, 58, 229, 336, 428, 163, 0, 125, 45, 620, 0, 199, 201, 144, 656, 0, 190, 502, 0, 539, 597, 560, 269, 365, 285, 53, 44, 500, 0, 434, 485, 504, 636, 493, 137, 317, 178, 606, 421, 568, 0, 628, 556, 558, 318, 465, 437, 601, 212, 37, 658, 0, 575, 0, 507, 497, 573, 351, 209, 150, 0, 557, 677, 496, 225, 325, 105, 464, 34, 315, 475, 211, 615, 320, 612, 470, 0, 246, 113, 545, 443, 171, 359, 555, 166, 0, 635, 344, 197, 133, 411, 116, 570, 631, 531, 218, 474, 46, 452, 329, 661, 0, 449, 562, 0, 185, 447, 404, 210, 495, 640, 89, 0, 422, 377, 663, 0, 350, 386, 0, 675, 0, 213, 589, 0, 441, 638, 0, 487, 272, 134, 537, 378, 0, 262, 453, 292, 99, 516, 0, 484, 626, 569, 0, 358, 471, 0, 466, 567, 444, 0, 60, 149, 0, 147, 0, 63, 345, 100, 527, 0, 233, 179, 48, 321, 419, 341, 490, 170, 335, 425, 87, 592, 282, 362, 131, 183, 104, 595, 0, 203, 167, 0, 66, 468, 473, 294, 552, 0, 357, 0, 680, 277, 0, 111, 293, 67, 169, 412, 501, 115, 0, 51, 188, 118, 42, 509, 0, 173, 450, 608, 0, 223, 52, 222, 423, 654, 0, 97, 86, 103, 187, 605, 529, 95, 216, 432, 388, 476, 35, 390, 679, 0, 288, 200, 110, 578, 0, 494, 0, 554, 242, 0, 248, 383, 129, 181, 205, 0, 198, 436, 0, 396, 604, 521, 503, 480, 667, 0, 290, 499, 633, 491, 128, 0, 202, 488, 122, 610, 0, 93, 577, 477, 0, 247, 542, 0, 418, 528, 435, 462, 0, 551, 0, 0, 36, 0, 414, 241, 481, 0, 0, 283, 0, 540, 0, 670, 56, 0, 0, 0, 657, 0, 0, 0, 0, 0, 0, 0, 599, 0, 600, 593, 0, 0, 553, 0, 498, 596, 0, 0, 634, 0, 0, 630, 0, 186, 323, 0, 0, 0, 0, 0, 127, 0, 109, 324, 0, 204, 0, 0, 68, 0, 338, 267, 175, 30, 0, 0, 0, 239, 278, 508, 221, 284, 176, 332, 215, 458, 0, 618, 0, 492, 623, 0, 249, 523, 0, 0, 195, 0, 681, 0, 266, 613, 0, 427, 0, 0, 154, 354, 244, 0, 0, 0, 0, 193, 472, 0, 268, 0, 47, 0, 514, 316, 461, 426, 31, 0, 676, 0, 0, 0, 346, 0, 0, 0, 0, 124, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 622, 0, 0, 286, 0, 459, 0, 0, 0, 0, 0, 0, 0, 287, 0, 240, 220, 0, 594, 0, 114, 572, 217, 54, 352, 0, 0, 0, 0, 574, 337, 0, 245, 513, 0, 0, 0, 0, 0, 0, 602, 0, 433, 64, 192, 96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153, 0, 0, 0, 0, 102, 0, 536, 0, 270, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 637, 0, 629, 0, 107, 120, 0, 0, 0, 0, 519, 0, 0, 0, 379, 624, 0, 0, 0, 642, 0, 0, 0, 0, 0, 403, 603, 230, 532, 0, 543, 0, 673, 0, 69, 0, 643, 538, 0, 0, 0, 0, 0, 65, 611, 0, 0, 0, 0, 512, 0, 678, 207, 0, 0, 0, 0, 524, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 590, 0, 0, 660, 0, 0, 148, 0, 534, 194, 0, 160, 0, 0, 561, 0, 559, 0, 0, 0, 0, 0, 0, 0, 214, 0, 0, 126, 632, 460, 0, 0, 0, 0, 0, 0, 0, 591, 0, 0, 237, 674, 0, 0, 0, 0, 356, 431, 101, 0, 0, 0, 295, 522, 0, 530, 0, 319, 506, 457, 0, 0, 0, 33, 340, 49, 456, 0, 621, 598, 0, 38, 614, 0, 41, 106, 0, 0, 0, 189, 29, 0, 525, 0, 0, 607, 0, 627, 238, 0, 0, 0, 0, 0, 289, 0, 486, 108, 0, 0, 0, 265, 576, 177, 298, 243, 0, 408, 196, 0, 191, 0, 0, 0, 463, 43, 322, 236, 420, 0, 0, 0, 424, 0, 0, 0, 130, 0, 0, 0, 0, 0, 0, 0, 135, 0, 0, 0, 668, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 430, 136, 0, 28, 0, 0, 0});
        //p[4] = new Position(new int[]{92, 280, 232, 479, 469, 156, 440, 168, 369, 342, 0, 380, 659, 0, 517, 381, 0, 666, 454, 0, 279, 672, 331, 333, 297, 526, 0, 360, 276, 448, 263, 182, 151, 0, 227, 234, 398, 376, 180, 164, 0, 655, 410, 0, 165, 367, 566, 161, 510, 455, 94, 328, 505, 0, 0, 0, 0, 159, 0, 123, 152, 639, 146, 348, 413, 334, 281, 228, 442, 326, 184, 564, 397, 271, 226, 141, 0, 174, 662, 0, 544, 138, 625, 407, 273, 405, 50, 482, 417, 389, 264, 371, 235, 219, 139, 395, 343, 0, 400, 55, 363, 349, 0, 366, 374, 669, 478, 0, 224, 373, 157, 387, 563, 438, 0, 40, 98, 119, 664, 520, 671, 142, 391, 406, 370, 57, 399, 162, 0, 372, 565, 0, 274, 409, 206, 347, 62, 91, 85, 132, 0, 0, 0, 0, 0, 364, 261, 112, 518, 0, 541, 445, 0, 617, 467, 0, 392, 446, 609, 355, 619, 275, 291, 401, 330, 361, 143, 0, 489, 0, 0, 172, 339, 382, 117, 155, 375, 0, 0, 0, 0, 0, 0, 368, 158, 0, 384, 145, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 439, 59, 402, 571, 121, 451, 327, 231, 416, 415, 429, 296, 260, 208, 140, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 39, 393, 385, 0, 0, 0, 665, 483, 535, 0, 299, 394, 32, 616, 0, 515, 0, 511, 533, 568, 641, 88, 0, 58, 229, 336, 428, 163, 0, 125, 45, 620, 0, 199, 201, 144, 656, 0, 190, 502, 0, 539, 597, 560, 269, 365, 285, 53, 44, 500, 0, 434, 485, 504, 636, 493, 137, 317, 178, 606, 421, 0, 615, 0, 628, 556, 558, 318, 465, 437, 601, 212, 37, 658, 0, 575, 0, 507, 497, 573, 351, 209, 150, 0, 557, 677, 496, 225, 325, 105, 464, 34, 315, 475, 211, 668, 320, 0, 612, 470, 0, 246, 113, 545, 443, 171, 359, 555, 166, 0, 635, 344, 197, 133, 411, 116, 570, 631, 531, 218, 474, 46, 452, 329, 661, 0, 449, 562, 0, 185, 447, 404, 210, 495, 640, 89, 0, 422, 377, 663, 0, 350, 386, 0, 675, 0, 213, 589, 0, 441, 638, 0, 487, 272, 134, 537, 378, 0, 262, 453, 292, 99, 516, 0, 484, 626, 569, 0, 358, 471, 0, 466, 567, 444, 0, 60, 149, 0, 147, 0, 63, 345, 100, 527, 0, 233, 179, 48, 321, 419, 341, 490, 170, 335, 425, 87, 592, 282, 362, 131, 183, 104, 595, 0, 203, 167, 0, 66, 468, 473, 294, 552, 0, 357, 0, 680, 277, 0, 111, 293, 67, 169, 412, 501, 115, 0, 51, 188, 118, 42, 509, 0, 173, 450, 608, 0, 223, 52, 222, 423, 654, 0, 97, 86, 103, 187, 605, 529, 95, 216, 432, 388, 476, 35, 390, 679, 0, 288, 200, 110, 578, 0, 494, 0, 554, 242, 0, 248, 383, 129, 181, 205, 0, 198, 436, 0, 396, 604, 521, 503, 480, 667, 0, 290, 499, 633, 491, 128, 0, 202, 488, 122, 610, 0, 93, 577, 477, 0, 247, 542, 0, 418, 528, 435, 462, 0, 551, 0, 0, 36, 0, 414, 241, 481, 0, 0, 283, 0, 540, 0, 670, 56, 0, 0, 0, 657, 0, 0, 0, 0, 0, 0, 0, 599, 0, 600, 593, 0, 0, 553, 0, 498, 596, 0, 0, 634, 0, 0, 630, 0, 186, 323, 0, 0, 0, 0, 0, 127, 0, 109, 324, 0, 204, 0, 0, 68, 0, 338, 267, 175, 30, 0, 0, 0, 239, 278, 508, 221, 284, 176, 332, 215, 458, 0, 618, 0, 492, 623, 0, 249, 523, 0, 0, 195, 0, 681, 0, 266, 613, 0, 427, 0, 0, 154, 354, 244, 0, 0, 0, 0, 193, 472, 0, 268, 0, 47, 0, 514, 316, 461, 426, 31, 0, 676, 0, 0, 0, 346, 0, 0, 0, 0, 124, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 622, 0, 0, 286, 0, 459, 0, 0, 0, 0, 0, 0, 0, 287, 0, 240, 220, 0, 594, 0, 114, 572, 217, 54, 352, 0, 0, 0, 0, 574, 337, 0, 245, 513, 0, 0, 0, 0, 0, 0, 602, 0, 433, 64, 192, 96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153, 0, 0, 0, 0, 102, 0, 536, 0, 270, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 637, 0, 629, 0, 107, 120, 0, 0, 0, 0, 519, 0, 0, 0, 379, 624, 0, 0, 0, 642, 0, 0, 0, 0, 0, 403, 603, 230, 532, 0, 543, 0, 673, 0, 69, 0, 643, 538, 0, 0, 0, 0, 0, 65, 611, 0, 0, 0, 0, 512, 0, 678, 207, 0, 0, 0, 0, 524, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 590, 0, 0, 660, 0, 0, 148, 0, 534, 194, 0, 160, 0, 0, 561, 0, 559, 0, 0, 0, 0, 0, 0, 0, 214, 0, 0, 126, 632, 460, 0, 0, 0, 0, 0, 0, 0, 591, 0, 0, 237, 674, 0, 0, 0, 0, 356, 431, 101, 0, 0, 0, 295, 522, 0, 530, 0, 319, 506, 457, 0, 0, 0, 33, 340, 49, 456, 0, 621, 598, 0, 38, 614, 0, 41, 106, 0, 0, 0, 189, 29, 0, 525, 0, 0, 607, 0, 627, 238, 0, 0, 0, 0, 0, 289, 0, 486, 108, 0, 0, 0, 265, 576, 177, 298, 243, 0, 408, 196, 0, 191, 0, 0, 0, 463, 43, 322, 236, 420, 0, 0, 0, 424, 0, 0, 0, 130, 0, 0, 0, 0, 0, 0, 0, 135, 0, 0, 0, 353, 430, 136, 0, 28, 0, 0, 0});
        //p[4] = new Position(new int[]{608, 343, 669, 632, 385, 0, 140, 51, 0, 293, 359, 369, 551, 0, 394, 341, 554, 172, 0, 569, 656, 638, 544, 272, 235, 273, 87, 264, 422, 435, 416, 183, 447, 64, 147, 624, 141, 377, 423, 120, 66, 282, 229, 451, 674, 121, 102, 171, 234, 409, 399, 98, 118, 330, 151, 0, 139, 542, 491, 0, 403, 0, 0, 89, 209, 154, 387, 367, 357, 275, 291, 130, 0, 0, 0, 483, 477, 442, 0, 0, 0, 185, 91, 440, 0, 0, 0, 388, 373, 476, 233, 166, 368, 0, 0, 0, 556, 92, 405, 445, 372, 42, 182, 55, 180, 0, 0, 0, 0, 0, 0, 0, 350, 0, 0, 0, 0, 0, 329, 0, 157, 62, 449, 453, 658, 573, 0, 162, 371, 0, 170, 150, 0, 106, 574, 0, 559, 384, 0, 448, 226, 555, 142, 557, 88, 383, 260, 402, 156, 376, 404, 639, 290, 642, 0, 119, 334, 561, 407, 0, 328, 395, 398, 224, 444, 677, 0, 50, 541, 0, 660, 520, 655, 386, 568, 0, 570, 296, 552, 370, 0, 344, 325, 0, 149, 134, 0, 450, 340, 0, 0, 0, 0, 0, 0, 168, 0, 540, 0, 0, 0, 0, 0, 0, 105, 232, 225, 0, 0, 0, 107, 509, 104, 625, 0, 220, 518, 0, 560, 452, 0, 633, 95, 0, 534, 571, 654, 274, 0, 0, 0, 361, 208, 617, 666, 506, 411, 661, 0, 400, 640, 469, 0, 0, 0, 538, 436, 0, 363, 358, 0, 265, 531, 562, 543, 30, 122, 155, 212, 612, 0, 675, 96, 0, 318, 499, 148, 657, 58, 609, 0, 458, 620, 158, 201, 479, 0, 670, 0, 177, 124, 665, 613, 505, 219, 174, 207, 173, 437, 375, 354, 439, 228, 60, 493, 0, 611, 381, 0, 145, 572, 631, 222, 211, 637, 351, 237, 146, 0, 331, 227, 61, 319, 221, 164, 418, 0, 165, 332, 0, 668, 0, 160, 223, 0, 459, 159, 0, 673, 0, 193, 424, 0, 523, 38, 0, 681, 0, 607, 527, 0, 457, 288, 292, 564, 626, 590, 389, 43, 498, 0, 426, 47, 379, 513, 0, 496, 0, 65, 36, 391, 268, 577, 0, 322, 112, 188, 414, 0, 39, 187, 28, 231, 519, 0, 525, 0, 90, 335, 603, 415, 0, 593, 0, 643, 216, 0, 269, 267, 109, 161, 501, 522, 243, 114, 485, 59, 57, 271, 526, 0, 153, 195, 517, 0, 514, 676, 202, 192, 0, 133, 117, 203, 432, 0, 454, 336, 280, 298, 473, 672, 0, 463, 597, 0, 595, 425, 0, 558, 0, 244, 438, 591, 567, 663, 169, 34, 662, 467, 179, 596, 0, 323, 521, 0, 352, 598, 0, 86, 429, 278, 246, 94, 41, 327, 281, 396, 215, 213, 472, 553, 0, 602, 536, 197, 48, 99, 532, 421, 470, 111, 320, 392, 53, 345, 230, 413, 67, 601, 474, 0, 35, 431, 364, 433, 401, 390, 181, 636, 0, 614, 529, 629, 480, 285, 623, 129, 356, 0, 163, 0, 178, 481, 347, 137, 427, 599, 101, 0, 247, 475, 641, 0, 488, 576, 0, 135, 297, 680, 0, 242, 349, 0, 378, 184, 627, 0, 241, 537, 366, 484, 671, 0, 533, 200, 348, 382, 434, 615, 0, 443, 539, 0, 37, 204, 0, 190, 0, 594, 346, 0, 116, 45, 406, 490, 397, 465, 503, 0, 355, 0, 565, 326, 191, 575, 138, 68, 464, 132, 510, 430, 0, 508, 0, 123, 315, 446, 471, 0, 616, 0, 0, 462, 0, 592, 294, 0, 635, 0, 127, 628, 0, 143, 52, 0, 284, 487, 500, 393, 239, 276, 380, 365, 279, 667, 0, 249, 338, 0, 494, 0, 176, 0, 0, 0, 589, 93, 0, 0, 0, 44, 248, 0, 0, 0, 0, 0, 0, 461, 63, 103, 299, 125, 0, 0, 324, 136, 31, 0, 0, 0, 108, 196, 659, 0, 0, 175, 441, 563, 0, 507, 0, 167, 266, 0, 0, 0, 0, 0, 0, 100, 634, 0, 374, 0, 466, 144, 360, 0, 0, 0, 410, 489, 679, 0, 0, 0, 478, 128, 115, 0, 342, 214, 0, 0, 0, 0, 189, 283, 0, 0, 0, 468, 619, 0, 240, 606, 0, 0, 524, 337, 316, 131, 0, 0, 0, 0, 610, 0, 0, 0, 0, 0, 0, 333, 0, 286, 0, 32, 217, 678, 0, 205, 0, 0, 516, 0, 511, 0, 0, 0, 218, 0, 0, 408, 0, 512, 0, 270, 0, 199, 0, 0, 0, 0, 0, 0, 46, 277, 0, 622, 0, 0, 97, 0, 0, 0, 528, 0, 0, 0, 0, 0, 317, 0, 0, 0, 0, 0, 0, 0, 0, 206, 198, 504, 0, 0, 0, 0, 502, 0, 0, 515, 0, 535, 0, 0, 0, 0, 0, 0, 210, 0, 605, 0, 630, 186, 0, 0, 0, 194, 0, 0, 0, 0, 0, 0, 56, 0, 0, 0, 0, 0, 0, 0, 600, 0, 618, 0, 412, 0, 0, 0, 455, 0, 417, 362, 262, 604, 0, 0, 0, 0, 40, 0, 0, 0, 339, 0, 0, 0, 0, 0, 0, 0, 492, 0, 0, 0, 0, 621, 0, 0, 0, 0, 0, 0, 321, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 126, 0, 0, 0, 497, 460, 0, 0, 0, 0, 0, 0, 0, 49, 664, 0, 0, 0, 110, 0, 0, 0, 0, 0, 0, 263, 353, 54, 113, 0, 0, 0, 482, 0, 0, 295, 0, 261, 0, 0, 289, 0, 33, 456, 486, 287, 578, 29, 0, 0, 0, 69, 0, 0, 0, 0, 0, 0, 0, 545, 0, 530, 420, 0, 0, 236, 0, 245, 0, 0, 0, 152, 0, 0, 0, 0, 566, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 85, 0, 0, 495, 0, 428, 419, 0, 0, 0, 0, 0, 238});
        //p[4] = new Position(new int[]{284, 427, 540, 139, 356, 100, 274, 667, 479, 0, 183, 55, 334, 111, 99, 414, 210, 541, 0, 674, 660, 0, 401, 171, 604, 556, 543, 411, 396, 146, 405, 0, 657, 148, 143, 112, 218, 449, 118, 262, 231, 469, 485, 406, 676, 618, 438, 0, 275, 560, 0, 156, 477, 113, 185, 346, 140, 0, 669, 59, 0, 603, 442, 0, 206, 366, 389, 394, 90, 563, 419, 382, 332, 345, 519, 344, 571, 168, 154, 85, 69, 416, 263, 363, 518, 0, 446, 208, 180, 619, 0, 63, 559, 368, 67, 68, 106, 392, 164, 0, 467, 555, 0, 57, 343, 0, 562, 228, 0, 121, 475, 213, 365, 506, 132, 0, 422, 160, 276, 40, 450, 375, 423, 361, 435, 167, 455, 223, 335, 378, 625, 633, 147, 95, 219, 207, 233, 402, 615, 408, 412, 359, 283, 413, 517, 0, 614, 138, 558, 65, 103, 468, 349, 0, 661, 220, 0, 229, 454, 510, 466, 0, 369, 535, 158, 64, 295, 362, 131, 120, 443, 331, 342, 564, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 441, 429, 542, 0, 341, 273, 642, 565, 150, 0, 367, 358, 221, 478, 0, 209, 0, 182, 328, 172, 574, 0, 145, 227, 0, 664, 0, 62, 110, 568, 297, 0, 169, 671, 0, 224, 666, 665, 0, 0, 0, 0, 0, 384, 232, 264, 0, 0, 0, 658, 0, 640, 122, 572, 638, 0, 0, 0, 672, 490, 607, 0, 616, 390, 0, 593, 0, 267, 137, 318, 554, 0, 54, 611, 0, 201, 0, 36, 0, 0, 0, 623, 0, 539, 124, 0, 192, 457, 0, 248, 591, 0, 491, 135, 0, 0, 0, 0, 537, 495, 0, 243, 463, 266, 444, 476, 0, 668, 133, 0, 0, 0, 39, 281, 0, 659, 130, 0, 0, 0, 0, 534, 41, 452, 397, 0, 0, 0, 230, 381, 162, 260, 0, 0, 0, 448, 89, 225, 56, 447, 51, 377, 364, 101, 0, 0, 0, 0, 573, 395, 66, 679, 639, 0, 0, 0, 570, 373, 170, 234, 0, 0, 0, 371, 178, 624, 339, 0, 0, 0, 400, 0, 157, 204, 86, 428, 238, 37, 459, 151, 0, 0, 0, 0, 244, 315, 247, 662, 0, 0, 0, 0, 486, 129, 553, 0, 249, 0, 0, 0, 520, 0, 567, 144, 0, 165, 509, 557, 0, 528, 286, 636, 0, 532, 316, 0, 577, 0, 198, 592, 643, 354, 190, 0, 353, 270, 153, 613, 0, 0, 0, 0, 351, 269, 456, 128, 0, 0, 0, 236, 420, 494, 298, 0, 0, 0, 38, 488, 299, 29, 338, 317, 48, 215, 202, 0, 0, 0, 0, 629, 0, 461, 437, 501, 0, 0, 0, 0, 108, 605, 0, 462, 634, 0, 0, 0, 0, 216, 426, 502, 481, 0, 0, 0, 265, 503, 0, 240, 127, 630, 0, 31, 514, 0, 425, 116, 0, 0, 0, 497, 0, 598, 197, 493, 524, 596, 0, 0, 205, 0, 352, 287, 430, 0, 0, 0, 45, 199, 0, 626, 631, 187, 0, 0, 0, 536, 465, 319, 464, 193, 0, 600, 0, 489, 602, 0, 433, 0, 0, 0, 599, 0, 513, 445, 0, 0, 174, 504, 42, 97, 0, 0, 0, 222, 293, 0, 627, 370, 0, 0, 0, 296, 529, 211, 104, 403, 324, 436, 410, 337, 0, 0, 0, 0, 279, 492, 508, 0, 175, 321, 0, 0, 237, 0, 601, 0, 472, 44, 0, 0, 268, 0, 622, 0, 125, 545, 0, 0, 33, 0, 115, 474, 28, 417, 453, 637, 0, 678, 49, 0, 173, 388, 379, 512, 292, 0, 0, 0, 391, 0, 432, 96, 372, 0, 0, 387, 0, 194, 0, 608, 552, 0, 525, 0, 235, 93, 393, 105, 61, 239, 470, 480, 176, 0, 0, 0, 46, 617, 0, 241, 458, 0, 0, 0, 421, 505, 0, 551, 188, 538, 0, 0, 0, 30, 471, 597, 177, 0, 0, 0, 0, 628, 320, 277, 191, 0, 460, 326, 272, 134, 347, 0, 0, 0, 107, 189, 0, 498, 212, 0, 0, 0, 0, 94, 52, 404, 499, 0, 0, 0, 0, 0, 522, 0, 594, 330, 0, 0, 0, 0, 376, 0, 142, 0, 385, 350, 0, 179, 589, 0, 677, 0, 109, 289, 34, 336, 161, 0, 288, 0, 0, 0, 285, 566, 0, 431, 526, 0, 0, 0, 0, 612, 500, 530, 126, 515, 0, 0, 0, 0, 278, 578, 195, 0, 35, 531, 0, 32, 610, 0, 355, 0, 163, 569, 0, 0, 398, 357, 0, 91, 50, 0, 0, 0, 155, 434, 609, 0, 333, 0, 0, 0, 242, 87, 440, 325, 0, 0, 0, 149, 323, 245, 487, 656, 0, 635, 282, 0, 360, 399, 0, 0, 0, 606, 294, 0, 523, 0, 60, 0, 496, 680, 0, 451, 590, 0, 0, 0, 0, 380, 386, 0, 159, 407, 0, 0, 0, 0, 0, 620, 291, 0, 152, 88, 409, 117, 271, 280, 418, 0, 0, 0, 181, 261, 290, 483, 0, 0, 0, 621, 595, 544, 226, 340, 0, 0, 0, 576, 374, 0, 200, 0, 681, 0, 0, 0, 533, 415, 0, 123, 575, 114, 0, 136, 203, 0, 58, 0, 0, 196, 675, 0, 473, 561, 0, 0, 0, 246, 53, 0, 424, 641, 673, 0, 0, 0, 217, 0, 507, 186, 527, 654, 0, 0, 0, 43, 632, 214, 0, 322, 141, 383, 0, 439, 655, 0, 47, 0, 0, 484, 0, 348, 119, 0, 0, 0, 0, 0, 327, 516, 0, 663, 166, 0, 0, 0, 0, 92, 670, 102, 98, 0, 0, 0, 0, 482, 511, 521, 184, 329});
        //p[4] = new Position(new int[]{283, 330, 56, 565, 663, 183, 662, 521, 164, 640, 668, 608, 107, 101, 52, 438, 412, 222, 138, 453, 526, 400, 0, 282, 0, 0, 0, 92, 0, 0, 0, 419, 0, 0, 0, 485, 0, 0, 0, 133, 428, 0, 0, 0, 0, 0, 0, 386, 157, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 619, 0, 0, 0, 0, 0, 0, 476, 393, 449, 398, 341, 0, 0, 0, 235, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 567, 0, 0, 0, 664, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 371, 377, 292, 174, 418, 154, 0, 0, 0, 0, 0, 0, 64, 339, 147, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 263, 0, 0, 0, 0, 0, 0, 0, 0, 110, 0, 232, 0, 0, 0, 291, 230, 224, 0, 0, 0, 96, 57, 152, 360, 227, 0, 0, 0, 122, 156, 440, 552, 414, 0, 0, 0, 121, 0, 0, 0, 274, 469, 0, 0, 0, 0, 0, 0, 167, 434, 0, 0, 0, 180, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 511, 516, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 362, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 417, 0, 0, 0, 483, 0, 0, 0, 0, 0, 0, 0, 334, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 178, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 478, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 540, 0, 145, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 357, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 97, 572, 0, 0, 0, 0, 0, 0, 0, 0, 659, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 212, 0, 0, 0, 218, 0, 593, 47, 0, 216, 637, 0, 267, 109, 215, 501, 522, 667, 114, 322, 494, 0, 533, 177, 0, 626, 0, 30, 195, 0, 514, 496, 610, 426, 241, 192, 525, 43, 0, 475, 336, 444, 298, 473, 41, 264, 320, 612, 347, 0, 425, 380, 265, 442, 244, 435, 595, 319, 0, 59, 34, 90, 387, 591, 238, 106, 323, 678, 166, 0, 396, 168, 493, 0, 278, 136, 487, 271, 91, 451, 294, 209, 272, 472, 234, 69, 187, 602, 536, 48, 246, 197, 421, 470, 270, 66, 482, 53, 130, 429, 620, 395, 0, 474, 601, 0, 455, 268, 499, 0, 28, 681, 0, 295, 60, 590, 0, 480, 629, 0, 129, 328, 569, 433, 51, 49, 538, 388, 0, 477, 596, 0, 372, 247, 65, 457, 285, 488, 275, 381, 135, 481, 172, 50, 441, 445, 206, 599, 0, 523, 210, 276, 194, 0, 112, 325, 211, 100, 627, 0, 200, 0, 680, 243, 0, 513, 392, 0, 359, 113, 202, 0, 219, 539, 0, 468, 45, 190, 0, 467, 490, 577, 0, 179, 495, 175, 262, 631, 0, 118, 55, 464, 503, 0, 430, 518, 191, 624, 333, 315, 117, 471, 171, 611, 479, 0, 462, 342, 670, 675, 87, 364, 160, 0, 318, 551, 389, 413, 404, 555, 403, 0, 575, 239, 0, 592, 0, 506, 408, 0, 249, 338, 394, 326, 169, 176, 182, 498, 0, 614, 500, 509, 422, 603, 0, 370, 139, 618, 0, 86, 340, 317, 461, 423, 385, 299, 125, 228, 185, 324, 407, 124, 564, 290, 0, 108, 508, 0, 89, 384, 437, 558, 281, 0, 654, 0, 463, 530, 266, 201, 0, 544, 0, 382, 288, 196, 448, 0, 623, 0, 42, 517, 0, 507, 502, 545, 231, 410, 557, 0, 556, 634, 553, 115, 128, 458, 214, 676, 0, 636, 0, 658, 351, 443, 415, 679, 0, 617, 616, 578, 240, 576, 0, 346, 337, 316, 542, 573, 615, 116, 296, 632, 0, 85, 489, 189, 0, 356, 119, 0, 229, 120, 32, 217, 532, 355, 561, 606, 524, 0, 519, 543, 348, 391, 329, 293, 677, 369, 0, 625, 148, 541, 459, 280, 233, 527, 673, 560, 205, 0, 554, 46, 0, 344, 520, 638, 140, 0, 512, 199, 0, 574, 373, 0, 223, 350, 213, 0, 416, 99, 111, 105, 221, 465, 345, 661, 0, 497, 535, 622, 431, 491, 0, 142, 0, 58, 607, 0, 153, 273, 609, 0, 505, 528, 0, 237, 566, 0, 269, 655, 198, 141, 95, 504, 0, 159, 0, 671, 366, 0, 261, 515, 0, 143, 0, 165, 131, 0, 352, 349, 0, 40, 605, 35, 0, 454, 397, 88, 630, 368, 186, 436, 0, 666, 0, 68, 132, 31, 158, 161, 0, 327, 123, 446, 137, 62, 204, 594, 641, 104, 220, 203, 0, 383, 162, 0, 600, 94, 0, 242, 39, 568, 321, 450, 365, 102, 37, 378, 279, 188, 0, 367, 225, 358, 126, 0, 93, 163, 402, 0, 529, 127, 0, 424, 361, 146, 0, 510, 642, 672, 103, 63, 193, 405, 44, 531, 0, 639, 297, 0, 559, 492, 589, 353, 54, 170, 439, 61, 379, 332, 427, 181, 411, 537, 0, 0, 375, 597, 289, 374, 33, 456, 628, 287, 486, 29, 466, 173, 621, 635, 633, 447, 226, 399, 277, 335, 354, 409, 432, 660, 420, 0, 0, 665, 236, 208, 245, 669, 149, 656, 343, 604, 144, 67, 401, 376, 260, 390, 36, 571, 151, 613, 331, 286, 484, 452, 674, 207, 657, 406, 534, 363, 38, 155, 562, 150, 598, 248, 284, 184, 570, 134, 460, 563, 643});
        //p[4] = new Position(new int[] {448, 130, 405, 294, 569, 444, 0, 460, 219, 61, 66, 0, 437, 0, 326, 517, 151, 616, 273, 0, 445, 389, 283, 0, 64, 0, 218, 355, 342, 296, 519, 0, 104, 432, 572, 672, 391, 423, 349, 452, 92, 281, 112, 224, 171, 150, 654, 641, 291, 559, 325, 589, 152, 571, 490, 233, 358, 284, 170, 662, 428, 208, 0, 164, 346, 642, 156, 414, 348, 101, 63, 555, 451, 0, 334, 111, 669, 408, 638, 411, 222, 614, 261, 160, 359, 209, 469, 673, 667, 162, 264, 221, 658, 417, 94, 419, 612, 380, 565, 491, 181, 603, 180, 148, 290, 50, 331, 207, 51, 368, 172, 381, 0, 640, 674, 178, 106, 439, 556, 330, 668, 570, 443, 0, 666, 123, 60, 0, 158, 167, 476, 524, 96, 226, 664, 134, 0, 0, 609, 168, 56, 387, 418, 163, 0, 535, 558, 200, 220, 372, 339, 375, 343, 147, 534, 496, 42, 280, 179, 145, 231, 485, 132, 402, 362, 435, 553, 275, 676, 211, 376, 0, 297, 378, 0, 618, 118, 0, 119, 322, 412, 327, 120, 140, 276, 554, 382, 0, 340, 379, 139, 422, 659, 510, 466, 561, 478, 661, 206, 446, 0, 165, 185, 404, 143, 401, 633, 99, 657, 677, 563, 356, 568, 87, 615, 523, 235, 121, 52, 416, 223, 608, 385, 390, 110, 292, 544, 173, 639, 279, 624, 332, 619, 146, 511, 0, 449, 69, 102, 542, 282, 413, 656, 262, 68, 234, 142, 62, 467, 155, 447, 0, 0, 89, 232, 0, 98, 394, 0, 97, 0, 88, 637, 577, 210, 107, 453, 483, 100, 454, 398, 0, 610, 0, 396, 567, 323, 114, 131, 597, 103, 539, 174, 293, 365, 440, 573, 0, 229, 606, 0, 552, 0, 48, 636, 0, 591, 477, 0, 617, 124, 49, 193, 129, 0, 468, 344, 388, 186, 459, 45, 465, 502, 65, 0, 90, 337, 470, 125, 29, 480, 157, 0, 213, 431, 464, 137, 241, 678, 0, 236, 631, 0, 598, 484, 0, 175, 566, 623, 503, 596, 607, 184, 481, 545, 505, 0, 53, 560, 202, 109, 177, 133, 0, 0, 248, 315, 43, 500, 0, 0, 406, 0, 38, 508, 486, 529, 117, 397, 516, 0, 0, 455, 540, 518, 506, 141, 0, 198, 377, 0, 450, 366, 655, 144, 199, 0, 635, 0, 604, 0, 0, 345, 392, 457, 105, 0, 0, 363, 0, 295, 489, 28, 357, 244, 0, 0, 0, 0, 347, 551, 0, 182, 0, 578, 0, 0, 41, 0, 228, 521, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 599, 128, 0, 0, 576, 0, 0, 0, 192, 0, 321, 189, 0, 249, 0, 0, 0, 531, 0, 532, 205, 0, 319, 507, 0, 0, 605, 0, 31, 0, 0, 630, 204, 495, 0, 195, 0, 116, 0, 0, 436, 472, 0, 557, 274, 240, 0, 0, 0, 0, 0, 0, 0, 0, 0, 161, 625, 0, 0, 361, 0, 0, 0, 0, 0, 0, 0, 367, 0, 0, 0, 0, 0, 0, 0, 373, 497, 0, 0, 0, 494, 0, 0, 0, 643, 135, 0, 493, 0, 430, 592, 0, 514, 288, 0, 621, 0, 680, 0, 522, 0, 287, 681, 0, 34, 487, 320, 37, 383, 0, 0, 0, 127, 266, 32, 115, 0, 456, 369, 0, 270, 0, 0, 0, 527, 0, 403, 0, 0, 0, 0, 0, 520, 0, 0, 0, 399, 0, 479, 371, 0, 122, 0, 350, 0, 0, 0, 333, 0, 562, 93, 0, 0, 415, 671, 0, 0, 0, 0, 0, 0, 384, 400, 138, 191, 85, 0, 0, 620, 0, 0, 393, 0, 0, 0, 0, 0, 427, 499, 438, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 271, 341, 0, 492, 225, 679, 0, 0, 441, 0, 267, 217, 33, 600, 166, 0, 0, 0, 0, 420, 317, 473, 36, 0, 0, 0, 126, 0, 536, 0, 0, 196, 538, 602, 0, 575, 247, 0, 47, 190, 0, 237, 504, 488, 239, 324, 269, 268, 0, 0, 0, 187, 0, 530, 0, 541, 0, 0, 475, 0, 0, 0, 59, 0, 0, 0, 613, 0, 54, 528, 0, 533, 154, 0, 601, 0, 675, 58, 629, 0, 263, 0, 0, 0, 201, 537, 0, 245, 40, 0, 0, 0, 197, 0, 214, 512, 153, 0, 525, 0, 108, 353, 634, 0, 0, 0, 0, 0, 0, 0, 425, 299, 188, 30, 0, 595, 0, 474, 426, 203, 216, 0, 670, 0, 67, 632, 0, 0, 169, 0, 370, 316, 95, 574, 0, 0, 0, 0, 0, 0, 0, 0, 0, 665, 0, 0, 509, 0, 328, 159, 0, 0, 0, 0, 0, 91, 0, 0, 660, 0, 663, 0, 0, 230, 0, 526, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 611, 0, 498, 0, 0, 0, 593, 0, 0, 0, 0, 0, 429, 0, 513, 0, 0, 0, 0, 0, 0, 86, 374, 0, 183, 57, 0, 0, 0, 543, 395, 501, 0, 227, 0, 0, 0, 0, 212, 0, 0, 0, 410, 0, 0, 0, 0, 627, 0, 0, 272, 0, 564, 0, 0, 0, 434, 329, 0, 0, 0, 35, 471, 360, 0, 0, 0, 0, 407, 338, 386, 0, 0, 55, 0, 0, 0, 0, 44, 458, 215, 352, 0, 0, 0, 277, 515, 0, 461, 0, 0, 0, 0, 0, 318, 278, 336, 260, 238, 628, 0, 0, 626, 0, 0, 0, 0, 590, 0, 0, 0, 0, 149, 622, 0, 265, 0, 0, 409, 0, 0, 285, 0, 335, 482, 0, 0, 0, 0, 194, 594, 0, 0, 0, 176, 421, 351, 113, 463, 0, 0, 0, 243, 364, 0, 0, 0, 0, 0, 0, 246, 289, 424, 442, 0, 0, 0, 136, 462, 433, 286, 0, 0, 0, 39, 46, 0, 0, 298, 354, 242});


        /*for(int i = -1, is = p.length; ++i < is; )
        {
            Position.replace(pso.particles[0].data.positions[i], p[i]);
        }*/
        //pso.repairData(pso.particles[0]);
        //pso.calculateFitness(pso.particles[0]);
        //pso.updateSwarmFitness();
        for(int i = -1, is = 100000; ++i < is; )
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.particles[0].calculateVelocity(pso.gBest);
            pso.particles[0].updateData();
            pso.repairData(pso.particles[0]);
            pso.calculateFitness(pso.particles[0]);
            //pso.updateSwarmFitness();
        }
    }

    @Test public void testAssignGBestWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 10;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        for(int i = 0, is = 50000000; ++i < is; )
        {
            pso.assignGBest();
        }
    }

    @Test public void testClassroomPlacementWithStabilityChecking()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 1;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final double expectedClassroomPlacementFitness = 105240.0;
        int          correctnessCounter                = 0;
        final int    testSize                          = 10000000;

        PSOP2 pso = new PSOP2(setting, gen);

        pso.initializeSwarm();
        for(int i = -1, is = testSize; ++i < is; )
        {
            pso.repairDataForClassroomPlacement(pso.particles[0]);
            pso.calculateFitness(pso.particles[0]);
            correctnessCounter += pso.particles[0].data.fitness == expectedClassroomPlacementFitness ? 1 : 0;
        }
        Assert.assertEquals("Test Size", testSize, correctnessCounter);
    }

    @Test public void testClassroomPlacementAfterRepairWithStabilityChecking()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 1;
        setting.MAX_EPOCHS = 1;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        final double expectedClassroomPlacementFitness = 105240.0;
        int          correctnessCounter                = 0;
        final int    testSize                          = 1000000;

        PSOP2 pso = new PSOP2(setting, gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        for(int i = -1, is = testSize; ++i < is; )
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.particles[0].calculateVelocity(pso.gBest);
            pso.particles[0].updateData();
            pso.repairData(pso.particles[0]);
            pso.calculateFitness(pso.particles[0]);
            correctnessCounter += (pso.particles[0].data.fitness == expectedClassroomPlacementFitness ? 1 : 0);
        }
        Assert.assertEquals("Test Size", testSize, correctnessCounter);
    }

    @Test public void testSystemWithBenchmark()
    {
        Setting setting = Setting.getInstance();
        setting.bglob = 0.5;
        setting.bloc = 0.5;
        setting.brand = 0.5;
        setting.MAX_PARTICLES = 20;
        setting.MAX_EPOCHS = 10000;

        Main.getMyDatabaseAccount();
        final Dataset2<Timeoff, Lesson>                  dataset    = new Dataset2<>(1);
        final WorkingSet                                 workingset = new WorkingSet();
        final DatasetConverter<Int2IntLinkedOpenHashMap> encoder    = new DatasetConverter<>();
        final DatasetConverter<Int2IntLinkedOpenHashMap> decoder    = new DatasetConverter<>();
        final DatasetP2Generator3                        gen        = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        PSOP2 pso = new PSOP2(setting, gen);
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
}