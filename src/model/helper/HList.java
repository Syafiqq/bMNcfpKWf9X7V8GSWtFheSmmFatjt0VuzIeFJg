package model.helper;

/**
 * This <Skripsi_003> project in package <model.pso.helper> created by :
 * Name         : syafiq
 * Date / Time  : 21 May 2016, 5:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class HList<Data_Type>
{
    public final Data_Type[] list;
    protected    int         counter;

    protected HList()
    {
        this(null);
    }

    public HList(Data_Type[] transpositions)
    {
        list = transpositions;
        this.counter = -1;
    }

    public abstract void set(Data_Type data);

    public Data_Type get()
    {
        return this.get(this.counter);
    }

    @SuppressWarnings("ConstantConditions") public Data_Type get(int index)
    {
        return this.list[index];
    }

    public void reset()
    {
        this.counter = -1;
    }

    public void backward(int length)
    {
        this.counter -= length;
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

    public void forward(int i)
    {
        this.counter += i;
    }


    public void moveTo(int length)
    {
        this.counter = length - 1;
    }
}
