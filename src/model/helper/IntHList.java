package model.helper;

/**
 * This <Skripsi_003> project in package <model.helper> created by :
 * Name         : syafiq
 * Date / Time  : 16 June 2016, 11:00 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class IntHList
{
    public final int[]   list;
    public       int     counter;
    private      boolean empty;

    public IntHList(int size)
    {
        this.list = new int[size];
        this.counter = -1;
    }

    public void add(int value)
    {
        this.list[++this.counter] = value;
    }

    public int get()
    {
        return this.get(this.counter);
    }

    @SuppressWarnings("ConstantConditions") public int get(int index)
    {
        return this.list[index];
    }

    public void reset()
    {
        this.counter = -1;
    }

    public int size()
    {
        return counter + 1;
    }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.counter == -1)
        {
            return "-";
        }
        else
        {
            sb.append('{');
            sb.append(this.list[0]);
            for(int i = 0, is = this.counter + 1; ++i < is; )
            {
                sb.append(',');
                sb.append(' ');
                sb.append(this.list[i]);
            }
            sb.append('}');
            return sb.toString();
        }
    }
}
