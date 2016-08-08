package model.assets.com.github.ashkrit.concurrentqueue;

/**
 * This <Skripsi_003> project in package <model.assets.com.github.ashkrit.concurrentqueue> created by :
 * Name         : syafiq
 * Date / Time  : 27 June 2016, 2:46 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 *
 * @see : https://github.com/ashkrit/blog/blob/master/src/main/java/concurrentqueue/CLBlockingQueue.java
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class CLBlockingQueue<E> implements BlockingQueue<E>
{

    private ConcurrentLinkedQueue<E> queue = new ConcurrentLinkedQueue<>();
    private WaitStrategy waitStrategy;

    public CLBlockingQueue(WaitStrategy waitStrategy)
    {
        this.waitStrategy = waitStrategy;
    }

    @Override public E remove()
    {
        return this.queue.remove();
    }

    @Override public boolean addAll(Collection<? extends E> c)
    {
        return this.queue.addAll(c);
    }

    @Override public E poll()
    {
        return queue.poll();
    }

    @Override public E peek()
    {
        return queue.peek();
    }

    @Override public int size()
    {
        return queue.size();
    }

    @Override public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    @Override public Iterator<E> iterator()
    {
        return queue.iterator();
    }

    @Override public Object[] toArray()
    {
        return this.queue.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall") @Override public <T> T[] toArray(T[] a)
    {
        return this.queue.toArray(a);
    }

    @Override public boolean containsAll(Collection<?> c)
    {
        return this.queue.containsAll(c);
    }

    @Override public boolean removeAll(Collection<?> c)
    {
        return this.queue.removeAll(c);
    }

    @Override public boolean retainAll(Collection<?> c)
    {
        return this.queue.retainAll(c);
    }

    @Override public void clear()
    {
        this.queue.clear();
    }

    @Override public boolean add(E e)
    {
        return this.queue.add(e);
    }

    @Override public boolean offer(E e)
    {
        queue.offer(e);
        waitStrategy.release();
        return true;
    }

    @Override public void put(E e) throws InterruptedException
    {
        queue.add(e);
        waitStrategy.release();
    }

    @Override public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException
    {
        put(e);
        return true;
    }

    @Override public E take() throws InterruptedException
    {
        while(true)
        {
            if(Thread.currentThread().isInterrupted())
            {
                return null;
            }
            E value = queue.poll();
            if(value != null)
            {
                return value;
            }
            waitStrategy.block();
        }
    }

    @Override public E poll(long timeout, TimeUnit unit) throws InterruptedException
    {
        throw new IllegalArgumentException();
    }

    @Override public int remainingCapacity()
    {
        throw new IllegalArgumentException();
    }

    @Override public boolean remove(Object o)
    {
        throw new IllegalArgumentException();
    }

    @Override public boolean contains(Object o)
    {
        throw new IllegalArgumentException();
    }

    @Override public int drainTo(Collection<? super E> c)
    {
        throw new IllegalArgumentException();
    }

    @Override public int drainTo(Collection<? super E> c, int maxElements)
    {
        throw new IllegalArgumentException();
    }

    @Override public E element()
    {
        throw new IllegalArgumentException();
    }

}