package utils.Thread;

import java.text.NumberFormat;

import components.Device.Device;
import utils.FileDataSource.FileDataSource;

public class MultiThread extends Thread {
    private String filename;

    public MultiThread(String filename) {
        this.filename = filename;
    }

    public synchronized void run() {

        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sb.append("free memory: " + format.format(freeMemory / 1024) + "<br/>");
        sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "<br/>");
        sb.append("max memory: " + format.format(maxMemory / 1024) + "<br/>");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "<br/>");

        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            FileDataSource file2 = new FileDataSource(filename);
            StringBuffer str = new StringBuffer();
            for (int i = 0; i < 100000; i++) {
                str.append((new Device()).toString());
                if (i % 1000 == 0) {
                    System.out.println(i);
                    // System.out.println(sb.toString());
                }
            }
            file2.writeDataNotOverride(str.toString());

        } catch (Exception e) {
            // Throwing an exception
            System.out.println("Exception is caught");
        }
    }
}