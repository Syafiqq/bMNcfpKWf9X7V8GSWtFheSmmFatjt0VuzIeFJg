package model.pso.component;

import model.helper.HList;

/*
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 6:44 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity extends HList<Transposition>
{
    /**
     * Constructor of Velocity
     *
     * @param expected_size = Size Maximum Of Velocity
     */
    public Velocity(int expected_size)
    {
        super(new Transposition[expected_size]);
        for(int counter = -1; ++counter < expected_size; )
        {
            super.list[counter] = new Transposition(0, 0);
        }
    }

    /**
     * Get distance between two positions
     *
     * @param velocity       : velocity container
     * @param destination    : destination position of particle
     * @param source         : source position of particle (read-only)
     * @param source_mimic   : source position of particle (simulation change)
     * @param temp_container : temporary container for processing
     */
    public static void getDistance(final Velocity velocity, final Position destination, final Position source, final Position source_mimic, final Position temp_container)
    {
        velocity.reset();
        System.arraycopy(source.position, 0, source_mimic.position, 0, source.position.length);
        Velocity.getDistance(velocity, destination.position, source_mimic.position, temp_container.position);
    }

    /**
     * Clone value of velocity
     *
     * @param source      : source of velocity
     * @param destination : destination of velocity
     */
    public static void cloneVelocity(final Velocity source, final Velocity destination)
    {
        destination.reset();
        for(int counter_velocity = -1, velocity_size = source.size(); ++counter_velocity < velocity_size; )
        {
            destination.set(source.get(counter_velocity));
        }
    }

    /**
     * Reversing order of transposition of velocity
     *
     * @param velocity = velocity to be reversed
     */
    public static void reverseVelocity(final Velocity velocity)
    {
        Transposition         temp = new Transposition(0, 0);
        final Transposition[] list = velocity.list;
        for(int i = -1, is = velocity.size() / 2, j = velocity.size() - 1; ++i < is; --j)
        {
            temp.set(list[i]);
            list[i].set(list[j]);
            list[j].set(temp);
        }
    }

    /**
     * Add velocity speed from another velocity
     *
     * @param destination = velocity to be added
     * @param source      = source of velocity
     */
    @SuppressWarnings("Duplicates") public static void additionVelocity(final Velocity destination, final Velocity source)
    {
        final Transposition[] vel_destination  = destination.list;
        final Transposition[] vel_source       = source.list;
        final int             size_destination = destination.size();
        final int             size_source      = source.size();
        boolean               need_arrange     = false;

        if(size_destination <= size_source)
        {
            for(int counter_destination = size_destination * 2, counter_lookup = size_destination, separation_length = size_destination - 1; --separation_length >= 0; )
            {
                vel_destination[counter_destination -= 2].set(vel_destination[--counter_lookup]);
            }

            for(int counter_destination = -1, counter_lookup = -1, insertion_length = size_destination; --insertion_length >= 0; )
            {
                try
                {
                    if(vel_source[++counter_lookup].equalsTransposition(vel_destination[++counter_destination]))
                    {
                        vel_destination[counter_destination].setDefault();
                        vel_destination[++counter_destination].setDefault();
                        need_arrange = true;
                    }
                    else if(vel_source[counter_lookup].equalsTransposition(vel_destination[counter_destination + 2]))
                    {
                        vel_destination[++counter_destination].setDefault();
                        vel_destination[counter_destination + 1].setDefault();
                        need_arrange = true;
                    }
                    else
                    {
                        vel_destination[++counter_destination].set(vel_source[counter_lookup]);
                    }
                }
                catch(ArrayIndexOutOfBoundsException ignored)
                {
                    vel_destination[++counter_destination].set(vel_source[counter_lookup]);
                }
            }

            for(int counter_destination = size_destination * 2 - 1, counter_lookup = size_destination - 1, remaining_length = size_source - size_destination; --remaining_length >= 0; )
            {
                vel_destination[++counter_destination].set(vel_source[++counter_lookup]);
            }
        }
        else
        {
            for(int counter_destination = size_source + size_destination, counter_lookup = size_destination, remaining_length = size_destination - size_source; --remaining_length >= 0; )
            {
                vel_destination[--counter_destination].set(vel_destination[--counter_lookup]);
            }

            for(int counter_destination = size_source * 2, counter_lookup = size_source, separation_length = size_source - 1; --separation_length >= 0; )
            {
                vel_destination[counter_destination -= 2].set(vel_destination[--counter_lookup]);
            }

            for(int counter_destination = -1, counter_lookup = -1, insertion_length = size_source; --insertion_length >= 0; )
            {
                if(vel_source[++counter_lookup].equalsTransposition(vel_destination[++counter_destination]))
                {
                    vel_destination[counter_destination].setDefault();
                    vel_destination[++counter_destination].setDefault();
                    need_arrange = true;
                }
                else if(vel_source[counter_lookup].equalsTransposition(vel_destination[counter_destination + 2]))
                {
                    vel_destination[++counter_destination].setDefault();
                    vel_destination[counter_destination + 1].setDefault();
                    need_arrange = true;
                }
                else
                {
                    vel_destination[++counter_destination].set(vel_source[counter_lookup]);
                }
            }
        }
        if(need_arrange)
        {
            int counter_repair = -1;
            for(int repair_lookup = -1, velocity_size = size_destination + size_source; ++repair_lookup < velocity_size; )
            {
                if((!vel_destination[repair_lookup].isDefault()) && (repair_lookup != ++counter_repair))
                {
                    vel_destination[counter_repair].set(vel_destination[repair_lookup]);
                }
            }
            destination.moveTo(counter_repair + 1);
        }
        else
        {
            destination.moveTo(size_destination + size_source);
        }
    }

    /**
     * Multiplication of velocity given coefficient
     *
     * @param coefficient    = coefficient to be multiplicate
     * @param velocity       = velocity
     * @param temp_container = container of velocity if needed for processing
     */
    public static void multiplicationVelocity(double coefficient, final Velocity velocity, final Velocity temp_container)
    {
        if(!velocity.isEmpty())
        {
            if(coefficient == 0.0)
            {
                velocity.reset();
            }
            else if(Math.abs(coefficient) <= 1.0)
            {
                if(coefficient < 0.0)
                {
                    reverseVelocity(velocity);
                }
                velocity.backward((int) Math.floor(velocity.size() * (1.0 - Math.abs(coefficient))));
            }
            else
            {
                if(coefficient < 0.0)
                {
                    reverseVelocity(velocity);
                    coefficient = Math.abs(coefficient);
                }
                cloneVelocity(velocity, temp_container);
                int natural_coefficient = (int) Math.floor(coefficient);
                coefficient -= natural_coefficient;
                for(int i = 1; i < natural_coefficient; ++i)
                {
                    Velocity.additionVelocity(velocity, temp_container);
                }
                //Keep that line below
                Velocity.multiplicationVelocity(coefficient, temp_container, null);
                Velocity.additionVelocity(velocity, temp_container);
            }
        }
    }

    /**
     * Get distance between two positions
     *
     * @param velocity       : velocity container
     * @param destinations   : destination position of particle
     * @param mimic          : source position of particle (simulation change)
     * @param temp_container : temporary container for processing
     */
    private static void getDistance(final Velocity velocity, final int[] destinations, final int[] mimic, final int[] temp_container)
    {
        for(int i = -1, is = mimic.length; ++i < is; )
        {
            int value = mimic[i];
            if(value != 0)
            {
                temp_container[value] = i;
            }
        }

        for(int counter_destination = -1, destination_size = destinations.length; ++counter_destination < destination_size; )
        {
            final int destination = destinations[counter_destination];
            if(destination != 0)
            {
                if(counter_destination != temp_container[destination])
                {
                    velocity.set(counter_destination, temp_container[destination]);

                    /*
                     * Swap Mimic
                     * */
                    mimic[temp_container[destination]] = mimic[counter_destination];

                    /*
                     * Swap Temp Container
                     * */
                    temp_container[mimic[counter_destination]] = temp_container[destination];
                }
            }
        }
    }

    public static String toString(final Velocity[] velocities, int indent)
    {
        String storedIndent = "";
        for(int i = indent; --i >= 0; )
        {
            storedIndent += '\t';
        }

        StringBuilder sb = new StringBuilder();
        for(int i = -1, is = velocities.length; ++i < is; )
        {
            sb.append(String.format("%s%14s : %s\n", storedIndent, String.format("%s [%d]", "Velocity", i), velocities[i]));
        }
        return sb.toString();
    }

    /**
     * Insert Transposition to Velocity
     *
     * @param transpositions = Set of Transposition to be inserted to velocity
     */
    public void set(final Transposition... transpositions)
    {
        for(final Transposition transposition : transpositions)
        {
            this.set(transposition);
        }
    }

    /**
     * Insert Transposition to Velocity
     *
     * @param transposition Transposition to be inserted
     */
    @Override public void set(Transposition transposition)
    {
        super.list[++super.counter].set(transposition.source, transposition.destination);
    }

    /**
     * Insert Transposition to Velocity
     *
     * @param source      : Source of index
     * @param destination : Destination of index
     */
    public void set(int source, int destination)
    {
        super.list[++super.counter].set(source, destination);
    }

    /**
     * Check whether velocity is empty or not
     *
     * @return velocity empty state
     */
    public boolean isEmpty()
    {
        return super.counter == -1;
    }

    /**
     * Insert Transposition with checking index first
     *
     * @param transposition : transposition to be inserted
     */
    public void checkAndSet(final Transposition transposition)
    {
        if((super.counter != -1) && (super.list[super.counter].equalsTransposition(transposition)))
        {
            super.backward(1);
        }
        else
        {
            super.list[++super.counter].set(transposition);
        }
    }

    public String toString()
    {
        if(this.isEmpty())
        {
            return "[\u2205]";
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            sb.append(super.list[0]);
            for(int i = 0, is = super.counter + 1; ++i < is; )
            {
                sb.append(", ");
                sb.append(super.list[i]);
            }
            sb.append(']');
            return sb.toString();
        }
    }
}
