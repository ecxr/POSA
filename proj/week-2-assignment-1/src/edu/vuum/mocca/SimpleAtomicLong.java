package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/**
 * @class SimpleAtomicLong
 *
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 */
class SimpleAtomicLong
{
    /**
     * The value that's manipulated atomically via the methods.
     */
    private long mValue;
    
    /**
     * The ReentrantReadWriteLock used to serialize access to mValue.
     */

    // TODO -- you fill in here by replacing the null with an
    // initialization of ReentrantReadWriteLock.
    // SKNOTE: forgot to make final
    private final ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();

    /**
     * Creates a new SimpleAtomicLong with the given initial value.
     */
    public SimpleAtomicLong(long initialValue)
    {
        // TODO -- you fill in here
    	// SKNOTE: locking unnecessary in the constructor!
    	mValue = initialValue;
    }

    /**
     * @brief Gets the current value.
     * 
     * @returns The current value
     */
    public long get()
    {
    	// final Lock lock = mRWLock.readLock();
    	// lock.lock();
    	
        long value = 0;

        // TODO -- you fill in here
        mRWLock.readLock().lock();
        value = mValue;
        mRWLock.readLock().unlock();

        // SKNOTE: prof solution
        // try {
        //	  return mValue;
        // } finally {
        //    lock.unlock();
        // }
        
        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the updated value
     */
    // SKNOTE: prof solution
    /*
    public long decrementAndGet()
    {
    	final Lock lock = mRWLock.writeLock();
    	lock.lock();
    	
    	try {
    		return --mValue;
    	} finally {
    		lock.unlock();
    	}
    }
    */
    
    public long decrementAndGet()
    {
        long value = 0;

        // TODO -- you fill in here
        mRWLock.writeLock().lock();
        mValue--;
        value = mValue;
        mRWLock.writeLock().unlock();

        return value;
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the previous value
     */
    // prof solution...No need to get read lock then write lock!!!
    /*
    public long getAndIncrement()
    {
        mRWLock.writeLock().lock();
        long value = mValue++;
        mRWLock.writeLock().unlock();

        return value;
    }
    */
    
    // SKNOTE: 
    // use try...finally.
    // mValue could be modified after write lock is unlocked 
    // and before the return statement....
    // use try...finally!!!!
    // Code is OK since we are returning local copy, but more complex
    // also, readlock then writelock is wrong...use one writelock
    public long getAndIncrement()
    {
        long value = 0;

        // TODO -- you fill in here
        mRWLock.readLock().lock();
        value = mValue;
        mRWLock.readLock().unlock();

        mRWLock.writeLock().lock();
        mValue++;
        mRWLock.writeLock().unlock();

        return value;
    }

    /**
     * @brief Atomically decrements by one the current value
     *
     * @returns the previous value
     */
    public long getAndDecrement()
    {
        long value = 0;

        // TODO -- you fill in here
        mRWLock.readLock().lock();
        value = mValue;
        mRWLock.readLock().unlock();

        mRWLock.writeLock().lock();
        mValue--;
        mRWLock.writeLock().unlock();

        return value;
    }

    /**
     * @brief Atomically increments by one the current value
     *
     * @returns the updated value
     */
    public long incrementAndGet()
    {
        long value = 0;

        // TODO -- you fill in here
        mRWLock.writeLock().lock();
        mValue++;
        value = mValue;
        mRWLock.writeLock().unlock();

        return value;
    }
}

