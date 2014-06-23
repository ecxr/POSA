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


// know how condition variables work. condition variable video, java synchronisation and scheduling mechanisms video,
// See 1:26:00 - 1:33:00 office hour 3 video


public class SimpleSemaphore{
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	// reentrant lock goes here see video on condition object,  and example that uses reentrant lock.

	final ReentrantLock lock;    // = new ReentrantLock(true);
	
    
	/**
     * Define a Condition that waits while the number of permits is 0.
     * Interface that is initialised by factory method on reentrant lock called "newCondition".
     */
    // TODO - you fill in here

	private final Condition zeroPermits;    

	
	/**
     * Define a count of the number of available permits.
     * make this volatile or put locks in the aquire permits method.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads.. 
	// Video Time office hrs 3 - 1:26:30

	// I Made this private but i am wondering if i made it public or used a setter method that the
	// SimpleSemaphore class could then be configured for a dynamic range of permits.
	private volatile int numPermits;
	
	
    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	// Video Time office hrs 3 - 1:26:45
    	// 1. Make a newCondition. Initialise the data member using the newCondition factory method thats part of the reentrant lock you created
    	// 3. set/assign the permits the number of available permits and pass in the fair parameter to the reentrant lock when you create it.
    	//
    	//Grading video Office hrs 4 -> 4:40 - 7:38
    	lock = new ReentrantLock(fair);   // I put in "true' as a value where Fair should have been passed in. it is fixed here
    	zeroPermits = lock.newCondition();
    	numPermits = permits;  				//i put in a static value of numPermints = 2 and should have passed in "permits"
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here. 1:27:30 make interruptible
    	// Video Time office hrs 3 -  1:27:20
    	// 
    	// 1. Acquire and lock in an interruptible way.
    	// 2. while the number of permits = 0 going to await() on the condition
    	// 3. when  awake from the interrupt (released semaphore) rechecks to see if permit count is 
    	// Greater than 0 if yes decrement by one and release the lock and out. 
    	// 4. lock interruptible and wait interruptible are to be used
    	//
    	//Grading video Office hrs 4 -> 7:38-12:50
    	lock.lockInterruptibly();
    	
    	try{
    		while (availablePermits() == 0) {    //Unix and sun will support - permits will so <= is a valid condition.
				zeroPermits.await();    //await() is interruptible.
			}
    		--numPermits;
    		
    		//if (availablePermits() > 0 ) {   //If statement is not required as While loop will check condition one more time on wake of await!
    		//	numPermits--;
			//}
    		
    	}finally{
    		lock.unlock();
    	}
    	
   	
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
    	// Video Time office hrs 3 - 1:28.:03
    	// 1. makes call to lock wait uninterruptible instead of
    	//    lock interruptible and await interruptible as in aquire().
    	//
    	//Grading video Office hrs 4 -> 12:50-14:30
    	lock.lock();
    	
    	try{
    		while (availablePermits() == 0) {
				zeroPermits.awaitUninterruptibly();
			}
    		--numPermits;
    		//Probably not necessary but i did the check anyways
    		//if (availablePermits() > 0 ) {   //If statement is not required as While loop will check condition one more time on wake of await!
    		//	numPermits--;
			//}
    		
    	}finally{
    		lock.unlock();
    	}
    	

    }
    

    /**
     * Return one permit to the semaphore. 16:30
     */
    void release() {
        // TODO - you fill in here.
    	// Video Time office hrs 3 - 1:28:14
    	// 1. Aquires the reentrant lock 
    	// 2. increments the count by 1
    	// 3. signals the condition to let any 'waiters' know that now additional permit available
    	// 4. releases the lock
      	//
    	//Grading video Office hrs 4 -> 14:30-20:00
    	//Book: Java concurency in Practice    by: brian Goets, Doug lee
    	lock.lock();
    	
    	try{
		     ++numPermits;
		     // if (numPermits > 0)    //Optional 
		     	
    		//zeroPermits.signalAll();   //signal all is not necessary as it has more (not as effecient) CPU cycles  and in not needed in this context.
    		
    		if (numPermits > 0)			//not strickly necessary but this piece of code will ensure the threads are signaled if the conditions in not 0. Would be used if - value semaphores are used.
    			zeroPermits.signal();   
    		
    		
    		
    	}finally{
    		lock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
    	// Video Time office hrs 3 - 1:28.:38
    	// 1. going to return the count of the number of available permits defined in class
    	// 2. if using volatile no additional code needed. if volatile is not uses will need to acquire and release the lock 
    	//
    	//Grading video Office hrs 4 -> 22:05 - 23:30
    	// numPermits is volatile
        return numPermits;   // no lock is need because of volatile variable.
    }
}
