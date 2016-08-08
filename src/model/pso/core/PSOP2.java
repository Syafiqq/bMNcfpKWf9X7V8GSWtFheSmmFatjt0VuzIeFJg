package model.pso.core;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import model.custom.it.unimi.dsi.fastutil.ints.ScheduleContainer;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.ScheduleShufflingProperties;
import model.dataset.component.Timeoff;
import model.dataset.component.TimeoffPlacement;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.LessonGroupSet;
import model.dataset.core.LessonPoolSet;
import model.dataset.core.WorkingSet;
import model.helper.IntHList;
import model.pso.component.Data;
import model.pso.component.ParticleP2;
import model.pso.component.PlacementProperties;
import model.pso.component.Position;
import model.pso.component.RepairProperties;
import model.pso.component.Setting;
import model.pso.component.Velocity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/*
 * This <Skripsi_003> project in package <model.pso.core> created by :
 * Name         : syafiq
 * Date / Time  : 12 June 2016, 1:24 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2 extends PSOOperation<Data, Velocity[], ParticleP2> implements ScheduleRandomable<Position[]>
{
    //Setting
    private final Setting setting;

    //Dataset
    private final int       school;
    private final int[]     active_days;
    private final int[]     active_periods;
    private final Timeoff[] classes;
    private final Timeoff[] classrooms;
    private final Timeoff[] lecturers;
    private final Timeoff[] subjects;
    private final Lesson[]  lessons;
    private final int[][][] classroom_available_time;
    private final int[]     sks_distribution;

    //Working Set
    private final LessonGroupSet[]            lesson_set;
    private final LessonPoolSet[]             lesson_pool;
    private final ScheduleShufflingProperties shuffling_properties;

    //Converter
    private final DatasetConverter<Int2IntLinkedOpenHashMap> encoder;
    private final DatasetConverter<Int2IntLinkedOpenHashMap> decoder;

    //Logging
    private Logger          logger;
    private StringBuilder[] logBuffers;

    //Thread Pool Executor
    private int                          max_core_allowed;
    private int                          max_pool_allowed;
    private ArrayBlockingQueue<Runnable> thread_queue;

    public PSOP2(Setting setting, DatasetP2Generator3 generator)
    {
        this.setting = setting;
        final Dataset2<Timeoff, Lesson> dataset = generator.getDataset();

        //Generate Dataset
        this.school = dataset.school;
        this.active_days = dataset.active_days;
        this.active_periods = dataset.active_periods;
        this.classes = dataset.classes;
        this.classrooms = dataset.classrooms;
        this.lecturers = dataset.lecturers;
        this.subjects = dataset.subjects;
        this.lessons = dataset.lessons;
        this.classroom_available_time = dataset.classroom_available_time;
        this.sks_distribution = dataset.sks_distribution;

        //Generate WorkingSet
        final WorkingSet workingset = generator.getWorkingset();
        this.lesson_set = workingset.lesson_set;
        this.lesson_pool = workingset.lesson_pool;
        this.shuffling_properties = generator.generateProperties();

        //Generate Converter
        this.encoder = generator.getEncoder();
        this.decoder = generator.getDecoder();

        //Generate Particle
        super.particles = new ParticleP2[this.setting.max_particle];

        super.cEpoch = 0;
        super.gBest = Data.newInstance(this.lesson_pool.length);

        for(int position_number = -1, position_size = this.lesson_pool.length; ++position_number < position_size; )
        {
            super.gBest.positions[position_number] = Position.newInstance(this.lesson_pool[position_number].lessons.length + this.lesson_pool[position_number].lesson_null.length);
        }

        this.initializeMultiprocessorCore();
        //this.initializeInjectionOperator();
        //this.initializeLogger();
    }

    private void initializeLogger()
    {
        this.logger = LogManager.getLogger(PSOP2.class);
        this.logBuffers = new StringBuilder[this.lesson_pool.length];
        for(int log_index = -1, log_size = this.logBuffers.length; ++log_index < log_size; )
        {
            this.logBuffers[log_index] = new StringBuilder();
        }
        this.logger.debug(new Timestamp(new Date().getTime()));
    }

    private void initializeMultiprocessorCore()
    {
        this.max_core_allowed = (this.setting.total_core > Runtime.getRuntime().availableProcessors() ? Runtime.getRuntime().availableProcessors() : this.setting.total_core);
        this.max_pool_allowed = ((this.setting.max_particle - this.max_core_allowed) < this.max_core_allowed ? this.max_core_allowed : (this.setting.max_particle - this.max_core_allowed));
        this.thread_queue = new ArrayBlockingQueue<>((this.setting.max_particle - this.max_core_allowed) < 1 ? 1 : (this.setting.max_particle - this.max_core_allowed));
    }

    public void updateAllParticlePBest()
    {
        for(ParticleP2 particle : super.particles)
        {
            particle.assignPBest();
        }
    }

    public void updateAllParticlePBestWithMultiProcessor()
    {
        final ThreadPoolExecutor executor = this.generatePoolExecutor(this.max_core_allowed, this.max_pool_allowed, 10, TimeUnit.MILLISECONDS);
        executor.allowCoreThreadTimeOut(true);

        for(final ParticleP2 particle : super.particles)
        {
            executor.execute(particle::assignPBest);
        }
        executor.shutdown();
        while(!executor.isTerminated())
        {
            ;
        }
    }

    public void evaluateAllParticle()
    {
        for(final ParticleP2 particle : super.particles)
        {
            particle.calculateVelocity(this.gBest, super.cEpoch, this.setting.max_epoch);
            particle.updateData();
            this.repairData(particle);
            this.calculateFitness(particle);
        }
    }

    public void evaluateAllParticleWithMultiProcessor()
    {
        final ThreadPoolExecutor executor = this.generatePoolExecutor(this.max_core_allowed, this.max_pool_allowed, 10, TimeUnit.MILLISECONDS);
        executor.allowCoreThreadTimeOut(true);

        for(final ParticleP2 particle : super.particles)
        {
            executor.execute(() ->
            {
                particle.calculateVelocity(this.gBest, super.cEpoch, this.setting.max_epoch);
                particle.updateData();
                this.repairData(particle);
                this.calculateFitness(particle);
            });
        }
        executor.shutdown();
        while(!executor.isTerminated())
        {
            ;
        }
    }

    @Override
    public void updateSwarmFitness()
    {
        /*
         * Thread Pool Executor     : 4 core  | 10 pool   | 10ms    | 10 particle   | 50000 iter    = ~2m 19s 655ms LinkedBlockedQueue
         * Thread Pool Executor     : 4 core  | 10 pool   | 50ms    | 10 particle   | 50000 iter    = ~2m 17s ---ms ArrayBlockedQueue
         * Thread Pool Executor     : 4 core  | 10 pool   | 5ms     | 10 particle   | 50000 iter    = ~2m 24s 655ms LinkedBlockedQueue
         * Thread Pool Executor     : 3 core  | 10 pool   | 10ms    | 10 particle   | 50000 iter    = ~2m 0s  636ms LinkedBlockedQueue
         * Thread Pool Executor     : 3 core  | 10 pool   | 10ms    | 10 particle   | 50000 iter    = ~1m 54s 471ms ArrayBlockedQueue
         * Thread Pool Executor     : 3 core  | 10 pool   | 50ms    | 10 particle   | 50000 iter    = ~1m 53s 52ms  ArrayBlockedQueue
         * Thread Pool Executor     : 2 core  | 10 pool   | 50ms    | 10 particle   | 50000 iter    = ~2m 12s 381ms  ArrayBlockedQueue
         * Thread Pool Executor     : 1 core  | 10 pool   | 50ms    | 10 particle   | 50000 iter    = ~3m 09s 109ms  ArrayBlockedQueue
         *
         * Cached Thread Pool       : 0 core  | 2^31      | 1m      | 10 particle   | 50000 iter    = ~2m 53s 899ms
         * No Thread                : 1 core  | 1 pool    |   ~     | 10 particle   | 50000 iter    = ~3m 10s 194ms
         * */
        //ArrayBlockingQueue<Runnable> tmpQueue = new ArrayBlockingQueue<Runnable>()

        //ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), setting.max_particles, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, setting.max_particles, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(this.setting.max_particles), new ThreadPoolExecutor.CallerRunsPolicy());
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, setting.max_particles, 50, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(setting.max_particles));
        final ThreadPoolExecutor executor = this.generatePoolExecutor(this.max_core_allowed, this.max_pool_allowed, 10, TimeUnit.MILLISECONDS);
        executor.allowCoreThreadTimeOut(true);
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(setting.max_particles, setting.max_particles, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        //executor.allowCoreThreadTimeOut(true);
        //ExecutorService executor = Executors.newCachedThreadPool();
        //executor.execute();

        for(ParticleP2 particle : super.particles)
        {
            executor.execute(() -> this.calculateFitness(particle));
        }
        executor.shutdown();
        while(!executor.isTerminated())
        {
            ;
        }
    }

    @NotNull private ThreadPoolExecutor generatePoolExecutor(int max_core_allowed, int max_pool_allowed, long keep_alive, TimeUnit time_unit)
    {
        return new ThreadPoolExecutor(max_core_allowed, max_pool_allowed, keep_alive, time_unit, this.thread_queue);
    }

    @Override
    public void assignGBest()
    {
        int best_index = 0;
        Arrays.sort(super.particles, ParticleP2.particlePbestFitnessDescComparator);

        if(super.particles[best_index].pBest.fitness > super.gBest.fitness)
        {
            Data.replaceData(super.gBest, super.particles[best_index].pBest);
        }
    }

    @Override
    public void calculateFitness(ParticleP2 data)
    {
        final TimeoffPlacement[] lecture_placement = data.placement_properties.lecture_placement;
        final TimeoffPlacement[] class_placement   = data.placement_properties.class_placement;
        final IntHList           lecture_fill      = data.placement_properties.lecture_fill;
        final IntHList           class_fill        = data.placement_properties.class_fill;
        double                   fitness           = 0.0;

        int pool_index = -1;
        for(final LessonPoolSet lesson_pool : this.lesson_pool)
        {
            final int[]    lesson_id        = data.data.positions[++pool_index].position;
            final IntHList lesson_conflicts = data.lesson_conflicts[pool_index];

            int    lesson_counter  = -1;
            Lesson lesson          = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks      = lesson.sks;
            int    current_sks     = 0;
            int    lesson_conflict = -1;

            lesson_conflicts.reset();

            for(final int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(final double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    ++day_index;
                    int period_index = -1;
                    for(final double period : day)
                    {
                        ++period_index;
                        if(period != 0.2)
                        {
                            if(lesson.subject == -1)
                            {
                                lecture_fill.add(-1);
                                class_fill.add(-1);
                            }
                            else
                            {
                                double fitness_1 = (.02 * (this.subjects[lesson.subject].timeoff[day_index][period_index]));
                                double fitness_2 = (1 * (lesson.lecture == -1 ? 10 : (this.lecturers[lesson.lecture].timeoff[day_index][period_index])));
                                double fitness_3 = (.04 * (this.classes[lesson.clazz].timeoff[day_index][period_index]));
                                double fitness_4 = (0.001 * period); // classroom timeoff
                                double fitness_5 = (5 * (lesson.lecture == -1 ? 10 : (lecture_placement[lesson.lecture].putPlacementIfAbset(day_index, period_index, lesson_id[lesson_counter]) ? 10 : 0.1)));
                                double fitness_6 = (5 * (lesson.clazz == -1 ? 10 : (class_placement[lesson.clazz].putPlacementIfAbset(day_index, period_index, lesson_id[lesson_counter]) ? 10 : 0.1)));
                                double fitness_7 = (3 * (lesson.link.length == 0 ? 10 : class_placement[lesson.clazz].isNotTheSameDay(day_index, lesson.link) ? 10 : 0.1));
                                double fitness_8 = (.5 * (lesson.allowed_classroom[classroom] ? 10 : 0.1));

                                fitness += (fitness_1 + fitness_2 + fitness_3 + fitness_4 + fitness_5 + fitness_6 + fitness_7 + fitness_8);

                                if((fitness_5 < 50.0) || (fitness_6 < 50.0))
                                {
                                    lesson_conflict = lesson_counter;
                                }

                                lecture_fill.add(lesson.lecture);
                                class_fill.add(lesson.clazz);
                            }

                            if(++current_sks == lesson_sks)
                            {
                                if(lesson_conflict != -1)
                                {
                                    lesson_conflicts.add(lesson_conflict);
                                }
                                try
                                {
                                    lesson = this.lessons[lesson_id[++lesson_counter]];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                }
                                finally
                                {
                                    lesson_sks = lesson.sks;
                                    current_sks = 0;
                                    lesson_conflict = -1;
                                }
                            }
                        }
                        else
                        {
                            lecture_fill.add(-1);
                            class_fill.add(-1);
                        }
                    }
                }
            }
        }
        data.data.fitness = fitness;
        data.placement_properties.resetPlacement();
    }

    /**
     * Index    :   Description
     * 0   : Total Fitness
     * 1   : Fitness Constraint 1
     * 2   : Fitness Constraint 2
     * 3   : Fitness Constraint 3
     * 4   : Fitness Constraint 4
     * 5   : Fitness Constraint 5
     * 6   : Fitness Constraint 6
     * 7   : Fitness Constraint 7
     * 8   : Fitness Constraint 8
     * 9   : Lesson ID
     *
     * @param data Particle Data
     * @return Fitness Data
     */
    @SuppressWarnings({"unused", "ConstantConditions"}) public DoubleArrayList[][][] visualizeScheduleByFitness(ParticleP2 data)
    {
        final TimeoffPlacement[]    lecture_placement = data.placement_properties.lecture_placement;
        final TimeoffPlacement[]    class_placement   = data.placement_properties.class_placement;
        final IntHList              lecture_fill      = data.placement_properties.lecture_fill;
        final IntHList              class_fill        = data.placement_properties.class_fill;
        final DoubleArrayList[][][] container         = new DoubleArrayList[this.classrooms.length][this.active_days.length][];
        double[]                    fitness           = new double[8];

        int pool_index = -1;
        for(final LessonPoolSet lesson_pool : this.lesson_pool)
        {
            final Int2IntLinkedOpenHashMap classroom_decoder = lesson_pool.clustered_classroom_decoder;
            final int[]                    lesson_id         = gBest.positions[++pool_index].position;

            int    lesson_counter = -1;
            Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks     = lesson.sks;
            int    current_sks    = 0;

            for(final int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    final DoubleArrayList[] temp_container = new DoubleArrayList[10];
                    for(int constraint_index = -1, constraint_size = 10; ++constraint_index < constraint_size; )
                    {
                        temp_container[constraint_index] = new DoubleArrayList(this.active_periods.length);
                    }

                    ++day_index;
                    int period_index = -1;
                    for(final double period : day)
                    {
                        ++period_index;
                        if(period != 0.2)
                        {
                            if(lesson.subject == -1)
                            {
                                for(int constraint_index = -1, constraint_size = 8; ++constraint_index < constraint_size; )
                                {
                                    temp_container[constraint_index].add(-1.);
                                }
                                temp_container[8].add(5);
                                temp_container[9].add(-1);
                                lecture_fill.add(-1);
                                class_fill.add(-1);
                            }
                            else
                            {
                                fitness[0] = (.02 * (this.subjects[lesson.subject].timeoff[day_index][period_index]));
                                fitness[1] = (1 * (lesson.lecture == -1 ? 10 : (this.lecturers[lesson.lecture].timeoff[day_index][period_index])));
                                fitness[2] = (.04 * (this.classes[lesson.clazz].timeoff[day_index][period_index]));
                                fitness[3] = (0.001 * period); // classroom timeoff
                                fitness[4] = (5 * (lesson.lecture == -1 ? 10 : (lecture_placement[lesson.lecture].putPlacementIfAbset(day_index, period_index, lesson_id[lesson_counter]) ? 10 : 0.1)));
                                fitness[5] = (5 * (lesson.clazz == -1 ? 10 : (class_placement[lesson.clazz].putPlacementIfAbset(day_index, period_index, lesson_id[lesson_counter]) ? 10 : 0.1)));
                                fitness[6] = (3 * (lesson.link.length == 0 ? 10 : class_placement[lesson.clazz].isNotTheSameDay(day_index, lesson.link) ? 10 : 0.1));
                                fitness[7] = (.5 * (lesson.allowed_classroom[classroom] ? 10 : 0.1));


                                for(int constraint_index = 0, constraint_size = 9; ++constraint_index < constraint_size; )
                                {
                                    temp_container[constraint_index].add(fitness[constraint_index - 1]);
                                }
                                temp_container[0].add(Arrays.stream(fitness).sum());
                                temp_container[9].add(lesson_id[lesson_counter]);

                                lecture_fill.add(lesson.lecture);
                                class_fill.add(lesson.clazz);
                            }

                            if(++current_sks == lesson_sks)
                            {
                                try
                                {
                                    lesson = this.lessons[lesson_id[++lesson_counter]];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                }
                                finally
                                {
                                    lesson_sks = lesson.sks;
                                    current_sks = 0;
                                }
                            }
                        }
                        else
                        {
                            lecture_fill.add(-1);
                            class_fill.add(-1);
                            for(int constraint_index = -1, constraint_size = 10; ++constraint_index < constraint_size; )
                            {
                                temp_container[constraint_index].add(-1.);
                            }
                        }
                    }
                    container[classroom_decoder.get(classroom)][day_index] = temp_container;
                }
            }
        }
        data.placement_properties.resetPlacement();
        for(int classroom_index = -1, classroom_length = this.classrooms.length; ++classroom_index < classroom_length; )
        {
            for(final int day : active_days)
            {
                if(container[classroom_index][day] == null)
                {
                    final DoubleArrayList[] temp_container = new DoubleArrayList[10];
                    for(int constraint_index = -1, constraint_size = 10; ++constraint_index < constraint_size; )
                    {
                        temp_container[constraint_index] = new DoubleArrayList(this.active_periods.length);
                        for(final int period : active_periods)
                        {
                            temp_container[constraint_index].add(-1);
                        }
                    }
                    container[classroom_index][day] = temp_container;
                }
            }
        }
        return container;
    }

    /**
     * Index    :   Description
     * 0   : Lesson ID
     * 1   : Lesson Subject
     * 2   : Lesson Class
     * 3   : Lesson Lecture
     * 4   : Lesson Classroom
     *
     * @param data Particle Data
     * @return Fitness Data
     */
    @SuppressWarnings("unused") public IntArrayList[][][] visualizeScheduleByComponents(ParticleP2 data)
    {
        IntArrayList[][][] container = new IntArrayList[this.classrooms.length][this.active_days.length][];

        int pool_index = -1;
        for(final LessonPoolSet lesson_pool : this.lesson_pool)
        {
            final Int2IntLinkedOpenHashMap classroom_decoder = lesson_pool.clustered_classroom_decoder;
            final int[]                    lesson_id         = gBest.positions[++pool_index].position;

            int    lesson_counter = -1;
            Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks     = lesson.sks;
            int    current_sks    = 0;

            for(final int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(final double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    final IntArrayList[] temp_container = new IntArrayList[5];
                    for(int constraint_index = -1, constraint_size = 5; ++constraint_index < constraint_size; )
                    {
                        temp_container[constraint_index] = new IntArrayList(this.active_periods.length);
                    }

                    ++day_index;
                    for(final double period : day)
                    {
                        if(period != 0.2)
                        {
                            if(lesson.subject == -1)
                            {
                                for(int constraint_index = -1, constraint_size = 5; ++constraint_index < constraint_size; )
                                {
                                    temp_container[constraint_index].add(-1);
                                }
                            }
                            else
                            {
                                temp_container[0].add(lesson_id[lesson_counter]);
                                temp_container[1].add(lesson.subject);
                                temp_container[2].add(lesson.clazz);
                                temp_container[3].add(lesson.lecture);
                                temp_container[4].add(classroom_decoder.get(classroom));
                            }

                            if(++current_sks == lesson_sks)
                            {
                                try
                                {
                                    lesson = this.lessons[lesson_id[++lesson_counter]];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                }
                                finally
                                {
                                    lesson_sks = lesson.sks;
                                    current_sks = 0;
                                }
                            }
                        }
                        else
                        {
                            for(int constraint_index = -1, constraint_size = 5; ++constraint_index < constraint_size; )
                            {
                                temp_container[constraint_index].add(-1);
                            }
                        }
                    }
                    container[classroom_decoder.get(classroom)][day_index] = temp_container;
                }
            }
        }
        for(int classroom_index = -1, classroom_length = this.classrooms.length; ++classroom_index < classroom_length; )
        {
            for(final int day : active_days)
            {
                if(container[classroom_index][day] == null)
                {
                    final IntArrayList[] temp_container = new IntArrayList[5];
                    for(int constraint_index = -1, constraint_size = 5; ++constraint_index < constraint_size; )
                    {
                        temp_container[constraint_index] = new IntArrayList(this.active_periods.length);
                        for(final int period : active_periods)
                        {
                            temp_container[constraint_index].add(-1);
                        }
                    }
                    container[classroom_index][day] = temp_container;
                }
            }
        }
        return container;
    }

    /**
     * This is for random method stress testing, to check lesson placement according to classroom placement
     *
     * @param data : Particle's data
     */
    public void repairDataForClassroomPlacement(ParticleP2 data)
    {
        this.random(data.velocity_properties.rand_properties, data.velocity_properties.prand);
        for(int pool_index = -1, pool_size = this.lesson_pool.length; ++pool_index < pool_size; )
        {
            Position.replace(data.data.positions[pool_index], data.velocity_properties.prand[pool_index]);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void repairData(ParticleP2 data)
    {
        int         pool_index  = -1;
        final int[] active_days = this.active_days;
        /*
        * For all available lesson pool
        * */
        pool_lookup:
        for(final LessonPoolSet lesson_pool : this.lesson_pool)
        {
            ++pool_index;
            try
            {
                final int[]            lesson_id         = data.data.positions[pool_index].position;
                final RepairProperties repair_properties = data.repair_properties[pool_index];

                int    lesson_counter = -1;
                Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];

                /*
                * For all classroom in current lesson pool
                * */
                for(final int classroom : lesson_pool.classrooms)
                {
                    /*
                    * For all operational day in current classroom
                    * */
                    for(final int day : this.active_days)
                    {
                        repair_properties.absent[classroom][day] = true;
                        repair_properties.index[classroom][day] = lesson_counter;

                        final int[] time        = lesson_pool.classroom_available_time[classroom][day];
                        int         current_sks = 0;
                        /*
                        * For all time cluster in current day
                        * */
                        for(int time_index = 1, time_size = time.length; time_index < time_size; )
                        {
                            /*
                            * Check whether current lesson is allowed in current classroom
                            * */
                            if((lesson.allowed_classroom[classroom]) || (lesson.subject == -1))
                            {
                                /*
                                 * Check whether incoming sks + current sks less than cluster capacity
                                 * */
                                if((current_sks + lesson.sks) < time[time_index])
                                {
                                    current_sks += lesson.sks;
                                    lesson = this.lessons[lesson_id[++lesson_counter]];
                                }

                                /*
                                 * Check whether incoming sks + current sks equals sks cluster capacity so we change cluster time after that
                                 * */
                                else if((current_sks + lesson.sks) == time[time_index])
                                {
                                    try
                                    {
                                        lesson = this.lessons[lesson_id[++lesson_counter]];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                    }
                                    ++time_index;
                                    current_sks = 0;
                                }

                                /*
                                 * Check whether incoming sks + current sks overflow sks cluster capacity so we must change incoming lesson with lesson in which occupy remaining sks capacity
                                 * */
                                else
                                {
                                    final int need   = time[time_index] - current_sks;
                                    boolean   change = false;

                                    /*
                                     * Find lesson which its sks completely equals remaining sks [search forward]
                                     * */
                                    for(int lookup_index = lesson_counter, lookup_size = lesson_id.length; ++lookup_index < lookup_size; )
                                    {
                                        final Lesson les_temp = this.lessons[lesson_id[lookup_index]];
                                        if((les_temp.sks == need) && ((les_temp.allowed_classroom[classroom]) || (les_temp.subject == -1)))
                                        {
                                            change = true;
                                            final int swap_temp = lesson_id[lookup_index];
                                            lesson_id[lookup_index] = lesson_id[lesson_counter];
                                            lesson_id[lesson_counter] = swap_temp;
                                            break;
                                        }
                                    }

                                    /*
                                     * If function above fail, find lesson which its sks less than remaining sks [search forward]
                                     * */
                                    if(!change)
                                    {
                                        change = false;
                                        for(int lookup_index = lesson_counter, lookup_size = lesson_id.length; ++lookup_index < lookup_size; )
                                        {
                                            final Lesson les_temp = this.lessons[lesson_id[lookup_index]];
                                            if((les_temp.sks < need) && ((les_temp.allowed_classroom[classroom]) || (les_temp.subject == -1)))
                                            {
                                                change = true;
                                                final int swap_temp = lesson_id[lookup_index];
                                                lesson_id[lookup_index] = lesson_id[lesson_counter];
                                                lesson_id[lesson_counter] = swap_temp;
                                                break;
                                            }
                                        }

                                        /*
                                         * If function above fail, find lesson in all possible classrooms [search from its available classroom that already observed]
                                         * */
                                        if(!change)
                                        {
                                            change = false;
                                            final int remain = lesson.sks - need;

                                            /*
                                             * Lookup all its lesson available classroom
                                             * */
                                            lookup_replacement:
                                            for(int classroom_lookup : lesson.available_classroom)
                                            {
                                                /*
                                                * Lookup operational day of current classroom
                                                * */
                                                for(int day_lookup : active_days)
                                                {
                                                    int lookup_start = -1;
                                                    int lookup_end   = -1;
                                                    /*
                                                     * Check if current classroom and day is already observed for lookup_start
                                                     * */
                                                    if(repair_properties.absent[classroom_lookup][day_lookup])
                                                    {
                                                        try
                                                        {
                                                            /*
                                                             * Check if current classroom and the day after is already observed for lookup_end
                                                             * */
                                                            if(repair_properties.absent[classroom_lookup][day_lookup + 1])
                                                            {
                                                                lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                                lookup_end = repair_properties.index[classroom_lookup][day_lookup + 1];
                                                            }
                                                        }
                                                        catch(ArrayIndexOutOfBoundsException ignored)
                                                        {
                                                            try
                                                            {
                                                                /*
                                                                 * Check if current day and the classroom after is already observed for lookup_end
                                                                 * */
                                                                if(repair_properties.absent[classroom_lookup + 1][0])
                                                                {
                                                                    lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                                    lookup_end = repair_properties.index[classroom_lookup + 1][0];
                                                                }
                                                            }
                                                            /*
                                                             * Assign lookup_end in the end of lesson_id list
                                                             * */
                                                            catch(ArrayIndexOutOfBoundsException ignored1)
                                                            {
                                                                lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                                lookup_end = lesson_id.length;
                                                            }
                                                        }
                                                    }

                                                    /*
                                                    * Check if lookup bound is discovered
                                                    * */
                                                    if(lookup_end != -1)
                                                    {
                                                        /*
                                                        * Try to replace lesson between specified bound
                                                        * */
                                                        if(this.exchangeAndReplace(lookup_start, lookup_end, need, remain, classroom, lesson_id, lesson_counter, lesson_pool.classroom_available_time[classroom_lookup][day_lookup]))
                                                        {
                                                            change = true;
                                                            /*
                                                            * Shift all cluster index
                                                            * */
                                                            do
                                                            {
                                                                try
                                                                {
                                                                    --repair_properties.index[classroom_lookup][++day_lookup];
                                                                }
                                                                catch(ArrayIndexOutOfBoundsException ignored)
                                                                {
                                                                    --repair_properties.index[++classroom_lookup][day_lookup = 0];
                                                                }
                                                            }
                                                            while((classroom_lookup != classroom) || (day_lookup != day));

                                                            /*
                                                            * Read previous lesson as current lesson
                                                            * */
                                                            lesson = this.lessons[lesson_id[--lesson_counter]];
                                                            break lookup_replacement;
                                                        }
                                                    }
                                                }
                                            }

                                            /*
                                             * If function above fail, generate new schedule.
                                             * */
                                            if(!change)
                                            {
                                                this.random(data.velocity_properties.rand_properties, data.velocity_properties.prand, pool_index);
                                                Position.replace(data.data.positions[pool_index], data.velocity_properties.prand[pool_index]);
                                                continue pool_lookup;
                                            }
                                        }
                                        /*
                                        * If swap lesson successful read current lesson again
                                        * */
                                        else
                                        {
                                            lesson = this.lessons[lesson_id[lesson_counter]];
                                        }
                                    }
                                    /*
                                    * If swap lesson successful read current lesson again
                                    * */
                                    else
                                    {
                                        lesson = this.lessons[lesson_id[lesson_counter]];
                                    }
                                }
                            }
                            else
                            {
                                final int need   = time[time_index] - current_sks;
                                boolean   change = false;
                                /*
                                 * Find lesson which its sks less or equals than remaining sks [search forward]
                                 * */
                                for(int lookup_index = lesson_counter, lookup_size = lesson_id.length; ++lookup_index < lookup_size; )
                                {
                                    final Lesson les_temp = this.lessons[lesson_id[lookup_index]];
                                    if((les_temp.sks <= need) && ((les_temp.allowed_classroom[classroom]) || (les_temp.subject == -1)))
                                    {
                                        change = true;
                                        final int swap_temp = lesson_id[lookup_index];
                                        lesson_id[lookup_index] = lesson_id[lesson_counter];
                                        lesson_id[lesson_counter] = swap_temp;
                                        break;
                                    }
                                }

                                /*
                                 * If function above fail find lesson that equal current misplaced lesson
                                 * */
                                if(!change)
                                {
                                    change = false;
                                    /*
                                     * Lookup all its lesson available classroom
                                     * */
                                    lookup_replacement:
                                    for(int classroom_lookup : lesson.available_classroom)
                                    {
                                        /*
                                        * Lookup operational day of current classroom
                                        * */
                                        for(int day_lookup : active_days)
                                        {
                                            int lookup_start = -1;
                                            int lookup_end   = -1;
                                            /*
                                             * Check if current classroom and day is already observed for lookup_start
                                             * */
                                            if(repair_properties.absent[classroom_lookup][day_lookup])
                                            {
                                                try
                                                {
                                                    /*
                                                     * Check if current classroom and the day after is already observed for lookup_end
                                                     * */
                                                    if(repair_properties.absent[classroom_lookup][day_lookup + 1])
                                                    {
                                                        lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                        lookup_end = repair_properties.index[classroom_lookup][day_lookup + 1];
                                                    }
                                                }
                                                catch(ArrayIndexOutOfBoundsException ignored)
                                                {
                                                    try
                                                    {
                                                        /*
                                                         * Check if current day and the classroom after is already observed for lookup_end
                                                         * */
                                                        if(repair_properties.absent[classroom_lookup + 1][0])
                                                        {
                                                            lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                            lookup_end = repair_properties.index[classroom_lookup + 1][0];
                                                        }
                                                    }
                                                    /*
                                                     * Assign lookup_end in the end of lesson_id list
                                                     * */
                                                    catch(ArrayIndexOutOfBoundsException ignored1)
                                                    {
                                                        lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                        lookup_end = lesson_id.length;
                                                    }
                                                }
                                            }

                                            /*
                                            * Check if lookup bound is discovered
                                            * */
                                            if(lookup_end != -1)
                                            {
                                                while(++lookup_start < lookup_end)
                                                {
                                                    final Lesson lesson_lookup = this.lessons[lesson_id[lookup_start]];
                                                    if((lesson_lookup.sks == lesson.sks) && ((lesson_lookup.allowed_classroom[classroom]) || (lesson_lookup.subject == -1)))
                                                    {
                                                        change = true;
                                                        final int swap_temp = lesson_id[lookup_start];
                                                        lesson_id[lookup_start] = lesson_id[lesson_counter];
                                                        lesson_id[lesson_counter] = swap_temp;
                                                        break lookup_replacement;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /*
                                     * If function above fail, find lesson in all possible classrooms [search from its available classroom that already observed]
                                     * */
                                    if(!change)
                                    {
                                        change = false;
                                        /*
                                         * Lookup all its lesson available classroom
                                         * */
                                        lookup_replacement:
                                        for(int classroom_lookup : lesson.available_classroom)
                                        {
                                            /*
                                            * Lookup operational day of current classroom
                                            * */
                                            for(int day_lookup : active_days)
                                            {
                                                int lookup_start = -1;
                                                int lookup_end   = -1;
                                                /*
                                                 * Check if current classroom and day is already observed for lookup_start
                                                 * */
                                                if(repair_properties.absent[classroom_lookup][day_lookup])
                                                {
                                                    try
                                                    {
                                                        /*
                                                         * Check if current classroom and the day after is already observed for lookup_end
                                                         * */
                                                        if(repair_properties.absent[classroom_lookup][day_lookup + 1])
                                                        {
                                                            lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                            lookup_end = repair_properties.index[classroom_lookup][day_lookup + 1];
                                                        }
                                                    }
                                                    catch(ArrayIndexOutOfBoundsException ignored)
                                                    {
                                                        try
                                                        {
                                                            /*
                                                             * Check if current day and the classroom after is already observed for lookup_end
                                                             * */
                                                            if(repair_properties.absent[classroom_lookup + 1][0])
                                                            {
                                                                lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                                lookup_end = repair_properties.index[classroom_lookup + 1][0];
                                                            }
                                                        }
                                                        /*
                                                         * Assign lookup_end in the end of lesson_id list
                                                         * */
                                                        catch(ArrayIndexOutOfBoundsException ignored1)
                                                        {
                                                            lookup_start = repair_properties.index[classroom_lookup][day_lookup] - 1;
                                                            lookup_end = lesson_id.length;
                                                        }
                                                    }
                                                }

                                                /*
                                                * Check if lookup bound is discovered
                                                * */
                                                if(lookup_end != -1)
                                                {
                                                    int remain = lesson.sks - need;
                                                    /*
                                                    * Try to find replacement
                                                    * */
                                                    if(this.exchangeAndReplace(lookup_start, lookup_end, need, remain, classroom, lesson_id, lesson_counter, lesson_pool.classroom_available_time[classroom_lookup][day_lookup]))
                                                    {
                                                        change = true;
                                                        /*
                                                        * Shift all cluster index
                                                        * */
                                                        do
                                                        {
                                                            try
                                                            {
                                                                --repair_properties.index[classroom_lookup][++day_lookup];
                                                            }
                                                            catch(ArrayIndexOutOfBoundsException ignored)
                                                            {
                                                                --repair_properties.index[++classroom_lookup][day_lookup = 0];
                                                            }
                                                        }
                                                        while((classroom_lookup != classroom) || (day_lookup != day));

                                                        /*
                                                        * Read previous lesson as current lesson
                                                        * */
                                                        lesson = this.lessons[lesson_id[--lesson_counter]];
                                                        break lookup_replacement;
                                                    }
                                                }
                                            }
                                        }

                                        /*
                                         * If function above fail, generate random schedule.
                                         * */
                                        if(!change)
                                        {
                                            this.random(data.velocity_properties.rand_properties, data.velocity_properties.prand, pool_index);
                                            Position.replace(data.data.positions[pool_index], data.velocity_properties.prand[pool_index]);
                                            continue pool_lookup;
                                        }
                                    }
                                    /*
                                    * If swap lesson successful read current lesson again
                                    * */
                                    else
                                    {
                                        lesson = this.lessons[lesson_id[lesson_counter]];
                                    }
                                }
                                /*
                                * If swap lesson successful read current lesson again
                                * */
                                else
                                {
                                    lesson = this.lessons[lesson_id[lesson_counter]];
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception ignored)
            {
                this.random(data.velocity_properties.rand_properties, data.velocity_properties.prand, pool_index);
                Position.replace(data.data.positions[pool_index], data.velocity_properties.prand[pool_index]);
            }
        }

        for(RepairProperties properties : data.repair_properties)
        {
            properties.resetAbsent();
        }
    }

    private boolean exchangeAndReplace(int lookup_start, int lookup_end, int need, int remain, int current_classroom, int[] lesson_id, int lesson_counter, int[] time) throws Exception
    {
        boolean found            = false;
        int     need_index       = -1;
        int     need_cluster     = -1;
        int     overflow_index   = -1;
        int     overflow_cluster = -1;
        int     time_index       = 1;
        int     current_sks      = 0;

        /*
         * Find lesson that equals number of sks need and allowed each other if replace happened.
         * */
        for(int lesson_index = lookup_start; ++lesson_index < lookup_end; )
        {
            final Lesson lesson_need = this.lessons[lesson_id[lesson_index]];
            current_sks += lesson_need.sks;
            if(lesson_need.sks == need && (lesson_need.allowed_classroom[current_classroom] || (lesson_need.subject == -1)))
            {
                need_index = lesson_index;
                need_cluster = time_index;
                found = true;
                break;
            }
            if(current_sks == time[time_index])
            {
                ++time_index;
                current_sks = 0;
            }
        }

        /*
         * Find lesson that equals number of sks overflow and allowed each other if replace happened.
         * */
        if(found)
        {
            found = false;
            current_sks = 0;
            time_index = 1;
            for(int lesson_index = lookup_start; ++lesson_index < lookup_end; )
            {
                final Lesson lesson_overflow = this.lessons[lesson_id[lesson_index]];
                current_sks += lesson_overflow.sks;
                if((lesson_overflow.sks == remain) && (lesson_index != need_index) && (lesson_overflow.allowed_classroom[current_classroom] || (lesson_overflow.subject == -1)))
                {
                    overflow_index = lesson_index;
                    overflow_cluster = time_index;
                    found = true;
                    break;
                }
                if(current_sks == time[time_index])
                {
                    ++time_index;
                    current_sks = 0;
                }
            }
        }

        /*
         * check if lesson need and lesson overflow has been found
         * */
        if(found)
        {
            /*
             * check if both of lesson in same cluster
             * */
            if(need_cluster == overflow_cluster)
            {
                /*
                 * check lesson need next to lesson overflow
                 * */
                if(Math.abs(overflow_index - need_index) == 1)
                {
                    /*
                     * Arrange lesson position so need index < overflow index
                     * xonxxx -> xnoxxx
                     * */
                    if(need_index > overflow_index)
                    {
                        int index_temp = lesson_id[need_index];
                        lesson_id[need_index] = lesson_id[overflow_index];
                        lesson_id[overflow_index] = index_temp;

                        index_temp = overflow_index;
                        overflow_index = need_index;
                        need_index = index_temp;
                    }
                }
                else
                {
                    /*
                     * Arrange lesson position so need index < overflow index
                     * xoxnxxx -> xnoxxxx
                     * */
                    if(need_index > overflow_index)
                    {
                        final int index_temp = lesson_id[need_index];
                        lesson_id[need_index] = lesson_id[overflow_index + 1];
                        lesson_id[overflow_index + 1] = lesson_id[overflow_index];
                        lesson_id[overflow_index] = index_temp;

                        need_index = overflow_index;
                        overflow_index = need_index + 1;
                    }
                    /*
                     * Arrange lesson position so need index < overflow index
                     * xnxoxxx -> xnoxxxx
                     * */
                    else
                    {
                        final int index_temp = lesson_id[need_index + 1];
                        lesson_id[need_index + 1] = lesson_id[overflow_index];
                        lesson_id[overflow_index] = index_temp;

                        overflow_index = need_index + 1;
                    }
                }

                /*
                 * Begin to exchange and shift position
                 * */
                final int need_value     = lesson_id[need_index];
                final int overflow_value = lesson_id[overflow_index];

                System.arraycopy(lesson_id, overflow_index + 1, lesson_id, overflow_index, Math.abs(lesson_counter - overflow_index));

                lesson_id[need_index] = lesson_id[lesson_counter];
                lesson_id[lesson_counter - 1] = need_value;
                lesson_id[lesson_counter] = overflow_value;
                return true;
            }
            /*
             * lesson need and lesson overflow on different cluster time
             * xnx|xxx|oxx -> nox|xxx|xxx
             * */
            else
            {
                return this.simulateExchange(lookup_start, lookup_end, need_index, overflow_index, lesson_counter, time, lesson_id);
            }
        }

        /*
        * Fail to find lesson need and lesson overflow candidate
        * */
        return false;
    }

    private boolean simulateExchange(int lookup_start, int lookup_end, int need_index, int overflow_index, int lesson_counter, int[] time, int[] lesson_id) throws Exception
    {
        int          last_index_sks       = 0;
        int          cumulative_need_size = this.lessons[lesson_id[need_index]].sks + this.lessons[lesson_id[overflow_index]].sks;
        IntArrayList container            = new IntArrayList(lookup_end - lookup_start - 1);
        IntArrayList fuel                 = new IntArrayList(lookup_end - lookup_start - 2);

        /*
        * Fill fuel with lesson id in one day sort by its sks size descending
        * */
        lesson_lookup:
        for(int lesson_index = lookup_start; ++lesson_index < lookup_end; )
        {
            if((lesson_index != need_index) && (lesson_index != overflow_index))
            {
                int lesson_sks = this.lessons[lesson_id[lesson_index]].sks;
                for(int fuel_index = -1, fuel_size = fuel.size(); ++fuel_index < fuel_size; )
                {
                    if(lesson_sks == last_index_sks)
                    {
                        fuel.add(lesson_index);
                        continue lesson_lookup;
                    }
                    if(lesson_sks > this.lessons[lesson_id[fuel.getInt(fuel_index)]].sks)
                    {
                        fuel.add(fuel_index, lesson_index);
                        continue lesson_lookup;
                    }
                }
                fuel.add(lesson_index);
                last_index_sks = lesson_sks;
            }
        }
        /*
        * Fill lesson need and overflow wrapped by -1 lesson
        * */
        for(int fuel_index = -1, fuel_size = fuel.size(); ++fuel_index < fuel_size; )
        {
            if(cumulative_need_size >= this.lessons[lesson_id[fuel.getInt(fuel_index)]].sks)
            {
                fuel.add(fuel_index, -1);
                break;
            }
        }

        /*
        * Fill container which fit each cluster
        * */
        int time_index  = 1;
        int current_sks = 0;
        int old_fuel_size;
        do
        {
            old_fuel_size = fuel.size();
            for(int fuel_index = -1, fuel_size = fuel.size(); ++fuel_index < fuel_size; )
            {
                final int fuel_lesson = fuel.getInt(fuel_index);
                int       temp_sks;
                /*
                * fill incoming sks size;
                * */
                try
                {
                    temp_sks = this.lessons[lesson_id[fuel_lesson]].sks;
                }
                catch(ArrayIndexOutOfBoundsException ignored)
                {
                    temp_sks = cumulative_need_size;
                }

                /*
                * Check incoming sks fit current cluster time
                * */
                if(temp_sks + current_sks <= time[time_index])
                {
                    if(fuel_lesson == -1)
                    {
                        /*
                        * Add Lesson need and overflow
                        * */
                        container.add(need_index);
                        container.add(overflow_index);

                        /*
                        * Change need index and overflow index
                        * */
                        need_index = container.size() - 1 + lookup_start;
                        overflow_index = need_index + 1;
                    }
                    else
                    {
                        /*
                        * Add ordinary lesson
                        * */
                        container.add(fuel_lesson);
                    }

                    /*
                    * Shift lesson cluster time
                    * */
                    if(temp_sks + current_sks == time[time_index])
                    {
                        ++time_index;
                        current_sks = 0;
                    }

                    /*
                    * Remove Fuel
                    * */
                    fuel.removeInt(fuel_index);

                    /*
                    * Skip remaining search
                    * */
                    break;
                }
            }
        }
        while(old_fuel_size != fuel.size());

        /*
        * Check if arrangement fits all lesson cluster
        * */
        if(fuel.size() == 0)
        {
            /*
            * Convert container data with lesson id according to data
            * */
            for(int container_index = -1, container_size = container.size(); ++container_index < container_size; )
            {
                container.set(container_index, lesson_id[container.getInt(container_index)]);
            }

            /*
            * Arrange lesson id according container arrangement
            * */
            for(int lesson_id_temp : container)
            {
                lesson_id[++lookup_start] = lesson_id_temp;
            }

            /*
             * Begin to exchange and shift position
             * */
            final int need_value     = lesson_id[need_index];
            final int overflow_value = lesson_id[overflow_index];

            System.arraycopy(lesson_id, overflow_index + 1, lesson_id, overflow_index, Math.abs(lesson_counter - overflow_index));

            lesson_id[need_index] = lesson_id[lesson_counter];
            lesson_id[lesson_counter - 1] = need_value;
            lesson_id[lesson_counter] = overflow_value;
            return true;
        }
        else
        {
            /*
            * Fail to arrange lesson id
            * */
            return false;
        }
    }

    @Override
    public void updateStoppingCondition()
    {
        ++super.cEpoch;
    }

    @Override
    public boolean isConditionSatisfied()
    {
        return super.cEpoch == setting.max_epoch;
        /*
        * For Test Lesson Conflict
        * */
        /*if(super.cEpoch == setting.max_epochs)
        {
            for(final IntHList list : super.particles[0].lesson_conflicts)
            {
                System.out.println(list.toString());
            }
            System.out.println();
            return true;
        }
        else
        {
            return false;
        }*/
    }

    @Override
    public void initializeSwarm()
    {
        int classroom = 0;
        for(LessonPoolSet pool : this.lesson_pool)
        {
            classroom += pool.classrooms.length;
        }

        for(int particle_number = -1, particle_size = this.setting.max_particle; ++particle_number < particle_size; )
        {
            RepairProperties[] repair_properties = new RepairProperties[this.lesson_pool.length];
            for(int pool_index = -1, pool_size = this.lesson_pool.length; ++pool_index < pool_size; )
            {
                repair_properties[pool_index] = new RepairProperties(this.lesson_pool[pool_index].classrooms.length, this.active_days.length);
            }
            PlacementProperties placement_properties = new PlacementProperties(this.lecturers.length, this.classes.length, this.classrooms.length, this.active_days.length, this.active_periods.length, classroom);
            this.particles[particle_number] = new ParticleP2(this.shuffling_properties, PSOP2.this, this.setting, placement_properties, repair_properties);
        }
    }

    @Override
    public Position[] random(final ScheduleShufflingProperties properties)
    {
        final Position[] random_schedule = new Position[this.lesson_pool.length];
        for(int position_number = -1, position_size = this.lesson_pool.length; ++position_number < position_size; )
        {
            random_schedule[position_number] = Position.newInstance(this.lesson_pool[position_number].lessons.length + this.lesson_pool[position_number].lesson_null.length);
        }
        this.random(properties, random_schedule);
        return random_schedule;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void random(final ScheduleShufflingProperties properties, final Position[] random_schedule)
    {
        final int[]           day_set         = properties.day_set;
        final int[][][]       classrooms_set  = properties.classrooms_set;
        final int[][][]       lessons_set     = properties.lessons_set;
        final LessonPoolSet[] lesson_pool_set = this.lesson_pool;
        final int             period_length   = this.active_periods.length;
        final Lesson[]        lesson_dataset  = this.lessons;
        properties.reset_classroom_current_time();

        for(int counter_lesson_pool = -1, lesson_pool_size = lesson_pool_set.length; ++counter_lesson_pool < lesson_pool_size; )
        {
            final LessonPoolSet         lesson_pool                  = lesson_pool_set[counter_lesson_pool];
            final int                   cumulative_lesson_length     = this.getCumulativeLessonLength(lesson_pool);
            final int                   lesson_window                = this.getLessonWindow(cumulative_lesson_length, lesson_pool.classrooms.length);
            final int[][][]             classroom_available_time     = lesson_pool.classroom_available_time;
            final int[][][]             classroom_available_property = properties.classroom_current_time[counter_lesson_pool];
            final ScheduleContainer[][] temp_container               = new ScheduleContainer[lesson_pool.classrooms.length][day_set.length];

            /*
             * Instantiate Schedule Container with period length
             * */
            for(int counter_classroom = -1, classroom_size = temp_container.length; ++counter_classroom < classroom_size; )
            {
                for(int counter_day = -1, day_size = temp_container[counter_classroom].length; ++counter_day < day_size; )
                {
                    temp_container[counter_classroom][counter_day] = new ScheduleContainer(period_length);
                }
            }

            /*
             * Calculate lesson for each lesson set within lesson pool
             * */
            for(int counter_lesson_set = -1, lesson_set_size = lesson_pool.merge.length; ++counter_lesson_set < lesson_set_size; )
            {
                final int[] lesson_temp            = lessons_set[counter_lesson_pool][counter_lesson_set];
                final int[] classroom_temp         = classrooms_set[counter_lesson_pool][counter_lesson_set];
                final int[] local_sks_distribution = IntArrays.copy(lesson_pool.merge[counter_lesson_set].local_sks_distribution);

                IntArrays.shuffle(lesson_temp, ThreadLocalRandom.current());
                IntArrays.shuffle(classroom_temp, ThreadLocalRandom.current());

                int counter_lesson = -1;
                int lesson         = lesson_temp[++counter_lesson];
                int classroom_current_size;
                int sks;

                /*
                 * Fill Lesson inside Scheduler Container
                 * */
                gate_1:
                for(int classroom : classroom_temp)
                {
                    IntArrays.shuffle(day_set, ThreadLocalRandom.current());
                    for(int day : day_set)
                    {
                        int[] property            = classroom_available_property[classroom][day];
                        int[] classroom_available = classroom_available_time[classroom][day];
                        int   global_availability = classroom_available[0];

                        property[1] += classroom_available[++property[0]];
                        while((classroom_current_size = temp_container[classroom][day].getSizeSKS()) < lesson_window)
                        {
                            sks = lesson_dataset[lesson].sks;
                            int future_size = classroom_current_size + sks;
                            if(future_size <= global_availability)
                            {
                                if(future_size < property[1])
                                {
                                    temp_container[classroom][day].addSchedule(lesson, sks);
                                    --local_sks_distribution[sks];
                                    try
                                    {
                                        lesson = lesson_temp[++counter_lesson];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        property[1] -= classroom_available[property[0]--];
                                        break gate_1;
                                    }
                                }
                                else if(future_size == property[1])
                                {
                                    temp_container[classroom][day].addSchedule(lesson, sks);
                                    --local_sks_distribution[sks];
                                    try
                                    {
                                        property[1] += classroom_available[++property[0]];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        --property[0];
                                    }

                                    try
                                    {
                                        lesson = lesson_temp[++counter_lesson];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        property[1] -= classroom_available[property[0]--];
                                        break gate_1;
                                    }
                                }
                                else
                                {
                                    int remaining_size = property[1] - classroom_current_size;

                                    if(local_sks_distribution[remaining_size] > 0)
                                    {
                                        int counter = counter_lesson + 1;
                                        for(; lesson_dataset[lesson_temp[counter]].sks != remaining_size; )
                                        {
                                            ++counter;
                                        }
                                        final int value_temp = lesson_temp[counter_lesson];
                                        lesson_temp[counter_lesson] = lesson_temp[counter];
                                        lesson_temp[counter] = value_temp;

                                        sks = remaining_size;
                                        lesson = lesson_temp[counter_lesson];
                                        temp_container[classroom][day].addSchedule(lesson, sks);
                                        --local_sks_distribution[sks];

                                        try
                                        {
                                            property[1] += classroom_available[++property[0]];
                                        }
                                        catch(ArrayIndexOutOfBoundsException ignored)
                                        {
                                            --property[0];
                                        }

                                        try
                                        {
                                            lesson = lesson_temp[++counter_lesson];
                                        }
                                        catch(ArrayIndexOutOfBoundsException ignored)
                                        {
                                            property[1] -= classroom_available[property[0]--];
                                            break gate_1;
                                        }
                                    }
                                    else
                                    {
                                        /*
                                        * Need to Change
                                        * */
                                        while(remaining_size-- != 0)
                                        {
                                            temp_container[classroom][day].addSchedule(0, 1);
                                        }
                                        try
                                        {
                                            property[1] += classroom_available[++property[0]];
                                        }
                                        catch(ArrayIndexOutOfBoundsException ignored)
                                        {
                                            --property[0];
                                        }
                                    }
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                        property[1] -= classroom_available[property[0]--];
                    }
                }
            }

            /*
             * Fill remaining schedule container with null lesson
             * */
            IntArrayList full_schedule = new IntArrayList(lesson_pool.lessons.length + lesson_pool.lesson_null.length + lesson_pool.lesson_null.length);
            for(int counter_classroom = -1, classroom_size = temp_container.length; ++counter_classroom < classroom_size; )
            {
                for(int counter_day = -1, day_size = temp_container[counter_classroom].length; ++counter_day < day_size; )
                {
                    full_schedule.addAll((temp_container[counter_classroom][counter_day]));
                    for(int counter_null = -1, null_size = classroom_available_time[counter_classroom][counter_day][0] - temp_container[counter_classroom][counter_day].getSizeSKS(); ++counter_null < null_size; )
                    {
                        full_schedule.add(0);
                    }
                }
            }
            try
            {
                System.arraycopy(full_schedule.toIntArray(), 0, random_schedule[counter_lesson_pool].position, 0, full_schedule.size());
            }
            catch(Exception ignored)
            {
                this.random(properties, random_schedule, counter_lesson_pool);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void random(final ScheduleShufflingProperties properties, final Position[] random_schedule, final int pool_index)
    {
        properties.reset_classroom_current_time(pool_index);
        final LessonPoolSet         lesson_pool                  = this.lesson_pool[pool_index];
        final int                   period_length                = this.active_periods.length;
        final int                   cumulative_lesson_length     = this.getCumulativeLessonLength(lesson_pool);
        final int                   lesson_window                = this.getLessonWindow(cumulative_lesson_length, lesson_pool.classrooms.length);
        final int[]                 day_set                      = properties.day_set;
        final int[][][]             classroom_available_time     = lesson_pool.classroom_available_time;
        final int[][][]             classroom_available_property = properties.classroom_current_time[pool_index];
        final Lesson[]              lesson_dataset               = this.lessons;
        final ScheduleContainer[][] temp_container               = new ScheduleContainer[lesson_pool.classrooms.length][day_set.length];
        /*
         * Instantiate Schedule Container with period length
         * */
        for(int counter_classroom = -1, classroom_size = temp_container.length; ++counter_classroom < classroom_size; )
        {
            for(int counter_day = -1, day_size = temp_container[counter_classroom].length; ++counter_day < day_size; )
            {
                temp_container[counter_classroom][counter_day] = new ScheduleContainer(period_length);
            }
        }

        /*
         * Calculate lesson for each lesson set within lesson pool
         * */
        for(int counter_lesson_set = -1, lesson_set_size = lesson_pool.merge.length; ++counter_lesson_set < lesson_set_size; )
        {
            final int[] lesson_temp            = properties.lessons_set[pool_index][counter_lesson_set];
            final int[] classroom_temp         = properties.classrooms_set[pool_index][counter_lesson_set];
            final int[] local_sks_distribution = IntArrays.copy(lesson_pool.merge[counter_lesson_set].local_sks_distribution);

            IntArrays.shuffle(lesson_temp, ThreadLocalRandom.current());
            IntArrays.shuffle(classroom_temp, ThreadLocalRandom.current());

            int counter_lesson = -1;
            int lesson         = lesson_temp[++counter_lesson];
            int classroom_current_size;
            int sks;

            /*
             * Fill Lesson inside Scheduler Container
             * */
            gate_1:
            for(int classroom : classroom_temp)
            {
                IntArrays.shuffle(day_set, ThreadLocalRandom.current());
                for(int day : day_set)
                {
                    int[] property            = classroom_available_property[classroom][day];
                    int[] classroom_available = classroom_available_time[classroom][day];
                    int   global_availability = classroom_available[0];

                    property[1] += classroom_available[++property[0]];
                    while((classroom_current_size = temp_container[classroom][day].getSizeSKS()) < lesson_window)
                    {
                        sks = lesson_dataset[lesson].sks;
                        int future_size = classroom_current_size + sks;
                        if(future_size <= global_availability)
                        {
                            if(future_size < property[1])
                            {
                                temp_container[classroom][day].addSchedule(lesson, sks);
                                --local_sks_distribution[sks];
                                try
                                {
                                    lesson = lesson_temp[++counter_lesson];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                    property[1] -= classroom_available[property[0]--];
                                    break gate_1;
                                }
                            }
                            else if(future_size == property[1])
                            {
                                temp_container[classroom][day].addSchedule(lesson, sks);
                                --local_sks_distribution[sks];
                                try
                                {
                                    property[1] += classroom_available[++property[0]];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                    --property[0];
                                }

                                try
                                {
                                    lesson = lesson_temp[++counter_lesson];
                                }
                                catch(ArrayIndexOutOfBoundsException ignored)
                                {
                                    property[1] -= classroom_available[property[0]--];
                                    break gate_1;
                                }
                            }
                            else
                            {
                                int remaining_size = property[1] - classroom_current_size;

                                if(local_sks_distribution[remaining_size] > 0)
                                {
                                    int counter = counter_lesson + 1;
                                    for(; lesson_dataset[lesson_temp[counter]].sks != remaining_size; )
                                    {
                                        ++counter;
                                    }
                                    final int value_temp = lesson_temp[counter_lesson];
                                    lesson_temp[counter_lesson] = lesson_temp[counter];
                                    lesson_temp[counter] = value_temp;

                                    sks = remaining_size;
                                    lesson = lesson_temp[counter_lesson];
                                    temp_container[classroom][day].addSchedule(lesson, sks);
                                    --local_sks_distribution[sks];

                                    try
                                    {
                                        property[1] += classroom_available[++property[0]];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        --property[0];
                                    }

                                    try
                                    {
                                        lesson = lesson_temp[++counter_lesson];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        property[1] -= classroom_available[property[0]--];
                                        break gate_1;
                                    }
                                }
                                else
                                {
                                    /*
                                    * Need to Change
                                    * */
                                    while(remaining_size-- != 0)
                                    {
                                        temp_container[classroom][day].addSchedule(0, 1);
                                    }
                                    try
                                    {
                                        property[1] += classroom_available[++property[0]];
                                    }
                                    catch(ArrayIndexOutOfBoundsException ignored)
                                    {
                                        --property[0];
                                    }
                                }
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    property[1] -= classroom_available[property[0]--];
                }
            }
        }

        /*
         * Fill remaining schedule container with null lesson
         * */
        IntArrayList full_schedule = new IntArrayList(lesson_pool.lessons.length + lesson_pool.lesson_null.length + lesson_pool.lesson_null.length);
        for(int counter_classroom = -1, classroom_size = temp_container.length; ++counter_classroom < classroom_size; )
        {
            for(int counter_day = -1, day_size = temp_container[counter_classroom].length; ++counter_day < day_size; )
            {
                full_schedule.addAll((temp_container[counter_classroom][counter_day]));
                for(int counter_null = -1, null_size = classroom_available_time[counter_classroom][counter_day][0] - temp_container[counter_classroom][counter_day].getSizeSKS(); ++counter_null < null_size; )
                {
                    full_schedule.add(0);
                }
            }
        }
        try
        {
            System.arraycopy(full_schedule.toIntArray(), 0, random_schedule[pool_index].position, 0, full_schedule.size());
        }
        catch(ArrayIndexOutOfBoundsException ignored)
        {
            this.random(properties, random_schedule, pool_index);
        }
    }

    private int getCumulativeLessonLength(final LessonPoolSet lesson_pool)
    {
        int cumulative_lesson_length = 0;
        for(int counter_lesson : lesson_pool.lessons)
        {
            cumulative_lesson_length += this.lessons[counter_lesson].sks;
        }
        return cumulative_lesson_length;
    }

    private int getLessonWindow(final int total, final int classroom_length)
    {
        return (int) Math.ceil(total * 1f / classroom_length / this.active_days.length);
    }
}
