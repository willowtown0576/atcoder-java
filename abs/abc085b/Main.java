import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Integer[] d = new Integer[n];

        for (int i = 0; i < n; i++) {
            d[i] = Integer.valueOf(sc.nextInt());
        }

        Set<Integer> kagamimochi = Arrays.stream(d).collect(Collectors.toSet());
        System.out.println(kagamimochi.size());

        sc.close();
    }
}
