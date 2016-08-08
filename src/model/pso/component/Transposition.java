package model.pso.component;

/**
 * This <Skripsi_003> project in package <model.pso.component> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 4:52 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Transposition
{
    public int source;
    public int destination;

    public Transposition(int source, int destination)
    {
        this.set(source, destination);
    }

    public void set(int source, int destination)
    {
        this.source = source;
        this.destination = destination;
    }

    public boolean equalsTransposition(final Transposition t)
    {
        return (this.source == t.source) && (this.destination == t.destination);
    }

    public void set(final Transposition t)
    {
        this.set(t.source, t.destination);
    }

    @Override public String toString()
    {
        return "{" + source + ", " + destination + "}";
    }

    public void setDefault()
    {
        this.source = this.destination = 0;
    }

    public boolean isDefault()
    {
        return (this.source == 0) && (this.destination == 0);
    }
}
