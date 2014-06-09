package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
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
	// SKTODO: Create lock like from video
	private final ReentrantLock lock;
	
    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	private final Condition havePermits;
	
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..

	private volatile int permits;
	
    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	if (permits <= 0)
    		throw new IllegalArgumentException();
    	
    	this.permits = permits;
    	lock = new ReentrantLock(fair);
    	havePermits = lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	// aqcuire lock.
    	// while # of permits is 0, wait on the condition
    	// acquires interruptible manner (lock/waitInterruptible)
    	final ReentrantLock lock = this.lock();	// acquire lock interruptibly
    	lock.lockInterruptibly();
    	
    	try {
        	// when awoken, check permit count > 0, 
        	// if you do decrement by one, and you are out
    		while (permits == 0)
    			havePermits.await();
    		--permits;
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
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
    	// aqcuires reentrant lock, 
    	// increments permits by 1
    	// signals condition to let waiters know that something is available
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
    	// return # of permits available - volatile so dont need locking
        return 0;
    }
}
