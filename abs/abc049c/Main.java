import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String[] pattern = { "dream", "dreamer", "erase", "eraser" };

        outer: while (!s.isEmpty()) {
            for (String p : pattern) {
                if (s.endsWith(p)) {
                    s = s.substring(0, s.length() - p.length());
                    continue outer;
                }
            }
            break;
        }

        if (s.isEmpty()) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        sc.close();
    }
}
