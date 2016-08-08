package model.pso.component.test;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.Velocity;
import org.junit.Assert;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 28 June 2016, 1:40 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class VelocityTest
{
    @Test public void testSetDestinationWithSmallNumberWithoutBenchmark()
    {
        int[] a1 = new int[] {2, 0, 4, 0, 3, 5, 1};
        int[] a2 = new int[] {0, 3, 5, 4, 2, 1, 0};

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(a1);
        Velocity velocity  = new Velocity(16);


        System.out.println(p1);
        System.out.println(p2);
        Velocity.getDistance(velocity, p1, p2, mimic, container);
        System.out.println(velocity);
        Position.update(p2, velocity);
        System.out.println(p2);
        System.out.println(p1);
    }

    @Test public void testSetDestinationWithHighNumberWithoutBenchmark()
    {
        int   null_size = 200;
        int   size      = 3000;
        int[] a1        = new int[size + null_size];
        int[] a2        = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(a1);
        Velocity velocity  = new Velocity(size + null_size);


        System.out.println(p1);
        System.out.println(p2);
        Velocity.getDistance(velocity, p1, p2, mimic, container);
        System.out.println(velocity);
        Position.update(p2, velocity);
        System.out.println(p2);
        System.out.println(p1);
    }

    @Test public void testSetDestinationWithHighNumberWithBenchmark()
    {
        int   null_size = 200;
        int   size      = 3000;
        int[] a1        = new int[size + null_size];
        int[] a2        = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(a1);
        Velocity velocity  = new Velocity(size);

        for(int i = -1, is = 50000; ++i < is; )
        {
            Velocity.getDistance(velocity, p1, p2, mimic, container);
        }
    }

    /**
     * Test Set Destination With High Number With Benchmark And Stability Checking
     */
    @Test public void tSDWHNWBASC()
    {
        int   null_size = 200;
        int   size      = 3000;
        int[] a1        = new int[size + null_size];
        int[] a2        = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(a1);
        Velocity velocity  = new Velocity(size);

        int tesSize  = 50000;
        int currSize = 0;
        for(int i = -1; ++i < tesSize; )
        {
            Velocity.getDistance(velocity, p1, p2, mimic, container);
            Position.update(p2, velocity);
            currSize += Arrays.equals(p1.position, p2.position) ? 1 : 0;
            IntArrays.shuffle(a2, ThreadLocalRandom.current());
        }
        Assert.assertEquals(currSize, tesSize);
    }

    @Test public void testSetDestinationWithSmallNumberAndNoSequenceWithoutBenchmark()
    {
        //int[] a1 = new int[]{2, 0, 4, 0, 3, 8, 1};
        //int[] a2 = new int[]{0, 3, 8, 4, 2, 1, 0};

        int[] a1 = new int[] {4, 2, 0, 6, 0};
        int[] a2 = new int[] {6, 0, 2, 4, 0};

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(7);
        Velocity velocity  = new Velocity(7);


        System.out.println(p1);
        System.out.println(p2);
        Velocity.getDistance(velocity, p1, p2, mimic, container);
        System.out.println(velocity);
        Position.update(p2, velocity);
        System.out.println(p2);
        System.out.println(p1);
    }


    /**
     * Test Set Destination With Generated Small Number Without Benchmark
     */
    @Test public void tSDWGSNWB()
    {
        int   null_size  = 2;
        int   size       = 100;
        int   multiplier = 2;
        int[] a1         = new int[size + null_size];
        int[] a2         = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : (i * multiplier);
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        int max = -1;
        for(int cMax : a1)
        {
            max = cMax > max ? cMax : max;
        }

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(max + 1);
        Velocity velocity  = new Velocity(size + null_size);


        System.out.println(p1);
        System.out.println(p2);
        Velocity.getDistance(velocity, p1, p2, mimic, container);
        System.out.println(velocity);
        Position.update(p2, velocity);
        System.out.println(p2);
        System.out.println(p1);
    }

    /**
     * Test Set Destination With Generated High Number With Benchmark
     */
    @Test public void tSDWGHNWB()
    {
        int   null_size  = 500;
        int   size       = 3000;
        int   multiplier = 3;
        int[] a1         = new int[size + null_size];
        int[] a2         = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : (i * multiplier);
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        int max = -1;
        for(int cMax : a1)
        {
            max = cMax > max ? cMax : max;
        }

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(max + 1);
        Velocity velocity  = new Velocity(size + null_size);

        for(int i = -1, is = 500000; ++i < is; )
        {
            Velocity.getDistance(velocity, p1, p2, mimic, container);
        }
    }

    /**
     * Test Set Destination With Generated High Number With Benchmark And Stability Checking
     */
    @Test public void tSDWGHNWBASC()
    {
        int   null_size  = 500;
        int   size       = 3000;
        int   multiplier = 3;
        int[] a1         = new int[size + null_size];
        int[] a2         = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : (i * multiplier);
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        int max = -1;
        for(int cMax : a1)
        {
            max = cMax > max ? cMax : max;
        }

        Position p1        = new Position(a1);
        Position p2        = new Position(a2);
        Position mimic     = Position.newInstance(a1);
        Position container = Position.newInstance(max + 1);
        Velocity velocity  = new Velocity(size + null_size);


        int tesSize  = 50000;
        int currSize = 0;
        for(int i = -1; ++i < tesSize; )
        {
            Velocity.getDistance(velocity, p1, p2, mimic, container);
            Position.update(p2, velocity);
            currSize += Arrays.equals(p1.position, p2.position) ? 1 : 0;
            IntArrays.shuffle(a2, ThreadLocalRandom.current());
        }
        Assert.assertEquals(currSize, tesSize);
    }
}