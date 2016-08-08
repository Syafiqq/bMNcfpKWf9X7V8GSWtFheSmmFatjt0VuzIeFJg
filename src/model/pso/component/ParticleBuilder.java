package model.pso.component;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:00 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class ParticleBuilder<Data, Velocity>
{
    public Data     data;
    public Data     pBest;
    public Velocity velocity;

    public abstract void assignPBest();

    public abstract void calculateVelocity(Data gBest);

    public abstract void updateData();
}
