package model.pso.component.dump;

import model.pso.component.Position;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2016, 6:51 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity1 extends Velocity
{
    public Velocity1(int expected_size)
    {
        super(expected_size);
    }

    /**
     * @param velocity
     * @param destination
     * @param source
     */
    public static void distance(final Velocity velocity, final Position destination, final Position source)
    {
        velocity.reset();
        Velocity1.distance(velocity, destination.position, source.position.clone());
    }

    /**
     * @param velocity
     * @param destination
     * @param mimic
     */
    private static void distance(final Velocity velocity, final int[] destination, final int[] mimic)
    {
        for(int counter_destination = 0, destination_size = destination.length; counter_destination < destination_size; ++counter_destination)
        {
            final int value = destination[counter_destination];
            if(value == 0)
            {
                continue;
            }
            if(value != mimic[counter_destination])
            {
                /**
                 * Lookup Destination
                 * */
                int destination_lookup = -1;
                while(value != mimic[++destination_lookup])
                {
                    ;
                }
                velocity.set(counter_destination, destination_lookup);
                /**
                 * Swap
                 * */
                final int temp = mimic[counter_destination];
                mimic[counter_destination] = mimic[destination_lookup];
                mimic[destination_lookup] = temp;
            }
        }
    }
}
