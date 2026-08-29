# Algorithms for Competitive Programming

競技プログラミングで頻出するアルゴリズムを、問題から採用方針を判断しやすい形でまとめる。

コード例は Java 21 を前提とする。実際に使用する際は、問題の制約、入力形式、頂点番号、オーバーフローを確認して調整すること。

## 目次

- [アルゴリズムを選ぶ前に](#アルゴリズムを選ぶ前に)
- [早見表](#早見表)
- [基本的な処理](#基本的な処理)
    - [各桁の数字の和](#各桁の数字の和)
    - [10進数の桁数](#10進数の桁数)
    - [回文判定](#回文判定)
    - [英小文字の出現回数](#英小文字の出現回数)
    - [プリミティブ配列を降順にする](#プリミティブ配列を降順にする)
- [再帰](#再帰)
    - [ベースケースと再帰ケース](#ベースケースと再帰ケース)
    - [行きがけと帰りがけ](#行きがけと帰りがけ)
    - [戻り値を使う再帰](#戻り値を使う再帰)
    - [状態の持ち方](#状態の持ち方)
    - [バックトラッキング](#バックトラッキング)
    - [計算量](#再帰の計算量)
    - [再帰を使うか反復処理にするか](#再帰を使うか反復処理にするか)
- [数論](#数論)
    - [素数判定](#素数判定)
    - [エラトステネスの篩](#エラトステネスの篩)
    - [最大公約数（GCD）](#最大公約数gcd)
    - [最小公倍数（LCM）](#最小公倍数lcm)
    - [約数列挙](#約数列挙)
    - [剰余演算](#剰余演算)
    - [繰り返し二乗法](#繰り返し二乗法)
    - [切り上げ除算と切り下げ除算](#切り上げ除算と切り下げ除算)
- [グラフ探索](#グラフ探索)
    - [グラフの表現](#グラフの表現)
    - [幅優先探索（BFS）](#幅優先探索bfs)
    - [グリッド上の BFS](#グリッド上の-bfs)
    - [深さ優先探索（DFS）](#深さ優先探索dfs)
    - [BFS と DFS の選択](#bfs-と-dfs-の選択)
- [Union-Find](#union-find)
- [最短経路](#最短経路)
    - [Dijkstra 法](#dijkstra-法)
    - [0-1 BFS](#0-1-bfs)
- [DAG](#dag)
    - [トポロジカルソート](#トポロジカルソート)
- [探索と境界](#探索と境界)
    - [二分探索](#二分探索)
    - [答えで二分探索](#答えで二分探索)
- [区間処理](#区間処理)
    - [累積和](#累積和)
    - [二次元累積和](#二次元累積和)
    - [いもす法](#いもす法)
- [連続区間](#連続区間)
    - [尺取り法](#尺取り法)
    - [固定長スライディングウィンドウ](#固定長スライディングウィンドウ)
- [座標圧縮](#座標圧縮)
- [動的計画法（DP）](#動的計画法dp)
    - [1次元 DP](#1次元-dp)
    - [0/1 ナップサック](#01-ナップサック)
    - [部分和 DP](#部分和-dp)
    - [2次元 DP](#2次元-dp)
    - [メモ化再帰](#メモ化再帰)
- [全探索](#全探索)
    - [bit 全探索](#bit-全探索)
    - [順列全探索](#順列全探索)
    - [組み合わせ全探索](#組み合わせ全探索)
- [貪欲法](#貪欲法)
    - [区間スケジューリング](#例-区間スケジューリング)
- [木](#木)
    - [木の基本性質](#木の基本性質)
    - [部分木サイズ](#部分木サイズ)
    - [木の直径](#木の直径)
- [よくある失敗](#よくある失敗)
- [`template/Main.java` アルゴリズム索引](#templatemainjava-アルゴリズム索引)
- [発展的なアルゴリズム](#発展的なアルゴリズム)

## アルゴリズムを選ぶ前に

最初に入力サイズから許容される計算量を見積もる。

|     入力サイズの目安 | 検討できる計算量   |
| -------------------: | ------------------ |
|               N ≤ 20 | O(2^N)、O(N × 2^N) |
|              N ≤ 500 | O(N^3) を検討      |
|            N ≤ 5,000 | O(N^2) を検討      |
|         N ≤ 2 × 10^5 | O(N log N)、O(N)   |
|             N ≤ 10^6 | ほぼ O(N)          |
| 値だけが非常に大きい | O(log N) を検討    |

これは厳密な基準ではない。定数倍、制限時間、言語、処理内容によって変わる。

## 早見表

| 問題の特徴                           | 候補                                |
| ------------------------------------ | ----------------------------------- |
| 重みなしグラフの最短距離             | BFS                                 |
| グリッドの最短手数                   | BFS                                 |
| 連結成分、到達可能性、木の走査       | DFS / BFS                           |
| 辺の追加と連結判定                   | Union-Find                          |
| 非負重みグラフの最短距離             | Dijkstra                            |
| 依存関係の順序、DAG                  | トポロジカルソート                  |
| ソート済み列から境界を探す           | 二分探索                            |
| 答えが単調に判定できる               | 答えで二分探索                      |
| 静的な区間和を何度も求める           | 累積和                              |
| 区間加算をまとめて処理する           | いもす法                            |
| 連続区間を伸縮して条件を満たす       | 尺取り法 / スライディングウィンドウ |
| 値は大きいが種類数が少ない           | 座標圧縮                            |
| 選択肢の結果を状態として再利用できる | 動的計画法                          |
| N ≤ 20 程度で全部分集合を調べる      | bit 全探索                          |
| 局所的な最善選択が交換可能           | 貪欲法                              |

# 基本的な処理

## 各桁の数字の和

文字列として受け取る場合:

```java
int digitSum = text.chars().map(character -> character - '0').sum();
```

整数として受け取る場合:

```java
static int digitSum(long value) {
    value = Math.abs(value);
    int sum = 0;

    do {
        sum += value % 10;
        value /= 10;
    } while (value > 0);

    return sum;
}
```

`Long.MIN_VALUE` は `Math.abs` でも正数にできない。入力範囲に含まれる場合は文字列として扱う。

## 10進数の桁数

```java
int digitCount = Long.toString(Math.abs(value)).length();
```

`Long.MIN_VALUE` を含む場合:

```java
int digitCount = Long.toString(value).replace("-", "").length();
```

## 回文判定

```java
static boolean isPalindrome(String text) {
    int left = 0;
    int right = text.length() - 1;

    while (left < right) {
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }

    return true;
}
```

計算量は $`O(N)`$、追加メモリは $`O(1)`$。

## 英小文字の出現回数

```java
int[] frequencies = new int[26];
for (char character : text.toCharArray()) {
    frequencies[character - 'a']++;
}
```

英小文字以外を含む場合は、`Map<Character, Integer>` や `Map.merge` を検討する。

## プリミティブ配列を降順にする

`Arrays.sort` はプリミティブ配列に `Comparator` を指定できないため、昇順ソート後に反転する。

```java
Arrays.sort(values);

for (int left = 0, right = values.length - 1; left < right; left++, right--) {
    int temporary = values[left];
    values[left] = values[right];
    values[right] = temporary;
}
```

# 再帰

再帰は、メソッドの処理中に同じメソッドを呼び出す書き方である。大きな問題を同じ形の小さな問題へ分解できる場合に使う。

例えば、`1`から`n`までの和は、「`1`から`n - 1`までの和」に`n`を加えたものとして表せる。

```java
static long sumTo(int n) {
    if (n == 0) {
        return 0;
    }
    return sumTo(n - 1) + n;
}
```

`sumTo(3)`の呼び出しは次のように展開される。

```text
sumTo(3)
= sumTo(2) + 3
= sumTo(1) + 2 + 3
= sumTo(0) + 1 + 2 + 3
= 0 + 1 + 2 + 3
= 6
```

## ベースケースと再帰ケース

再帰メソッドは、次の2つで構成する。

- **ベースケース**: それ以上分解せず、値を直接返す条件
- **再帰ケース**: 問題を小さくして自分自身を呼び出す処理

```java
static long factorial(int n) {
    if (n == 0) { // ベースケース
        return 1;
    }
    return n * factorial(n - 1); // 再帰ケース
}
```

再帰呼び出しの引数は、必ずベースケースへ近づかなければならない。次の実装は`n`が変化しないため終了しない。

```java
static long factorial(int n) {
    if (n == 0) {
        return 1;
    }
    return n * factorial(n); // nが減らない
}
```

入力として負数もあり得るなら、ベースケースだけでなく、メソッドが受け付ける範囲も決める。

```java
static long factorial(int n) {
    if (n < 0) {
        throw new IllegalArgumentException("n must be non-negative");
    }
    if (n == 0) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

## 行きがけと帰りがけ

再帰呼び出しより前の処理は、深い呼び出しへ進む順に実行される。再帰呼び出しより後の処理は、ベースケースへ到達してから逆順に実行される。

```java
static void trace(int n) {
    if (n == 0) {
        return;
    }

    System.out.println("enter " + n); // 行きがけ
    trace(n - 1);
    System.out.println("leave " + n); // 帰りがけ
}
```

`trace(3)`の出力:

```text
enter 3
enter 2
enter 1
leave 1
leave 2
leave 3
```

木の探索では、行きがけの処理を先行順、帰りがけの処理を後行順として利用できる。部分木の情報を集めて親の値を求める処理は、子の呼び出しが終わった後に置く。

## 戻り値を使う再帰

再帰メソッドが値を返す場合は、次の3点を決める。

1. 引数がどの状態を表すか
2. 戻り値が何を表すか
3. 子の戻り値から現在の答えをどう作るか

次の例では、`current`を根とする部分木の頂点数を返す。

```java
static int subtreeSize(
    List<List<Integer>> tree,
    int current,
    int parent
) {
    int result = 1;

    for (int next : tree.get(current)) {
        if (next == parent) {
            continue;
        }
        result += subtreeSize(tree, next, current);
    }

    return result;
}
```

木では親へ戻る辺を除外する。一般グラフには親以外を経由する閉路もあるため、`visited`による訪問済み管理が必要になる。

## 状態の持ち方

再帰中に使う値は、役割に応じて置き場所を分ける。

| 置き場所             | 適する値                                                           |
| -------------------- | ------------------------------------------------------------------ |
| 引数                 | 現在位置、残り回数、親頂点など、呼び出しごとに異なる状態           |
| ローカル変数         | 部分木の合計など、その呼び出しの中だけで作る値                     |
| 戻り値               | 子の計算結果を親へ渡す値                                           |
| 共有フィールド・配列 | グラフ、訪問済み配列、探索全体の答えなど、全呼び出しで共有する状態 |

Javaにはトップレベルのグローバル変数はない。競プロでは、`Main`の`static`フィールドを全呼び出しで共有する変数として使うことがある。

```java
private static List<List<Integer>> graph;
private static boolean[] visited;
private static int visitedCount;

private static void dfs(int current) {
    visited[current] = true;
    visitedCount++;

    for (int next : graph.get(current)) {
        if (!visited[next]) {
            dfs(next);
        }
    }
}
```

共有フィールドを使うと引数を減らせるが、どの値を読み書きしているかがメソッドの宣言から分かりにくくなる。現在位置のような呼び出し固有の状態は引数に置き、グラフのように探索中ずっと同じ対象だけを共有すると整理しやすい。

答えを共有フィールドへ加算する代わりに、戻り値で集計できる場合もある。

```java
private static int countReachable(int current) {
    visited[current] = true;
    int count = 1;

    for (int next : graph.get(current)) {
        if (!visited[next]) {
            count += countReachable(next);
        }
    }

    return count;
}
```

どちらを選ぶかは、値の意味で判断する。

- 部分問題の答えを親で組み合わせるなら、戻り値を使う
- 探索全体で1つの答えを更新するなら、共有フィールドを使える
- 分岐ごとに値が異なるなら、引数またはローカル変数にする
- 配列やリストを全呼び出しで参照するなら、変更が他の分岐にも残ることを考慮する

共有状態を変更した後、元に戻す必要があるかは探索の意味によって異なる。`visited`のように「一度処理したら探索全体で処理済み」とする状態は戻さない。現在の選択列や使用中の要素のように「この分岐でだけ有効」な状態は、再帰から戻った後に元へ戻す。

```java
used[index] = true;
current.add(values[index]);

search();

current.remove(current.size() - 1);
used[index] = false;
```

同じ再帰メソッドを複数回実行する場合は、共有フィールドの初期化にも注意する。

```java
visited = new boolean[vertexCount];
visitedCount = 0;
dfs(start);
```

前回の値が残ると、単独では正しく動くメソッドでも2回目以降の結果が変わる。

## バックトラッキング

候補を1つ選んで再帰し、呼び出しから戻った後に選択を取り消すことで、別の候補を試せる。この手順をバックトラッキングという。

```java
static void enumerate(
    int[] values,
    boolean[] used,
    List<Integer> current
) {
    if (current.size() == values.length) {
        System.out.println(current);
        return;
    }

    for (int index = 0; index < values.length; index++) {
        if (used[index]) {
            continue;
        }

        used[index] = true;
        current.add(values[index]);

        enumerate(values, used, current);

        current.remove(current.size() - 1);
        used[index] = false;
    }
}
```

`current.add`と`used[index] = true`が選択にあたり、再帰後の2行が選択の取り消しにあたる。取り消しを忘れると、次の分岐へ前の状態が残る。

配列を書き換えながら探索する場合も同様に、変更前の値を保存し、再帰後に元へ戻す。

## 再帰の計算量

再帰であること自体から計算量は決まらない。次の2つを分けて数える。

- メソッドが呼ばれる回数
- 1回の呼び出しで、再帰呼び出し以外に行う処理

`sumTo(n)`は呼び出し回数が`n + 1`で、各呼び出しの処理が`O(1)`なので、時間計算量は`O(N)`となる。呼び出しが戻るまで各状態をスタックに保持するため、追加メモリも`O(N)`となる。

```java
static long fibonacci(int n) {
    if (n <= 1) {
        return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
}
```

この実装は同じ引数を何度も計算するため、呼び出し回数が指数的に増える。計算済みの結果を保存するメモ化再帰か、反復DPへ変更する。

また、再帰の深さは呼び出し回数と同じとは限らない。二分木をすべて探索する場合、呼び出し回数は頂点数に比例するが、同時にスタックへ積まれる深さは木の高さに比例する。

## 再帰を使うか反復処理にするか

再帰は、木構造、DFS、分割統治、バックトラッキングのように、処理対象が再帰的な構造を持つ場合に書きやすい。一方、単純な繰り返しや深い探索では反復処理が適している。

| 条件                               | 選択                         |
| ---------------------------------- | ---------------------------- |
| 再帰的な構造をそのまま表現したい   | 再帰を検討する               |
| 呼び出しの深さが小さいと保証できる | 再帰を使える                 |
| 深さが入力に比例し、入力が大きい   | 反復処理を使う               |
| 同じ状態を何度も計算する           | メモ化再帰またはDPを使う     |
| 状態を戻しながら全候補を試す       | バックトラッキングを検討する |

Javaでは、深い再帰呼び出しにより`StackOverflowError`が発生する。特に、直線状の木やグラフでは頂点数と再帰の深さが等しくなる。深さが`10^5`程度になる可能性があるDFSは、`Deque`をスタックとして使う反復DFSへ変更する。

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(start);

while (!stack.isEmpty()) {
    int current = stack.pop();
    // currentを処理し、次の状態をstackへ追加する
}
```

末尾再帰で書いても、Javaは末尾呼び出し最適化を保証しない。再帰の深さは減らないため、単純なループへ変換できる処理はループで書く。

# 数論

## 素数判定

### 解決したい問題

1つの整数 `n` が素数か判定する。素数は「1と自分自身以外に正の約数を持たない、2以上の整数」である。

### 考え方

$`n`$ が合成数なら、$`n = ab`$ と表せる。もし $`a`$ と $`b`$ が両方とも $`\sqrt{n}`$ より大きければ、積は $`n`$ より大きくなってしまう。したがって、合成数には必ず $`\sqrt{n}`$ 以下の約数が存在する。

この性質により、$`2`$ から $`n - 1`$ まで調べる必要はなく、$`\sqrt{n}`$ まで割り切れる数があるか確認すればよい。

### 実装

`template/Main.java` の `isPrime(long)` を使う。

```java
boolean prime = isPrime(value);
```

実装では `divisor * divisor <= n` とすると積がオーバーフローする可能性があるため、`divisor <= n / divisor` の形で比較している。

### 計算量

$`O(\sqrt{n})`$

### 採用判断

- 判定対象が1個または少数なら `isPrime`
- `1` から `N` まで大量に判定するならエラトステネスの篩
- 非常に大きな64 bit整数を大量に判定する場合は Miller–Rabin 法などを検討

### 注意点

- `0`、`1`、負数は素数ではない
- `2` は素数である
- 偶数を先に除外すると試し割りを半分にできる

## エラトステネスの篩

### 解決したい問題

`0` 以上 `N` 以下の各整数が素数か、一度に前計算する。

### 考え方

素数 `p` が見つかったら、`p` の倍数はすべて合成数である。小さい素数から順に倍数を消していくと、最後まで消されなかった2以上の整数だけが素数として残る。

$`p`$ の倍数を $`2p`$ から消す必要はない。$`2p, 3p, \ldots, (p - 1)p`$ は、それより小さい因数を処理した時点ですでに消されている。そのため、未処理の可能性がある最初の倍数 $`p^2`$ から始められる。

### 実装

`template/Main.java` の `sieve(int)` を使う。

```java
boolean[] prime = sieve(limit);

if (prime[value]) {
    // valueは素数
}
```

### 計算量

- 時間: $`O(N \log\,\log N)`$
- メモリ: $`O(N)`$

### 採用判断

- 上限 `N` が現実的な大きさで、多数の素数判定を行う
- 素数一覧や素数の個数も必要
- 判定対象の最大値が $`10^7`$ 前後までなら、メモリ制限を確認したうえで有力

### 注意点

- `boolean[N + 1]` のメモリが必要
- `p * p` が `int` を超えないよう、実装上の型に注意する
- 上限が $`10^9`$ でも判定対象が数個だけなら、配列を作らず `isPrime` のほうが適する

## 最大公約数（GCD）

### 解決したい問題

2つの整数をともに割り切る最大の正整数を求める。分数の約分、周期の一致、比の正規化などで使う。

### 考え方

ユークリッドの互除法は次の性質を使う。

$$
\gcd(a, b) = \gcd(b, a \bmod b)
$$

$`a = qb + r`$ とすると、$`a`$ と $`b`$ の共通約数は $`r = a - qb`$ も割り切る。逆に、$`b`$ と $`r`$ の共通約数は $`a = qb + r`$ も割り切る。したがって、2組の共通約数は同じであり、最大公約数も変わらない。

余りへ置き換えるたびに値が小さくなり、余りが0になったときの除数が最大公約数となる。

### 実装

`template/Main.java` の `gcd(long, long)` を使う。

```java
long divisor = gcd(first, second);
```

### 計算量

$`O(\log(\min(|a|, |b|)))`$

### 重要な性質

$$
\gcd(a, 0) = |a|, \qquad \gcd(0, b) = |b|
$$

複数の値にも順番に適用できる。

```java
long result = 0;
for (long value : values) {
    result = gcd(result, value);
}
```

## 最小公倍数（LCM）

### 解決したい問題

2つの整数の共通の倍数のうち、最小の非負整数を求める。周期が初めて一致する時点などで使う。

### 考え方

素因数分解で見ると、GCD は各素因数の指数の最小値、LCM は最大値を取る。この関係から次が成り立つ。

$$
|ab| = \gcd(a, b)\mathrm{lcm}(a, b)
$$

よって次の式で求められる。

$$
\mathrm{lcm}(a, b)
= \left|\frac{a}{\gcd(a, b)}b\right|
$$

先に GCD で割ることで、`a * b` を直接計算するよりオーバーフローしにくくなる。

### 実装

`template/Main.java` の `lcm(long, long)` を使う。

```java
long multiple = lcm(first, second);
```

### 計算量

GCD が支配するため $`O(\log(\min(|a|, |b|)))`$。

### 注意点

- どちらかが0なら LCM は0
- 結果が `long` の範囲を超える可能性は残る
- 複数の値に適用すると LCM が急速に大きくなりやすい

## 約数列挙

### 解決したい問題

正整数 `n` の正の約数をすべて求める。

### 考え方

`d` が `n` の約数なら、`n / d` も約数である。約数は積が `n` になるペアで現れる。

$$
d\frac{n}{d} = n
$$

各ペアの片方は必ず $`\sqrt{n}`$ 以下なので、$`1`$ から $`\sqrt{n}`$ まで調べれば全約数を発見できる。

### 実装

`template/Main.java` の `divisors(long)` を使う。

```java
List<Long> values = divisors(n);
```

戻り値は昇順である。

### 計算量

- 列挙: $`O(\sqrt{n})`$
- ソートを含む実装では、約数個数を `D` として追加で $`O(D \log D)`$

### 注意点

完全平方数では `d == n / d` となるため、同じ約数を2回追加しない。

$$
n = 36, \qquad d = 6, \qquad \frac{n}{d} = 6
$$

## 剰余演算

### 解決したい問題

非常に大きな整数の計算結果を、ある正整数 `mod` で割った余りとして求める。競技プログラミングでは、答えを $`10^9 + 7`$ や $`998244353`$ で割った余りとして出力する問題、周期を扱う問題、偶奇や末尾の桁だけが必要な問題で頻出する。

### 考え方

2つの整数 `a` と `b` を `mod` で割った余りが等しいとき、`a` と `b` は `mod` を法として合同であるという。

$$
a \equiv b \pmod {\mathrm{mod}}
$$

これは、差 $`a - b`$ が `mod` の倍数であることと同じである。

$$
\mathrm{mod} \mid (a - b)
$$

合同な値は「`mod` で割った余りだけを考える世界では同じ値」とみなせる。たとえば、法を7とすると、3、10、17はすべて合同である。

$$
3 \equiv 10 \equiv 17 \pmod 7
$$

この考え方により、巨大な値そのものを保持せず、同じ余りを持つ小さな値へ置き換えながら計算できる。

### 加算・減算・乗算の法則

合同式は加算、減算、乗算を保つ。

$$
\begin{aligned}
(a + b) \bmod m
  &= ((a \bmod m) + (b \bmod m)) \bmod m \\
(a - b) \bmod m
  &= ((a \bmod m) - (b \bmod m)) \bmod m \\
(ab) \bmod m
  &= (a \bmod m)(b \bmod m) \bmod m
\end{aligned}
$$

より一般には、$`a \equiv a' \pmod m`$ かつ $`b \equiv b' \pmod m`$ なら、次が成り立つ。

$$
\begin{aligned}
a + b &\equiv a' + b' \pmod m \\
a - b &\equiv a' - b' \pmod m \\
ab &\equiv a'b' \pmod m
\end{aligned}
$$

したがって、和や積を繰り返す処理では毎回余りを取ってよい。

```java
long sum = 0;
for (long value : values) {
    sum = (sum + value) % mod;
}
```

```java
long product = 1;
for (long value : values) {
    product = product * (value % mod) % mod;
}
```

途中で余りを取ると値の増大を抑えられる。ただし、`product * (value % mod)` の乗算を行った時点で `long` の範囲を超える場合は、余りを取る前にオーバーフローする。AtCoderでよく使われる $`10^9 + 7`$ や $`998244353`$ 未満の2数の積は `long` に収まるが、法がそれより大きい場合は制約を確認する。

### 負数の正規化

数学では、法 `mod` に対する代表値として通常 $`0`$ 以上 $`\mathrm{mod} - 1`$ 以下を使う。一方、Javaの `%` は剰余演算子であり、左辺が負なら結果も負になり得る。

```java
System.out.println(-3 % 5); // -3
```

減算結果などを非負の範囲へ正規化するには `Math.floorMod` を使う。

```java
long normalized = Math.floorMod(value, mod);
long difference = Math.floorMod(a - b, mod);
```

値がすでに $`0 \le a, b < \mathrm{mod}`$ の範囲にあると分かっている場合は、次の形も使える。

```java
long difference = (a - b + mod) % mod;
```

`a` や `b` が任意の `long` なら、`a - b` や `a - b + mod` 自体のオーバーフローに注意する。

### 除算はそのまま適用できない

加算・減算・乗算と異なり、合同式の両辺を任意の値で割ることはできない。たとえば、法6では次が成り立つ。

$$
2(1) \equiv 2(4) \pmod 6
$$

しかし、両辺を2で割った次の式は成り立たない。

$$
1 \not\equiv 4 \pmod 6
$$

法 `mod` のもとで `a` による除算を行うには、次を満たす **逆元** $`a^{-1}`$ を掛ける。

$$
a(a^{-1}) \equiv 1 \pmod {\mathrm{mod}}
$$

逆元が存在する条件は次のとおりである。

$$
\gcd(a, \mathrm{mod}) = 1
$$

`mod` が素数で、`a` が `mod` の倍数でなければ、フェルマーの小定理から逆元を求められる。

$$
a^{-1} \equiv a^{\mathrm{mod} - 2} \pmod {\mathrm{mod}}
$$

`template/Main.java` の `modPow` を使う場合:

```java
long inverse = modPow(a, mod - 2, mod);
long quotient = numerator % mod * inverse % mod;
```

この方法は法が合成数の場合には一般に使えない。法が素数か、除数と法が互いに素かを必ず確認する。

### よく使う場面

- 数え上げの答えを指定された法で出力する
- DPの遷移ごとに余りを取り、値の増大を防ぐ
- 巨大なべき乗の下位桁や余りを求める
- 周期的な状態を `index % cycleLength` で表す
- 偶奇を `value % 2` で判定する
- 時計や環状配列の位置を `Math.floorMod(index, length)` で循環させる

### 注意点

- 法 `mod` は正であることを前提にする
- Javaの `%` と数学的な非負の剰余は、負数に対する結果が異なる
- 加減乗では途中で余りを取れるが、通常の除算には同じ法則を適用できない
- 掛け算の前に、使用する整数型の範囲を確認する
- 最終出力だけでなく、DPや数え上げの各更新でも余りを取る

## 繰り返し二乗法

### 解決したい問題

$`\mathrm{base}^{\mathrm{exponent}} \bmod m`$ を、指数が非常に大きい場合でも高速に求める。

### 考え方

指数を2進数で分解する。

$$
\begin{aligned}
13 &= 8 + 4 + 1 \\
\mathrm{base}^{13}
  &= (\mathrm{base}^{8})
   (\mathrm{base}^{4})
   (\mathrm{base})
\end{aligned}
$$

底を毎回二乗すると、$`\mathrm{base}^{1}, \mathrm{base}^{2}, \mathrm{base}^{4}, \mathrm{base}^{8}, \ldots`$ を順に作れる。指数の最下位ビットが1のときだけ、その底を答えへ掛ける。

各反復で指数を半分にするため、指数回の掛け算ではなく $`O(\log(\mathrm{exponent}))`$ 回で済む。

### 実装

`template/Main.java` の `modPow(long, long, long)` を使う。

```java
long result = modPow(base, exponent, mod);
```

### ループ不変条件

処理途中でも、概念的には次の関係が保たれる。

$$
\mathrm{base}_{0}^{\mathrm{exponent}_{0}}
\equiv
\mathrm{result}
(\mathrm{base}^{\mathrm{exponent}})
\pmod m
$$

指数のビットを1つ処理するたびに、必要な因子を `result` へ移し、底を二乗して次のビットへ進む。

### 計算量

- 時間: $`O(\log(\mathrm{exponent}))`$
- メモリ: $`O(1)`$

### 注意点

- 指数は0以上、法は正である必要がある
- 負の底は法の範囲へ正規化する
- `long` 同士の乗算自体がオーバーフローするほど法が大きい場合、この実装だけでは安全でない

## 切り上げ除算と切り下げ除算

### 問題の核心

Java の整数除算 `/` は0方向へ切り捨てる。これは数学的な床関数とは、負数を含む場合に異なる。

$$
\begin{aligned}
\mathrm{trunc}(-5 / 2) &= -2 && \text{（0方向）} \\
\left\lfloor -5 / 2 \right\rfloor &= -3 && \text{（負の無限大方向）} \\
\left\lceil -5 / 2 \right\rceil &= -2 && \text{（正の無限大方向）}
\end{aligned}
$$

`template/Main.java` には次がある。

- `floorDiv(dividend, divisor)`: 数学的な切り下げ
- `ceilDiv(dividend, divisor)`: 数学的な切り上げ

```java
long groups = ceilDiv(itemCount, groupSize);
long bucket = floorDiv(value, width);
```

符号が混在する場合も扱えるため、正数限定の `(a + b - 1) / b` より一般的である。

### 計算量

$`O(1)`$

### 注意点

除数が0なら `ArithmeticException` となる。

# グラフ探索

## グラフの表現

重みなしグラフは隣接リストで表現する。

```java
List<List<Integer>> graph = new ArrayList<>(vertexCount);
for (int vertex = 0; vertex < vertexCount; vertex++) {
    graph.add(new ArrayList<>());
}

for (int edge = 0; edge < edgeCount; edge++) {
    int from = fs.nextInt();
    int to = fs.nextInt();

    graph.get(from).add(to);
    graph.get(to).add(from); // 無向グラフの場合
}
```

計算量とメモリはおおむね $`O(V + E)`$ となる。

## 幅優先探索（BFS）

### 採用条件

- 辺の重みがすべて同じグラフで最短距離を求める
- 最小手数を求める
- 始点から近い順に探索したい

### 考え方

BFS は始点からの距離が小さい頂点から順に処理する。キューへは、距離 `d` の頂点から見つけた未訪問頂点を距離 `d + 1` として末尾へ追加する。そのため、キュー内の頂点は距離の小さい順に並び、ある頂点へ初めて到達した経路が最短経路になる。

重みなしグラフでは、どの辺を通っても距離は必ず1だけ増える。この「距離ごとの層を順番に探索する」性質が BFS の核心である。辺ごとのコストが異なると層の順序が崩れるため、通常の BFS では最短距離を保証できない。

### 正しさの見方

距離 `0` の始点は明らかに最短である。距離 `d` 以下の頂点がすべて最短距離で確定しているとする。そこから初めて見つかる頂点には長さ `d + 1` の経路がある。一方、より短い経路があるなら、その直前の頂点は距離 `d - 1` 以下であり、すでに探索されているはずなので矛盾する。よって初回到達時の距離が最短となる。

### 計算量

各頂点を高々1回キューへ入れ、各辺を高々1回ずつ調べるため $`O(V + E)`$。隣接行列では辺の走査が $`O(V^2)`$ になる。

### 最短距離

```java
static int[] bfs(List<List<Integer>> graph, int start) {
    int[] distances = new int[graph.size()];
    Arrays.fill(distances, -1);

    Deque<Integer> queue = new ArrayDeque<>();
    distances[start] = 0;
    queue.addLast(start);

    while (!queue.isEmpty()) {
        int current = queue.removeFirst();

        for (int next : graph.get(current)) {
            if (distances[next] != -1) {
                continue;
            }

            distances[next] = distances[current] + 1;
            queue.addLast(next);
        }
    }

    return distances;
}
```

`distances[vertex] == -1` なら始点から到達できない。

### 経路復元

各頂点へ初めて到達したときの直前の頂点を保存する。

```java
int[] previous = new int[vertexCount];
Arrays.fill(previous, -1);

// nextへ初めて到達した箇所
previous[next] = current;
```

終点から逆順にたどる。

```java
List<Integer> path = new ArrayList<>();
for (int vertex = goal; vertex != -1; vertex = previous[vertex]) {
    path.add(vertex);
}
Collections.reverse(path);
```

終点が未到達の場合は復元しないこと。

## グリッド上の BFS

### 採用条件

- 上下左右への移動回数を最小化する
- 各移動のコストが同じ
- 壁を避けて最短手数を求める

```java
static int[][] bfsGrid(char[][] grid, int startRow, int startColumn) {
    int height = grid.length;
    int width = grid[0].length;

    int[][] distances = new int[height][width];
    for (int[] row : distances) {
        Arrays.fill(row, -1);
    }

    Deque<int[]> queue = new ArrayDeque<>();
    distances[startRow][startColumn] = 0;
    queue.addLast(new int[] { startRow, startColumn });

    while (!queue.isEmpty()) {
        int[] current = queue.removeFirst();
        int row = current[0];
        int column = current[1];

        for (int direction = 0; direction < 4; direction++) {
            int nextRow = row + DR[direction];
            int nextColumn = column + DC[direction];

            if (!isInside(nextRow, nextColumn, height, width)) {
                continue;
            }
            if (grid[nextRow][nextColumn] == '#') {
                continue;
            }
            if (distances[nextRow][nextColumn] != -1) {
                continue;
            }

            distances[nextRow][nextColumn] = distances[row][column] + 1;
            queue.addLast(new int[] { nextRow, nextColumn });
        }
    }

    return distances;
}
```

`DR`、`DC`、`isInside` は `template/Main.java` に定義されている。

## 深さ優先探索（DFS）

### 採用条件

- 連結成分を調べる
- 到達可能性を判定する
- 木の部分木を処理する
- 探索経路を戻りながら処理する
- 全探索の状態遷移を再帰で表現する

### 考え方

DFS は、現在の頂点から進める限り1本の経路を深くたどり、進めなくなったら直前の分岐点へ戻る。再帰呼び出しのスタックが「現在たどっている経路」を自然に表す。

この戻る動作により、子の情報を集めて親の答えを作る後行順処理や、選択を元に戻して別の選択肢を試すバックトラッキングを書きやすい。

`visited` は単なる高速化ではない。一般グラフには閉路があるため、訪問済み管理がなければ同じ頂点を永久に巡回する可能性がある。

### 計算量

隣接リストでは各頂点と各辺を高々定数回処理するため $`O(V + E)`$。

### 再帰 DFS

```java
static void dfs(
    List<List<Integer>> graph,
    int current,
    boolean[] visited
) {
    visited[current] = true;

    for (int next : graph.get(current)) {
        if (visited[next]) {
            continue;
        }
        dfs(graph, next, visited);
    }
}
```

Java は深い再帰で `StackOverflowError` が発生しやすい。頂点数が `10^5` 規模になる可能性がある場合は反復 DFS を検討する。

### 反復 DFS

```java
static boolean[] dfsIterative(List<List<Integer>> graph, int start) {
    boolean[] visited = new boolean[graph.size()];
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        int current = stack.pop();
        if (visited[current]) {
            continue;
        }

        visited[current] = true;

        for (int next : graph.get(current)) {
            if (!visited[next]) {
                stack.push(next);
            }
        }
    }

    return visited;
}
```

### 木で親方向へ戻らない DFS

```java
static void dfsTree(
    List<List<Integer>> tree,
    int current,
    int parent
) {
    for (int next : tree.get(current)) {
        if (next == parent) {
            continue;
        }
        dfsTree(tree, next, current);
    }
}
```

木では `visited` の代わりに親頂点を渡せる。

## BFS と DFS の選択

| 目的                       | 選択               |
| -------------------------- | ------------------ |
| 重みなし最短距離           | BFS                |
| 近い順に探索               | BFS                |
| 連結判定だけ               | どちらでもよい     |
| 木の部分木を再帰的に集計   | DFS                |
| 深いグラフを安全に探索     | BFS または反復 DFS |
| 経路を戻りながら状態を復元 | DFS                |

# Union-Find

## 解決できる問題

Union-Find（Disjoint Set Union、DSU）は、最初は別々になっている要素の集合に対して、次の操作を繰り返すデータ構造である。

- 2つの集合を統合する
- 2要素が同じ集合に属するか判定する
- 要素が属する集合の代表元や要素数を求める
- 現在の集合数や、各集合の要素を求める

無向グラフでは、頂点を要素、辺の追加を集合の統合とみなせる。辺を追加するだけの連結判定、連結成分数の管理、閉路を作る辺の判定、Kruskal法などに利用できる。

## 考え方

各集合を根付き木として表し、親をたどって到達する根を集合の代表元とする。同じ代表元を持つ要素は同じ集合に属する。

高速化のため、次の2つを組み合わせる。

- **union by size**: 要素数の少ない木を多い木の下へ接続する
- **経路圧縮**: 代表元を探した要素を根へ直接つなぎ替える

実装では、根の `parentOrSize[root]` に負の集合サイズを、根以外には親の番号を格納する。`merge` が実際に異なる集合を統合したときだけ、集合数を1減らす。

## スニペット

完成コードは [`snippets/UnionFind.java`](../snippets/UnionFind.java) に置いている。`Main.java` の末尾へクラス全体をコピーして使う。`Main.java` にない場合は、ファイル先頭へ次のimportも追加する。

```java
import java.util.Arrays;
```

## API

| メソッド       | 計算量         | 意味                                                |
| -------------- | -------------- | --------------------------------------------------- |
| `leader(v)`    | 償却 `O(α(N))` | `v` が属する集合の代表元を返す                      |
| `same(a, b)`   | 償却 `O(α(N))` | `a` と `b` が同じ集合なら `true`                    |
| `merge(a, b)`  | 償却 `O(α(N))` | 異なる集合を統合したら `true`、既に同じなら `false` |
| `size(v)`      | 償却 `O(α(N))` | `v` が属する集合の要素数を返す                      |
| `groupCount()` | `O(1)`         | 現在の集合数を返す                                  |
| `groups()`     | `O(N α(N))`    | 全集合を `int[][]` で返す                           |

`α` は逆アッカーマン関数であり、現実的な入力ではほぼ定数とみなせる。内部配列のメモリは $`O(N)`$。`groups()` の戻り値にも全要素分の $`O(N)`$ のメモリが必要になる。

## 基本的な使い方

```java
UnionFind unionFind = new UnionFind(elementCount);

boolean merged = unionFind.merge(first, second);
boolean connected = unionFind.same(first, second);
int representative = unionFind.leader(first);
int componentSize = unionFind.size(first);
int componentCount = unionFind.groupCount();

for (int[] group : unionFind.groups()) {
    // groupには同じ集合に属する要素が入る
}
```

代表元の番号は実装上選ばれた根にすぎず、最小番号などの意味は持たない。`merge(a, b)` 後の代表元が必ず `a` や `leader(a)` になるとも限らない。

## 対応できない問題

通常の Union-Find は集合の統合と連結判定を扱う。次の操作や情報が必要な場合は、専用の派生実装や別のアルゴリズムを使う。

| 必要な操作・情報                  | 検討する構造                           |
| --------------------------------- | -------------------------------------- |
| 2要素間の重み・距離・差を管理する | Weighted Union-Find                    |
| 敵味方関係や偶奇制約を管理する    | Potentialized / Parity Union-Find      |
| 過去の状態へ戻す                  | Rollback Union-Find                    |
| 辺をオンラインで削除する          | 動的連結性用データ構造。通常版では不可 |
| 各集合の和・最小値などを管理する  | 根ごとの集約値を持つ拡張版             |
| 有向グラフの到達可能性を判定する  | DFS、BFS、SCCなど                      |

## 注意点

- 有効な要素番号は `0` 以上 `N - 1` 以下。入力が1-indexedなら変換する
- `merge` の戻り値は「統合後に同じ集合か」ではなく「今回新しく統合したか」を表す
- `groups()` が返す集合の順番や、各集合の代表元そのものに依存しない
- 集合の統合はできるが、通常版では分割できない
- 最初から要素数 `N` が固定される。後から要素を増やす場合は再構築するか、追加対応版を使う

# 最短経路

## Dijkstra 法

### 採用条件

- 辺の重みが非負
- 単一始点から各頂点への最短距離を求める
- 辺ごとに異なる重みを持つ

### 考え方

各頂点への暫定距離を持ち、その時点で最も距離が小さい頂点から辺を緩和する。辺の重みが非負なら、最小の暫定距離を持つ頂点へ、未処理頂点を経由して後からより短く到達することはない。未処理頂点へ到達するだけで現在値以上の距離が必要で、そこから非負コストが加わるためである。

**緩和**とは、現在判明している経路を使って隣接頂点の距離を短くできるか確認する操作である。

$$
\begin{aligned}
d_{\mathrm{candidate}}(\mathrm{next})
  &= d(\mathrm{current}) \\
  &\quad {} + w(\mathrm{current}, \mathrm{next})
\end{aligned}
$$

候補が既知の距離より小さければ更新する。

Java の `PriorityQueue` には要素の優先度を直接更新する操作がない。そのため新しい距離を追加し、取り出した状態が現在の `distances` と一致しなければ古い状態として捨てる。

### 計算量

優先度付きキューと隣接リストで $`O((V + E) \log V)`$。各辺の緩和でキューへ状態が追加されうる。

```java
record Edge(int to, long cost) {}
record State(int vertex, long distance) {}
```

```java
static long[] dijkstra(List<List<Edge>> graph, int start) {
    long infinity = Long.MAX_VALUE / 4;
    long[] distances = new long[graph.size()];
    Arrays.fill(distances, infinity);

    PriorityQueue<State> queue = new PriorityQueue<>(
        Comparator.comparingLong(State::distance)
    );

    distances[start] = 0;
    queue.add(new State(start, 0));

    while (!queue.isEmpty()) {
        State current = queue.poll();

        if (current.distance() != distances[current.vertex()]) {
            continue;
        }

        for (Edge edge : graph.get(current.vertex())) {
            long nextDistance = current.distance() + edge.cost();

            if (nextDistance >= distances[edge.to()]) {
                continue;
            }

            distances[edge.to()] = nextDistance;
            queue.add(new State(edge.to(), nextDistance));
        }
    }

    return distances;
}
```

### 注意点

- 負の重みがある場合は使えない
- 距離は `long` を基本とする
- `Long.MAX_VALUE` に足し算するとオーバーフローするため、余裕を持った値を無限大にする
- 優先度付きキューから取り出した古い状態を無視する

## 0-1 BFS

### 採用条件

辺の重みが `0` または `1` だけの場合に使う。通常の Dijkstra より軽量で、計算量は $`O(V + E)`$。

### 考え方

Dijkstra 法では最小距離の状態を優先度付きキューから選ぶ。重みが0と1だけなら、新しく見つかる距離は現在距離と同じか、ちょうど1大きいだけである。

- コスト0の辺: 現在と同じ距離なので両端キューの先頭へ入れる
- コスト1の辺: 現在より1大きいので末尾へ入れる

これにより、両端キューは優先度付きキューを使わなくても、距離が小さい状態を前方に保つ。0コストの移動を先に処理することが最短距離順を維持する核心である。

```java
record ZeroOneEdge(int to, int cost) {}
```

```java
static int[] zeroOneBfs(List<List<ZeroOneEdge>> graph, int start) {
    int[] distances = new int[graph.size()];
    Arrays.fill(distances, Integer.MAX_VALUE);

    Deque<Integer> deque = new ArrayDeque<>();
    distances[start] = 0;
    deque.addFirst(start);

    while (!deque.isEmpty()) {
        int current = deque.removeFirst();

        for (ZeroOneEdge edge : graph.get(current)) {
            int nextDistance = distances[current] + edge.cost();
            if (nextDistance >= distances[edge.to()]) {
                continue;
            }

            distances[edge.to()] = nextDistance;
            if (edge.cost() == 0) {
                deque.addFirst(edge.to());
            } else {
                deque.addLast(edge.to());
            }
        }
    }

    return distances;
}
```

# DAG

## トポロジカルソート

### 採用条件

- 依存関係を満たす順序を求める
- 有向グラフが DAG か判定する
- 前提タスクから順に処理する

### 考え方

入次数は、その頂点より前に処理しなければならない頂点の数と考えられる。入次数0の頂点には未処理の前提がないため、現在の順序へ安全に追加できる。

頂点を追加したら、その頂点から出る辺を削除したと考え、行き先の入次数を1減らす。新たに0になった頂点は、すべての前提が処理済みになったので次の候補となる。

閉路がある場合、閉路内の各頂点は互いを前提とし続け、どの頂点も入次数0にならない。そのため、出力頂点数が `V` 未満なら有向閉路が存在すると分かる。

### 計算量

各頂点と各辺を1回処理するため $`O(V + E)`$。

```java
static List<Integer> topologicalSort(List<List<Integer>> graph) {
    int[] indegrees = new int[graph.size()];

    for (List<Integer> edges : graph) {
        for (int to : edges) {
            indegrees[to]++;
        }
    }

    Deque<Integer> queue = new ArrayDeque<>();
    for (int vertex = 0; vertex < graph.size(); vertex++) {
        if (indegrees[vertex] == 0) {
            queue.addLast(vertex);
        }
    }

    List<Integer> order = new ArrayList<>(graph.size());

    while (!queue.isEmpty()) {
        int current = queue.removeFirst();
        order.add(current);

        for (int next : graph.get(current)) {
            indegrees[next]--;
            if (indegrees[next] == 0) {
                queue.addLast(next);
            }
        }
    }

    return order;
}
```

```java
List<Integer> order = topologicalSort(graph);
boolean directedAcyclicGraph = order.size() == vertexCount;
```

辞書順最小の順序が必要なら、`Deque` の代わりに `PriorityQueue<Integer>` を使う。

# 探索と境界

## 二分探索

`template/Main.java` に次のメソッドが定義されている。

- `lowerBound`: `target` 以上となる最初の位置
- `upperBound`: `target` より大きくなる最初の位置

### 考え方

二分探索は、探索区間を半分ずつ捨てられる単調性を利用する。`lowerBound` では「値が `target` 未満か」という条件が、ソート済み配列上で `true` から `false` へ一度だけ変化する。その境界を探す。

探索区間を `[left, right)` とし、常に次を保つ。

- `left` より前は条件を満たさない側
- `right` 以降は条件を満たす側
- 未確定部分は `[left, right)`

中央を調べるたびに未確定区間が半分になるため、線形探索の $`O(N)`$ に対して $`O(\log N)`$ で境界へ到達する。

ソート済み配列で使うこと。

```java
int firstAtLeastTarget = lowerBound(values, target);
int firstGreaterThanTarget = upperBound(values, target);
int targetCount = firstGreaterThanTarget - firstAtLeastTarget;
```

## 答えで二分探索

### 採用条件

ある候補値 `x` に対して、条件が次のように単調に変化する場合に使う。

```text
false false false true true true
```

「条件を満たす最小値」を求める例:

```java
long ng = -1;       // 条件を満たさないことが分かっている値
long ok = upperBound; // 条件を満たすことが分かっている値

while (ok - ng > 1) {
    long middle = ng + (ok - ng) / 2;

    if (isFeasible(middle)) {
        ok = middle;
    } else {
        ng = middle;
    }
}

long answer = ok;
```

### 注意点

- `isFeasible` が単調であることを確認する
- `ng` と `ok` のどちらが条件を満たす側か固定する
- 中央値は `(left + right) / 2` より `left + (right - left) / 2` が安全
- 上限が不明なら、制約から十分大きな値を決める

# 区間処理

## 累積和

`template/Main.java` の `prefixSums` は、先頭に `0` を持つ長さ `N + 1` の配列を返す。

```java
long[] sums = prefixSums(values);
long rangeSum = sums[right] - sums[left]; // [left, right)
```

### 考え方

`sums[i]` を先頭から `i` 個分の和と定義する。区間 `[left, right)` より前までの和 `sums[right]` から、不要な先頭部分 `sums[left]` を引けば、区間内だけが残る。

$$
\mathrm{sum}(\mathrm{left}, \mathrm{right})
= S_{\mathrm{right}} - S_{\mathrm{left}}
$$

先頭に0を置くことで、`left == 0` も特別扱いせず同じ式で処理できる。前計算により、各クエリで区間を走査する仕事を差し引き1回へ置き換える考え方である。

### 採用条件

- 元の配列が更新されない
- 区間和を何度も問い合わせる

構築 $`O(N)`$、各区間和 $`O(1)`$。

## 二次元累積和

長方形領域の和を何度も求める場合に使う。

### 考え方

`sums[row][column]` を、左上 `(0, 0)` から `(row, column)` の直前までの長方形の和として持つ。ある長方形の和は、大きな左上長方形から上側と左側を引いて求める。ただし左上の重なり部分を2回引くため、最後に1回足し戻す。

これは包除原理そのものである。

$$
\begin{aligned}
\mathrm{sum}(t, b, l, r)
  &= S_{b,r} - S_{t,r} \\
  &\quad - S_{b,l} + S_{t,l}
\end{aligned}
$$

先頭に0行・0列を追加すると、グリッド端の長方形も境界分岐なしで同じ式にできる。

```java
long[][] sums = new long[height + 1][width + 1];

for (int row = 0; row < height; row++) {
    for (int column = 0; column < width; column++) {
        sums[row + 1][column + 1] =
            grid[row][column]
                + sums[row][column + 1]
                + sums[row + 1][column]
                - sums[row][column];
    }
}
```

`[top, bottom) x [left, right)` の和:

```java
long rectangleSum =
    sums[bottom][right]
        - sums[top][right]
        - sums[bottom][left]
        + sums[top][left];
```

## いもす法

### 採用条件

- 区間への加算が何度もある
- 全ての加算後の各位置の値を求める

### 考え方

区間内の全要素を毎回更新する代わりに、「値が変化し始める境界」だけを記録する。

`[left, right)` に `value` を加える操作は、`left` で `value` を開始し、`right` で同じ変化を打ち消すイベントとして表せる。最後に左から累積すると、開始イベントの効果が区間内へ伝播し、終了イベントで止まる。

微分に相当する差分配列へイベントを記録し、累積和で積分して元の値を復元する考え方である。

`[left, right)` に `value` を加算する。

```java
long[] differences = new long[length + 1];

differences[left] += value;
differences[right] -= value;
```

全クエリを反映した後に累積する。

```java
for (int index = 1; index < length; index++) {
    differences[index] += differences[index - 1];
}
```

全体で $`O(N + Q)`$。

# 連続区間

## 尺取り法

### 採用条件

- 対象が連続部分列
- 右端を伸ばすと条件が一方向に変化する
- 左端を進めることで条件を戻せる

### 考え方

左端を固定したときの適切な右端を求め、次の左端では前回の右端から探索を再開する。条件に単調性があれば、左端を進めたときに右端を後ろへ戻す必要がない。

左右の端点はどちらも配列全体を高々1回ずつ進む。そのため、二重ループに見えても処理回数は $`O(N)`$ となる。この「探索済みの右端を使い回す」ことが全区間を $`O(N^2)`$ で調べない核心である。

非負整数列で、和が `limit` 以下となる区間を処理する例:

```java
long sum = 0;
int right = 0;

for (int left = 0; left < values.length; left++) {
    while (right < values.length && sum + values[right] <= limit) {
        sum += values[right];
        right++;
    }

    // [left, right) が条件を満たす最大の区間

    if (right == left) {
        right++;
    } else {
        sum -= values[left];
    }
}
```

### 注意点

負数を含む区間和では単調性が崩れるため、この形の尺取り法は通常使えない。

## 固定長スライディングウィンドウ

長さ `windowSize` の区間和を順番に求める。

```java
long sum = 0;
for (int index = 0; index < windowSize; index++) {
    sum += values[index];
}

for (int left = 0; left + windowSize <= values.length; left++) {
    // 現在の区間和はsum

    int next = left + windowSize;
    if (next < values.length) {
        sum += values[next];
        sum -= values[left];
    }
}
```

全体で $`O(N)`$。

# 座標圧縮

## 採用条件

- 座標や値が `10^9` など大きい
- 実際に登場する値の種類数は `N` 個程度
- 値の大小関係だけを維持して配列の添字にしたい

### 考え方

実際に登場する値だけをソートして重複除去し、その順位へ置き換える。元の値そのものではなく、等しいか、どちらが小さいかという順序関係を保存する。

```text
元の値:  1000000000, -5, 1000000000, 20
圧縮後:           2,  0,          2,  1
```

これにより、巨大な値を小さな連続添字として配列や Fenwick Tree で扱える。ただし数値間の距離は保存されない。`20 - (-5) = 25` だが、圧縮後の差は `1` である。

```java
int[] sortedValues = Arrays.stream(values)
    .sorted()
    .distinct()
    .toArray();

int[] compressed = new int[values.length];
for (int index = 0; index < values.length; index++) {
    compressed[index] = lowerBound(sortedValues, values[index]);
}
```

`compressed[index]` は `0` から `種類数 - 1` の範囲になる。

# 動的計画法（DP）

## 採用判断

次の特徴がある場合に検討する。

- 同じ状態に複数の経路から到達する
- 過去の小さい問題の答えから現在の答えを作れる
- 全探索では同じ計算を何度も繰り返す

### 考え方

全探索の途中で同じ状況へ何度も到達する場合、その先の最適解も同じになる。そこで「未来の答えを決めるために必要な情報」だけを状態として切り出し、各状態の答えを1回だけ計算して再利用する。

DP の難しさはループを書くことではなく、過去の細かな経路を忘れても未来を正しく決められる状態を設計することにある。状態が不足すると誤った経路を同一視し、状態が多すぎると計算量が増える。

DP を設計するときは次を文章で明確にする。

1. `dp[...]` が何を表すか
2. 初期値がなぜ正しいか
3. どの選択がどの遷移に対応するか
4. 遷移元が確定してから遷移先を計算できる順序か
5. 問題の答えをどの状態から取得するか

## 1次元 DP

### 例: 各位置までの最小コスト

```java
long infinity = Long.MAX_VALUE / 4;
long[] dp = new long[n];
Arrays.fill(dp, infinity);
dp[0] = 0;

for (int index = 0; index < n; index++) {
    if (index + 1 < n) {
        dp[index + 1] = Math.min(
            dp[index + 1],
            dp[index] + cost1(index)
        );
    }

    if (index + 2 < n) {
        dp[index + 2] = Math.min(
            dp[index + 2],
            dp[index] + cost2(index)
        );
    }
}
```

`dp[index]` を「位置 `index` へ到達する最小コスト」と定義している。

## 0/1 ナップサック

### 採用条件

- 各品物を選ぶか選ばないか
- 重さの合計に上限がある
- 価値の最大化を求める

計算量 $`O(NW)`$。

```java
long[] dp = new long[capacity + 1];

for (int item = 0; item < itemCount; item++) {
    int weight = weights[item];
    long value = values[item];

    for (int currentWeight = capacity; currentWeight >= weight; currentWeight--) {
        dp[currentWeight] = Math.max(
            dp[currentWeight],
            dp[currentWeight - weight] + value
        );
    }
}
```

重さを大きいほうから更新する。小さいほうから更新すると同じ品物を複数回選べる完全ナップサックになる。

## 部分和 DP

合計 `sum` を作れるか判定する。

```java
boolean[] dp = new boolean[target + 1];
dp[0] = true;

for (int value : values) {
    for (int sum = target; sum >= value; sum--) {
        dp[sum] |= dp[sum - value];
    }
}
```

答えは `dp[target]`。

## 2次元 DP

文字列の最長共通部分列など、2つの添字を状態に持つ場合に使う。

```java
int[][] dp = new int[first.length() + 1][second.length() + 1];

for (int firstIndex = 0; firstIndex < first.length(); firstIndex++) {
    for (int secondIndex = 0; secondIndex < second.length(); secondIndex++) {
        if (first.charAt(firstIndex) == second.charAt(secondIndex)) {
            dp[firstIndex + 1][secondIndex + 1] =
                dp[firstIndex][secondIndex] + 1;
        } else {
            dp[firstIndex + 1][secondIndex + 1] = Math.max(
                dp[firstIndex][secondIndex + 1],
                dp[firstIndex + 1][secondIndex]
            );
        }
    }
}
```

計算量とメモリは $`O(NM)`$。

## メモ化再帰

状態遷移を再帰で自然に書ける場合に使う。

```java
static long search(int state, long[] memo) {
    if (isGoal(state)) {
        return 0;
    }
    if (memo[state] != -1) {
        return memo[state];
    }

    long result = Long.MAX_VALUE / 4;
    for (int next : nextStates(state)) {
        result = Math.min(result, 1 + search(next, memo));
    }

    return memo[state] = result;
}
```

未計算を表す値と、正当な答えが衝突しないようにする。

# 全探索

## bit 全探索

$`N \le 20`$ 程度で、各要素を選ぶか選ばないか調べる場合に使う。計算量は $`O(N2^N)`$。

### 考え方

$`N`$ 個の選択を $`N`$ bit の整数へ対応させる。bit $`i`$ が1なら要素 $`i`$ を選び、0なら選ばないと解釈する。$`0`$ から $`2^N - 1`$ までの各整数は異なる bit 列を持つため、全部分集合と1対1に対応する。

$`N = 3`$ の場合、各整数と部分集合は次のように対応する。

| bit 列 | 対応する部分集合 |
| ------ | ---------------- |
| 000₂   | ∅                |
| 001₂   | {0}              |
| 010₂   | {1}              |
| 011₂   | {0, 1}           |
| ⋮      | ⋮                |
| 111₂   | {0, 1, 2}        |

集合という複雑な対象を整数の bit 表現に変換することで、漏れや重複なく列挙できる。

```java
for (long subset = 0; subset < (1L << n); subset++) {
    for (int index = 0; index < n; index++) {
        if ((subset & (1L << index)) != 0) {
            // index番目の要素を含む
        }
    }
}
```

`1L << n` を使うため、通常は `n < 63` の場合に限る。

### 指定した集合の部分集合を列挙する

```java
for (long subset = mask; subset > 0; subset = (subset - 1) & mask) {
    // 空集合以外のsubset
}
```

`subset - 1` は、現在の部分集合で最も右にある1を0にし、それより右をすべて1にする。そこへ `& mask` を適用すると、`mask` に存在しない bit を除きながら、次に小さい部分マスクへ移動できる。空集合も処理する場合は、ループ後に別途処理する。

`mask` の立っている bit 数を $`K`$ とすると、その部分マスク数は $`2^K`$。

## 順列全探索

### 解決したい問題

配列の要素を並べ替えて作れるすべての順列を調べる。要素がすべて異なる場合、長さ $`N`$ の配列には $`N!`$ 通りの順列があるため、通常は $`N \le 8`$ 程度で検討する。

順列では並び順を区別する。例えば `[1, 2]` と `[2, 1]` は異なる順列である。

### 実装

Java 標準 API に next permutation はない。`template/Main.java` の `permutations(int[])` または `permutations(char[])` を使う。

```java
List<int[]> results = permutations(new int[] {1, 2, 3});
for (int[] permutation : results) {
    // permutationを評価する
}
```

文字配列の場合:

```java
List<char[]> results = permutations(new char[] {'a', 'b', 'c'});
for (char[] permutation : results) {
    String text = new String(permutation);
    // textを評価する
}
```

テンプレート関数は入力配列を変更せず、相異なる順列を辞書順で返す。重複要素を含む場合、同じ値の並びは一度だけ生成する。

```java
permutations(new int[] {1, 1, 2});
// [1, 1, 2], [1, 2, 1], [2, 1, 1]
```

内部では最初に配列を昇順ソートし、現在の順列から辞書順で次の順列を繰り返し生成する。

### 計算量

配列長を $`N`$、相異なる順列数を $`P`$ とすると、時間計算量は $`O(N \log N + NP)`$、返却する順列を含む空間計算量は $`O(NP)`$。

順列を保存せず、生成するたびに評価する実装なら追加メモリを削減できる。

## 組み合わせ全探索

### 解決したい問題

$`N`$ 個の要素から、並び順を区別せずに $`K`$ 個を選ぶすべての組み合わせを調べる。組み合わせ数は $`\binom{N}{K}`$ である。

例えば `[1, 2]` と `[2, 1]` は順列では異なるが、組み合わせでは同じ選び方として扱う。

### 実装

`template/Main.java` の `combinations(int, int)` を使う。選ぶインデックスを常に昇順にすると、同じ組み合わせを重複して生成しない。

```java
static List<int[]> combinations(int n, int k) {
    if (k < 0 || k > n) {
        return List.of();
    }

    List<int[]> results = new ArrayList<>();
    enumerateCombinations(0, 0, n, new int[k], results);
    return results;
}

static void enumerateCombinations(
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
```

使用例:

```java
int[] values = {10, 20, 30, 40};
for (int[] indices : combinations(values.length, 2)) {
    int first = values[indices[0]];
    int second = values[indices[1]];
    // (10, 20), (10, 30), (10, 40), (20, 30), (20, 40), (30, 40)
}
```

この実装は選んだ要素のインデックスを返す。同じ値が異なるインデックスにある場合、それぞれを別の要素として扱う。

`char[]` から文字を直接選ぶ場合は、テンプレートの `combinations(char[], int)` を使う。入力配列は変更せず、値として同一の組み合わせを除外して辞書順で返す。

```java
List<char[]> results = combinations(new char[] {'a', 'a', 'b', 'c'}, 2);
for (char[] combination : results) {
    out.println(new String(combination));
}
// aa, ab, ac, bc
```

### 計算量

生成する組み合わせ数を $`C = \binom{N}{K}`$ とすると、各結果のコピーを含む時間・空間計算量は $`O(KC)`$。

$`N`$ が小さく、選ぶ個数が固定されていない場合は、すべての部分集合を生成できる [bit 全探索](#bit-全探索) も候補になる。

# 貪欲法

## 採用判断

その時点で最善に見える選択をしても、後の選択肢を不当に失わない場合に使う。

頻出する考え方:

- 終了時刻が早い区間から選ぶ
- コストが小さいものから処理する
- 締切が早いものから処理する
- 差分や利益が大きい操作を優先する
- ソートして隣接要素だけを見る

貪欲法は実装より正当性の証明が重要となる。次のいずれかで説明できるか確認する。

- 交換しても答えが悪化しない
- 最適解の先頭を貪欲な選択へ置き換えられる
- 選択後に同じ形の部分問題が残る

局所最適が常に全体最適になるとは限らない。小さいケースで反例を探してから採用すること。

## 例: 区間スケジューリング

重ならない区間を最大数選ぶ問題では、終了時刻が早い区間から選ぶ。

```java
record Interval(int start, int end) {}

intervals.sort(Comparator.comparingInt(Interval::end));

int selectedCount = 0;
int previousEnd = Integer.MIN_VALUE;

for (Interval interval : intervals) {
    if (interval.start() < previousEnd) {
        continue;
    }

    selectedCount++;
    previousEnd = interval.end();
}
```

### なぜ終了時刻が早いものを選ぶのか

ある最適解の最初の区間を、全区間の中で終了時刻が最も早い区間へ交換しても、その後に使える時間は減らない。むしろ同じか広くなる。そのため、この交換によって選べる区間数は悪化しない。

この交換を先頭から繰り返すと、終了時刻順に選ぶ解が最適解の1つになる。これが交換法による貪欲法の正当化である。

# 木

## 木の基本性質

頂点数を `V` とすると、木には次の性質がある。

- 辺数は `V - 1`
- 任意の2頂点間の単純パスは1つ
- 連結で閉路を持たない
- 1本の辺を削除すると2つの連結成分に分かれる

## 部分木サイズ

### 考え方

木を根付き木として見ると、ある頂点の部分木サイズは「自分自身の1」と「各子の部分木サイズ」の和になる。子の答えが確定してから親へ足す必要があるため、DFS の帰りがけ、つまり後行順で集計する。

$$
\mathrm{subtreeSize}(v)
= 1 + \sum_{u \in \mathrm{children}(v)}
\mathrm{subtreeSize}(u)
$$

辺には向きがないため、呼び出し元の親へ戻らないよう `parent` を渡す。

```java
static int calculateSubtreeSizes(
    List<List<Integer>> tree,
    int current,
    int parent,
    int[] subtreeSizes
) {
    int size = 1;

    for (int next : tree.get(current)) {
        if (next == parent) {
            continue;
        }
        size += calculateSubtreeSizes(tree, next, current, subtreeSizes);
    }

    return subtreeSizes[current] = size;
}
```

再帰が深くなる木では `StackOverflowError` に注意する。

## 木の直径

重みなし木では次の2回の BFS で求められる。

1. 任意の頂点から最遠の頂点 `first` を求める
2. `first` から最遠の距離を求める

### 考え方

木には2頂点間の単純パスが1本しかない。任意の頂点から最も遠い頂点の1つは、直径をなすパスの端点になる。そこから再び最遠頂点を探すと、木の中で最も長い端点間距離へ到達する。

直感的には、最初の探索で木の外側まで進み、2回目の探索でその端から反対側の最も遠い端まで木全体を横断する。閉路のある一般グラフではこの性質は保証されない。

```java
int[] firstDistances = bfs(tree, 0);
int first = 0;
for (int vertex = 1; vertex < tree.size(); vertex++) {
    if (firstDistances[vertex] > firstDistances[first]) {
        first = vertex;
    }
}

int[] diameterDistances = bfs(tree, first);
int diameter = Arrays.stream(diameterDistances).max().orElseThrow();
```

# よくある失敗

## `int` のオーバーフロー

距離、個数、積、累積和は `long` を検討する。

```java
long product = (long) first * second;
```

## 未到達と距離0の混同

BFS の距離は `-1` で初期化する。

```java
Arrays.fill(distances, -1);
```

## 無限大への加算

`Long.MAX_VALUE` をそのまま無限大にすると加算でオーバーフローする。

```java
long infinity = Long.MAX_VALUE / 4;
```

## 頂点番号

入力が1始まりで、配列が0始まりの場合は読み込み時に変換する。

```java
int vertex = fs.nextInt() - 1;
```

出力時に元へ戻す必要があるか確認する。

## 閉区間と半開区間

Java の配列処理では `[left, right)` に統一すると扱いやすい。

- 長さは `right - left`
- 累積和は `sums[right] - sums[left]`
- `substring(left, right)` と同じ形式

## 再帰の深さ

Java では深い DFS が `StackOverflowError` になりやすい。入力が直線状の木になる可能性も考え、必要なら `Deque` を使った反復処理へ変更する。

# `template/Main.java` アルゴリズム索引

`template/Main.java` から利用できるアルゴリズムの用途と計算量を示す。

| テンプレート要素 | 解決する問題                   | 計算量               |
| ---------------- | ------------------------------ | -------------------- |
| `DR` / `DC`      | グリッド上の上下左右移動       | 1方向あたり O(1)     |
| `isInside`       | 座標がグリッド内か判定         | O(1)                 |
| `ceilDiv`        | 符号を含む数学的な切り上げ除算 | O(1)                 |
| `floorDiv`       | 符号を含む数学的な切り下げ除算 | O(1)                 |
| `isPrime`        | 1つの整数の素数判定            | O(√N)                |
| `sieve`          | N 以下の素数を一括判定         | O(N log log N)       |
| `gcd`            | 最大公約数                     | O(log min(A, B))     |
| `lcm`            | 最小公倍数                     | O(log min(A, B))     |
| `modPow`         | べき乗剰余                     | O(log exponent)      |
| `lowerBound`     | `target` 以上の最初の位置      | O(log N)             |
| `upperBound`     | `target` より大きい最初の位置  | O(log N)             |
| `prefixSums`     | 静的な区間和の前計算           | 構築 O(N)、取得 O(1) |
| `divisors`       | 正の約数列挙                   | O(√N + D log D)      |
| `permutations`   | 配列の相異なる順列を全列挙     | O(N log N + NP)      |
| `combinations`   | N 個から K 個を選ぶ組合せ列挙  | O(K × C(N, K))       |

入力、出力、配列表示など、Java API とテンプレートの使用例は [`java-cheatsheet.md`](java-cheatsheet.md) から確認できる。

# 発展的なアルゴリズム

より難しい問題では、次のアルゴリズムやデータ構造も候補になる。

- Bellman-Ford 法
- Warshall-Floyd 法
- 最小全域木（Kruskal / Prim）
- 強連結成分分解
- Lowest Common Ancestor
- Fenwick Tree
- Segment Tree
- 遅延評価 Segment Tree
- 最大流・最小費用流
- 文字列アルゴリズム（Z-algorithm、KMP）
