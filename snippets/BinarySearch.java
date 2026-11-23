/**
 * 昇順配列の lower bound・upper bound を求める。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class BinarySearch {

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
    static int lowerBound(int[] values, int target) {
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
    static int upperBound(int[] values, int target) {
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
    static int lowerBound(long[] values, long target) {
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
    static int upperBound(long[] values, long target) {
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
}
