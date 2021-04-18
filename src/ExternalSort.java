import java.io.*;
import java.util.*;

/**
 * author: Khoai X. Pham (Pham Xuan Khoai) email: phamxuankhoai@gmail.com phone:
 * +84 1648 380 645 skype: khoaipx93
 *
 * Program: External Sort Development environment: Ubuntu 15.10
 *
 * If you have any question, contact me by above email or phone please!
 */

public class ExternalSort {
    public static final String TEMPORARY_FILE_NAME_PREFIX = "template_file_";

    public static Comparator<String> defaultcomparator = new Comparator<String>() {

        @Override
        public int compare(String a, String b) {
            // String[] arrayA = a.split(",");
            // String[] arrayB = b.split(",");
            int aa = Integer.valueOf(a.charAt(a.length() - 2));
            int bb = Integer.valueOf(b.charAt(b.length() - 2));

            return aa < bb ? 1 : aa == bb ? 0 : -1;
        }
    };

    public static String nomalizeOwner(String line) {
        String[] elements = line.split(",");
        String oldOwner = elements[2];
        String newOwnn = oldOwner.toLowerCase().trim().replaceAll(" +", " ");
        return elements[0] + "," + elements[1] + "," + capitalizeString(newOwnn) + "," + elements[3] + ","
                + elements[4];

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

    private static long estimateSizeOfChunk(long memoryLimit) {
        return memoryLimit / 2;
    }

    private static long estimateStringSize(String s) {
        return (s.length() * 2 + 60);
    }

    public static File sortTempFile(List<String> temp, Comparator<String> comparator, boolean reverse, File tmpDir,
            boolean quiet) throws Exception {
        File file = File.createTempFile(TEMPORARY_FILE_NAME_PREFIX, ".txt", tmpDir);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        if (!quiet) {
            System.out.println("Start sorting");
        }

        if (reverse) {
            Collections.sort(temp, comparator.reversed());
        } else {
            Collections.sort(temp, comparator);
        }

        for (String line : temp) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();

        if (!quiet) {
            System.out.println("Write to file " + file.getAbsolutePath());
        }

        return file;
    }

    public static List<File> splitAndSort(File input, long memoryLimit, Comparator<String> comparator, boolean reverse,
            File tmpDir, boolean quiet) throws Exception {
        List<File> tempFiles = new ArrayList<>();
        String line = "";

        if (input.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(input));

            long sizeOfChunk = estimateSizeOfChunk(memoryLimit);

            long currentSize = 0;
            List<String> temp = new ArrayList<>();

            while (null != line) {
                while ((currentSize < sizeOfChunk) && null != (line = reader.readLine())) {
                    temp.add(line);
                    currentSize += line.getBytes().length;
                }

                File tempFile = sortTempFile(temp, comparator, reverse, tmpDir, quiet);
                tempFiles.add(tempFile);
                temp.clear();
                currentSize = 0;
            }
        } else {
            if (!quiet) {
                System.out.println("Can't open file " + input.getAbsolutePath());
            }
        }

        return tempFiles;
    }

    public static void merge(File output, List<Buffer> buffers, boolean distinct, Comparator<String> comparator,
            boolean reverse, boolean quiet) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        if (!quiet) {
            System.out.println("Make priority queue (heap)");
        }

        Comparator bufferComparator = new Comparator<Buffer>() {
            @Override
            public int compare(Buffer o1, Buffer o2) {
                return comparator.compare(o1.head(), o2.head());
            }
        };

        PriorityQueue<Buffer> pq;
        if (reverse) {
            pq = new PriorityQueue<>(bufferComparator.reversed());
        } else {
            pq = new PriorityQueue<>(bufferComparator);
        }

        for (Buffer buffer : buffers) {
            if (!buffer.empty()) {
                pq.add(buffer);
            }
        }

        String last = null;
        int counter = 0;
        while (!pq.isEmpty()) {
            Buffer buffer = pq.poll();
            String s = nomalizeOwner(buffer.head());

            buffer.load();

            if (!distinct || last == null || !last.equals(s)) {
                writer.write(s);
                writer.newLine();
                last = s;
            }

            counter++;
            if (counter % 1000000 == 0 && !quiet) {
                System.out.println("Processing at line #" + Integer.toString(counter));
            }

            if (buffer.empty()) {
                buffer.close();
            } else {
                pq.add(buffer);
            }
        }
        writer.close();
    }

    public static void mergeSortedFiles(File output, List<File> tempFiles, boolean distinct,
            Comparator<String> comparator, boolean reverse, boolean keepFile, boolean quiet) throws Exception {
        List<Buffer> buffers = new ArrayList<>();

        for (File tempFile : tempFiles) {
            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            Buffer buffer = new Buffer(reader);

            buffers.add(buffer);
        }

        if (!quiet) {
            System.out.println("Start merging");
        }

        merge(output, buffers, distinct, comparator, reverse, quiet);

        if (!keepFile) {
            for (File file : tempFiles) {
                file.delete();
            }

            if (!quiet) {
                System.out.println("Remove all temporary files");
            }
        }
    }

    public static void externalSort(File input, File output, long memoryLimit, boolean distinct,
            Comparator<String> comparator, boolean reverse, File tmpDir, boolean keepFile, boolean quiet)
            throws Exception {
        List<File> files = splitAndSort(input, memoryLimit, comparator, reverse, tmpDir, quiet);

        if (!quiet) {
            System.out.println("Created " + Integer.toString(files.size()) + " temporary files at directory "
                    + tmpDir.getAbsolutePath());
        }

        mergeSortedFiles(output, files, distinct, comparator, reverse, keepFile, quiet);
    }

    public static void main(String[] args) throws Exception {
        final long startTime = System.currentTimeMillis();
        File input = new File("data\\input1.txt");
        File output = new File("data\\output1.txt");
        long memoryLimit = 1024 * 1024 * 1024; // 1G Memory

        boolean distinct = false;
        boolean reverse = false;
        File tmpDir = new File(System.getProperty("user.dir"));
        boolean keepFile = false;
        boolean quiet = false;

        externalSort(input, output, memoryLimit, distinct, defaultcomparator, reverse, tmpDir, keepFile, quiet);
        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime));
    }

    private static class Buffer {
        private BufferedReader reader;
        private String cache;

        public Buffer(BufferedReader reader) throws IOException {
            this.reader = reader;
            load();
        }

        public boolean empty() {
            return (cache == null);
        }

        public void close() throws IOException {
            reader.close();
        }

        public void load() throws IOException {
            cache = reader.readLine();
        }

        public String head() {
            return cache;
        }
    }
}