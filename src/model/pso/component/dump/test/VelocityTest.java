package model.pso.component.dump.test;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.dump.Velocity;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2016, 5:34 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class VelocityTest
{
    @Test public void testVelocityBechmark()
    {
        final int size     = 5;
        Velocity  velocity = new Velocity(size);
        Random    random   = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            velocity.set(random.nextInt(size), random.nextInt(size));
        }

        for(int i = -1, is = 0x50000000; ++i < is; )
        {
            Velocity.reverse(velocity);
        }
    }

    @Test public void testVelocity()
    {
        final int size     = 9;
        Velocity  velocity = new Velocity(size);
        Random    random   = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            velocity.set(random.nextInt(size), random.nextInt(size));
        }

        System.out.println(velocity);
        for(int i = -1, is = 0x1; ++i < is; )
        {
            Velocity.reverse(velocity);
        }
        System.out.println(velocity);
    }

    @Test public void testSetDestinationWIthoutBenchmark()
    {
        int   size = 5;
        int[] a1   = new int[size];
        int[] a2   = new int[size];
        for(int i = -1; ++i < size; )
        {
            a1[i] = i;
            a2[i] = i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());

        a1 = new int[] {1, 2, 3, 5, 4};
        a2 = new int[] {2, 3, 5, 1, 4};

        Position p1       = new Position(a1);
        Position p2       = new Position(a2);
        Velocity velocity = new Velocity(size);


        System.out.println(p1);
        System.out.println(p2);
        System.out.println(velocity);
        for(int i = -1, is = 0x1; ++i < is; )
        {
            Velocity.distance(velocity, p1, p2);
        }
        System.out.println(velocity);
    }
}