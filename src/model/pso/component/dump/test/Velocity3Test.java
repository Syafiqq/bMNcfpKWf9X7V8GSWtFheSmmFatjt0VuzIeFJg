package model.pso.component.dump.test;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.dump.Velocity2;
import model.pso.component.dump.Velocity3;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 23 May 2016, 11:05 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity3Test
{
    @Test public void testAdditionMoreSourceLessDestWithoutLosingData_0()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);

        System.out.println(v1);
        System.out.println(v2);

        Velocity3.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionAllEmpty()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        System.out.println(v1);
        System.out.println(v2);

        Velocity3.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionLowerDestinationWithBenchmark()
    {
        int       size     = 1000;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        Random random = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
            v2.set(random.nextInt(size), random.nextInt(size));
        }
        v1.backward(1);

        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity3.additionVelocity(v1, v2);
            v1.moveTo(size - 1);
        }
    }

    @Test public void testAdditionLowerSourceWithBenchmark()
    {
        int       size     = 1000;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        Random random = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
            v2.set(random.nextInt(size), random.nextInt(size));
        }
        v2.backward(1);

        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity3.additionVelocity(v1, v2);
            v1.moveTo(size);
        }
    }

    @Test public void testAdditionMoreSourceLessDest_1()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v1.set(6, 7);
        v1.set(7, 8);

        v2.set(2, 1);
        v2.set(3, 2);
        v2.set(4, 3);
        v2.set(5, 4);
        v2.set(6, 5);
        v2.set(7, 6);
        v2.set(8, 7);

        System.out.println(v1);
        System.out.println(v2);

        Velocity3.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionMoreSourceLessDestWithoutLosingData_2()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(8, 7);
        v1.set(9, 8);

        v2.set(4, 5);
        v2.set(5, 6);
        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);

        System.out.println(v1);
        System.out.println(v2);

        Velocity3.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testMultiplication()
    {
        int       size     = 3;
        int       capacity = size * 5;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);

        System.out.println(v1);

        Velocity3.multiplicationVelocity(2.0, v1, v2);


        System.out.println(v1);
    }


    @Test public void testSetDestinationWIthoutBenchmark()
    {
        int   null_size = 200;
        int   size      = 1000;
        int[] a1        = new int[size + null_size];
        int[] a2        = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        a1 = new int[] {2, 0, 4, 0, 3, 5, 1};
        a2 = new int[] {0, 3, 5, 4, 2, 1, 0};

        Position  p1        = new Position(a1);
        Position  p2        = new Position(a2);
        Position  mimic     = new Position(a1.clone());
        Position  container = new Position(a1.clone());
        Velocity3 velocity  = new Velocity3(size);


        System.out.println(p1);
        System.out.println(p2);
        Velocity2.distance(velocity, p1, p2, mimic, container);
/*        System.out.println(velocity);*/
        Position.update(p2, velocity);

        System.out.println(p2);
        System.out.println(p1);
    }
}