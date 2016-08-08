package model.pso.component.dump.test;

import model.pso.component.dump.Velocity3;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 6:21 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("Duplicates") public class Velocity3TestLowerSorceAddition
{

    @Test public void testAddition10()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition20()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition30()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition40()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition50()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition60()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition70()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition71()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition72()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);
        v2.set(2, 3);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition73()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);
        v2.set(2, 3);
        v2.set(3, 2);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition74()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);
        v2.set(2, 3);
        v2.set(3, 2);
        v2.set(2, 1);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition75()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);
        v2.set(2, 3);
        v2.set(3, 2);
        v2.set(2, 1);
        v2.set(2, 2);

        v1.set(6, 7);
        v1.set(7, 8);
        v1.set(8, 9);
        v1.set(3, 4);
        v1.set(2, 3);
        v1.set(3, 3);
        v1.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition76()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(1, 2);
        v2.set(2, 3);
        v2.set(3, 4);
        v2.set(4, 5);
        v2.set(5, 6);
        v2.set(6, 7);

        v1.set(2, 1);
        v1.set(3, 2);
        v1.set(4, 3);
        v1.set(5, 4);
        v1.set(6, 5);
        v1.set(7, 6);
        v1.set(8, 7);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition761()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 2);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition7616()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 2);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(2, 6);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition76136()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(8, 9);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(2, 6);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition761356()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(8, 9);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 2);
        v2.set(2, 6);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition7613456()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(8, 9);
        v1.set(2, 1);
        v1.set(3, 4);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 2);
        v2.set(2, 6);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }


    @Test public void testAddition761234567()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(8, 9);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(2, 3);
        v2.set(8, 9);
        v2.set(2, 1);
        v2.set(2, 2);
        v2.set(2, 6);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

}