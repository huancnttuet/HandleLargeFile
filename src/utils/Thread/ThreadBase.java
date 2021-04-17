package utils.Thread;

import components.Device.Device;
import utils.FileDataSource.FileDataSource;

public class ThreadBase implements Runnable {

    private String filename;

    public ThreadBase(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            System.out.println("ThreadBase " + Thread.currentThread().getId() + " is running");
            // FileDataSource file2 = new FileDataSource(filename);
            // StringBuffer str = new StringBuffer();
            // for (int i = 0; i < 1000000; i++) {
            //     str.append((new Device()).toString());
            //     if (i % 1000 == 0) {
            //         System.out.println(i);
            //         // System.out.println(sb.toString());
            //     }
            // }
            // file2.writeDataNotOverride(str.toString());

            System.out.println("ThreadBase " + Thread.currentThread().getId() + " is finished");

        } catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
    
}

