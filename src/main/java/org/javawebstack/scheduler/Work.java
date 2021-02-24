package org.javawebstack.scheduler;

public abstract class Work implements Runnable {

    private boolean stopRequested;

    public void run() {
        stopRequested = false;
        while (!stopRequested) {
            if(!runOnce() && !stopRequested) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        stopRequested = true;
    }

    abstract public boolean runOnce();

}
