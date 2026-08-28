import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * AtCoder向けの入出力処理と、競技プログラミングで頻出する処理をまとめたテンプレート。
 */
@SuppressWarnings("unused")
public class Main {

    /** インスタンス化を禁止する。 */
    private Main() {}

    /** 標準入力を高速に読み取るスキャナー。 */
    private static final FastScanner fs = new FastScanner(System.in);
    /** 解答を標準出力へ書き込むライター。 */
    private static final PrintWriter out = new PrintWriter(
        new BufferedWriter(new OutputStreamWriter(System.out))
    );

    /**
     * Delta Row（行番号の変化量）。
     * グリッド上で上下左右へ移動するとき、{@link #DC} の同じインデックスと組み合わせる。
     * 順番は上、右、下、左。
     */
    private static final int[] DR = { -1, 0, 1, 0 };

    /**
     * Delta Column（列番号の変化量）。
     * グリッド上で上下左右へ移動するとき、{@link #DR} の同じインデックスと組み合わせる。
     * 順番は上、右、下、左。
     */
    private static final int[] DC = { 0, 1, 0, -1 };

    /**
     * プログラムのエントリーポイント。
     *
     * @param args コマンドライン引数（通常は使用しない）
     */
    public static void main(String[] args) {
        solve();
        out.flush();
    }

    /**
     * 入力を受け取り、問題の解答を出力する。
     * 問題に応じてこのメソッドの内容を書き換えて使用する。
     */
    private static void solve() {
        int n = fs.nextInt();
        char[] chars = fs.nextCharArray(n);

        String answer = new String(chars);
        out.println(answer);
    }

    /**
     * 指定した座標がグリッドの範囲内か判定する。
     * 時間計算量は O(1)。
     *
     * <pre>{@code
     * int nextRow = row + DR[direction];
     * int nextColumn = column + DC[direction];
     * if (isInside(nextRow, nextColumn, height, width)) {
     *     char nextCell = grid[nextRow][nextColumn];
     * }
     * }</pre>
     *
     * @param row 判定する行番号
     * @param column 判定する列番号
     * @param height グリッドの行数
     * @param width グリッドの列数
     * @return {@code 0 <= row < height} かつ {@code 0 <= column < width} なら {@code true}
     */
    private static boolean isInside(
        int row,
        int column,
        int height,
        int width
    ) {
        return 0 <= row && row < height && 0 <= column && column < width;
    }

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
    private static long ceilDiv(long dividend, long divisor) {
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
    private static long floorDiv(long dividend, long divisor) {
        return Math.floorDiv(dividend, divisor);
    }

    /**
     * 小数を指定した小数点以下の桁数に丸め、改行付きで出力する。
     * {@link Locale#US} を使用するため、実行環境にかかわらず小数点は {@code .} となる。
     * 時間計算量は O(digits)。
     *
     * <pre>{@code
     * printFixed(1.0 / 3.0, 10); // 0.3333333333
     * printFixed(1.23456, 3);     // 1.235
     * }</pre>
     *
     * @param value 出力する値
     * @param digits 小数点以下の桁数
     * @throws IllegalArgumentException {@code digits} が負の場合
     */
    private static void printFixed(double value, int digits) {
        if (digits < 0) {
            throw new IllegalArgumentException("digits must be non-negative");
        }
        out.printf(Locale.US, "%." + digits + "f%n", value);
    }

    /**
     * {@code int} 配列の要素を空白区切りで1行に出力する。
     * 時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(new int[] {1, 2, 3}); // 1 2 3
     * }</pre>
     *
     * @param values 出力する配列
     */
    private static void printArray(int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.print(' ');
            }
            out.print(values[i]);
        }
        out.println();
    }

    /**
     * {@code long} 配列の要素を空白区切りで1行に出力する。
     * 時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(new long[] {1L, 2L, 3L}); // 1 2 3
     * }</pre>
     *
     * @param values 出力する配列
     */
    private static void printArray(long[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.print(' ');
            }
            out.print(values[i]);
        }
        out.println();
    }

    /**
     * {@code double} 配列の要素を空白区切りで1行に出力する。
     * 各要素の小数点以下の桁数は固定しない。時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(new double[] {1.5, 2.5, 3.5}); // 1.5 2.5 3.5
     * }</pre>
     *
     * @param values 出力する配列
     */
    private static void printArray(double[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.print(' ');
            }
            out.print(values[i]);
        }
        out.println();
    }

    /**
     * {@code char} 配列の要素を空白区切りで1行に出力する。
     * 時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(new char[] {'a', 'b', 'c'}); // a b c
     * }</pre>
     *
     * @param values 出力する配列
     */
    private static void printArray(char[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.print(' ');
            }
            out.print(values[i]);
        }
        out.println();
    }

    /**
     * 参照型配列の要素を空白区切りで1行に出力する。
     * 各要素は {@link String#valueOf(Object)} で文字列化する。時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(new String[] {"red", "green", "blue"}); // red green blue
     * }</pre>
     *
     * @param values 出力する配列
     * @param <T> 配列要素の型
     */
    private static <T> void printArray(T[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.print(' ');
            }
            out.print(values[i]);
        }
        out.println();
    }

    /**
     * {@code Yes} を改行付きで出力する。時間計算量は O(1)。
     *
     * <pre>{@code
     * if (condition) Yes();
     * }</pre>
     */
    private static void Yes() {
        out.println("Yes");
    }

    /**
     * {@code No} を改行付きで出力する。時間計算量は O(1)。
     *
     * <pre>{@code
     * if (!condition) No();
     * }</pre>
     */
    private static void No() {
        out.println("No");
    }

    /**
     * {@code YES} を改行付きで出力する。時間計算量は O(1)。
     *
     * <pre>{@code
     * if (condition) YES();
     * }</pre>
     */
    private static void YES() {
        out.println("YES");
    }

    /**
     * {@code NO} を改行付きで出力する。時間計算量は O(1)。
     *
     * <pre>{@code
     * if (!condition) NO();
     * }</pre>
     */
    private static void NO() {
        out.println("NO");
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
    private static boolean isPrime(long n) {
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
    private static boolean[] sieve(int n) {
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
    private static long gcd(long a, long b) {
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
    private static long lcm(long a, long b) {
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
    private static long modPow(long base, long exponent, long mod) {
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
     * 昇順にソート済みの配列から、{@code target} 以上となる最初の位置を返す。
     *
     * <pre>{@code
     * int index = lowerBound(new int[] {1, 2, 2, 4}, 2); // 1
     * }</pre>
     * 時間計算量は O(log n)。
     *
     * @param values 昇順にソート済みの配列
     * @param target 検索する値
     * @return 条件を満たす最初のインデックス。存在しない場合は配列長
     */
    private static int lowerBound(int[] values, int target) {
        int left = 0;
        int right = values.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (values[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /**
     * 昇順にソート済みの配列から、{@code target} より大きくなる最初の位置を返す。
     *
     * <pre>{@code
     * int index = upperBound(new int[] {1, 2, 2, 4}, 2); // 3
     * }</pre>
     * 時間計算量は O(log n)。
     *
     * @param values 昇順にソート済みの配列
     * @param target 検索する値
     * @return 条件を満たす最初のインデックス。存在しない場合は配列長
     */
    private static int upperBound(int[] values, int target) {
        int left = 0;
        int right = values.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (values[middle] <= target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /**
     * 昇順にソート済みの配列から、{@code target} 以上となる最初の位置を返す。
     *
     * <pre>{@code
     * int index = lowerBound(new long[] {1L, 2L, 2L, 4L}, 2L); // 1
     * }</pre>
     * 時間計算量は O(log n)。
     *
     * @param values 昇順にソート済みの配列
     * @param target 検索する値
     * @return 条件を満たす最初のインデックス。存在しない場合は配列長
     */
    private static int lowerBound(long[] values, long target) {
        int left = 0;
        int right = values.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (values[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /**
     * 昇順にソート済みの配列から、{@code target} より大きくなる最初の位置を返す。
     *
     * <pre>{@code
     * int index = upperBound(new long[] {1L, 2L, 2L, 4L}, 2L); // 3
     * }</pre>
     * 時間計算量は O(log n)。
     *
     * @param values 昇順にソート済みの配列
     * @param target 検索する値
     * @return 条件を満たす最初のインデックス。存在しない場合は配列長
     */
    private static int upperBound(long[] values, long target) {
        int left = 0;
        int right = values.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (values[middle] <= target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /**
     * 配列の累積和を {@code long} 型で構築する。
     * 時間計算量は O(n)。
     * 区間 [left, right) の和は {@code sums[right] - sums[left]} で取得できる。
     *
     * <pre>{@code
     * long[] sums = prefixSums(new int[] {1, 2, 3, 4});
     * long rangeSum = sums[3] - sums[1]; // 2 + 3 = 5
     * }</pre>
     *
     * @param values 元の配列
     * @return 先頭に0を持つ、長さ {@code values.length + 1} の累積和配列
     */
    private static long[] prefixSums(int[] values) {
        long[] sums = new long[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            sums[i + 1] = sums[i] + values[i];
        }
        return sums;
    }

    /**
     * 配列の累積和を構築する。
     * 時間計算量は O(n)。
     * 区間 [left, right) の和は {@code sums[right] - sums[left]} で取得できる。
     *
     * <pre>{@code
     * long[] sums = prefixSums(new long[] {1L, 2L, 3L, 4L});
     * long rangeSum = sums[4] - sums[2]; // 3 + 4 = 7
     * }</pre>
     *
     * @param values 元の配列
     * @return 先頭に0を持つ、長さ {@code values.length + 1} の累積和配列
     */
    private static long[] prefixSums(long[] values) {
        long[] sums = new long[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            sums[i + 1] = sums[i] + values[i];
        }
        return sums;
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
    private static List<Long> divisors(long n) {
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

    /**
     * バッファリングによって標準入力を高速に読み取るスキャナー。
     * 入力は空白文字で区切られたASCIIトークンとして扱う。
     *
     * <p>読み取った各バイトを文字コードでデコードせず、直接 {@code char} へ変換する。
     * そのため、数値、英数字、記号などのASCII入力を前提とし、日本語や絵文字を含む
     * UTF-8のマルチバイト文字には対応しない。非ASCII文字を読み取ると文字化けし、
     * {@link #nextChar()} や {@link #nextChars()} で得られる文字数も入力上の文字数と一致しない。</p>
     *
     * {@link java.util.Scanner} は記述が簡単な一方、正規表現によるトークン分割や
     * 型変換などのオーバーヘッドが大きい。AtCoderで入力件数が多い問題に使用すると、
     * 入力処理がボトルネックとなり実行時間制限を超える場合がある。
     * このクラスはバイト列をまとめて読み込み、必要最小限の処理でトークンを切り出すことで、
     * 大量の入力を高速に処理する。
     */
    private static final class FastScanner {

        /** 一度に入力ストリームから読み込む最大バイト数。 */
        private static final int BUFFER_SIZE = 1 << 16;

        /** 読み取り元の入力ストリーム。 */
        private final InputStream in;
        /** 読み取ったバイトを一時的に保持するバッファ。 */
        private final byte[] buffer = new byte[BUFFER_SIZE];
        /** バッファ内で次に読み取る位置。 */
        private int pointer;
        /** バッファ内に保持している有効なバイト数。 */
        private int length;

        /**
         * 指定された入力ストリームを読み取るスキャナーを生成する。
         *
         * @param in 読み取り元の入力ストリーム
         */
        private FastScanner(InputStream in) {
            this.in = in;
        }

        /**
         * 入力ストリームから次の1バイトを読み取る。
         *
         * @return 読み取った符号なしバイト値。入力末尾の場合は {@code -1}
         * @throws UncheckedIOException 入力ストリームの読み取りに失敗した場合
         */
        private int readByte() {
            if (pointer >= length) {
                try {
                    length = in.read(buffer);
                    pointer = 0;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                if (length <= 0) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }

        /**
         * 次の空白区切りトークンを文字列として読み取る。
         *
         * @return 読み取った文字列
         * @throws NoSuchElementException 読み取るトークンが残っていない場合
         */
        String next() {
            StringBuilder sb = new StringBuilder();
            int b;
            do {
                b = readByte();
            } while (b <= ' ' && b != -1);

            if (b == -1) {
                throw new NoSuchElementException("No more input");
            }

            while (b > ' ') {
                sb.append((char) b);
                b = readByte();
            }
            return sb.toString();
        }

        /**
         * 次のトークンを {@code int} として読み取る。
         *
         * @return 読み取った整数
         * @throws NumberFormatException トークンを {@code int} に変換できない場合
         */
        int nextInt() {
            return Integer.parseInt(next());
        }

        /**
         * 次のトークンを {@code long} として読み取る。
         *
         * @return 読み取った整数
         * @throws NumberFormatException トークンを {@code long} に変換できない場合
         */
        long nextLong() {
            return Long.parseLong(next());
        }

        /**
         * 次のトークンを {@code double} として読み取る。
         *
         * @return 読み取った浮動小数点数
         * @throws NumberFormatException トークンを {@code double} に変換できない場合
         */
        double nextDouble() {
            return Double.parseDouble(next());
        }

        /**
         * 次のトークンの先頭文字を読み取る。
         *
         * @return 読み取ったトークンの先頭文字
         */
        char nextChar() {
            return next().charAt(0);
        }

        /**
         * 次の空白なしトークンを文字配列として読み取る。
         * 例えば {@code abcde} は {@code {'a', 'b', 'c', 'd', 'e'}} として読み取られる。
         *
         * <pre>{@code
         * char[] chars = fs.nextChars();
         * }</pre>
         *
         * @return 読み取ったトークンを1文字ずつ格納した配列
         */
        char[] nextChars() {
            return next().toCharArray();
        }

        /**
         * 空白を含まない文字列で構成されたグリッドを読み取る。
         * 各行の文字数が同じであることを前提とする。
         *
         * <pre>{@code
         * int height = fs.nextInt();
         * int width = fs.nextInt();
         * char[][] grid = fs.nextCharGrid(height);
         * }</pre>
         *
         * @param height グリッドの行数
         * @return 各入力行を文字配列として格納した二次元配列
         */
        char[][] nextCharGrid(int height) {
            char[][] grid = new char[height][];
            for (int row = 0; row < height; row++) {
                grid[row] = nextChars();
            }
            return grid;
        }

        /**
         * 空白区切りのトークンから先頭文字を指定個数読み取る。
         * 例えば {@code a b c} は {@code {'a', 'b', 'c'}} として読み取られる。
         *
         * @param length 読み取る文字数
         * @return 読み取った文字の配列
         */
        char[] nextCharArray(int length) {
            char[] values = new char[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextChar();
            }
            return values;
        }

        /**
         * 空白区切りの整数を指定個数読み取る。
         *
         * @param length 読み取る要素数
         * @return 読み取った {@code int} 配列
         */
        int[] nextIntArray(int length) {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextInt();
            }
            return values;
        }

        /**
         * 空白区切りの整数を指定個数読み取る。
         *
         * @param length 読み取る要素数
         * @return 読み取った {@code long} 配列
         */
        long[] nextLongArray(int length) {
            long[] values = new long[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextLong();
            }
            return values;
        }
    }
}
