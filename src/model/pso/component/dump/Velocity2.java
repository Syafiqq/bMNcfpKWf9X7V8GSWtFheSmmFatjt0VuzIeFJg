package model.pso.component.dump;

import model.pso.component.Position;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2016, 8:48 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity2 extends Velocity
{
    public Velocity2(int expected_size)
    {
        super(expected_size);
    }

    /**
     * @param velocity
     * @param destination
     * @param source
     * @param source_mimic
     * @param temp_container
     */
    public static void distance(final Velocity2 velocity, final Position destination, final Position source, final Position source_mimic, final Position temp_container)
    {
        velocity.reset();
        System.arraycopy(source.position, 0, source_mimic.position, 0, source.position.length);
        Velocity2.distance(velocity, destination.position, source_mimic.position, temp_container.position);
    }

    /**
     * My Biggest transposition algorithm optimize O(n^2) -> O(2n)
     * 1200 array length with 65536 iteration cost 600ms instead of 38s 306ms
     *
     * @param velocity
     * @param destination
     * @param mimic
     * @param temp_container
     */
    private static void distance(final Velocity2 velocity, final int[] destination, final int[] mimic, final int[] temp_container)
    {
        for(int i = -1, is = mimic.length; ++i < is; )
        {
            int value = mimic[i];
            if(value != 0)
            {
                temp_container[value] = i;
            }
        }
        for(int counter_destination = -1, destination_size = destination.length; ++counter_destination < destination_size; )
        {
            final int value = destination[counter_destination];
            if(value != 0)
            {
                velocity.set(counter_destination, temp_container[value]);
                /**
                 * Find Lookup Index and Swap
                 * */
                final int temp = mimic[counter_destination];
                mimic[counter_destination] = mimic[temp_container[value]];
                mimic[temp_container[value]] = temp;
                temp_container[mimic[temp_container[value]]] = counter_destination;
                temp_container[temp] = temp_container[value];
            }
        }
    }

    /**
     * @param coefficient
     * @param velocity
     * @param temp_container
     */
    public static void multiplicationVelocity(double coefficient, final Velocity2 velocity, final Velocity2 temp_container)
    {
        if(!velocity.isEmpty())
        {
            if(coefficient == 0.0)
            {
                velocity.reset();
            }
            else if(Math.abs(coefficient) < 1.0)
            {
                if(coefficient < 0.0)
                {
                    reverse(velocity);
                }
                velocity.backward((int) Math.floor(velocity.size() * (1.0 - Math.abs(coefficient))));
            }
            else
            {
                if(coefficient < 0.0)
                {
                    reverse(velocity);
                    coefficient = Math.abs(coefficient);
                }
                cloneVelocity(velocity, temp_container);
                int natural_coefficient = (int) Math.floor(coefficient);
                coefficient -= natural_coefficient;
                for(int i = 1; i < natural_coefficient; ++i)
                {
                    Velocity2.additionVelocity(velocity, temp_container);
                }
                //Keep that line below
                Velocity2.multiplicationVelocity(coefficient, temp_container, null);
                Velocity2.additionVelocity(velocity, temp_container);
            }
        }
    }

    /**
     * @param velocity1
     * @param velocity2
     */
    public static void additionVelocity(final Velocity2 velocity1, final Velocity2 velocity2)
    {
        /**
         * Alternative 1
         * v1 = {(1,2)(3,4)}
         * v2 = {(2,3)(4,5)}
         * v1 = {(1,2)(3,4)(2,3)(4,5)}
         * */
        for(int counter_velocity = -1, velocity_size = velocity2.size(); ++counter_velocity < velocity_size; )
        {
            velocity1.checkAndSet(velocity2.get(counter_velocity));
        }
    }
}

