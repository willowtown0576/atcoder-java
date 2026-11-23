import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * AtCoder向けの標準入力・標準出力をまとめたテンプレート。
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
        out.println("Hello, World!");
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
     * リストの要素を空白区切りで1行に出力する。空リストは改行だけを出力する。
     * 各要素は {@link String#valueOf(Object)} で文字列化する。時間計算量は O(n)。
     *
     * <pre>{@code
     * printArray(List.of(1, 2, 3)); // 1 2 3
     * printArray(List.of("red", "green")); // red green
     * }</pre>
     *
     * @param values 出力するリスト
     */
    private static void printArray(List<?> values) {
        boolean first = true;
        for (Object value : values) {
            if (!first) {
                out.print(' ');
            }
            out.print(value);
            first = false;
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
