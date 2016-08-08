package model.pso.component;

import model.dataset.component.ScheduleShufflingProperties;
import model.pso.core.ScheduleRandomable;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:04 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ParticleP2 extends ParticleBuilder<Data, Velocity[]>
{
    public Setting             setting;
    public VelocityProperties  velocity_properties;
    public PlacementProperties placement_properties;
    public RepairProperties[]  repair_properties;

    public ParticleP2(final ScheduleShufflingProperties builder, final ScheduleRandomable<Position[]> randomable, final Setting setting, PlacementProperties placement_properties, RepairProperties[] repair_properties)
    {
        this.setting = setting;
        super.data = new Data(randomable.random(builder));
        super.pBest = Data.newInstance(super.data.positions);
        super.velocity = new Velocity[super.data.positions.length];
        for(int counter_position = -1, position_size = super.data.positions.length; ++counter_position < position_size; )
        {
            super.velocity[counter_position] = new Velocity(super.data.positions[counter_position].position.length * (int) Math.ceil(this.setting.brand));
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
        }
    }

    @Override public void calculateVelocity(Data gBest)
    {
        final VelocityProperties property = this.velocity_properties;

        double random_coefficient;
        double constants_coefficient;

        property.initializePrand();
        property.initializeDloc(super.data);
        property.initializeDglob(super.data);

        for(int counter_data = -1, data_size = super.data.positions.length; ++counter_data < data_size; )
        {
            random_coefficient = this.setting.random.nextDouble();
            constants_coefficient = this.setting.bloc;
            Velocity.getDistance(property.velocity_temporary[counter_data], super.pBest.positions[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Position.update(property.dloc[counter_data], property.velocity_temporary[counter_data]);

            random_coefficient = this.setting.random.nextDouble();
            constants_coefficient = this.setting.bglob;
            Velocity.getDistance(property.velocity_temporary[counter_data], gBest.positions[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Position.update(property.dglob[counter_data], property.velocity_temporary[counter_data]);

            random_coefficient = this.setting.random.nextDouble();
            constants_coefficient = this.setting.brand;
            Velocity.getDistance(super.velocity[counter_data], property.prand[counter_data], super.data.positions[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(random_coefficient * constants_coefficient, super.velocity[counter_data], property.velocity_container[counter_data]);

            Velocity.getDistance(property.velocity_temporary[counter_data], property.dloc[counter_data], property.dglob[counter_data], property.position_mimic[counter_data], property.position_container[counter_data]);
            Velocity.multiplicationVelocity(0.5, property.velocity_temporary[counter_data], property.velocity_container[counter_data]);
            Velocity.additionVelocity(property.velocity_temporary[counter_data], super.velocity[counter_data]);
            Position.update(property.dglob[counter_data], property.velocity_temporary[counter_data]);
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

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s{\n", storedIndent));
        sb.append(String.format("%s\t%s : \n%s\n\n", storedIndent, "Data", super.data.toString(indent + 1)));
        sb.append(String.format("%s\t%s : [%s = %b]\n%s\n\n", storedIndent, "P-Best", "Data : P-Best", super.data == super.pBest, super.pBest.toString(indent + 1)));
        sb.append(String.format("%s\t%s : \n%s\t{\n", storedIndent, "Velocities", storedIndent));
        sb.append(String.format("%s", Velocity.toString(super.velocity, indent + 2)));
        sb.append(String.format("%s\t}\n\n", storedIndent));
        sb.append(String.format("%s\t%s : \n%s\n", storedIndent, "Velocity Properties", this.velocity_properties.toString(indent + 1)));
        sb.append(String.format("%s}", storedIndent));


        return sb.toString();
    }
}
