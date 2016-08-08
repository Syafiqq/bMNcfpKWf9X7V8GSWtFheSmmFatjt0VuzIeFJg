package model.pso.component.dump.test;

import model.pso.component.dump.Velocity3;
import org.junit.Test;

/**
 * This <Skripsi_003> project in package <model.pso.component.test> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 10:51 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("Duplicates") public class Velocity3TestLowerDestinationAddition
{
    @Test public void testAddition00()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition01()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition02()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
        v2.set(7, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition03()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition04()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition05()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition06()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
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

    @Test public void testAddition07()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition17()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition27()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition37()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 2);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition47()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(3, 2);
        v1.set(2, 1);

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition57()
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

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition67()
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

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition77()
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

        v2.set(6, 7);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(3, 4);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition771()
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
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition7712()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(7, 8);
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
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition77124()
    {
        int       size     = 7;
        int       capacity = size * 2;
        Velocity3 v1       = new Velocity3(capacity);
        Velocity3 v2       = new Velocity3(capacity);

        v1.set(1, 2);
        v1.set(2, 3);
        v1.set(7, 8);
        v1.set(2, 1);
        v1.set(2, 2);
        v1.set(2, 6);
        v1.set(2, 8);

        v2.set(1, 2);
        v2.set(7, 8);
        v2.set(8, 9);
        v2.set(2, 1);
        v2.set(2, 3);
        v2.set(3, 3);
        v2.set(5, 3);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition7717()
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
        v2.set(2, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition77137()
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
        v2.set(3, 3);
        v2.set(2, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }

    @Test public void testAddition771357()
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
        v2.set(3, 3);
        v2.set(2, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }


    @Test public void testAddition771234567()
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
        v2.set(2, 8);

        System.out.println(v1);
        System.out.println(v2);
        Velocity3.additionVelocity(v1, v2);
        System.out.println(v1);
    }
}