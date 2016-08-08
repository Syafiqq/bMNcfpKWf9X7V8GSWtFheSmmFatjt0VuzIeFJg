package model.pso.core;

import model.pso.component.ParticleBuilder;

/**
 * This <Skripsi_003> project in package <model.pso.core> created by :
 * Name         : syafiq
 * Date / Time  : 26 May 2016, 6:23 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class PSOOperation<Data, Velocity, Particle extends ParticleBuilder<Data, Velocity>> implements StoppingCondition, SwarmInitialization
{
    public Particle[] particles;
    public Data       gBest;
    public int        cEpoch;

    public void run()
    {
        this.initializeSwarm();
        this.updateSwarmFitness();
        while(!this.isConditionSatisfied())
        {
            for(Particle particle : this.particles)
            {
                particle.assignPBest();
            }
            this.assignGBest();
            for(Particle particle : this.particles)
            {
                particle.calculateVelocity(this.gBest, this.cEpoch, Integer.MAX_VALUE);
                particle.updateData();
                this.repairData(particle);
                this.calculateFitness(particle);
            }
            this.updateStoppingCondition();
        }
    }

    protected abstract void updateSwarmFitness();

    protected abstract void assignGBest();

    protected abstract void calculateFitness(Particle data);

    protected abstract void repairData(Particle data);
}
