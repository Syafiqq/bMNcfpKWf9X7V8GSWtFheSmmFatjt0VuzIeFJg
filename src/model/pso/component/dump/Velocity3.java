package model.pso.component.dump;

import model.pso.component.Transposition;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 23 May 2016, 10:34 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity3 extends Velocity2
{
    public Velocity3(int expected_size)
    {
        super(expected_size);
    }

    /**
     * @param velocity1
     * @param velocity2
     */
    @SuppressWarnings("Duplicates") public static void additionVelocity(final Velocity3 velocity1, final Velocity3 velocity2)
    {
        final Transposition[] velocity_1     = velocity1.list;
        final Transposition[] velocity_2     = velocity2.list;
        final int             velocity1_size = velocity1.size();
        final int             velocity2_size = velocity2.size();
        boolean               need_arrange   = false;

        if(velocity1_size <= velocity2_size)
        {
            for(int counter_destination = velocity1_size * 2, counter_lookup = velocity1_size, separation_length = velocity1_size - 1; --separation_length >= 0; )
            {
                velocity_1[counter_destination -= 2].set(velocity_1[--counter_lookup]);
            }

            for(int counter_destination = -1, counter_lookup = -1, insertion_length = velocity1_size; --insertion_length >= 0; )
            {
                try
                {
                    if(velocity_2[++counter_lookup].equalsTransposition(velocity_1[++counter_destination]))
                    {
                        velocity_1[counter_destination].setDefault();
                        velocity_1[++counter_destination].setDefault();
                        need_arrange = true;
                    }
                    else if(velocity_2[counter_lookup].equalsTransposition(velocity_1[counter_destination + 2]))
                    {
                        velocity_1[++counter_destination].setDefault();
                        velocity_1[counter_destination + 1].setDefault();
                        need_arrange = true;
                    }
                    else
                    {
                        velocity_1[++counter_destination].set(velocity_2[counter_lookup]);
                    }
                }
                catch(ArrayIndexOutOfBoundsException ignored)
                {
                    velocity_1[++counter_destination].set(velocity_2[counter_lookup]);
                }
            }

            for(int counter_destination = velocity1_size * 2 - 1, counter_lookup = velocity1_size - 1, remaining_length = velocity2_size - velocity1_size; --remaining_length >= 0; )
            {
                velocity_1[++counter_destination].set(velocity_2[++counter_lookup]);
            }
        }
        else
        {
            for(int counter_destination = velocity2_size + velocity1_size, counter_lookup = velocity1_size, remaining_length = velocity1_size - velocity2_size; --remaining_length >= 0; )
            {
                velocity_1[--counter_destination].set(velocity_1[--counter_lookup]);
            }

            for(int counter_destination = velocity2_size * 2, counter_lookup = velocity2_size, separation_length = velocity2_size - 1; --separation_length >= 0; )
            {
                velocity_1[counter_destination -= 2].set(velocity_1[--counter_lookup]);
            }

            for(int counter_destination = -1, counter_lookup = -1, insertion_length = velocity2_size; --insertion_length >= 0; )
            {
                if(velocity_2[++counter_lookup].equalsTransposition(velocity_1[++counter_destination]))
                {
                    velocity_1[counter_destination].setDefault();
                    velocity_1[++counter_destination].setDefault();
                    need_arrange = true;
                }
                else if(velocity_2[counter_lookup].equalsTransposition(velocity_1[counter_destination + 2]))
                {
                    velocity_1[++counter_destination].setDefault();
                    velocity_1[counter_destination + 1].setDefault();
                    need_arrange = true;
                }
                else
                {
                    velocity_1[++counter_destination].set(velocity_2[counter_lookup]);
                }
            }
        }
        if(need_arrange)
        {
            int counter_repair = -1;
            for(int repair_lookup = -1, velocity_size = velocity1_size + velocity2_size; ++repair_lookup < velocity_size; )
            {
                if((!velocity_1[repair_lookup].isDefault()) && (repair_lookup != ++counter_repair))
                {
                    velocity_1[counter_repair].set(velocity_1[repair_lookup]);
                }
            }
            velocity1.moveTo(counter_repair + 1);
        }
        else
        {
            velocity1.moveTo(velocity1_size + velocity2_size);
        }
    }

    /**
     * @param coefficient
     * @param velocity
     * @param temp_container
     */
    public static void multiplicationVelocity(double coefficient, final Velocity3 velocity, final Velocity3 temp_container)
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
                    Velocity3.additionVelocity(velocity, temp_container);
                }
                //Keep that line below
                Velocity3.multiplicationVelocity(coefficient, temp_container, null);
                Velocity3.additionVelocity(velocity, temp_container);
            }
        }
    }
}
