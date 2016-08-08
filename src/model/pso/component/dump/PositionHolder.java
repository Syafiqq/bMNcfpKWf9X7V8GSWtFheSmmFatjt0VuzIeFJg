package model.pso.component.dump;

import model.pso.component.Position;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 4:52 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PositionHolder
{
    public Position[] positions;

    public PositionHolder(int cluster_size)
    {
        this.positions = new Position[cluster_size];
    }
}
