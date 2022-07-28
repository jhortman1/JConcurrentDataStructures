package org.example.service;

import java.util.concurrent.Semaphore;

public class RateLimiter {
    private final int PERMITS_PER_SECOND;
    private Semaphore sem;

    public static RateLimiter create(int permitsPerSecond) {
        return new RateLimiter(permitsPerSecond);
    }

    private RateLimiter(int permitsPerSecond) {
        PERMITS_PER_SECOND = permitsPerSecond;
        sem = new Semaphore(PERMITS_PER_SECOND);
        Thread t = new Thread(()->{
            while(true)
            {
                try {
                    Thread.sleep(1000);
                    sem.release(PERMITS_PER_SECOND);
                }catch (InterruptedException e)
                {
                    System.out.println("ERROR");
                }
            }
        });
        t.start();
    }
    /**
     * If 'count' number of permits are available, claim them.
     * Else, wait.
     */
    public void acquire(int count) {
        // TODO
        if(sem.availablePermits() >= count)
        {
            try
            {
                sem.acquire(count);
            }catch (InterruptedException e)
            {
                System.out.println("ERROR");
            }
        }

    }

    /**
     * If 1 permit is available, claim it.
     * Else, wait.
     */
    public void acquire() {
        // TODO
        try
        {
            sem.acquire(1);
        }catch (InterruptedException e)
        {
            System.out.println("ERROR");
        }


    }



}
