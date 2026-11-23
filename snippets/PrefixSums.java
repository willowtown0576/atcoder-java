/**
 * 半開区間の和を求めるための累積和を構築する。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class PrefixSums {

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
    static long[] prefixSums(int[] values) {
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
    static long[] prefixSums(long[] values) {
        long[] sums = new long[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            sums[i + 1] = sums[i] + values[i];
        }
        return sums;
    }
}
