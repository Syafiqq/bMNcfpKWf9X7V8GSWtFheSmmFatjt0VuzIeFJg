package model.pso.component;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 7:33 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Data
{
    public Position[] positions;
    public double     fitness;

    public Data()
    {
        this(null);
    }

    public Data(final Position[] positions)
    {
        this.positions = positions;
        this.fitness = 0.0;
    }

    public static Data newInstance(Position[] positions)
    {
        Data new_data = Data.newInstance(positions.length);
        for(int counter_position = -1, position_size = positions.length; ++counter_position < position_size; )
        {
            new_data.positions[counter_position] = Position.newInstance(positions[counter_position].position);
        }
        return new_data;
    }

    public static Data newInstance(final int length)
    {
        Data new_data = new Data();
        new_data.positions = new Position[length];
        return new_data;
    }

    public static void replaceData(final Data destination, final Data source)
    {
        Data.replacePositon(destination.positions, source.positions);
        destination.fitness = source.fitness;
    }

    public static void replacePositon(final Position[] destination, final Position[] source)
    {
        for(int counter_position = -1, position_size = source.length; ++counter_position < position_size; )
        {
            System.arraycopy(source[counter_position].position, 0, destination[counter_position].position, 0, source[counter_position].position.length);
        }
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
        sb.append(String.format("%s", Position.toString(this.positions, indent + 1)));
        sb.append(String.format("%s\t%12s : %g\n", storedIndent, "Fitness", this.fitness));
        sb.append(String.format("%s}", storedIndent));
        return sb.toString();
    }

    @Override public String toString()
    {
        return this.toString(0);
    }
}
