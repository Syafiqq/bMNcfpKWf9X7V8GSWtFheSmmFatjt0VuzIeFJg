package model.pso.component.dump;

import model.pso.component.Position;
import model.pso.component.Transposition;
import model.pso.helper.HList;

/**
 * This <Skripsi_003> project in package <model.pso.helper> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 5:18 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Velocity extends HList<Transposition>
{
    public Velocity(int expected_size)
    {
        super(new Transposition[expected_size]);
        final Transposition[] transpositions = super.list;
        for(int counter = -1; ++counter < expected_size; )
        {
            transpositions[counter] = new Transposition(0, 0);
        }
    }

    public static void reverse(final Velocity velocity)
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

    public static void distance(final Velocity velocity, final Position destination, final Position source)
    {
        velocity.reset();
        Velocity.distance(velocity, destination.position, source.position.clone());
    }

    private static void distance(final Velocity velocity, final int[] destination, final int[] mimic)
    {
        for(int i = 0, is = destination.length; i < is; ++i)
        {
            int vd = destination[i];
            if(vd != mimic[i])
            {
                int j = i + 1;
                while(vd != mimic[j])
                {
                    ++j;
                }
                velocity.set(i, j);
                final int temp = mimic[i];
                mimic[i] = mimic[j];
                mimic[j] = temp;
            }
        }
    }

    public static void cloneVelocity(final Velocity source, final Velocity destination)
    {
        destination.reset();
        for(int counter_velocity = -1, velocity_size = source.size(); ++counter_velocity < velocity_size; )
        {
            destination.set(source.get(counter_velocity));
        }
    }

    public void set(final Transposition... transpositions)
    {
        for(final Transposition transposition : transpositions)
        {
            this.set(transposition);
        }
    }

    @Override public void set(Transposition data)
    {
        super.list[++super.counter].set(data.source, data.destination);
    }

    public void set(int source, int destination)
    {
        super.list[++super.counter].set(source, destination);
    }

    public boolean isEmpty()
    {
        return super.counter == -1;
    }

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
}
