package dump.fancyVelocity;

import model.pso.component.Velocity;

/**
 * This <Skripsi_003> project in package <dump.fancyVelocity> created by :
 * Name         : syafiq
 * Date / Time  : 23 May 2016, 6:38 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class FancyVelocity
{
    public static void main(String[] args)
    {
        Velocity v  = new Velocity(3);
        Velocity v1 = new Velocity(3);
        v.set(1, 2);
        v.set(2, 3);
        System.out.println(v);
        System.out.println(v1);
        Velocity.cloneVelocity(v, v1);
        System.out.println(v);
        System.out.println(v1);
        v1.backward(1);
        v1.set(3, 9);
        v1.forward(1);
        System.out.println(v);
        System.out.println(v1);
    }
}
