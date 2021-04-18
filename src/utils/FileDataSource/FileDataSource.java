package utils.FileDataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import components.Device.Device;

public class FileDataSource implements DataSource {

    private String name;
    private List<Device> list = new ArrayList<Device>();

    public FileDataSource(String name) {
        this.name = name;
    }

    private static final FileDataSource inst = new FileDataSource();

    private FileDataSource() {
        super();
    }

    public FileDataSource getInstance() {
        return inst;
    }

    @Override
    public void writeData(String data) {
        File file = new File(name);
        try (OutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Device> readData() {

        try {
            BufferedReader data = new BufferedReader(new FileReader(this.name));
            String line;
            while ((line = data.readLine()) != null) {
                String[] deviceString = line.split("\\,", 5);
                if (deviceString.length != 5) {
                    continue;
                } else {
                    Device device = new Device(deviceString);
                    list.add(device);
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public int getSize() {
        return list.size();
    }

    @Override
    public void writeDataNotOverride(String data) {
        File file = new File(name);
        try (OutputStream fos = new FileOutputStream(file, true)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void pipeline(String inputFilename, String ouputFilename, int min, int max) {
        StringBuffer sb = new StringBuffer();
        List<String[]> list = new ArrayList<String[]>();
        try {
            BufferedReader data = new BufferedReader(new FileReader(inputFilename));
            String line;
            int i = min;
            while ((line = data.readLine()) != null && i >= min && i < max) {

                // String[] deviceString = line.split("\\,", 5);
             
                // list.add(new String[] { deviceString[0] + "," + deviceString[1] + "," + nomalizeOwner(deviceString[2])
                //         + "," + deviceString[3] + ",", deviceString[4] });
               sb.append(line);

                // if (i % 100000 == 0) {
                // System.out.println(i);
                // }
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // Collections.sort(list, (a, b) -> Integer.valueOf(a[1]) >
        // Integer.valueOf(b[1]) ? -1
        // : Integer.valueOf(a[1]) == Integer.valueOf(b[1]) ? 0 : 1);

        // for (String[] l : list) {
        //     sb.append(l[0] + l[1] + "\n");
        // }

        File file = new File(ouputFilename);
        try (OutputStream fos = new FileOutputStream(file, true)) {
            fos.write(sb.toString().getBytes(), 0, sb.toString().length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private String nomalizeOwner(String oldOwner) {
        String newOwnn = oldOwner.toLowerCase().trim().replaceAll(" +", " ");
        return capitalizeString(newOwnn);

    }

    public static String capitalizeString(String string) {
        char[] chars = string.toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

}
