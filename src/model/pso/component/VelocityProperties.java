package model.pso.component;

import model.dataset.component.ScheduleShufflingProperties;
import model.pso.core.ScheduleRandomable;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:23 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class VelocityProperties
{
    public final Position[]                     dloc;
    public final Position[]                     dglob;
    public final Position[]                     prand;
    public final Position[]                     position_mimic;
    public final Position[]                     position_container;
    public final Velocity[]                     velocity_container;
    public final Velocity[]                     velocity_temporary;
    public final ScheduleRandomable<Position[]> random_generator;
    public final ScheduleShufflingProperties    rand_properties;

    public VelocityProperties(final ScheduleShufflingProperties builder, final ScheduleRandomable<Position[]> randomable, final Setting setting, final Position[] sample_position)
    {
        final int sample_size = sample_position.length;
        this.random_generator = randomable;
        this.dloc = new Position[sample_size];
        this.dglob = new Position[sample_size];
        this.prand = new Position[sample_size];
        this.position_mimic = new Position[sample_size];
        this.position_container = new Position[sample_size];
        this.velocity_temporary = new Velocity[sample_size];
        this.velocity_container = new Velocity[sample_size];
        this.rand_properties = ScheduleShufflingProperties.newInstance(builder);

        for(int counter_sample = -1; ++counter_sample < sample_size; )
        {
            int max = 0;
            for(int current_max : sample_position[counter_sample].position)
            {
                max = current_max > max ? current_max : max;
            }

            int position_length = sample_position[counter_sample].position.length;
            this.dloc[counter_sample] = Position.newInstance(position_length);
            this.dglob[counter_sample] = Position.newInstance(position_length);
            this.prand[counter_sample] = Position.newInstance(position_length);
            this.position_mimic[counter_sample] = Position.newInstance(position_length);
            this.position_container[counter_sample] = Position.newInstance(max + 1);
            int velocity_bound = (position_length * (int) Math.ceil(Math.max(setting.bloc_max, setting.bglob_max)));
            velocity_bound = velocity_bound == 0 ? position_length * 2 : velocity_bound;
            this.velocity_temporary[counter_sample] = new Velocity(velocity_bound);
            this.velocity_container[counter_sample] = new Velocity(velocity_bound);
        }
    }

    public void initializeDloc(final Data data)
    {
        this.initializePositionFromData(this.dloc, data.positions);
    }

    public void initializeDglob(final Data data)
    {
        this.initializePositionFromData(this.dglob, data.positions);
    }

    public void initializePrand()
    {
        this.random_generator.random(this.rand_properties, this.prand);
    }

    private void initializePositionFromData(final Position[] destination, final Position[] source)
    {
        for(int counter_position = -1, position_size = source.length; ++counter_position < position_size; )
        {
            Position.replace(destination[counter_position], source[counter_position]);
        }
    }

    public String toString(int indent)
    {
        String storedIndent = "";
        for(int i = indent; --i >= 0; )
        {
            storedIndent += '\t';
        }

        return String.format("%s{\n", storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "D-Loc", storedIndent, Position.toString(this.dloc, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "D-Glob", storedIndent, Position.toString(this.dglob, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "D-Rand", storedIndent, Position.toString(this.prand, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "Position Mimic", storedIndent, Position.toString(this.position_mimic, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "Position Container", storedIndent, Position.toString(this.position_container, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n\n", storedIndent, "Velocity Container", storedIndent, Velocity.toString(this.velocity_container, indent + 2), storedIndent)
                + String.format("%s\t%s : \n%s\t{\n%s%s\t}\n", storedIndent, "Velocity Temporary", storedIndent, Velocity.toString(this.velocity_temporary, indent + 2), storedIndent)
                + String.format("%s}", storedIndent);
    }
}
