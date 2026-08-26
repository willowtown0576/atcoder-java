import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long y = sc.nextLong();

        sc.close();

        // 一万円の枚数
        for (int i = 0; i <= n; i++) {
            // 五千円の枚数
            for (int j = 0; j <= n - i; j++) {
                // 千円の枚数
                int k = n - i - j;
                long sum = 10000 * i + 5000 * j + 1000 * k;
                if (sum == y) {
                    System.out.println(i + " " + j + " " + k);
                    return;
                }
            }
        }

        System.out.println("-1 -1 -1");

    }
}
