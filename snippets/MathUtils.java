import java.util.ArrayList;
import java.util.List;

/**
 * 整数の除算・素数判定・最大公約数・累乗剰余・約数列挙。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class MathUtils {

    /**
     * 2つの整数を除算し、正の無限大方向へ切り上げた整数を返す。
     * Javaの {@code /} は0方向への切り捨てであり、負数に対する結果が異なる点に注意する。
     * 時間計算量は O(1)。
     *
     * <pre>{@code
     * long positive = ceilDiv(10, 3);  // 4
     * long negative = ceilDiv(-10, 3); // -3
     * }</pre>
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @return 除算結果を正の無限大方向へ切り上げた整数
     * @throws ArithmeticException {@code divisor} が0の場合
     */
    static long ceilDiv(long dividend, long divisor) {
        long quotient = dividend / divisor;
        long remainder = dividend % divisor;
        if (remainder != 0 && (dividend ^ divisor) >= 0) {
            quotient++;
        }
        return quotient;
    }

    /**
     * 2つの整数を除算し、負の無限大方向へ切り下げた整数を返す。
     * Javaの {@code /} は0方向への切り捨てであり、負数に対する結果が異なる点に注意する。
     * 時間計算量は O(1)。
     *
     * <pre>{@code
     * long positive = floorDiv(10, 3);  // 3
     * long negative = floorDiv(-10, 3); // -4
     * }</pre>
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @return 除算結果を負の無限大方向へ切り下げた整数
     * @throws ArithmeticException {@code divisor} が0の場合
     */
    static long floorDiv(long dividend, long divisor) {
        return Math.floorDiv(dividend, divisor);
    }

    /**
     * 指定された整数が素数か判定する。計算量は O(√n)。
     *
     * <pre>{@code
     * boolean prime = isPrime(17); // true
     * }</pre>
     *
     * @param n 判定する整数
     * @return {@code n} が素数なら {@code true}、それ以外は {@code false}
     */
    static boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }
        if (n % 2 == 0) {
            return n == 2;
        }
        for (long divisor = 3; divisor <= n / divisor; divisor += 2) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * エラトステネスの篩により、0以上 {@code n} 以下の素数を列挙する。
     * 計算量は O(n log log n)。
     *
     * <pre>{@code
     * boolean[] prime = sieve(100);
     * if (prime[97]) out.println("97 is prime");
     * }</pre>
     *
     * @param n 列挙する範囲の上限
     * @return インデックスが素数のときだけ {@code true} となる長さ {@code n + 1} の配列
     * @throws IllegalArgumentException {@code n} が負の場合
     */
    static boolean[] sieve(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        boolean[] prime = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            prime[i] = true;
        }
        for (int i = 2; i <= n / i; i++) {
            if (!prime[i]) {
                continue;
            }
            for (int multiple = i * i; multiple <= n; multiple += i) {
                prime[multiple] = false;
            }
        }
        return prime;
    }

    /**
     * ユークリッドの互除法により、2つの整数の最大公約数を求める。
     * 時間計算量は O(log max(|a|, |b|))。
     *
     * <pre>{@code
     * long result = gcd(12, 18); // 6
     * }</pre>
     *
     * @param a 1つ目の整数
     * @param b 2つ目の整数
     * @return {@code a} と {@code b} の非負の最大公約数
     */
    static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    /**
     * 2つの整数の最小公倍数を求める。
     * 時間計算量は O(log max(|a|, |b|))。
     * 結果が {@code long} の範囲を超える場合のオーバーフローは検出しない。
     *
     * <pre>{@code
     * long result = lcm(12, 18); // 36
     * }</pre>
     *
     * @param a 1つ目の整数
     * @param b 2つ目の整数
     * @return 最小公倍数。いずれかが0の場合は0
     */
    static long lcm(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs((a / gcd(a, b)) * b);
    }

    /**
     * 繰り返し二乗法により {@code base^exponent mod mod} を求める。
     * 時間計算量は O(log exponent)。
     * 乗算結果が {@code long} の範囲内に収まる法を使用すること。
     *
     * <pre>{@code
     * long result = modPow(2, 10, 1_000_000_007L); // 1024
     * }</pre>
     *
     * @param base 底
     * @param exponent 0以上の指数
     * @param mod 正の法
     * @return {@code base^exponent} を {@code mod} で割った余り
     * @throws IllegalArgumentException 指数が負、または法が0以下の場合
     */
    static long modPow(long base, long exponent, long mod) {
        if (exponent < 0 || mod <= 0) {
            throw new IllegalArgumentException(
                "exponent must be non-negative and mod must be positive"
            );
        }

        long result = 1 % mod;
        base = Math.floorMod(base, mod);
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent >>= 1;
        }
        return result;
    }

    /**
     * 正の整数の約数を昇順で列挙する。計算量は O(√n)。
     *
     * <pre>{@code
     * List<Long> result = divisors(12); // [1, 2, 3, 4, 6, 12]
     * }</pre>
     *
     * @param n 約数を求める正の整数
     * @return {@code n} の約数を昇順に格納したリスト
     * @throws IllegalArgumentException {@code n} が0以下の場合
     */
    static List<Long> divisors(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        List<Long> lower = new ArrayList<>();
        List<Long> upper = new ArrayList<>();
        for (long divisor = 1; divisor <= n / divisor; divisor++) {
            if (n % divisor != 0) {
                continue;
            }
            lower.add(divisor);
            if (divisor != n / divisor) {
                upper.add(n / divisor);
            }
        }
        for (int i = upper.size() - 1; i >= 0; i--) {
            lower.add(upper.get(i));
        }
        return lower;
    }
}
