package utils.Thread;

import components.Device.Device;
import utils.FileDataSource.FileDataSource;

public class SingleThread implements Runnable {

    private  String filename;

    public SingleThread(String filename) {
        this.filename = filename;
    }


    

    @Override
    public synchronized void run() {
        try {
            System.out.println("===========================");
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            
            for(int i = 0;i<10;i++){
                ThreadBase thread = new ThreadBase(filename);
                Thread t = new Thread(thread);
                t.start();
            }
            System.out.println("Thread " + Thread.currentThread().getId() + " is finished");
            System.out.println("===========================");

        
        } catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
    
}
