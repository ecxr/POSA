package edu.vuum.mocca;

// Import the necessary Java synchronization and scheduling classes.
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.Sides;

/**
 * @class PingPongRight
 * 
 * @brief This class implements a Java program that creates two
 *        instances of the PlayPingPongThread and start these thread
 *        instances to correctly alternate printing "Ping" and "Pong",
 *        respectively, on the console display.
*        Office Hrs 4 - 38:38 - 48:00
*/
public class PingPongRight {
   /**
    * Number of iterations to run the test program.
    * 
    * This can be ignored see office hrs. 4 at 39:20
    */

    public final static int mMaxIterations = 10;

    /**
     * Latch that will be decremented each time a thread exits.
     * 
     *
     */
    public static CountDownLatch mLatch = null;

    /**
     * @class PlayPingPongThread
     * 
     * @brief This class implements the ping/pong processing algorithm
     *        using the SimpleSemaphore to alternate printing "ping"
     *        and "pong" to the console display.
     */
    public static class PlayPingPongThread extends Thread {
        /**
         * Constants to distinguish between ping and pong
         * SimpleSemaphores, if you choose to use an array of
         * SimpleSemaphores.  If you don't use this implementation
         * feel free to remove these constants.
         */
        private final static int FIRST_SEMA = 0;
        private final static int SECOND_SEMA = 1;

        /**
         * Maximum number of loop iterations.
         */
        private int mMaxLoopIterations = 0;

        /**
         * String to print (either "ping!" or "pong"!) for each
         * iteration.
         * 
         * Office Hrs. 4-> 40:34
         * 1.) Create a string that will be initialized in the constructor or whatever is passed to it
         */
        // TODO - You fill in here.
        private String thePrintString;
        
        /**
         * Two SimpleSemaphores use to alternate pings and pongs.  You
         * can use an array of SimpleSemaphores or just define them as
         * two data members.
         * 
         * create and array of 2 entries then used SECOND_SEMA FIRST_SEMA as index into array
         */
        // TODO - You fill in here.
        private SimpleSemaphore semaphoreArray[] = new SimpleSemaphore[2];
        

        /**
         * Constructor initializes the data member(s).
         */
        public PlayPingPongThread(String stringToPrint,
                                  SimpleSemaphore semaphoreOne,
                                  SimpleSemaphore semaphoreTwo,
                                  int maxIterations) {
            // TODO - You fill in here.
        	//41:40
        	//assign to data memebers
        	thePrintString = stringToPrint;
        	semaphoreArray[FIRST_SEMA] = semaphoreOne;
        	semaphoreArray[SECOND_SEMA] = semaphoreTwo;
        	mMaxLoopIterations = maxIterations;
        }
        /**
         * Main event loop that runs in a separate thread of control
         * and performs the ping/pong algorithm using the
         * SimpleSemaphores.
         */
        public void run() {
            /**
             * This method runs in a separate thread of control and
             * implements the core ping/pong algorithm.
             * 41:50-43:00
             */

            // TODO - You fill in here.
        	// 1. acquires semaphores 
        	// 2. Prints the appropriate string
        	// 3. Then releases Semaphore and does this in a loop
        	// 4. loop for max iternation times. Every time you print string you also print iteration number
        	// 5. See java syncronization and scheduling as exact example is in those videos (video 1 3:55, video 2 0:30, video 2 2:40, video 2 3:00,  video 2 4:48, video 2 5:12
        	
        	for (int loopsDone =1; loopsDone <= mMaxLoopIterations ; ++loopsDone)
        	{
        		acquire();
        		System.out.println(thePrintString);
        		release();
        	}
        	
        
        	
        	
        }

        /**
         * Method for acquiring the appropriate SimpleSemaphore.
         * 
         * go back and forth between acquire and release to acquire first semaphore and release second Semaphore
         */
        private void acquire() {
            // TODO fill in here
        	// 43:08
        	// use uninteruptable acquire.
        	// 
        	semaphoreArray[FIRST_SEMA].acquireUninterruptibly();
        }

        /**
         * Method for releasing the appropriate SimpleSemaphore.
         */
        private void release() {
            // TODO fill in here
        	//
        	semaphoreArray[SECOND_SEMA].release();
        }
    }

    /**
     * The method that actually runs the ping/pong program.
     * 
     * 43:50
     */
    public static void process(String startString, 
                               String pingString,
                               String pongString, 
                               String finishString, 
                               int maxIterations) throws InterruptedException {

        // TODO initialize this by replacing null with the appropriate
        // constructor call.
    	// 2 threads so you need to wait for 2x things
        mLatch = new CountDownLatch(2);

        // Create the ping and pong SimpleSemaphores that control
        // alternation between threads.

        // TODO - You fill in here, make pingSema start out unlocked.
        // give permit count to start out unlocked.
        SimpleSemaphore pingSema = new SimpleSemaphore(1, true);
        // TODO - You fill in here, make pongSema start out locked.
        // give permit count to start out locked.
        SimpleSemaphore pongSema = new SimpleSemaphore(0, true);

        System.out.println(startString);

        // Create the ping and pong threads, passing in the string to
        // print and the appropriate SimpleSemaphores.
        // 45:20
        // pass in ping/pong string
        // pass in appropriate semaphore
        // ping passes ping then pong
        // pong passes pong then ping
        PlayPingPongThread ping = new PlayPingPongThread(/*
                                                          * TODO - You fill in
                                                          * here
                                                          */     
        		pingString,pingSema,pongSema,maxIterations);
        PlayPingPongThread pong = new PlayPingPongThread(/*
                                                          * TODO - You fill in
                                                          * here
                                                          */
        		pongString,pongSema,pingSema,maxIterations);

        // TODO - Initiate the ping and pong threads, which will call
        // the run() hook method.

        //CRAP ran out of time.... have mercy!!!!!!!!
        
        
        // TODO - replace the following line with a barrier
        // synchronizer call to mLatch that waits for both threads to
        // finish.
        // wait for count down latch to hit zero.
        throw new java.lang.InterruptedException();
       // did this to compile
       // System.out.println(finishString);
    }

    /**
     * The main() entry point method into PingPongRight program.
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        process("Ready...Set...Go!", 
                "Ping!  ",
                " Pong! ",
                "Done!",
                mMaxIterations);
    }
}

