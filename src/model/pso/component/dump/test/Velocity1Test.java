package model.pso.component.dump.test;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.dump.Velocity1;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2016, 6:57 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity1Test
{
    @Test public void testSetDestinationWIthoutBenchmark()
    {
        int   null_size = 100;
        int   size      = 100;
        int[] a1        = new int[size + null_size];
        int[] a2        = new int[size + null_size];
        for(int i = -1, is = size + null_size; ++i < is; )
        {
            a2[i] = a1[i] = i > size ? 0 : i;
        }

        IntArrays.shuffle(a1, ThreadLocalRandom.current());
        IntArrays.shuffle(a2, ThreadLocalRandom.current());


        Position  p1       = new Position(a1);
        Position  p2       = new Position(a2);
        Velocity1 velocity = new Velocity1(size);


        System.out.println(p1);
        System.out.println(p2);
        Velocity1.distance(velocity, p1, p2);
        System.out.println(velocity);
        Position.update(p2, velocity);
        System.out.println(p2);
        System.out.println(p1);
    }

    @Test public void testSetDestinationWithBenchmark()
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

        Position  p1       = new Position(a1);
        Position  p2       = new Position(a2);
        Velocity1 velocity = new Velocity1(size);

        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity1.distance(velocity, p1, p2);
        }
    }

}