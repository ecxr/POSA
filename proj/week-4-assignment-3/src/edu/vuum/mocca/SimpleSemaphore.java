package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private final ReentrantLock mLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
    private final Condition mHavePermitsCond;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..

    private volatile int mPermits;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
        
    	// Available permits can start negative
    	//if (permits <= 0)
        //    throw new IllegalArgumentException();

        mPermits = permits;
        mLock = new ReentrantLock(fair);
        mHavePermitsCond = mLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.

        // acquire lock.
        // while # of permits is 0, wait on the condition
        final ReentrantLock lock = mLock;
        lock.lockInterruptibly();

        // Wait until a permit is available.
        // when awoken, check permit count > 0,
        // if it is, decrement permit count, unlock, and exit.
        try {
        	// uses guarded suspension pattern
            while (mPermits <= 0)
            	// await() is interruptible.  Needs to be in try/finally
            	mHavePermitsCond.await();
            --mPermits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
        // like above but dont use interruptible functions
        // acquire lock.
        // while # of permits is 0, wait on the condition
        final ReentrantLock lock = mLock;
        lock.lock();

        // Wait until a permit is available.
        // when awoken, check permit count > 0,
        // if it is, decrement permit count, unlock, and exit.
        try {
            while (mPermits <= 0)
            	mHavePermitsCond.awaitUninterruptibly();
            --mPermits;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here.
        // acquires the reentrant lock,
        // increments permits by 1
        // signals condition to let waiters know that something is available
        final ReentrantLock lock = mLock;
        lock.lock();

        try {
            ++mPermits;
        	// Per comments in lecture and on the forum, use signal instead of signalAll()
        	// See: http://stackoverflow.com/questions/37026/java-notify-vs-notifyall-all-over-again
            if (mPermits > 0)
            	// allow for negative sempaphores
            	mHavePermitsCond.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.

        // permits was declared volatile, no other locking needed.
        return mPermits;
    }
}
