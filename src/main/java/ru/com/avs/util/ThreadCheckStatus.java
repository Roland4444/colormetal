package ru.com.avs.util;

public class ThreadCheckStatus extends Thread{
    ServerAktor akt;
    OnCheckCycle check;
    boolean responced = false;
    public void run(){
        while (!responced){
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            check.check();;
        }
    };
}
