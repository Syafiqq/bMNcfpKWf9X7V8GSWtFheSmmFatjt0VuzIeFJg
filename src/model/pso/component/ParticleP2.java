package model.pso.component;

import java.util.Comparator;
import model.dataset.component.ScheduleShufflingProperties;
import model.helper.IntHList;
import model.pso.core.ScheduleRandomable;

/*
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:04 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ParticleP2 extends ParticleBuilder<Data, Velocity[]>
{
    public static final Comparator<ParticleP2> particlePbestFitnessDescComparator = (p1, p2) -> (int) (p2.pBest.fitness - p1.pBest.fitness);
    public static final Comparator<ParticleP2> particleDataFitnessAscComparator   = (p1, p2) -> (int) (p1.data.fitness - p2.data.fitness);

    public final Setting             setting;
    public final VelocityProperties  velocity_properties;
    public final PlacementProperties placement_properties;
    public final RepairProperties[]  repair_properties;
    public final IntHList[]          lesson_conflicts;


    public ParticleP2(final ScheduleShufflingProperties builder, final ScheduleRandomable<Position[]> randomable, final Setting setting, PlacementProperties placement_properties, RepairProperties[] repair_properties)
    {
        this.setting = setting;
        super.data = new Data(randomable.random(builder));
        super.pBest = Data.newInstance(super.data.positions);
        super.velocity = new Velocity[super.data.positions.length];
        this.lesson_conflicts = new IntHList[super.data.positions.length];
        for(int counter_position = -1, position_size = super.data.positions.length; ++counter_position < position_size; )
        {
            this.lesson_conflicts[counter_position] = new IntHList(super.data.positions[counter_position].position.length);
            //int velocity_bound = super.data.positions[counter_position].position.length * (int) Math.ceil(this.setting.brand_max);
            int velocity_bound = super.data.positions[counter_position].position.length * (int) Math.ceil(this.setting.brand);
            velocity_bound = velocity_bound == 0 ? super.data.positions[counter_position].position.length : velocity_bound;
            super.velocity[counter_position] = new Velocity(velocity_bound);
        }
        this.velocity_properties = new VelocityProperties(builder, randomable, setting, super.data.positions);
        this.placement_properties = placement_properties;
        this.repair_properties = repair_properties;
    }

    @Override public void assignPBest()
    {
        if(super.data.fitness > super.pBest.fitness)
        {
            Data.replaceData(super.pBest, super.data);
        }/*
        else if (super.data.fitness < super.pBest.fitness)
        {
            Data.replaceData(super.data, super.pBest);
        }*/
    }

    @Override public void calculateVelocity(Data gBest, int cEpoch, int max_epoch)
    {
        final VelocityProperties property = this.velocity_properties;

        double random_coefficient;
        double constants_coefficient;

        property.initializePrand();
        property.initializeDloc(super.data);
        property.initializeDglob(super.data);

        random_coefficient = this.setting.random.nextDouble();
        //constants_coefficient = ((this.setting.bloc_max - this.setting.bloc_min) * (cEpoch * 1f / max_epoch)) + this.setting.bloc_min;
        constants_coefficient = this.setting.bloc;
        for(int counter_data = -1, data_size = super.data.positions.length; ++counter_data < data_size; )
        {
            Velocity.getDistance(property.velocity_temporary[counter_data], super.pBest.positions[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Position.update(property.dloc[counter_data], property.velocity_temporary[counter_data]);
        }

        random_coefficient = this.setting.random.nextDouble();
        //constants_coefficient = this.setting.bglob_max - ((this.setting.bglob_max - this.setting.bglob_min) * (cEpoch * 1f / max_epoch));
        constants_coefficient = this.setting.bglob;
        for(int counter_data = -1, data_size = super.data.positions.length; ++counter_data < data_size; )
        {
            Velocity.getDistance(property.velocity_temporary[counter_data], gBest.positions[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Position.update(property.dglob[counter_data], property.velocity_temporary[counter_data]);
        }

        random_coefficient = this.setting.random.nextDouble();
        //constants_coefficient = this.setting.brand_max - ((this.setting.brand_max - this.setting.brand_min) * (cEpoch * 1f / max_epoch));
        constants_coefficient = this.setting.brand;
        for(int counter_data = -1, data_size = super.data.positions.length; ++counter_data < data_size; )
        {
            Velocity.getDistance(super.velocity[counter_data], property.prand[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, super.velocity[counter_data], property.velocity_container[counter_data]);
        }

        for(int counter_data = -1, data_size = super.data.positions.length; ++counter_data < data_size; )
        {
            Velocity.getDistance(property.velocity_temporary[counter_data], property.dloc[counter_data], property.dglob[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(0.5, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Position.update(property.dglob[counter_data], property.velocity_temporary[counter_data]);
            Position.update(property.dglob[counter_data], super.velocity[counter_data]);
        }
    }

    @Override public void updateData()
    {
        Data.replacePositon(super.data.positions, this.velocity_properties.dglob);
    }

    public String toString(int indent)
    {
        String storedIndent = "";
        for(int i = indent; --i >= 0; )
        {
            storedIndent += '\t';
        }

        return String.format("%s{\n", storedIndent)
                + String.format("%s\t%s : \n%s\n\n", storedIndent, "Data", super.data.toString(indent + 1))
                + String.format("%s\t%s : [%s = %b]\n%s\n\n", storedIndent, "P-Best", "Data : P-Best", super.data == super.pBest, super.pBest.toString(indent + 1))
                + String.format("%s\t%s : \n%s\t{\n", storedIndent, "Velocities", storedIndent) + String.format("%s", Velocity.toString(super.velocity, indent + 2))
                + String.format("%s\t}\n\n", storedIndent) + String.format("%s\t%s : \n%s\n", storedIndent, "Velocity Properties", this.velocity_properties.toString(indent + 1))
                + String.format("%s}", storedIndent);
    }
}
