package model.pso.core.dump;

import model.dataset.component.Lesson;
import model.dataset.core.LessonPoolSet;
import model.pso.component.ParticleP2;

/**
 * This <Skripsi_003> project in package <model.pso.core.dump> created by :
 * Name         : syafiq
 * Date / Time  : 15 June 2016, 2:06 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2Dump
{
    //Wednesday, [06 - 15 - 2016]
    public void calculateFitnessWithBreakSKSPrediction(ParticleP2 data)
    {
        int pool_index = -1;
        for(LessonPoolSet lesson_pool : this.lesson_pool)
        {
            System.out.printf("[%4d] : ", data.data.positions[pool_index + 1].position.length);
            int    lesson_counter = -1;
            int[]  lesson_id      = data.data.positions[++pool_index].position;
            Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks     = lesson.sks;
            int    current_sks    = 0;


            for(int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    ++day_index;
                    int period_index = -1;
                    for(double period : day)
                    {
                        ++period_index;
                        if(current_sks == lesson_sks)
                        {
                            lesson = this.lessons[lesson_id[++lesson_counter]];
                            lesson_sks = lesson.sks;
                            current_sks = 0;
                        }

                        if(period == 0.2)
                        {
                            System.out.printf(" [%d]", current_sks);
                        }
                        else
                        {
                            ++current_sks;
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    //Wednesday, [06 - 15 - 2016]
    public void calculateFitnessWithLessonPlacement(ParticleP2 data)
    {
        int pool_index = -1;
        for(LessonPoolSet lesson_pool : this.lesson_pool)
        {
            System.out.printf("[%4d] : ", data.data.positions[pool_index + 1].position.length);
            for(int lesson : data.data.positions[pool_index + 1].position)
            {
                System.out.printf("%4d", lesson);
            }
            System.out.println();
            System.out.printf("[%4d] : ", data.data.positions[pool_index + 1].position.length);

            int    lesson_counter = -1;
            int[]  lesson_id      = data.data.positions[++pool_index].position;
            Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks     = lesson.sks;
            int    current_sks    = 0;


            for(int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    ++day_index;
                    int period_index = -1;
                    for(double period : day)
                    {
                        ++period_index;
                        if(current_sks == lesson_sks)
                        {
                            //System.out.printf("%4d", lesson_id[lesson_counter]);
                            System.out.printf("%4d", lesson_sks);
                            lesson = this.lessons[lesson_id[++lesson_counter]];
                            lesson_sks = lesson.sks;
                            current_sks = 0;
                        }

                        if(period == 0.2)
                        {
                        }
                        else
                        {
                            ++current_sks;
                        }
                    }
                }
            }
            System.out.printf("%4d\n", current_sks);
        }
    }

    //Wednesday, [06 - 15 - 2016]
    public void calculateFitnessWithLessonAndBreakPlacement(ParticleP2 data)
    {
        int pool_index = -1;
        for(LessonPoolSet lesson_pool : this.lesson_pool)
        {
            System.out.printf("[%4d] : ", data.data.positions[pool_index + 1].position.length);
            for(int lesson : data.data.positions[pool_index + 1].position)
            {
                System.out.printf("%4d", lesson);
            }
            System.out.println();
            System.out.printf("[%4d] : \n", data.data.positions[pool_index + 1].position.length);

            int    lesson_counter = -1;
            int[]  lesson_id      = data.data.positions[++pool_index].position;
            Lesson lesson         = this.lessons[lesson_id[++lesson_counter]];
            int    lesson_sks     = lesson.sks;
            int    current_sks    = 0;


            for(int classroom : lesson_pool.classrooms)
            {
                int day_index = -1;
                for(double[] day : lesson_pool.classroom_timeoff[classroom].timeoff)
                {
                    ++day_index;
                    int period_index = -1;
                    for(double period : day)
                    {
                        ++period_index;


                        if(period == 0.2)
                        {
                            System.out.printf(" [%d]", current_sks);
                        }
                        else
                        {
                            if(++current_sks == lesson_sks)
                            {
                                //System.out.printf("%4d", lesson_id[lesson_counter]);
                                System.out.printf("%4d", lesson_sks);
                                try
                                {
                                    lesson = this.lessons[lesson_id[++lesson_counter]];
                                }
                                catch(ArrayIndexOutOfBoundsException e)
                                {
                                }
                                lesson_sks = lesson.sks;
                                current_sks = 0;
                            }
                        }
                    }
                    System.out.println();
                }
            }
            //System.out.printf("%4d\n", current_sks);
        }
        System.out.println();
    }
}
