package model.assets.com.github.ashkrit.concurrentqueue;

/**
 * This <Skripsi_003> project in package <model.assets.com.github.ashkrit.concurrentqueue> created by :
 * Name         : syafiq
 * Date / Time  : 27 June 2016, 2:51 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 *
 * @see : https://github.com/ashkrit/blog/blob/master/src/main/java/concurrentqueue/LockWaitStrategy.java
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockWaitStrategy implements WaitStrategy
{

    private ReentrantLock lock        = new ReentrantLock();
    private Condition     dataChanged = lock.newCondition();

    @Override public void block() throws InterruptedException
    {
        try
        {
            lock.lock();
            dataChanged.await();
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override public void release()
    {
        try
        {
            lock.lock();
            dataChanged.signalAll();
        }
        finally
        {
            lock.unlock();
        }
    }

}
