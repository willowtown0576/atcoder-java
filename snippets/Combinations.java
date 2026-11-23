import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * インデックスまたは文字の組み合わせを辞書順で列挙する。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class Combinations {

    /**
     * {@code 0} 以上 {@code n} 未満のインデックスから {@code k} 個を選ぶ組み合わせを、
     * 各配列内およびリスト全体について辞書順ですべて返す。
     * 組み合わせ数を C とすると、時間・空間計算量は返却する配列を含めて O(kC)。
     *
     * <pre>{@code
     * List<int[]> results = combinations(4, 2);
     * // [0, 1], [0, 2], [0, 3], [1, 2], [1, 3], [2, 3]
     * }</pre>
     *
     * @param n 選択対象の要素数
     * @param k 選ぶ要素数
     * @return すべての組み合わせ。{@code k < 0} または {@code k > n} の場合は空リスト
     * @throws IllegalArgumentException {@code n} が負の場合
     */
    static List<int[]> combinations(int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (k < 0 || k > n) {
            return List.of();
        }

        List<int[]> results = new ArrayList<>();
        enumerateCombinations(0, 0, n, new int[k], results);
        return results;
    }

    /** 選択するインデックスを昇順に構築し、組み合わせを列挙する。 */
    private static void enumerateCombinations(
        int start,
        int depth,
        int n,
        int[] current,
        List<int[]> results
    ) {
        if (depth == current.length) {
            results.add(current.clone());
            return;
        }

        int remaining = current.length - depth;
        for (int index = start; index <= n - remaining; index++) {
            current[depth] = index;
            enumerateCombinations(index + 1, depth + 1, n, current, results);
        }
    }

    /**
     * {@code char} 配列から {@code k} 文字を選ぶ相異なる組み合わせを辞書順ですべて返す。
     * 入力配列は変更しない。組み合わせ数を C とすると、時間計算量は O(n log n + kC)、
     * 空間計算量は返却する配列を含めて O(kC)。
     *
     * <pre>{@code
     * List<char[]> results = combinations(new char[] {'a', 'a', 'b', 'c'}, 2);
     * // aa, ab, ac, bc
     * }</pre>
     *
     * @param values 選択対象の文字
     * @param k 選ぶ文字数
     * @return 相異なるすべての組み合わせ。{@code k < 0} または配列長を超える場合は空リスト
     */
    static List<char[]> combinations(char[] values, int k) {
        if (k < 0 || k > values.length) {
            return List.of();
        }

        char[] sorted = values.clone();
        Arrays.sort(sorted);
        List<char[]> results = new ArrayList<>();
        enumerateCombinations(sorted, 0, 0, new char[k], results);
        return results;
    }

    /** 同じ深さで同じ文字を選ばないように、文字の組み合わせを列挙する。 */
    private static void enumerateCombinations(
        char[] values,
        int start,
        int depth,
        char[] current,
        List<char[]> results
    ) {
        if (depth == current.length) {
            results.add(current.clone());
            return;
        }

        int remaining = current.length - depth;
        for (int index = start; index <= values.length - remaining; index++) {
            if (index > start && values[index] == values[index - 1]) {
                continue;
            }
            current[depth] = values[index];
            enumerateCombinations(
                values,
                index + 1,
                depth + 1,
                current,
                results
            );
        }
    }
}
