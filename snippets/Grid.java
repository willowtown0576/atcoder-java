/**
 * 上下左右への移動と、グリッドの範囲内判定。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class Grid {

    /**
     * Delta Row（行番号の変化量）。
     * グリッド上で上下左右へ移動するとき、{@link #DC} の同じインデックスと組み合わせる。
     * 順番は上、右、下、左。
     */
    static final int[] DR = { -1, 0, 1, 0 };

    /**
     * Delta Column（列番号の変化量）。
     * グリッド上で上下左右へ移動するとき、{@link #DR} の同じインデックスと組み合わせる。
     * 順番は上、右、下、左。
     */
    static final int[] DC = { 0, 1, 0, -1 };

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
    static boolean isInside(
        int row,
        int column,
        int height,
        int width
    ) {
        return 0 <= row && row < height && 0 <= column && column < width;
    }
}
