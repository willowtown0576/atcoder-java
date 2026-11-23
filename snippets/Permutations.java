import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 相異なる順列を辞書順で列挙する。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class Permutations {

    /**
     * {@code int} 配列の相異なる順列を辞書順ですべて返す。
     * 入力配列は変更しない。要素数を n、順列数を P とすると、時間計算量は O(n log n + nP)、
     * 空間計算量は返却する配列を含めて O(nP)。
     *
     * <pre>{@code
     * List<int[]> results = permutations(new int[] {1, 1, 2});
     * // [1, 1, 2], [1, 2, 1], [2, 1, 1]
     * }</pre>
     *
     * @param values 並べ替える値
     * @return 相異なるすべての順列。空配列の場合は空配列1つを含む
     */
    static List<int[]> permutations(int[] values) {
        int[] current = values.clone();
        Arrays.sort(current);

        List<int[]> results = new ArrayList<>();
        do {
            results.add(current.clone());
        } while (nextPermutation(current));
        return results;
    }

    /**
     * {@code char} 配列の相異なる順列を辞書順ですべて返す。
     * 入力配列は変更しない。要素数を n、順列数を P とすると、時間計算量は O(n log n + nP)、
     * 空間計算量は返却する配列を含めて O(nP)。
     *
     * <pre>{@code
     * List<char[]> results = permutations(new char[] {'a', 'a', 'b'});
     * // aab, aba, baa
     * }</pre>
     *
     * @param values 並べ替える文字
     * @return 相異なるすべての順列。空配列の場合は空配列1つを含む
     */
    static List<char[]> permutations(char[] values) {
        char[] current = values.clone();
        Arrays.sort(current);

        List<char[]> results = new ArrayList<>();
        do {
            results.add(current.clone());
        } while (nextPermutation(current));
        return results;
    }

    /** 辞書順で次の順列へ進める。最後の順列の場合は {@code false} を返す。 */
    static boolean nextPermutation(int[] values) {
        int pivot = values.length - 2;
        while (pivot >= 0 && values[pivot] >= values[pivot + 1]) {
            pivot--;
        }
        if (pivot < 0) {
            return false;
        }

        int successor = values.length - 1;
        while (values[successor] <= values[pivot]) {
            successor--;
        }
        int temporary = values[pivot];
        values[pivot] = values[successor];
        values[successor] = temporary;

        for (
            int left = pivot + 1, right = values.length - 1;
            left < right;
            left++, right--
        ) {
            temporary = values[left];
            values[left] = values[right];
            values[right] = temporary;
        }
        return true;
    }

    /** 辞書順で次の順列へ進める。最後の順列の場合は {@code false} を返す。 */
    static boolean nextPermutation(char[] values) {
        int pivot = values.length - 2;
        while (pivot >= 0 && values[pivot] >= values[pivot + 1]) {
            pivot--;
        }
        if (pivot < 0) {
            return false;
        }

        int successor = values.length - 1;
        while (values[successor] <= values[pivot]) {
            successor--;
        }
        char temporary = values[pivot];
        values[pivot] = values[successor];
        values[successor] = temporary;

        for (
            int left = pivot + 1, right = values.length - 1;
            left < right;
            left++, right--
        ) {
            temporary = values[left];
            values[left] = values[right];
            values[right] = temporary;
        }
        return true;
    }
}
