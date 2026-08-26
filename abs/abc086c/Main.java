import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] t = new int[n + 1];
        int[] x = new int[n + 1];
        int[] y = new int[n + 1];
        t[0] = 0;
        x[0] = 0;
        y[0] = 0;

        for (int i = 1; i <= n; i++) {
            t[i] = sc.nextInt();
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }

        sc.close();

        for (int i = 0; i < n; i++) {
            int t1 = t[i];
            int x1 = x[i];
            int y1 = y[i];
            int t2 = t[i + 1];
            int x2 = x[i + 1];
            int y2 = y[i + 1];

            int time = t2 - t1;
            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int d = dx + dy;

            if (d > time) {
                System.out.println("No");
                return;
            }

            boolean timeIsEven = time % 2 == 0;
            boolean dIsEven = d % 2 == 0;

            if (!(timeIsEven && dIsEven) && !(!timeIsEven && !dIsEven)) {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }
}
