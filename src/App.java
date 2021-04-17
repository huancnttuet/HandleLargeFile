import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import components.Device.Device;
import utils.FileDataSource.ExportDataDecorator;
import utils.FileDataSource.FileDataSource;
import utils.FileDataSource.NomalizeDecorator;
import utils.FileDataSource.ReverseDecorator;
import utils.FileDataSource.SortDecorator;
import utils.Thread.MultiThread;
import utils.Thread.SingleThread;

public class App {
    public static void main(String[] args) throws Exception {
        final int MAX = 100000000;

        final long startTime = System.currentTimeMillis();

        App app = new App();

        FileDataSource file = new FileDataSource("data\\input1.txt");

        NomalizeDecorator data = new NomalizeDecorator(file);

        SortDecorator sortedData = new SortDecorator(file);

        StringBuffer output = new StringBuffer();
        int batch = 1000000;
        List<List<String[]>> allList = new ArrayList<List<String[]>>();

        for (int i = 0; i < 100; i++) {
            allList.add(file.pipeline("data\\input1.txt", "data\\output" + i + ".txt", i * batch, batch * (i + 1)));
        }
        int count = 0;
        int[] arr = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        StringBuffer sb = new StringBuffer();
        while (count < MAX) {

            if (count % batch == 0 && count > 0) {
                File fileOutput = new File("data\\output.txt");
                try (OutputStream fos = new FileOutputStream(fileOutput, true)) {
                    fos.write(sb.toString().getBytes(), 0, sb.toString().length());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                sb.delete(0, sb.length());
            }
            List<String[]> stack = new ArrayList<String[]>();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == allList.get(i).size() || arr[i] > allList.get(i).size()) {
                    continue;
                } else {
                    if (count % 100000 == 0) {
                        System.out.println("========");
                        System.out.println(arr[i]);

                    }

                    stack.add(allList.get(i).get(arr[i]));
                }
            }
            if (stack.size() == 1) {
                sb.append(stack.get(0)[0] + stack.get(0)[1] + "\n");
                stack.remove(0);
                break;
            } else {
                int iterator = app.getMax(stack);
                sb.append(stack.get(iterator)[0] + stack.get(iterator)[1] + "\n");
                stack.remove(iterator);
                arr[iterator]++;
            }

            count++;

        }

        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));
    }

    public int getMax(List<String[]> stack) {
        int iterator = 0;
        int max = 0;
        int index = 0;
        for (String[] element : stack) {
            if (Integer.valueOf(element[1]) > max) {
                max = Integer.valueOf(element[1]);
                iterator = index;
            }
            index++;
        }
        return iterator;
    }
}
