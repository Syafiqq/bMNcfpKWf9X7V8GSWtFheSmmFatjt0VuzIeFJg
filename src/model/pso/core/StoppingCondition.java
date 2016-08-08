package model.pso.core;

/*
 * This <Skripsi_003> project in package <model.pso.core> created by :
 * Name         : syafiq
 * Date / Time  : 26 May 2016, 6:25 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public interface StoppingCondition
{
    void updateStoppingCondition();

    boolean isConditionSatisfied();
}
