import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Integer[] cards = new Integer[n];

        for (int i = 0; i < n; i++) {
            cards[i] = Integer.valueOf(sc.nextInt());
        }

        Arrays.sort(cards, Collections.reverseOrder());

        int alice = 0;
        int bob = 0;

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                alice += cards[i];
            } else {
                bob += cards[i];
            }
        }

        System.out.println(alice - bob);

        sc.close();
    }
}
