package model.pso.component;

import java.util.Arrays;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 7:24 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Position
{
    public int[] position;

    public Position()
    {
        this.position = null;
    }

    public Position(int[] position)
    {
        this.position = position;
    }

    public static Position newInstance(int[] source_position)
    {
        Position position = Position.newInstance(source_position.length);
        System.arraycopy(source_position, 0, position.position, 0, source_position.length);
        return position;
    }

    public static Position newInstance(int length)
    {
        Position position = new Position();
        position.position = new int[length];
        return position;
    }

    public static void replace(final Position target, final Position source)
    {
        System.arraycopy(source.position, 0, target.position, 0, source.position.length);
    }

    public static void update(final Position position, final Velocity velocity)
    {
        for(int counter_velocity = -1, velocity_size = velocity.size(); ++counter_velocity < velocity_size; )
        {
            position.update(velocity.get(counter_velocity));
        }
    }

    public static String toString(final Position[] positions, int indent)
    {
        String storedIndent = "";
        for(int i = indent; --i >= 0; )
        {
            storedIndent += '\t';
        }

        StringBuilder sb = new StringBuilder();
        for(int i = -1, is = positions.length; ++i < is; )
        {
            sb.append(String.format("%s%12s : %s\n", storedIndent, String.format("%s [%d]", "Position", i), positions[i]));
        }
        return sb.toString();
    }

    private void update(final Transposition transposition)
    {
        final int temp = this.position[transposition.source];
        this.position[transposition.source] = this.position[transposition.destination];
        this.position[transposition.destination] = temp;
    }

    @Override public String toString()
    {
        return Arrays.toString(this.position);
    }
}
