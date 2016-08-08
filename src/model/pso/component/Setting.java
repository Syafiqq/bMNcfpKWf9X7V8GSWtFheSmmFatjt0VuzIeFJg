package model.pso.component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 25 May 2016, 8:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Setting
{
    private final static Setting ourInstance = new Setting();

    public final Random random;
    public int    MAX_PARTICLES = 0;
    public int    MAX_EPOCHS    = 0;
    public double bloc          = 0.0;
    public double bglob         = 0.0;
    public double brand         = 0.0;

    private Setting()
    {
        this.random = ThreadLocalRandom.current();
    }

    public static Setting getInstance()
    {
        return ourInstance;
    }
}
