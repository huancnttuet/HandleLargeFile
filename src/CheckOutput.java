import java.io.*;

public class CheckOutput {
    public static void main(String[] args) {
        File input = new File("data\\output3.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String line;
            int i = 0;
            try {
                while (null != (line = reader.readLine())) {
                    i++;

                    if (i % 1000000 == 0)
                        System.out.println(line);
                    ;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
