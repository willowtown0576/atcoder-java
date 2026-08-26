import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        int ans = 0;
        boolean flag = true;

        while (flag) {
            for (int i = 0; i < n; i++) {
                if (a[i] % 2 != 0) {
                    flag = false;
                } else {
                    a[i] = a[i] / 2;
                }
            }

            if (flag) {
                ans++;
            }
        }

        System.out.println(ans);

        sc.close();
    }
}
