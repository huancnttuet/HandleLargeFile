import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        final int MAX = 10000000;
        List<String[]> list = new ArrayList<String[]>();
        // String[] temp = new String[2];
        Random randomizer = new Random();
        for (int i = 0; i < MAX; i++) {

            // temp[0] = "dasdasfasgawe asd as fqw fqwf adasd";
            // temp[1] = String.valueOf(randomizer.nextInt(10) + 1);
            if (i % 100000 == 0) {
                System.out.println(i);

            }
            list.add(new String[] { "dasd asd as das sda a", String.valueOf(randomizer.nextInt(10) + 1) });

        }
        System.out.println(list.size());

        Collections.sort(list, (a, b) -> Integer.valueOf(a[1]) < Integer.valueOf(b[1]) ? -1
                : Integer.valueOf(a[1]) == Integer.valueOf(b[1]) ? 0 : 1);

        System.out.println(list.get(1)[1]);
        System.out.println(list.get(2)[1]);
        System.out.println(list.get(3)[1]);
        System.out.println(list.get(MAX - 1)[1]);

    }

}
