package model.pso.component.test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.Velocity;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 7:35 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PositionTest
{
    @Test public void testReplaceWithoutBenchmark()
    {
        int   factor;
        int   size = 1500;
        int[] a1   = new int[size];
        int[] a2   = new int[size];
        factor = 0;
        for(int i = -1; ++i < size; )
        {
            a1[i] = i + factor;
        }

        factor = size;
        for(int i = -1; ++i < size; )
        {
            a2[i] = i + factor;
        }

        Position p1 = new Position(a1);
        Position p2 = new Position(a2);

        System.out.println(p1);
        System.out.println(p2);

        for(int i = -1, is = 0x1; ++i < is; )
        {
            Position.replace(p1, p2);
        }

        System.out.println(p1);
        System.out.println(p2);
    }

    @Test public void testReplace()
    {
        int   factor;
        int   size = 1500;
        int[] a1   = new int[size];
        int[] a2   = new int[size];
        factor = 0;
        for(int i = -1; ++i < size; )
        {
            a1[i] = i + factor;
        }

        factor = size;
        for(int i = -1; ++i < size; )
        {
            a2[i] = i + factor;
        }

        Position p1 = new Position(a1);
        Position p2 = new Position(a2);

        for(int i = 0, is = 0x5000000; ++i < is; )
        {
            Position.replace(p1, p2);
        }
    }

    @Test public void testUpdate()
    {
        int   factor;
        int   size = 1000;
        int[] a1   = new int[size];
        factor = 0;
        for(int i = -1; ++i < size; )
        {
            a1[i] = i + factor;
        }

        Position position = new Position(a1);

        final int sizes    = 1000;
        Velocity  velocity = new Velocity(sizes);
        Random    random   = ThreadLocalRandom.current();

        for(int i = -1; ++i < sizes; )
        {
            velocity.set(random.nextInt(size), random.nextInt(size));
        }

        for(int i = 0, is = 0x500000; ++i < is; )
        {
            Position.update(position, velocity);
        }
    }
}