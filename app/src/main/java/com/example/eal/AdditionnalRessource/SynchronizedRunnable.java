package com.example.eal.AdditionnalRessource;

public abstract class SynchronizedRunnable implements Runnable{
    private Object synchronizer;

    public SynchronizedRunnable(Object sync){
        this.synchronizer = sync;
    }

    @Override
    public void run() {
        synchronized (synchronizer){
            try {
                onRun();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void onRun() throws InterruptedException;

    public Object getSynchronizer() {
        return synchronizer;
    }
}
