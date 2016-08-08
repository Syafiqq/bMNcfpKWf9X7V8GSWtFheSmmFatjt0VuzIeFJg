package model.assets.com.github.ashkrit.concurrentqueue;

/**
 * This <Skripsi_003> project in package <model.assets.com.github.ashkrit.concurrentqueue> created by :
 * Name         : syafiq
 * Date / Time  : 27 June 2016, 2:47 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 *
 * @see : https://github.com/ashkrit/blog/blob/master/src/main/java/concurrentqueue/WaitStrategy.java
 */
public interface WaitStrategy
{
    void block() throws InterruptedException;

    void release();
}
