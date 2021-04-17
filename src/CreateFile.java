import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateFile {
    static List<String> stubCode = Arrays.asList("ERICVTTEK", "NSNVTTEK", "HUA");
    static List<String> stubName = Arrays.asList("MSC ERICSSON", "BTS NOKIA", "OCS HUAWEI", "EPC ERICSSON");
    static List<String> stubOwner1 = Arrays.asList("TraN", "NguyeN", "Le", "ngUYen ", "le ", "traN  ", "Le  ");
    static List<String> stubOwner2 = Arrays.asList("Van   ", "VAn  ", "tHi  ", "vAN ", "thi   ", " vaN ", "vAn    ");
    static List<String> stubOwner3 = Arrays.asList("A", "a", "B", "b", "C", "c", "D");
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static void main(String[] args) {

        final long startTime = System.currentTimeMillis();

        CreateFile createFile = new CreateFile();

        CreateFileThread t1 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t2 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t3 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t4 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t5 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t6 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t7 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t8 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t9 = createFile.new CreateFileThread("data\\input1.txt", startTime);
        CreateFileThread t10 = createFile.new CreateFileThread("data\\input1.txt", startTime);

        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(t1);
            Thread thread2 = new Thread(t2);
            Thread thread3 = new Thread(t3);
            Thread thread4 = new Thread(t4);
            Thread thread5 = new Thread(t5);
            Thread thread6 = new Thread(t6);
            Thread thread7 = new Thread(t7);
            Thread thread8 = new Thread(t8);
            Thread thread9 = new Thread(t9);
            Thread thread10 = new Thread(t10);
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();
            thread7.start();
            thread8.start();
            thread9.start();
            thread10.start();
        }

    }

    class CreateFileThread implements Runnable {

        private String filename;
        private long startTime;

        public CreateFileThread(String filename, long startTime) {
            this.filename = filename;
            this.startTime = startTime;
        }

        public CreateFileThread(String filename) {
            this.filename = filename;
        }

        @Override
        public synchronized void run() {
            System.out.println("ThreadBase " + Thread.currentThread().getId() + " is running");

            StringBuffer str = new StringBuffer();
            for (int i = 0; i < 1000000; i++) {
                str.append(RandomDevice());
                if (i % 100000 == 0) {
                    System.out.println(i);
                }
            }
            String data = str.toString();
            File file = new File(filename);
            try (OutputStream fos = new FileOutputStream(file, true)) {
                fos.write(data.getBytes(), 0, data.length());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            final long endTime = System.currentTimeMillis();

            System.out.println("Total execution time: " + (endTime - startTime));
        }

        public String RandomDevice() {

            Random randomizer = new Random();
            String code = stubCode.get(randomizer.nextInt(stubCode.size())) + randomizer.nextInt(10)
                    + randomizer.nextInt(10);
            String name = stubName.get(randomizer.nextInt(stubName.size()));
            String owner = stubOwner1.get(randomizer.nextInt(stubOwner1.size()))
                    + stubOwner2.get(randomizer.nextInt(stubOwner2.size()))
                    + stubOwner3.get(randomizer.nextInt(stubOwner3.size()));
            // long ms = -946771200000L + (Math.abs(randomizer.nextLong()) % (70L * 365 * 24
            // * 60 * 60 * 1000));
            // String inputDate = df.format(new Date(ms));
            String inputDate = "12/20/2004";
            String warrentyYear = String.valueOf(randomizer.nextInt(10) + 1);

            return code + "," + name + "," + owner + "," + inputDate + "," + warrentyYear + "\n";
        }

    }
}
