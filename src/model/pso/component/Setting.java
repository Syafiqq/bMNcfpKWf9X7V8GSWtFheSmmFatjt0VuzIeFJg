package model.pso.component;

/*
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Setting
{
    private final static Setting ourInstance = new Setting();

    public int max_particle = 0;
    public int max_epoch    = 0;

    public double bloc_min  = 0.0;
    public double bloc_max  = 0;
    public double bglob_min = 0.0;
    public double bglob_max = 0;
    public double brand_min = 0.0;
    public double brand_max = 0;

    public int total_core;

    private Setting()
    {

    }

    public static Setting getInstance()
    {
        return ourInstance;
    }
}
