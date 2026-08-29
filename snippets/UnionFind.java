import java.util.Arrays;

/**
 * 素集合データ構造（Union-Find、Disjoint Set Union）。
 * 経路圧縮と union by size により、集合の統合と同一集合判定を高速に行う。
 * 必要なときに、このクラスを {@code Main.java} の末尾へコピーして使用する。
 */
final class UnionFind {

    /**
     * 根では負の集合サイズ、根以外では親の要素番号を保持する。
     */
    private final int[] parentOrSize;

    /** 現在の集合数。 */
    private int groupCount;

    /**
     * {@code 0} から {@code size - 1} までの要素を、それぞれ独立した集合として初期化する。
     * 時間計算量とメモリ使用量は O(N)。
     *
     * @param size 要素数
     * @throws IllegalArgumentException {@code size} が負の場合
     */
    UnionFind(int size) {
        if (size < 0) {
            throw new IllegalArgumentException(
                "size must be non-negative: " + size
            );
        }
        parentOrSize = new int[size];
        Arrays.fill(parentOrSize, -1);
        groupCount = size;
    }

    /**
     * 指定した要素が属する集合の代表元を返す。
     * 探索中に通った要素を代表元へ直接つなぎ替え、経路を圧縮する。
     * 償却計算量は O(α(N))。
     *
     * @param vertex 要素番号
     * @return {@code vertex} が属する集合の代表元
     * @throws IndexOutOfBoundsException {@code vertex} が要素番号の範囲外の場合
     */
    int leader(int vertex) {
        if (parentOrSize[vertex] < 0) {
            return vertex;
        }
        return (parentOrSize[vertex] = leader(parentOrSize[vertex]));
    }

    /**
     * 2つの要素が同じ集合に属するか判定する。
     * 償却計算量は O(α(N))。
     *
     * @param first 1つ目の要素番号
     * @param second 2つ目の要素番号
     * @return 同じ集合に属する場合は {@code true}
     * @throws IndexOutOfBoundsException いずれかの要素番号が範囲外の場合
     */
    boolean same(int first, int second) {
        return leader(first) == leader(second);
    }

    /**
     * 2つの要素が属する集合を統合する。
     * 要素数の少ない木を多い木の下へ接続する。
     * 償却計算量は O(α(N))。
     *
     * @param first 1つ目の要素番号
     * @param second 2つ目の要素番号
     * @return 異なる集合を新しく統合した場合は {@code true}、既に同じ集合だった場合は {@code false}
     * @throws IndexOutOfBoundsException いずれかの要素番号が範囲外の場合
     */
    boolean merge(int first, int second) {
        int firstLeader = leader(first);
        int secondLeader = leader(second);

        if (firstLeader == secondLeader) {
            return false;
        }

        if (-parentOrSize[firstLeader] < -parentOrSize[secondLeader]) {
            int temporary = firstLeader;
            firstLeader = secondLeader;
            secondLeader = temporary;
        }

        parentOrSize[firstLeader] += parentOrSize[secondLeader];
        parentOrSize[secondLeader] = firstLeader;
        groupCount--;
        return true;
    }

    /**
     * 指定した要素が属する集合の要素数を返す。
     * 償却計算量は O(α(N))。
     *
     * @param vertex 要素番号
     * @return {@code vertex} が属する集合の要素数
     * @throws IndexOutOfBoundsException {@code vertex} が要素番号の範囲外の場合
     */
    int size(int vertex) {
        return -parentOrSize[leader(vertex)];
    }

    /**
     * 現在の集合数を返す。
     * 時間計算量は O(1)。
     *
     * @return 現在の集合数
     */
    int groupCount() {
        return groupCount;
    }

    /**
     * すべての集合について、所属する要素番号を列挙する。
     * 戻り値における集合の順番や代表元の番号には依存しないこと。
     * 時間計算量は O(Nα(N))、追加メモリ使用量は O(N)。
     *
     * @return 集合ごとの要素番号を格納した二次元配列
     */
    int[][] groups() {
        int[] groupSizes = new int[parentOrSize.length];
        for (int vertex = 0; vertex < parentOrSize.length; vertex++) {
            groupSizes[leader(vertex)]++;
        }

        int[][] membersByLeader = new int[parentOrSize.length][];
        for (int leader = 0; leader < parentOrSize.length; leader++) {
            if (groupSizes[leader] > 0) {
                membersByLeader[leader] = new int[groupSizes[leader]];
            }
        }

        int[] positions = new int[parentOrSize.length];
        for (int vertex = 0; vertex < parentOrSize.length; vertex++) {
            int leader = leader(vertex);
            membersByLeader[leader][positions[leader]++] = vertex;
        }

        int[][] result = new int[groupCount][];
        int resultIndex = 0;
        for (int leader = 0; leader < parentOrSize.length; leader++) {
            if (membersByLeader[leader] != null) {
                result[resultIndex++] = membersByLeader[leader];
            }
        }
        return result;
    }
}
