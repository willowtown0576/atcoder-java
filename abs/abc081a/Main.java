import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] splited = s.split("");

        int ans = 0;
        for (int i = 0; i < splited.length; i++) {
            if (splited[i].equals("1")) {
                ans++;
            }
        }

        System.out.println(ans);

        sc.close();
    }
}
