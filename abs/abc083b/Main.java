import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            int sumDigits = sumDigits(i);
            if (sumDigits >= a && sumDigits <= b) {
                ans += i;
            }
        }

        System.out.println(ans);
        sc.close();
    }

    private static int sumDigits(int num) {
        int result = 0;
        while (num > 0) {
            result += num % 10;
            num /= 10;
        }
        return result;
    }
}
