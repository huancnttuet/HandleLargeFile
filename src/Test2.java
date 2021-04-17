import java.util.ArrayList;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        Test2 app = new Test2();

        List<List<String[]>> allList = new ArrayList<List<String[]>>();
        List<String[]> list1 = new ArrayList<String[]>();
        list1.add(new String[] { "list1", "10" });
        list1.add(new String[] { "list1", "9" });
        list1.add(new String[] { "list1", "7" });
        allList.add(list1);
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(new String[] { "list2", "10" });
        list2.add(new String[] { "list2", "9" });
        list2.add(new String[] { "list2", "7" });
        allList.add(list2);
        List<String[]> list3 = new ArrayList<String[]>();
        list3.add(new String[] { "list3", "10" });
        list3.add(new String[] { "list3", "9" });
        list3.add(new String[] { "list3", "7" });
        allList.add(list3);
        int MAX = 100000000;
        int batch = 1000000;
        int count = 0;
        int[] arr = new int[] { 0, 0, 0 };
        StringBuffer sb = new StringBuffer();
        while (count < MAX) {

            if (count % batch == 0 && count > 0) {

                sb.delete(0, sb.length());
            }
            List<String[]> stack = new ArrayList<String[]>();
            for (int i = 0; i < allList.size(); i++) {
                if (arr[i] == allList.get(i).size() || arr[i] > allList.get(i).size()) {
                    continue;
                } else {
                    System.out.println("========");
                    System.out.println(arr[i]);
                    System.out.println(allList.get(i).size());

                    System.out.println(allList.get(i).get(arr[i])[0] + "," + allList.get(i).get(arr[i])[1]);
                    stack.add(allList.get(i).get(arr[i]));
                }
            }
            int iterator = app.getMax(stack);

            System.out.println("______");

            System.out.println(iterator);

            sb.append(stack.get(iterator)[0] + stack.get(iterator)[1] + "\n");
            stack.remove(iterator);

            arr[iterator]++;
            count++;
        }
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
