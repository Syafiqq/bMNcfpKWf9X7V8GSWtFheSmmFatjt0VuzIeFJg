package model.dataset;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import model.custom.it.unimi.dsi.fastutil.ints.ScheduleContainer;
import model.database.DBComponent;
import model.dataset.component.Lesson;
import model.dataset.component.ScheduleShufflingProperties;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetBuilder1;
import model.dataset.core.DatasetConverter;
import model.dataset.core.LessonGroupSet;
import model.dataset.core.LessonPoolSet;
import model.dataset.core.WorkingSet;

/*
 * This <Skripsi_003> project in package <model.dataset.test> created by :
 * Name         : syafiq
 * Date / Time  : 16 May 2016, 7:34 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2Generator3 extends DatasetBuilder1<Dataset2<Timeoff, Lesson>, WorkingSet, DatasetConverter<Int2IntLinkedOpenHashMap>>
{
    public DatasetP2Generator3(Dataset2<Timeoff, Lesson> dataset, WorkingSet workingset, DatasetConverter<Int2IntLinkedOpenHashMap> encoder, DatasetConverter<Int2IntLinkedOpenHashMap> decoder)
    {
        super(workingset, dataset, encoder, decoder);
    }

    @Override public void generateDataset()
    {
        Runtime runtime = Runtime.getRuntime();
        runtime.runFinalization();
        runtime.gc();
        this.activateDatabase();
        this.generateActiveDays();
        this.generateActivePeriods();
        this.generateClasses();
        this.generateClassrooms();
        this.generateLecturers();
        this.generateSubjects();
        this.generateLessons();
        this.generateSksDistribution();
        this.generateLessonGroupSet();
        this.generateLessonPool();
        this.updateLessonAvailableClassroom();
        this.deactivateDatabase();
        runtime.runFinalization();
        runtime.gc();
    }

    private void updateLessonAvailableClassroom()
    {
        int[] globalConverter = new int[this.dataset.classrooms.length];
        for(LessonPoolSet pool : this.workingset.lesson_pool)
        {
            for(Int2IntMap.Entry pair : pool.clustered_classroom_encoder.int2IntEntrySet())
            {
                globalConverter[pair.getIntKey()] = pair.getIntValue();
            }
        }
        for(Lesson lesson : this.dataset.lessons)
        {
            lesson.allowed_classroom = new boolean[this.dataset.classrooms.length];
            for(int counter_available_classroom = -1, available_classroom_size = lesson.available_classroom.length; ++counter_available_classroom < available_classroom_size; )
            {
                lesson.available_classroom[counter_available_classroom] = globalConverter[lesson.available_classroom[counter_available_classroom]];
                lesson.allowed_classroom[lesson.available_classroom[counter_available_classroom]] = true;
            }
        }
    }

    @Override protected void generateLessonGroupSet()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;
            int               table_size   = 0;

            /*
             * Expand Group Concat Stream Size
             * */
            String query = "SET SESSION group_concat_max_len = 1000000";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.executeQuery();

            /*
             * Get Group Lesson and Group Classroom
             * */
            query = "SELECT @rownum := @rownum + 1 AS 'row_counter', `classrooms`, `lessons` FROM (SELECT `available_classrooms` AS 'classrooms', GROUP_CONCAT(`id` ORDER BY `id` SEPARATOR ' ') AS 'lessons' FROM(SELECT `lesson`.`id`, GROUP_CONCAT(`lesson_available_classroom`.`classroom` ORDER BY `lesson_available_classroom`.`classroom` SEPARATOR ' ') AS 'available_classrooms', COUNT(`lesson_available_classroom`.`classroom`) AS 'classroom_count' FROM `lesson` LEFT OUTER JOIN `lesson_available_classroom` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` LEFT OUTER JOIN `subject` ON `subject`.`id` = `lesson`.`subject` WHERE `subject`.`school` = ? GROUP BY `lesson`.`id` ORDER BY COUNT(`lesson_available_classroom`.`classroom`) ASC, available_classrooms ASC, `lesson`.`id` ASC) AS `table1` GROUP BY `available_classrooms` ORDER BY `classroom_count` DESC) AS `table1` CROSS JOIN (SELECT @rownum := 0) AS `table2` ORDER BY `row_counter` DESC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            if(db_component.result_set.next())
            {
                table_size = db_component.result_set.getInt("row_counter");
                db_component.result_set.previous();
            }

            super.workingset.lesson_set = new LessonGroupSet[table_size];

            final IntArrayList             lesson_set_classroom_temp    = new IntArrayList(super.dataset.classrooms.length);
            final IntArrayList             lesson_set_lesson_temp       = new IntArrayList(super.dataset.lessons.length);
            final IntArrayList             lesson_set_extra_lesson_temp = new IntArrayList(super.dataset.lessons.length);
            final LessonGroupSet[]         lesson_set_container         = super.workingset.lesson_set;
            final Int2IntLinkedOpenHashMap lesson_encoder               = super.encoder.lessons;
            final Int2IntLinkedOpenHashMap classroom_encoder            = super.encoder.classrooms;
            final Lesson[]                 lessons                      = super.dataset.lessons;
            int                            counter_lesson_set           = -1;

            while(db_component.result_set.next())
            {
                lesson_set_classroom_temp.clear();
                lesson_set_lesson_temp.clear();
                lesson_set_extra_lesson_temp.clear();
                /*
                 * Store Classroom
                 * */
                for(String classroom_temp : db_component.result_set.getString("classrooms").split(" "))
                {
                    lesson_set_classroom_temp.add(classroom_encoder.get(Integer.parseInt(classroom_temp)));
                }

                /*
                 * Store Lesson
                 * */
                for(String lesson_temp : db_component.result_set.getString("lessons").split(" "))
                {
                    int lesson_id = lesson_encoder.get(Integer.parseInt(lesson_temp));
                    lesson_set_lesson_temp.add(lesson_id);

                    /*
                     * Store Extra Lesson
                     * */
                    for(int link : lessons[lesson_id].link)
                    {
                        lesson_set_extra_lesson_temp.add(link);
                    }
                }

                /*
                 * Merge Lesson and Extra Lesson
                 * */
                lesson_set_lesson_temp.addAll(lesson_set_extra_lesson_temp);

                /*
                 * Store Lesson And Classroom in Lesson Set
                 * */
                lesson_set_container[++counter_lesson_set] = new LessonGroupSet();
                final LessonGroupSet lesson_set = lesson_set_container[counter_lesson_set];

                lesson_set.lessons = lesson_set_lesson_temp.toIntArray();
                lesson_set.classrooms = lesson_set_classroom_temp.toIntArray();
                lesson_set.local_sks_distribution = new int[super.dataset.sks_distribution.length];
                for(int lesson : lesson_set.lessons)
                {
                    ++lesson_set.local_sks_distribution[lessons[lesson].sks];
                }
            }


            /*
             * Rollback Group Concat Stream Size
             * */
            query = "SET SESSION group_concat_max_len = 2048";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.executeQuery();
        }
        catch(SQLException ignore)
        {
            System.err.println("Generate Lesson Group Set");
            System.exit(1);
        }
    }

    @Override protected void generateLessonPool()
    {
        final int                                             lesson_set_size                     = super.workingset.lesson_set.length;
        final IntArrayList                                    registered_lesson_group_set         = new IntArrayList(lesson_set_size);
        final IntArrayList                                    registered_lesson_group_set_removal = new IntArrayList(lesson_set_size);
        final ObjectLinkedOpenHashSet<IntLinkedOpenHashSet[]> pool_set                            = new ObjectLinkedOpenHashSet<>(lesson_set_size);

        for(int array_counter = -1; ++array_counter < lesson_set_size; )
        {
            registered_lesson_group_set.add(array_counter);
        }

        final int maximum_size = super.dataset.lessons.length;
        while(!registered_lesson_group_set.isEmpty())
        {
            int                  index      = registered_lesson_group_set.removeInt(0);
            LessonGroupSet       group_set  = super.workingset.lesson_set[index];
            IntLinkedOpenHashSet lessons    = new IntLinkedOpenHashSet(maximum_size);
            IntLinkedOpenHashSet classrooms = new IntLinkedOpenHashSet(maximum_size);
            IntLinkedOpenHashSet merge      = new IntLinkedOpenHashSet(lesson_set_size);

            /*
             * Insert Classroom, Lesson and LessonGroupSet ID
             * */
            merge.add(index);
            for(int classroom_set : group_set.classrooms)
            {
                classrooms.add(classroom_set);
            }

            for(int lesson_set : group_set.lessons)
            {
                lessons.add(lesson_set);
            }

            /*
             * Check whether current lesson group have relation with others
             * */
            gate_1:
            for(int registered_lesson_set : registered_lesson_group_set)
            {
                group_set = super.workingset.lesson_set[registered_lesson_set];

                final int[] group_set_classroom = IntArrays.copy(group_set.classrooms);
                IntArrays.quickSort(group_set_classroom);

                for(int classroom : classrooms)
                {
                    /*
                     * You must ensure that the array must be sorted first;
                     * */
                    if(IntArrays.binarySearch(group_set_classroom, classroom) >= 0)
                    {
                        registered_lesson_group_set_removal.add(registered_lesson_set);

                        /*
                         * Add Classroom, Lesson and LessonGroupSet ID
                         * */
                        merge.add(registered_lesson_set);
                        for(int classroom_set : group_set.classrooms)
                        {
                            classrooms.add(classroom_set);
                        }

                        for(int lesson_set : group_set.lessons)
                        {
                            lessons.add(lesson_set);
                        }
                        continue gate_1;
                    }
                }
            }

            /*
             * Remove Linked Lesson Group
             * */
            while(!registered_lesson_group_set_removal.isEmpty())
            {
                int val = registered_lesson_group_set_removal.removeInt(0);
                for(int counter_registered = -1, registered_size = registered_lesson_group_set.size(); ++counter_registered < registered_size; )
                {
                    if(registered_lesson_group_set.getInt(counter_registered) == val)
                    {
                        registered_lesson_group_set.removeInt(counter_registered);
                        break;
                    }
                }
            }

            /*
             * Add Data To Temporary Lesson Pool
             * */
            pool_set.add(new IntLinkedOpenHashSet[] {merge, classrooms, lessons});
        }

        super.workingset.lesson_pool = new LessonPoolSet[pool_set.size()];

        final LessonPoolSet[] lesson_pool_container = super.workingset.lesson_pool;
        final Lesson[]        lessons               = super.dataset.lessons;
        int                   counter_pool_set      = -1;

        /*
         * Store Temporary Lesson Pool To Real Lesson Pool
         * */
        for(IntLinkedOpenHashSet[] pool : pool_set)
        {
            lesson_pool_container[++counter_pool_set] = new LessonPoolSet();
            final LessonPoolSet lesson_pool = lesson_pool_container[counter_pool_set];

            /*
             * Insert Collaborative Lesson Group Set
             * */
            lesson_pool.merge = new LessonGroupSet[pool[0].size()];
            int         counter_merge = -1;
            final int[] merge_temp    = new int[pool[0].size()];

            for(int merge : pool[0])
            {
                merge_temp[++counter_merge] = merge;
            }

            IntArrays.quickSort(merge_temp);

            counter_merge = -1;
            for(int merge : merge_temp)
            {
                lesson_pool.merge[++counter_merge] = super.workingset.lesson_set[merge];
            }

            /*
             * Insert Classroom Data
             * */
            lesson_pool.classrooms = pool[1].toIntArray();
            IntArrays.quickSort(lesson_pool.classrooms);

            /*
             * Adjust new classroom index with new generated encoder and decoder
             * */
            lesson_pool.clustered_classroom_encoder = new Int2IntLinkedOpenHashMap(pool[1].size());
            lesson_pool.clustered_classroom_decoder = new Int2IntLinkedOpenHashMap(pool[1].size());
            lesson_pool.classroom_timeoff = new Timeoff[pool[1].size()];
            lesson_pool.classroom_available_time = new int[pool[1].size()][super.dataset.active_days.length][];

            final Int2IntLinkedOpenHashMap clustered_classroom_encoder      = lesson_pool.clustered_classroom_encoder;
            final Int2IntLinkedOpenHashMap clustered_classroom_decoder      = lesson_pool.clustered_classroom_decoder;
            final Timeoff[]                classroom_timeoff                = lesson_pool.classroom_timeoff;
            final Timeoff[]                classroom_timeoff_dataset        = super.dataset.classrooms;
            final int[][][]                classroom_available_time         = lesson_pool.classroom_available_time;
            final int[][][]                classroom_available_time_dataset = super.dataset.classroom_available_time;

            for(int counter_classroom1 = -1, classroom1_size = pool[1].size(); ++counter_classroom1 < classroom1_size; )
            {
                final int encode_value = lesson_pool.classrooms[counter_classroom1];
                clustered_classroom_encoder.put(encode_value, counter_classroom1);
                clustered_classroom_decoder.put(counter_classroom1, encode_value);
                classroom_timeoff[counter_classroom1] = classroom_timeoff_dataset[encode_value];
                classroom_available_time[counter_classroom1] = classroom_available_time_dataset[encode_value];
                lesson_pool.classrooms[counter_classroom1] = counter_classroom1;
            }

            /*
             * Insert Lesson Data
             * */
            lesson_pool.lessons = pool[2].toIntArray();
            int filled = 0;
            for(int lesson : lesson_pool.lessons)
            {
                filled += lessons[lesson].sks;
            }
            IntArrays.quickSort(lesson_pool.lessons);

            /*
             * Calculate available working time given classrooms
             * */
            int available = 0;
            for(int counter_classroom1 : lesson_pool.classrooms)
            {
                for(int day : super.dataset.active_days)
                {
                    available += classroom_available_time[counter_classroom1][day][0];
                }
            }

            /*
             * Insert Null Lesson
             * */
            lesson_pool.lesson_null = new int[available - filled];

            /*
             * Adjust lesson group set classroom value5j
             * */
            for(LessonGroupSet groupSet : lesson_pool.merge)
            {
                for(int counter_classroom = -1, classroom_size = groupSet.classrooms.length; ++counter_classroom < classroom_size; )
                {
                    groupSet.classrooms[counter_classroom] = lesson_pool.clustered_classroom_encoder.get(groupSet.classrooms[counter_classroom]);
                }
            }
        }
    }

    @SuppressWarnings({"Duplicates", "StatementWithEmptyBody"}) @Override public int[][] generateRandomSchedule(final ScheduleShufflingProperties properties)
    {
        final int[]           day_set          = properties.day_set;
        final int[][][]       classrooms_set   = properties.classrooms_set;
        final int[][][]       lessons_set      = properties.lessons_set;
        final LessonPoolSet[] lesson_pool_set  = super.workingset.lesson_pool;
        final int             period_length    = super.dataset.active_periods.length;
        final Lesson[]        lesson_dataset   = super.dataset.lessons;
        final int[][]         random_schedule  = new int[super.workingset.lesson_pool.length][];
        int                   counter_schedule = -1;
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

                                    if(remaining_size < 0)
                                    {
                                        System.out.println(global_availability + "  " + property[1] + "  " + classroom_current_size + "  " + future_size + "   " + property[0]);
                                        System.out.println(Arrays.toString(temp_container[classroom][day].toIntArray()));
                                    }

                                    if(local_sks_distribution[remaining_size] > 0)
                                    {
                                        int counter = counter_lesson;
                                        while(lesson_dataset[lesson_temp[++counter]].sks != remaining_size)
                                        {
                                            ;
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
                                        /*Need to change*/
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
            random_schedule[++counter_schedule] = full_schedule.toIntArray();
        }
        return random_schedule;
    }

    @SuppressWarnings({"Duplicates", "StatementWithEmptyBody"}) public void generateRandomSchedule(final ScheduleShufflingProperties properties, final int[][] random_schedule)
    {
        final int[]           day_set          = properties.day_set;
        final int[][][]       classrooms_set   = properties.classrooms_set;
        final int[][][]       lessons_set      = properties.lessons_set;
        final LessonPoolSet[] lesson_pool_set  = super.workingset.lesson_pool;
        final int             period_length    = super.dataset.active_periods.length;
        final Lesson[]        lesson_dataset   = super.dataset.lessons;
        int                   counter_schedule = -1;
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

                                    if(remaining_size < 0)
                                    {
                                        System.out.println(global_availability + "  " + property[1] + "  " + classroom_current_size + "  " + future_size + "   " + property[0]);
                                        System.out.println(Arrays.toString(temp_container[classroom][day].toIntArray()));
                                    }

                                    if(local_sks_distribution[remaining_size] > 0)
                                    {
                                        int counter = counter_lesson;
                                        while(lesson_dataset[lesson_temp[++counter]].sks != remaining_size)
                                        {
                                            ;
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
                                        /*Need to change*/
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
            System.arraycopy(full_schedule.toIntArray(), 0, random_schedule[++counter_schedule], 0, random_schedule[counter_schedule].length);
        }
    }


    public ScheduleShufflingProperties generateProperties()
    {
        final int[]           day_set                = IntArrays.copy(super.dataset.active_days);
        final int[][][]       classrooms_set         = new int[super.workingset.lesson_pool.length][][];
        final int[][][]       lessons_set            = new int[super.workingset.lesson_pool.length][][];
        final int[][][][]     classroom_current_time = new int[super.workingset.lesson_pool.length][][][];
        final LessonPoolSet[] lesson_pool_set        = super.workingset.lesson_pool;
        for(int counter_lesson_pool = -1, lesson_pool_size = lesson_pool_set.length; ++counter_lesson_pool < lesson_pool_size; )
        {
            final LessonPoolSet lesson_pool = lesson_pool_set[counter_lesson_pool];
            classrooms_set[counter_lesson_pool] = new int[lesson_pool.merge.length][];
            lessons_set[counter_lesson_pool] = new int[lesson_pool.merge.length][];
            classroom_current_time[counter_lesson_pool] = new int[lesson_pool.classrooms.length][day_set.length][2];
            for(int counter_lesson_set = -1, lesson_set_size = lesson_pool.merge.length; ++counter_lesson_set < lesson_set_size; )
            {
                final LessonGroupSet lesson_set = lesson_pool.merge[counter_lesson_set];
                classrooms_set[counter_lesson_pool][counter_lesson_set] = IntArrays.copy(lesson_set.classrooms);
                lessons_set[counter_lesson_pool][counter_lesson_set] = IntArrays.copy(lesson_set.lessons);
            }
        }

        return new ScheduleShufflingProperties(day_set, classrooms_set, lessons_set, classroom_current_time);
    }

    private int getLessonWindow(final int total, final int classroom_length)
    {
        return (int) Math.ceil(total * 1f / classroom_length / super.dataset.active_days.length);
    }

    private int getCumulativeLessonLength(final LessonPoolSet lesson_pool)
    {
        final Lesson[] dataset_lesson           = super.dataset.lessons;
        int            cumulative_lesson_length = 0;
        for(int counter_lesson : lesson_pool.lessons)
        {
            cumulative_lesson_length += dataset_lesson[counter_lesson].sks;
        }
        return cumulative_lesson_length;
    }

    private void generateSksDistribution()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Sks Distribution
             * */
            /* Query for all Subjects in the specific school id*/
            String query = "SELECT MAX(`lesson`.`sks`) AS `sks` FROM `lesson` LEFT OUTER JOIN `subject` ON `subject`.`id` = `lesson`.`subject` WHERE `subject`.`school` = ? LIMIT 1";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            super.dataset.sks_distribution = new int[db_component.result_set.getInt("sks") + 1];
            final int[] distribution = super.dataset.sks_distribution;

            for(final Lesson lesson : super.dataset.lessons)
            {
                ++distribution[lesson.sks];
            }
            --distribution[1];
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Sks Distribution");
            System.exit(-1);
        }
    }

    @Override protected void generateLessons()
    {
        this.generateLessonSet();
        this.generateLessonAvailableClassroom();
    }

    private void generateLessonSet()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /* Query for all lessons in the specific school id*/
            String query = "SELECT COUNT(`lesson`.`id`) AS 'count', SUM(`lesson`.`count` - 1) AS 'extra' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` WHERE `subject`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            /*
             * Lesson Size = lessons Size + Lesson Extra Size + 1
             * */
            final int size                 = db_component.result_set.getInt("count") + db_component.result_set.getInt("extra") + 1;
            int       lesson_extra_counter = db_component.result_set.getInt("count");

            /* Query for all lessons in the specific school id*/
            query = "SELECT `lesson`.`id`, `lesson`.`subject`, COALESCE(`lesson`.`lecture`, -1) AS 'lecture', `lesson`.`sks`, `lesson`.`count`, `lesson`.`class`, COUNT(`lesson_available_classroom`.`id`) AS 'total' FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` LEFT OUTER JOIN `lesson_available_classroom` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `subject`.`school` = ? GROUP BY `lesson`.`id` ORDER BY `lesson`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.lessons = new Lesson[size];
            super.encoder.lessons = new Int2IntLinkedOpenHashMap(size);
            super.decoder.lessons = new Int2IntLinkedOpenHashMap(size);

            final Lesson[]                 dataset         = super.dataset.lessons;
            final Int2IntLinkedOpenHashMap encoder         = super.encoder.lessons;
            final Int2IntLinkedOpenHashMap decoder         = super.decoder.lessons;
            final IntArrayList             link_temporary  = new IntArrayList();
            final Int2IntLinkedOpenHashMap encoder_subject = super.encoder.subjects;
            final Int2IntLinkedOpenHashMap encoder_lecture = super.encoder.lecturers;
            final Int2IntLinkedOpenHashMap encoder_class   = super.encoder.classes;

            for(int result_set_counter = 0; db_component.result_set.next(); )
            {
                if(db_component.result_set.getInt("count") > 1)
                {
                    link_temporary.clear();

                    int link_size = db_component.result_set.getInt("count") - 1;
                    link_temporary.add(++result_set_counter);

                    Lesson lesson = new Lesson(encoder_subject.get(db_component.result_set.getInt("subject")), db_component.result_set.getInt("lecture") == -1 ? -1 : encoder_lecture.get(db_component.result_set.getInt("lecture")), db_component.result_set.getInt("sks"), encoder_class.get(db_component.result_set.getInt("class")), link_size, db_component.result_set.getInt("total"));
                    dataset[result_set_counter] = lesson;
                    for(int link_counter = -1; ++link_counter < link_size; )
                    {
                        lesson = new Lesson(encoder_subject.get(db_component.result_set.getInt("subject")), db_component.result_set.getInt("lecture") == -1 ? -1 : encoder_lecture.get(db_component.result_set.getInt("lecture")), db_component.result_set.getInt("sks"), encoder_class.get(db_component.result_set.getInt("class")), link_size, db_component.result_set.getInt("total"), result_set_counter);
                        dataset[++lesson_extra_counter] = lesson;
                        link_temporary.add(lesson_extra_counter);
                    }

                    for(int link : link_temporary)
                    {
                        lesson = dataset[link];
                        for(int link_counter = -1, link_lv1_counter = -1, link_lv1_size = link_temporary.size(); (++link_lv1_counter < link_lv1_size); )
                        {
                            int link_lv1 = link_temporary.getInt(link_lv1_counter);
                            if(link != link_lv1)
                            {
                                lesson.link[++link_counter] = link_lv1;
                            }
                        }
                    }
                }
                else
                {
                    dataset[++result_set_counter] = new Lesson(encoder_subject.get(db_component.result_set.getInt("subject")), db_component.result_set.getInt("lecture") == -1 ? -1 : encoder_lecture.get(db_component.result_set.getInt("lecture")), db_component.result_set.getInt("sks"), encoder_class.get(db_component.result_set.getInt("class")), 0, db_component.result_set.getInt("total"));
                }
                encoder.put(db_component.result_set.getInt("id"), result_set_counter);
                decoder.put(result_set_counter, db_component.result_set.getInt("id"));
            }
            dataset[0] = new Lesson(-1, -1, 1, -1, 0, 0);
        }
        catch(SQLException ignore)
        {
            System.err.println("Generate Lessons Set");
            System.exit(1);
        }
    }

    private void generateLessonAvailableClassroom()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /* Query for all lessons in the specific school id according to lessons*/
            String query = "SELECT `lesson`.`id`, `lesson_available_classroom`.`classroom` FROM `lesson` LEFT OUTER JOIN `subject` ON `lesson`.`subject` = `subject`.`id` LEFT OUTER JOIN `lesson_available_classroom` ON `lesson_available_classroom`.`lesson` = `lesson`.`id` WHERE `subject`.`school` = ? ORDER BY `lesson`.`id`, `lesson_available_classroom`.`classroom` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            int   lesson_index        = 0;
            int   index               = 0;
            int   available_classroom = -1;
            int[] link                = new int[0];

            final Lesson[]                 dataset_lesson    = super.dataset.lessons;
            final Int2IntLinkedOpenHashMap encoder_lesson    = super.encoder.lessons;
            final Int2IntLinkedOpenHashMap encoder_classroom = super.encoder.lessons;

            if(db_component.result_set.next())
            {
                index = db_component.result_set.getInt("id");
                lesson_index = encoder_lesson.get(index);
                link = dataset_lesson[lesson_index].link;
                db_component.result_set.previous();
            }

            final Int2IntLinkedOpenHashMap encoder_classrooms = super.encoder.classrooms;

            while(db_component.result_set.next())
            {
                if(index != db_component.result_set.getInt("id"))
                {
                    available_classroom = -1;
                    index = db_component.result_set.getInt("id");
                    lesson_index = encoder_lesson.get(index);
                    link = dataset_lesson[lesson_index].link;
                }
                int classroom = encoder_classrooms.get(encoder_classroom.get(db_component.result_set.getInt("classroom")));
                dataset_lesson[lesson_index].available_classroom[++available_classroom] = classroom;
                for(int lnk_idx : link)
                {
                    dataset_lesson[lnk_idx].available_classroom[available_classroom] = classroom;
                }
            }
        }
        catch(SQLException ignore)
        {
            System.err.println("Generate Lesson Available Classroom");
            System.exit(1);
        }
    }

    @Override protected void generateSubjects()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Subjects Size
             * */
            /* Query for all classrooms in the specific school id*/
            String query = "SELECT COUNT(`subject`.`id`) AS 'count' FROM `subject` WHERE `subject`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Subjects Data
             * */
            /* Query for all Subjects in the specific school id*/
            query = "SELECT `subject`.`id` FROM `subject` WHERE `subject`.`school` = ? ORDER BY `subject`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.subjects = new Timeoff[size];
            super.encoder.subjects = new Int2IntLinkedOpenHashMap(size);
            super.decoder.subjects = new Int2IntLinkedOpenHashMap(size);

            final Timeoff[]                dataset             = super.dataset.subjects;
            final Int2IntLinkedOpenHashMap encoder             = super.encoder.subjects;
            final Int2IntLinkedOpenHashMap decoder             = super.decoder.subjects;
            final int                      active_days_size    = super.dataset.active_days.length;
            final int                      active_periods_size = super.dataset.active_periods.length;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = new Timeoff(active_days_size, active_periods_size);
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }

            /*
             * Get Active Lecture Time-Off each Subjects
             * */
            /* Query for all Subjects in the specific school id*/
            query = "SELECT `subject_timeoff`.`subject`, `subject_timeoff`.`day`, `subject_timeoff`.`period`, `availability`.`value` FROM `subject_timeoff` LEFT OUTER JOIN `class` ON `class`.`id` = `subject_timeoff`.`subject` LEFT OUTER JOIN `availability` ON `availability`.`id` = `subject_timeoff`.`availability` WHERE `class`.`school` = ? ORDER BY `subject_timeoff`.`subject`, `subject_timeoff`.`day`, `subject_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            final Int2IntLinkedOpenHashMap encoder_active_days    = super.encoder.active_days;
            final Int2IntLinkedOpenHashMap encoder_active_periods = super.encoder.active_periods;
            while(db_component.result_set.next())
            {
                dataset[encoder.get(db_component.result_set.getInt("subject"))].add(encoder_active_days.get(db_component.result_set.getInt("day")), encoder_active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Classroom");
            System.exit(-1);
        }

    }

    @Override

    protected void generateLecturers()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Lecturers Size
             * */
            /* Query for all classrooms in the specific school id*/
            String query = "SELECT COUNT(`lecture`.`id`) AS 'count' FROM `lecture` WHERE `lecture`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Lecturers Data
             * */
            /* Query for all Lecturers in the specific school id*/
            query = "SELECT `lecture`.`id` FROM `lecture` WHERE `lecture`.`school` = ? ORDER BY `lecture`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.lecturers = new Timeoff[size];
            super.encoder.lecturers = new Int2IntLinkedOpenHashMap(size);
            super.decoder.lecturers = new Int2IntLinkedOpenHashMap(size);

            final Timeoff[]                dataset             = super.dataset.lecturers;
            final Int2IntLinkedOpenHashMap encoder             = super.encoder.lecturers;
            final Int2IntLinkedOpenHashMap decoder             = super.decoder.lecturers;
            final int                      active_days_size    = super.dataset.active_days.length;
            final int                      active_periods_size = super.dataset.active_periods.length;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = new Timeoff(active_days_size, active_periods_size);
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }

            /*
             * Get Active Lecture Time-Off each Lecturers
             * */
            /* Query for all Lecturers in the specific school id*/
            query = "SELECT `lecture_timeoff`.`lecture`, `lecture_timeoff`.`day`, `lecture_timeoff`.`period`, `availability`.`value` FROM `lecture_timeoff` LEFT OUTER JOIN `class` ON `class`.`id` = `lecture_timeoff`.`lecture` LEFT OUTER JOIN `availability` ON `availability`.`id` = `lecture_timeoff`.`availability` WHERE `class`.`school` = ? ORDER BY `lecture_timeoff`.`lecture`, `lecture_timeoff`.`day`, `lecture_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            final Int2IntLinkedOpenHashMap encoder_active_days    = super.encoder.active_days;
            final Int2IntLinkedOpenHashMap encoder_active_periods = super.encoder.active_periods;
            while(db_component.result_set.next())
            {
                dataset[encoder.get(db_component.result_set.getInt("lecture"))].add(encoder_active_days.get(db_component.result_set.getInt("day")), encoder_active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Classroom");
            System.exit(-1);
        }

    }

    @Override protected void generateClassrooms()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Classrooms Size
             * */
            /* Query for all classrooms in the specific school id*/
            String query = "SELECT COUNT(`classroom`.`id`) AS 'count' FROM `classroom` WHERE `classroom`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Classrooms Data
             * */
            /* Query for all classrooms in the specific school id*/
            query = "SELECT `classroom`.`id` FROM `classroom` WHERE `classroom`.`school` = ? ORDER BY `classroom`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.classrooms = new Timeoff[size];
            super.encoder.classrooms = new Int2IntLinkedOpenHashMap(size);
            super.decoder.classrooms = new Int2IntLinkedOpenHashMap(size);

            final Timeoff[]                dataset             = super.dataset.classrooms;
            final Int2IntLinkedOpenHashMap encoder             = super.encoder.classrooms;
            final Int2IntLinkedOpenHashMap decoder             = super.decoder.classrooms;
            final int                      active_days_size    = super.dataset.active_days.length;
            final int                      active_periods_size = super.dataset.active_periods.length;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = new Timeoff(active_days_size, active_periods_size);
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }

            /*
             * Get Active Classroom Time-Off each Classrooms
             * */
            /* Query for all classrooms in the specific school id*/
            query = "SELECT `classroom_timeoff`.`classroom`, `classroom_timeoff`.`day`, `classroom_timeoff`.`period`, `availability`.`value` FROM `classroom_timeoff` LEFT OUTER JOIN `class` ON `class`.`id` = `classroom_timeoff`.`classroom` LEFT OUTER JOIN `availability` ON `availability`.`id` = `classroom_timeoff`.`availability` WHERE `class`.`school` = ? ORDER BY `classroom_timeoff`.`classroom`, `classroom_timeoff`.`day`, `classroom_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            final Int2IntLinkedOpenHashMap encoder_active_days    = super.encoder.active_days;
            final Int2IntLinkedOpenHashMap encoder_active_periods = super.encoder.active_periods;
            while(db_component.result_set.next())
            {
                dataset[encoder.get(db_component.result_set.getInt("classroom"))].add(encoder_active_days.get(db_component.result_set.getInt("day")), encoder_active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }

            query = "SELECT `classroom`.`id`, `classroom_timeoff`.`day`, COUNT(`classroom_timeoff`.`id`) AS 'available'  FROM `classroom_timeoff` LEFT OUTER JOIN `classroom` ON `classroom`.`id` = `classroom_timeoff`.`classroom` WHERE `classroom`.`school` = ? AND `classroom_timeoff`.`availability` <> 1 GROUP BY `classroom_timeoff`.`classroom`, `classroom_timeoff`.`day` ORDER BY `classroom`.`id` ASC, `classroom_timeoff`.`day` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.classroom_available_time = new int[super.dataset.classrooms.length][active_days_size][];
            final int[][][] classroom_available_time = super.dataset.classroom_available_time;

            final IntArrayList distribution_container = new IntArrayList(super.dataset.active_periods.length);
            while(db_component.result_set.next())
            {
                distribution_container.clear();
                final int classroom         = encoder.get(db_component.result_set.getInt("id"));
                final int day               = encoder.get(db_component.result_set.getInt("day"));
                int       distribution_size = 0;
                for(int period : super.dataset.active_periods)
                {
                    if(dataset[classroom].get(day, period) != 0.2)
                    {
                        ++distribution_size;
                    }
                    else
                    {
                        if(distribution_size != 0)
                        {
                            distribution_container.add(distribution_size);
                            distribution_size = 0;
                        }
                    }
                }
                if(distribution_size != 0)
                {
                    distribution_container.add(distribution_size);
                }

                classroom_available_time[classroom][day] = new int[distribution_container.size() + 1];
                classroom_available_time[classroom][day][0] = db_component.result_set.getInt("available");
                System.arraycopy(distribution_container.toIntArray(), 0, classroom_available_time[classroom][day], 1, distribution_container.size());
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Classroom");
            System.exit(-1);
        }
    }

    @Override protected void generateClasses()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Classes Size
             * */
            /* Query for all class in the specific school id*/
            String query = "SELECT COUNT(`class`.`id`) AS 'count' FROM `class` WHERE `class`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Classes Data
             * */
            /* Query for all class in the specific school id*/
            query = "SELECT `class`.`id` FROM `class` WHERE `class`.`school` = ? ORDER BY `class`.`id` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.classes = new Timeoff[size];
            super.encoder.classes = new Int2IntLinkedOpenHashMap(size);
            super.decoder.classes = new Int2IntLinkedOpenHashMap(size);

            final Timeoff[]                dataset             = super.dataset.classes;
            final Int2IntLinkedOpenHashMap encoder             = super.encoder.classes;
            final Int2IntLinkedOpenHashMap decoder             = super.decoder.classes;
            final int                      active_days_size    = super.dataset.active_days.length;
            final int                      active_periods_size = super.dataset.active_periods.length;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = new Timeoff(active_days_size, active_periods_size);
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }

            /*
             * Get Active Class Time-Off each Classes
             * */
            /* Query for all class in the specific school id*/
            query = "SELECT `class_timeoff`.`class`, `class_timeoff`.`day`, `class_timeoff`.`period`, `availability`.`value` FROM `class_timeoff` LEFT OUTER JOIN `class` ON `class`.`id` = `class_timeoff`.`class` LEFT OUTER JOIN `availability` ON `availability`.`id` = `class_timeoff`.`availability` WHERE `class`.`school` = ? ORDER BY `class_timeoff`.`class`, `class_timeoff`.`day`, `class_timeoff`.`period` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            final Int2IntLinkedOpenHashMap encoder_active_days    = super.encoder.active_days;
            final Int2IntLinkedOpenHashMap encoder_active_periods = super.encoder.active_periods;
            while(db_component.result_set.next())
            {
                dataset[encoder.get(db_component.result_set.getInt("class"))].add(encoder_active_days.get(db_component.result_set.getInt("day")), encoder_active_periods.get(db_component.result_set.getInt("period")), db_component.result_set.getDouble("value"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Class");
            System.exit(-1);
        }
    }

    @Override protected void generateActivePeriods()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Periods Size
             * */
            String query = "SELECT COUNT(`active_period`.`id`) AS 'count' FROM `active_period` WHERE `active_period`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Periods Data
             * */
            query = "SELECT `active_period`.`id` FROM `active_period` WHERE `active_period`.`school` = ? ORDER BY `active_period`.`position` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.active_periods = new int[size];
            super.encoder.active_periods = new Int2IntLinkedOpenHashMap(size);
            super.decoder.active_periods = new Int2IntLinkedOpenHashMap(size);

            final int[]                    dataset = super.dataset.active_periods;
            final Int2IntLinkedOpenHashMap encoder = super.encoder.active_periods;
            final Int2IntLinkedOpenHashMap decoder = super.decoder.active_periods;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = counter_result_set;
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Period Day");
            System.exit(-1);
        }
    }

    @Override protected void generateActiveDays()
    {
        try
        {
            final DBComponent db_component = super.db_component;
            final int         school       = super.dataset.school;

            /*
             * Get Active Days Size
             * */
            String query = "SELECT COUNT(`active_day`.`id`) AS 'count' FROM `active_day` WHERE `active_day`.`school` = ?";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();
            db_component.result_set.next();
            final int size = db_component.result_set.getInt("count");

            /*
             * Get Active Days Data
             * */
            query = "SELECT `active_day`.`id` FROM `active_day` WHERE `active_day`.`school` = ? ORDER BY `active_day`.`position` ASC";
            db_component.statement = db_component.connection.prepareStatement(query);
            db_component.statement.setInt(1, school);
            db_component.result_set = db_component.statement.executeQuery();

            super.dataset.active_days = new int[size];
            super.encoder.active_days = new Int2IntLinkedOpenHashMap(size);
            super.decoder.active_days = new Int2IntLinkedOpenHashMap(size);

            final int[]                    dataset = super.dataset.active_days;
            final Int2IntLinkedOpenHashMap encoder = super.encoder.active_days;
            final Int2IntLinkedOpenHashMap decoder = super.decoder.active_days;

            for(int counter_result_set = 0; db_component.result_set.next(); ++counter_result_set)
            {
                dataset[counter_result_set] = counter_result_set;
                encoder.put(db_component.result_set.getInt("id"), counter_result_set);
                decoder.put(counter_result_set, db_component.result_set.getInt("id"));
            }
        }
        catch(SQLException ignored)
        {
            System.err.println("Generate Active Day");
            System.exit(-1);
        }
    }


}