package model.pso.component.dump.test;

import it.unimi.dsi.fastutil.ints.IntArrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.pso.component.Position;
import model.pso.component.dump.Velocity2;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2016, 9:18 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity2Test
{
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

/*        a1 = new int[]{2, 0, 4, 0, 3, 5, 1};
        a2 = new int[]{0, 3, 5, 4, 2, 1, 0};*/

        Position  p1        = new Position(a1);
        Position  p2        = new Position(a2);
        Position  mimic     = new Position(a1.clone());
        Position  container = new Position(a1.clone());
        Velocity2 velocity  = new Velocity2(size);


        //System.out.println(p1);
        //System.out.println(p2);
        Velocity2.distance(velocity, p1, p2, mimic, container);
/*        System.out.println(velocity);*/
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

        Position  p1        = new Position(a1);
        Position  p2        = new Position(a2);
        Position  mimic     = new Position(a1.clone());
        Position  container = new Position(a1.clone());
        Velocity2 velocity  = new Velocity2(size);

        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity2.distance(velocity, p1, p2, mimic, container);
        }
    }

    @Test public void testSetDestinationWithStabilityChecking()
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


        Position  p1        = new Position(a1);
        Position  p2        = new Position(a2);
        Position  mimic     = new Position(a1.clone());
        Position  container = new Position(a1.clone());
        Velocity2 velocity  = new Velocity2(size);

        int total = 0;

        for(int i = -1, is = 1000000; ++i < is; )
        {
            Velocity2.distance(velocity, p1, p2, mimic, container);
            Position.update(p2, velocity);
            //total += p1.equalsPosition(p2);
            IntArrays.shuffle(a1, ThreadLocalRandom.current());
            IntArrays.shuffle(a2, ThreadLocalRandom.current());
        }

        System.out.println(total);
    }

    @Test public void testAdditionWithoutLosingData()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        Random random = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
            v2.set(random.nextInt(size), random.nextInt(size));
        }

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionWithLosingData()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v2.set(5, 6);
        v2.set(4, 5);
        v2.set(4, 5);
        v2.set(3, 4);
        v2.set(2, 3);

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionWithLosingAllData()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v2.set(5, 6);
        v2.set(4, 5);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(1, 2);

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionWithEmptyTargetVelocityFirst()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v2.set(5, 6);
        v2.set(4, 5);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(1, 2);

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testAdditionWithEmptySourceVelocityFirst()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v2.set(5, 6);
        v2.set(4, 5);
        v2.set(3, 4);

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.additionVelocity(v1, v2);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testMultiplicationWithEmptyVelocity()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        double coefficient = 1.0;

        System.out.println(v1);
        System.out.println(v2);

        Velocity2.multiplicationVelocity(coefficient, v1, null);

        System.out.println(v1);
        System.out.println(v2);
    }

    @Test public void testMultiplicationWithZeroCoefficient()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);

        double coefficient = 0.0;

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);

        System.out.println(v1);

        Velocity2.multiplicationVelocity(coefficient, v1, null);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithZeroToOneCoefficient()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);

        double coefficient = ThreadLocalRandom.current().nextDouble(1);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v1.set(6, 7);

        System.out.println(v1);

        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, null);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithMinusOneToZeroCoefficient()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);

        double coefficient = -ThreadLocalRandom.current().nextDouble(1);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v1.set(6, 7);

        System.out.println(v1);

        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, null);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithMoreThanOneCoefficient()
    {
        double    coefficient = 1 + ThreadLocalRandom.current().nextDouble(2);
        int       size        = 6;
        int       capacity    = (size * (int) Math.ceil(coefficient));
        Velocity2 v1          = new Velocity2(capacity);
        Velocity2 container   = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v1.set(6, 7);

        System.out.println(v1);
        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, container);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithMoreThanOneAndLosingCoefficient()
    {
        double    coefficient = 1 + ThreadLocalRandom.current().nextDouble(2);
        int       size        = 6;
        int       capacity    = (size * (int) Math.ceil(coefficient));
        Velocity2 v1          = new Velocity2(capacity);
        Velocity2 container   = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(2, 3);
        v1.set(1, 2);

        System.out.println(v1);
        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, container);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithLessThanMinusOneCoefficient()
    {
        double    coefficient = -1 + -ThreadLocalRandom.current().nextDouble(2);
        int       size        = 6;
        int       capacity    = (size * (int) Math.ceil(Math.abs(coefficient)));
        Velocity2 v1          = new Velocity2(capacity);
        Velocity2 container   = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(5, 6);
        v1.set(6, 7);

        System.out.println(v1);
        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, container);

        System.out.println(v1);
    }

    @Test public void testMultiplicationWithLessThanMinusOneAndLosingCoefficient()
    {
        double    coefficient = -1 + -ThreadLocalRandom.current().nextDouble(2);
        int       size        = 6;
        int       capacity    = (size * (int) Math.ceil(Math.abs(coefficient)));
        Velocity2 v1          = new Velocity2(capacity);
        Velocity2 container   = new Velocity2(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 4);
        v1.set(4, 5);
        v1.set(2, 3);
        v1.set(1, 2);

        System.out.println(v1);
        System.out.println(coefficient);
        Velocity2.multiplicationVelocity(coefficient, v1, container);

        System.out.println(v1);
    }

    @Test public void testAdditionWithoutLosingDataWithBenchmarking()
    {
        int       size     = 1000;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        Random random = ThreadLocalRandom.current();

        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
            v2.set(random.nextInt(size), random.nextInt(size));
        }

        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity2.additionVelocity(v1, v2);
            v1.moveTo(size);
        }
    }

    @Test public void testMultiplicationWithEmptyVelocityWithBenchmark()
    {
        int       size     = 5;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        double coefficient = 1.0;
        Velocity2.multiplicationVelocity(coefficient, v1, null);
        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity2.additionVelocity(v1, v2);
            v1.moveTo(size);
        }

    }

    @Test public void testMultiplicationWithZeroCoefficientWithBenchmark()
    {
        int       size     = 1000;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        double coefficient = 0.0;

        Random random = ThreadLocalRandom.current();
        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
        }
        Velocity2.cloneVelocity(v1, v2);


        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity2.multiplicationVelocity(coefficient, v1, null);
            Velocity2.cloneVelocity(v1, v2);
        }

    }

    @Test public void testMultiplicationWithMinusOneToZeroCoefficientWithBenchmark()
    {
        int       size     = 1000;
        int       capacity = size * 2;
        Velocity2 v1       = new Velocity2(capacity);
        Velocity2 v2       = new Velocity2(capacity);

        double coefficient = ThreadLocalRandom.current().nextDouble(1);

        Random random = ThreadLocalRandom.current();
        for(int i = -1; ++i < size; )
        {
            v1.set(random.nextInt(size), random.nextInt(size));
        }
        Velocity2.cloneVelocity(v1, v2);


        for(int i = -1, is = 0x500000; ++i < is; )
        {
            Velocity2.multiplicationVelocity(coefficient, v1, null);
            Velocity2.cloneVelocity(v1, v2);
        }
    }
}