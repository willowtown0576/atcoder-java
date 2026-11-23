# Java 24 Cheat Sheet for AtCoder

Java 24 の構文、型、標準 API、変換方法をすぐに確認するための早見表である。アルゴリズムの選び方や実装例は [`algorithms.md`](algorithms.md) から確認できる。

## 目次

- [型と値](#型と値)
  - [プリミティブ型](#プリミティブ型)
  - [最大値・最小値](#最大値最小値)
  - [long リテラル](#long-リテラル)
  - [`record`](#record)
- [数値計算](#数値計算)
  - [`Math`](#math)
  - [オーバーフローを避ける](#オーバーフローを避ける)
  - [`floor`、`ceil`、`round`、`rint`](#floorceilroundrint)
  - [`Math.floorMod` で剰余を0以上にする](#mathfloormod-で剰余を0以上にする)
  - [剰余・整数除算の考え方](#剰余整数除算の考え方)
  - [`BigInteger`](#biginteger)
- [配列](#配列)
  - [作成と初期化](#作成と初期化)
  - [走査と条件判定](#走査と条件判定)
  - [検索](#検索)
  - [集計](#集計)
  - [コピーと比較](#コピーと比較)
  - [並べ替え](#並べ替え)
  - [加工と出力](#加工と出力)
- [文字列と文字](#文字列と文字)
  - [`String`](#string)
  - [`Character`](#character)
  - [`StringBuilder`](#stringbuilder)
- [コレクション](#コレクション)
  - [`List`](#list)
  - [`Set`](#set)
  - [`Map`](#map)
  - [キュー・スタック・両端キュー](#キュースタック両端キュー)
  - [`PriorityQueue`](#priorityqueue)
  - [`Collections`](#collections)
- [型変換](#型変換)
  - [数値変換](#数値変換)
  - [`int[]` と `long[]` を変換する](#int-と-long-を変換する)
  - [プリミティブ配列とラッパー配列を変換する](#プリミティブ配列とラッパー配列を変換する)
  - [配列とリストを変換する](#配列とリストを変換する)
  - [文字列と文字配列を変換する](#文字列と文字配列を変換する)
  - [文字列・数値・配列を組み合わせる](#文字列数値配列を組み合わせる)
- [比較とソート](#比較とソート)
  - [`==`、`equals`、`compareTo`](#equalscompareto)
  - [独自オブジェクトのソートに使う型とデータ](#独自オブジェクトのソートに使う型とデータ)
  - [`Comparable` と `Comparator`](#comparable-と-comparator)
  - [ソートと `Comparator`](#ソートと-comparator)
  - [数値・文字列フィールドで並べる](#数値文字列フィールドで並べる)
  - [逆順にする条件を指定する](#逆順にする条件を指定する)
  - [二次元配列を列指定でソートする](#二次元配列を列指定でソートする)
  - [Map のエントリを値順に並べる](#map-のエントリを値順に並べる)
- [Stream と Optional](#stream-と-optional)
  - [生成](#生成)
  - [変換と絞り込み](#変換と絞り込み)
  - [判定と集計](#判定と集計)
  - [結果の収集](#結果の収集)
  - [終端操作ごとに Stream を生成する](#終端操作ごとに-stream-を生成する)
  - [`Optional`](#optional)
- [ビット操作](#ビット操作)
  - [ビット演算](#ビット演算)
  - [`BitSet`](#bitset)
  - [ビットを使う全探索](#ビットを使う全探索)
- [import](#import)

## 型と値

### プリミティブ型

| 型        |   サイズ | おおよその範囲             | ラッパー型  |
| --------- | -------: | -------------------------- | ----------- |
| `byte`    |    8 bit | -128 ～ 127                | `Byte`      |
| `short`   |   16 bit | 約 -3.2万 ～ 3.2万         | `Short`     |
| `int`     |   32 bit | 約 -21億 ～ 21億           | `Integer`   |
| `long`    |   64 bit | 約 -9.2×10^18 ～ 9.2×10^18 | `Long`      |
| `float`   |   32 bit | 単精度浮動小数点           | `Float`     |
| `double`  |   64 bit | 倍精度浮動小数点           | `Double`    |
| `char`    |   16 bit | UTF-16 コード単位          | `Character` |
| `boolean` | JVM 依存 | `true` / `false`           | `Boolean`   |

### 最大値・最小値

```java
int intMin = Integer.MIN_VALUE;
int intMax = Integer.MAX_VALUE;
long longMin = Long.MIN_VALUE;
long longMax = Long.MAX_VALUE;
```

### long リテラル

`int` の範囲を超える整数には `L` を付ける。

```java
long value = 1_000_000_000_000L;
```

### `record`

複数の値をひとまとめにする場合に使える。

```java
record Point(int row, int column) {}
record Edge(int to, long cost) {}
```

利用例:

```java
Point point = new Point(2, 3);
int row = point.row();
int column = point.column();
```

`record` には `equals`、`hashCode`、`toString` が自動生成される。

## 数値計算

### `Math`

```java
int absolute = Math.abs(value);
int minimum = Math.min(a, b);
int maximum = Math.max(a, b);
long power = (long) Math.pow(base, exponent);
double squareRoot = Math.sqrt(value);
double ceiling = Math.ceil(value);
double floor = Math.floor(value);
long rounded = Math.round(value);
int quotient = Math.floorDiv(a, b);
int remainder = Math.floorMod(a, b);
```

オーバーフローを検出する演算:

```java
int sum = Math.addExact(a, b);
long product = Math.multiplyExact(a, b);
```

### オーバーフローを避ける

演算前に `long` へ変換する。

```java
long product = (long) a * b;
```

### `floor`、`ceil`、`round`、`rint`

| メソッド     | 丸め方向                           | 戻り値         |
| ------------ | ---------------------------------- | -------------- |
| `Math.floor` | 負の無限大方向                     | `double`       |
| `Math.ceil`  | 正の無限大方向                     | `double`       |
| `Math.round` | 最も近い整数。中間は正の無限大方向 | `int` / `long` |
| `Math.rint`  | 最も近い整数値。中間は偶数側       | `double`       |

負数では「小数部分を捨てる」と `floor` が一致しない点に注意する。

### `Math.floorMod` で剰余を0以上にする

```java
int normalized = Math.floorMod(value, mod);
long normalized = Math.floorMod(value, mod);
```

`value % mod` は `value` が負の場合に負の値を返す可能性がある。

### 剰余・整数除算の考え方

剰余の性質と使いどころは[剰余演算](algorithms.md#剰余演算)、符号を含む整数除算は[切り上げ除算と切り下げ除算](algorithms.md#切り上げ除算と切り下げ除算)を参照する。

### `BigInteger`

`long` の範囲を超える整数を扱う。

```java
BigInteger first = new BigInteger("12345678901234567890");
BigInteger second = BigInteger.valueOf(100);

BigInteger sum = first.add(second);
BigInteger difference = first.subtract(second);
BigInteger product = first.multiply(second);
BigInteger quotient = first.divide(second);
BigInteger remainder = first.mod(second);
BigInteger power = second.pow(exponent);
int order = first.compareTo(second);
```

定数:

```java
BigInteger.ZERO
BigInteger.ONE
BigInteger.TWO
BigInteger.TEN
```

## 配列

### 作成と初期化

#### 作成

```java
int[] values = new int[n];
long[] longs = new long[n];
boolean[] visited = new boolean[n];
int[][] grid = new int[height][width];
```

初期値は数値が `0`、`boolean` が `false`、参照型が `null` となる。

#### 初期化・比較・文字列化

```java
Arrays.fill(values, -1);
boolean same = Arrays.equals(first, second);
boolean deepSame = Arrays.deepEquals(firstGrid, secondGrid);
String text = Arrays.toString(values);
String deepText = Arrays.deepToString(grid);
```

#### 二次元配列を同じ値で初期化する

```java
for (int[] row : grid) {
    Arrays.fill(row, -1);
}
```

### 走査と条件判定

`int[]`・`long[]` は `Arrays.stream`、`char[]` はループで条件を確認できる。配列には `contains` メソッドがない。

| 操作 | 書き方 |
| ---- | ------ |
| 指定した値を含む | `anyMatch(value -> value == target)` |
| 条件を満たす要素がある | `anyMatch` |
| 全要素が条件を満たす | `allMatch` |
| 条件を満たす要素がない | `noneMatch` |
| 条件を満たす要素数 | `filter(...).count()` |
| 条件を満たす最初の要素 | `filter(...).findFirst()` |
| 一致する添字 | ループ、または `IntStream.range` |

#### `int[]` に値が含まれるか調べる

```java
int[] values = {3, 1, 3, -2};
int target = 3;
boolean contains = Arrays.stream(values).anyMatch(value -> value == target);
// contains == true
```

`Arrays.asList(values)` は `int[]` 全体を1要素とする `List<int[]>` になる。`Arrays.asList(values).contains(target)` では配列内の整数を検索できない。`long[]`・`char[]` も同様。

#### 条件を満たす要素があるか・すべて満たすか・どれも満たさないか

```java
int[] values = {2, 4, 6};
boolean anyEven = Arrays.stream(values).anyMatch(value -> value % 2 == 0);
boolean allPositive = Arrays.stream(values).allMatch(value -> value > 0);
boolean noNegative = Arrays.stream(values).noneMatch(value -> value < 0);
// いずれも true
```

```java
long[] values = {3_000_000_000L, 4_000_000_000L};
long target = 3_000_000_000L;
boolean contains = Arrays.stream(values).anyMatch(value -> value == target);
boolean allNonNegative = Arrays.stream(values).allMatch(value -> value >= 0);
boolean noZero = Arrays.stream(values).noneMatch(value -> value == 0);
// いずれも true
```

| 空配列に対する操作 | 結果 |
| ------------------ | ---- |
| `anyMatch` | `false` |
| `allMatch` | `true` |
| `noneMatch` | `true` |
| `filter(...).count()` | `0` |
| `findFirst`・`min`・`max` | 空の Optional |

「要素が存在し、すべて条件を満たす」を確認する場合は `values.length > 0 && Arrays.stream(values).allMatch(...)` とする。判定が確定すると走査を打ち切り、最悪の計算量は O(N)。

#### `char[]` の条件を確認する

`Arrays.stream(char[])` は用意されていない。値の存在確認はループで書ける。

```java
char[] characters = {'a', 'x', 'b'};
boolean containsX = false;
for (char character : characters) {
    if (character == 'x') {
        containsX = true;
        break;
    }
}
// containsX == true
```

```java
char[] characters = {'a', 'b', 'c'};
boolean allLowercase = true;
for (char character : characters) {
    if (character < 'a' || character > 'z') {
        allLowercase = false;
        break;
    }
}
// allLowercase == true
```

Stream を使う場合は添字からアクセスする。`allLowercase` は空配列で `true` になる。

```java
char[] characters = {'a', 'b', 'c'};
boolean anyX = IntStream.range(0, characters.length)
    .anyMatch(index -> characters[index] == 'x');
boolean allLowercase = IntStream.range(0, characters.length)
    .allMatch(index -> 'a' <= characters[index] && characters[index] <= 'z');
boolean noX = IntStream.range(0, characters.length)
    .noneMatch(index -> characters[index] == 'x');
// anyX == false、allLowercase == true、noX == true
```

### 検索

#### 最初・最後に一致する添字を探す

```java
int[] values = {3, 1, 3, -2};
int target = 3;
int firstIndex = -1;
int lastIndex = -1;
for (int index = 0; index < values.length; index++) {
    if (values[index] == target) {
        if (firstIndex == -1) {
            firstIndex = index;
        }
        lastIndex = index;
    }
}
// firstIndex == 0、lastIndex == 2。見つからなければ -1
```

`long[]`・`char[]` も同じ走査で比較できる。最初の一致だけ必要なら、見つかった時点で `break` する。

```java
int[] values = {3, 1, 3, -2};
int firstNegativeIndex = IntStream.range(0, values.length)
    .filter(index -> values[index] < 0)
    .findFirst()
    .orElse(-1);
int firstNegative = Arrays.stream(values).filter(value -> value < 0)
    .findFirst().orElseThrow();
// firstNegativeIndex == 3、firstNegative == -2
```

`orElseThrow()` は一致する要素があることが前提。存在しない可能性がある場合は Optional のまま扱うか、用途に合う既定値を指定する。

#### ソート済み配列で存在確認する

```java
int[] values = {1, 3, 3, 7}; // 昇順ソート済み
boolean contains = Arrays.binarySearch(values, 3) >= 0;
// contains == true
```

`long[]`・`char[]` にも同じ API がある。検索前に昇順にソートしておく必要がある。同値が複数あるときに返される添字は、先頭や末尾とは限らない。

戻り値の扱いは[配列のソート・検索](#配列のソート検索)、境界探索の実装と採用条件は[アルゴリズム集の二分探索](algorithms.md#二分探索)を参照する。

#### 同じ値集合に繰り返し存在確認する

```java
int[] values = {2, 5, 2};
boolean[] present = new boolean[10]; // 値が 0 以上 10 未満
for (int value : values) {
    present[value] = true;
}
boolean containsFive = present[5];
// containsFive == true
```

```java
long[] values = {-5L, 3_000_000_000L};
Set<Long> present = new HashSet<>();
for (long value : values) {
    present.add(value);
}
boolean contains = present.contains(3_000_000_000L);
// contains == true
```

値域と検索回数に応じた選択は[アルゴリズム集](algorithms.md#繰り返す存在確認の手法を選ぶ)を参照する。

### 集計

#### 値の出現回数・条件を満たす要素数を求める

```java
int[] values = {3, 1, 3, -2};
int target = 3;
long occurrences = Arrays.stream(values).filter(value -> value == target).count();
long positiveCount = Arrays.stream(values).filter(value -> value > 0).count();
// occurrences == 2、positiveCount == 3
```

```java
char[] characters = {'a', 'x', 'a'};
int occurrences = 0;
for (char character : characters) {
    if (character == 'a') {
        occurrences++;
    }
}
// occurrences == 2
```

`count()` の戻り値は `long`。`long[]` の要素も `Arrays.stream(values).filter(...).count()` で数えられる。

#### 合計・最大値・最小値を求める

```java
int[] values = {1_500_000_000, 1_500_000_000};
long sum = Arrays.stream(values).asLongStream().sum();
int minimum = Arrays.stream(values).min().orElseThrow();
int maximum = Arrays.stream(values).max().orElseThrow();
// sum == 3_000_000_000L、minimum == maximum == 1_500_000_000
```

```java
long[] values = {3_000_000_000L, 4_000_000_000L};
long sum = Arrays.stream(values).sum();
long minimum = Arrays.stream(values).min().orElseThrow();
long maximum = Arrays.stream(values).max().orElseThrow();
// sum == 7_000_000_000L、minimum == 3_000_000_000L、maximum == 4_000_000_000L
```

`IntStream.sum()` は `int` の範囲で加算する。合計が大きい場合は加算前に `asLongStream()` へ変換する。`LongStream.sum()` も `long` の範囲を超えるとオーバーフローする。空配列の合計は `0`。

#### 最大値・最小値の添字を求める

```java
int[] values = {4, -2, 7, 7}; // 非空配列
int minIndex = 0;
int maxIndex = 0;
for (int index = 1; index < values.length; index++) {
    if (values[index] < values[minIndex]) {
        minIndex = index;
    }
    if (values[index] > values[maxIndex]) {
        maxIndex = index;
    }
}
// minIndex == 1、maxIndex == 2。同値の場合は最初の添字を保持
```

`long[]`・`char[]` も同じ走査を使える。空配列の可能性がある場合は、添字を参照する前に長さを確認する。

### コピーと比較

#### コピー

```java
int[] copied = values.clone();
int[] resized = Arrays.copyOf(values, newLength);
int[] range = Arrays.copyOfRange(values, fromInclusive, toExclusive);
System.arraycopy(source, sourcePosition, destination, destinationPosition, length);
```

#### 二次元配列をコピーする

```java
int[][] grid = {{1, 2}, {3, 4}};
int[][] copied = new int[grid.length][];
for (int row = 0; row < grid.length; row++) {
    copied[row] = grid[row].clone();
}
copied[0][0] = 9;
// grid[0][0] は 1 のまま
```

`grid.clone()` は外側だけをコピーし、各行の配列を共有する。行も独立させる場合は各行をコピーする。

#### `Arrays.equals` と `Arrays.deepEquals`

| メソッド            | 比較対象                 |
| ------------------- | ------------------------ |
| `Arrays.equals`     | 一次元配列の各要素       |
| `Arrays.deepEquals` | 多次元配列を再帰的に比較 |

```java
Arrays.equals(firstRow, secondRow);
Arrays.deepEquals(firstGrid, secondGrid);
```

多次元配列へ `Arrays.equals` を使うと、内側の配列は参照として比較される。

### 並べ替え

#### 配列のソート・検索

```java
Arrays.sort(values);
Arrays.sort(objects, comparator);
int index = Arrays.binarySearch(values, key);
```

範囲を指定する場合は半開区間 `[fromIndex, toIndex)` になる。`fromIndex` は含み、`toIndex` は含まない。

```java
Arrays.sort(values, fromIndex, toIndex);
```

`int[]` の指定範囲を降順にする場合は、昇順ソートしてから範囲内を反転する。

```java
Arrays.sort(values, fromIndex, toIndex);
for (int left = fromIndex, right = toIndex - 1; left < right; left++, right--) {
    int temporary = values[left];
    values[left] = values[right];
    values[right] = temporary;
}
```

`Arrays.binarySearch` は、値が存在する場合はそのインデックスを返す。存在しない場合は `-(挿入位置) - 1` を返す。

```java
int insertionPoint = -index - 1;
```

#### プリミティブ配列を降順にする

`Arrays.sort` はプリミティブ配列に `Comparator` を指定できないため、昇順ソート後に反転する。

```java
Arrays.sort(values);

for (int left = 0, right = values.length - 1; left < right; left++, right--) {
    int temporary = values[left];
    values[left] = values[right];
    values[right] = temporary;
}
```

#### 配列・リストを逆順にする

```java
char[] characters = {'a', 'b', 'c'};
for (int left = 0, right = characters.length - 1; left < right; left++, right--) {
    char temporary = characters[left];
    characters[left] = characters[right];
    characters[right] = temporary;
}
List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
Collections.reverse(list);
// characters は {'c', 'b', 'a'}、list は [3, 2, 1]
```

`long[]` の反転では一時変数も `long` にする。

オブジェクトや二次元配列の比較条件は[比較とソート](#比較とソート)を参照する。

### 加工と出力

#### 条件に合う要素を配列へ取り出す

```java
int[] values = {-2, 3, 0, 5};
int[] positive = Arrays.stream(values).filter(value -> value > 0).toArray();
long[] longs = {-1L, 3_000_000_000L};
long[] nonNegative = Arrays.stream(longs).filter(value -> value >= 0).toArray();
// positive は {3, 5}、nonNegative は {3_000_000_000L}
```

```java
char[] characters = {'a', 'x', 'b', 'x'};
char[] buffer = new char[characters.length];
int size = 0;
for (char character : characters) {
    if (character != 'x') {
        buffer[size++] = character;
    }
}
char[] filtered = Arrays.copyOf(buffer, size);
// filtered は {'a', 'b'}
```

#### `Stream` で配列の重複を除く

```java
int[] unique = Arrays.stream(values).distinct().toArray();
int[] sortedUnique = Arrays.stream(values).sorted().distinct().toArray();
```

#### 配列の出力と `char[]` の特別扱い

`PrintWriter` の `print` / `println` には `char[]` 専用のオーバーロードがある。`char[]` を直接渡すと、各文字が区切りなしで連続して出力される。

```java
char[] characters = {'a', 'b', 'c'};
out.println(characters);             // abc
out.println(new String(characters)); // abc（明示的に文字列へ変換）
out.println(Arrays.toString(characters)); // [a, b, c]
```

例えば `List<char[]>` から取り出した値も、コンパイル時の型が `char[]` なので同じ専用オーバーロードが選ばれる。

```java
List<char[]> permutations = List.of(new char[] {'a', 'b', 'c'});
out.println(permutations.get(0)); // abc
```

`char[]` 以外の配列には、配列全体を内容どおりに出力する専用オーバーロードがない。直接渡すと `println(Object)` が選ばれ、型名とハッシュ値のような文字列が出力される。

```java
int[] values = {1, 2, 3};
out.println(values);                  // [I@... のような表示
out.println(Arrays.toString(values)); // [1, 2, 3]
```

| 目的                                | 書き方                                   | 出力例             |
| ----------------------------------- | ---------------------------------------- | ------------------ |
| `char[]` を連結した文字列として出力 | `out.println(characters)`                | `abc`              |
| `char[]` を明示的に `String` 化     | `out.println(new String(characters))`    | `abc`              |
| 1次元配列の内容を確認               | `out.println(Arrays.toString(values))`   | `[1, 2, 3]`        |
| 多次元配列の内容を確認              | `out.println(Arrays.deepToString(grid))` | `[[1, 2], [3, 4]]` |
| 配列要素を空白区切りで出力          | テンプレートの `printArray(values)`      | `1 2 3`            |

`char[][]` は `char[]` ではないため、直接渡しても各行の文字は連結されない。行ごとに出力する。

```java
for (char[] row : characterGrid) {
    out.println(row);
}
```

配列とリスト、プリミティブ配列とラッパー配列の変換は[型変換](#型変換)を参照する。

## 文字列と文字

### `String`

#### 文字列の基本操作

```java
int length = text.length();
char character = text.charAt(index);
String part = text.substring(begin, end); // end は含まない
boolean empty = text.isEmpty();
boolean contains = text.contains("abc");
boolean starts = text.startsWith("abc");
boolean ends = text.endsWith("xyz");
int firstIndex = text.indexOf('a');
int lastIndex = text.lastIndexOf('a');
String replaced = text.replace("old", "new");
String trimmed = text.trim();
String stripped = text.strip();
String upper = text.toUpperCase(Locale.ROOT);
String lower = text.toLowerCase(Locale.ROOT);
```

#### 文字を順番に処理する

```java
String text = "abc";
for (int index = 0; index < text.length(); index++) {
    char character = text.charAt(index);
    // index と character を使う
}
for (char character : text.toCharArray()) {
    // character を使う
}
```

`char` は UTF-16 コード単位。英数字や通常の競プロの文字グリッドは `char` で扱える。補助文字まで1文字単位で扱う場合は `codePoints()` を使う。

#### 分割・結合

`split` の引数は正規表現である。

```java
String[] words = text.split(" ");
String[] columns = text.split(",", -1); // 末尾の空文字も保持
String joined = String.join(" ", words);
```

`.` や `|` などを文字として分割する場合はエスケープが必要となる。

```java
String[] parts = text.split("\\.");
```

#### 文字列比較

```java
boolean same = first.equals(second);
boolean sameIgnoringCase = first.equalsIgnoreCase(second);
int order = first.compareTo(second);
```

文字列の内容比較には `==` ではなく `equals` を使う。

#### `trim` と `strip`

| メソッド  | 空白の判定                                       | Java          |
| --------- | ------------------------------------------------ | ------------- |
| `trim()`  | `U+0020` 以下の文字                              | Java 1.0 以降 |
| `strip()` | `Character.isWhitespace` が認識する Unicode 空白 | Java 11 以降  |

```java
String text = "\u2003Hello\u2003"; // EM SPACE
text.trim(); // EM SPACEは残る
text.strip(); // "Hello"
```

Unicode 空白を扱う場合は `strip()` を基本とする。先頭だけなら `stripLeading()`、末尾だけなら `stripTrailing()` を使う。

#### `isEmpty` と `isBlank`

| メソッド    | `true` になる条件                |
| ----------- | -------------------------------- |
| `isEmpty()` | 長さが0                          |
| `isBlank()` | 長さが0、または全て Unicode 空白 |

```java
"   ".isEmpty(); // false
"   ".isBlank(); // true
```

### `Character`

```java
boolean digit = Character.isDigit(character);
boolean letter = Character.isLetter(character);
boolean upper = Character.isUpperCase(character);
boolean lower = Character.isLowerCase(character);
char upperCharacter = Character.toUpperCase(character);
char lowerCharacter = Character.toLowerCase(character);
int numericValue = Character.getNumericValue(character);
```

#### 半角英字（`A-Z`、`a-z`）か判定する

```java
boolean isAsciiLetter = ('A' <= character && character <= 'Z')
        || ('a' <= character && character <= 'z');
```

`Character.isLetter(character)` は、ひらがなや漢字などの Unicode 文字にも `true` を返す。半角英字だけに限定する場合は、上記のように文字の範囲を直接比較する。

#### 英小文字の出現回数

```java
int[] frequencies = new int[26];
for (char character : text.toCharArray()) {
    frequencies[character - 'a']++;
}
```

英小文字以外を含む場合は、`Map<Character, Integer>` や `Map.merge` を検討する。

### `StringBuilder`

```java
StringBuilder builder = new StringBuilder();
builder.append(value);
builder.append(' ');
builder.insert(index, value);
builder.delete(begin, end);
builder.deleteCharAt(index);
builder.setCharAt(index, character);
builder.reverse();
char character = builder.charAt(index);
int length = builder.length();
String result = builder.toString();
```

#### `StringBuilder` で文字列を逆順にする

```java
String reversed = new StringBuilder(text).reverse().toString();
```

#### `StringBuilder` と `StringBuffer`

| 型              | スレッドセーフ | 一般的な選択               |
| --------------- | -------------- | -------------------------- |
| `StringBuilder` | いいえ         | 単一スレッドで高速         |
| `StringBuffer`  | はい           | 複数スレッドで共有する場合 |

競技プログラミングは通常単一スレッドなので `StringBuilder` を使う。

文字列と数値、文字配列、各桁の配列の変換は[型変換](#型変換)を参照する。

## コレクション

### `List`

#### List の基本操作

```java
List<Integer> list = new ArrayList<>();
list.add(value);
list.add(index, value);
int value = list.get(index);
list.set(index, value);
list.remove(index);
boolean removed = list.remove(Integer.valueOf(value));
boolean contains = list.contains(value);
int size = list.size();
boolean empty = list.isEmpty();
list.clear();
```

#### 条件を満たすオブジェクトを調べる

```java
record Item(int index, int score) {}
List<Item> items = List.of(new Item(0, 80), new Item(1, 95));
boolean contains = items.contains(new Item(0, 80));
boolean anyHighScore = items.stream().anyMatch(item -> item.score() >= 90);
boolean allPassed = items.stream().allMatch(item -> item.score() >= 60);
// いずれも true
```

`contains` は `equals` に基づいて比較する。`record` には値に基づく `equals` が生成される。通常のクラスで値を比較したい場合は `equals`・`hashCode` の実装が必要。

#### `List.remove(index)` と `List.remove(value)`

`List<Integer>` では整数引数がインデックスとして解釈される。

```java
list.remove(2);                  // index 2を削除
list.remove(Integer.valueOf(2)); // 値2を最初に見つけた位置から削除
```

#### `Arrays.asList`、`List.of`、`Stream.toList`

| 作成方法               | 要素変更 | 追加・削除 | `null`             |
| ---------------------- | -------- | ---------- | ------------------ |
| `Arrays.asList(array)` | 可       | 不可       | 可                 |
| `List.of(values...)`   | 不可     | 不可       | 不可               |
| `stream.toList()`      | 不可     | 不可       | Stream内にあれば可 |

`Arrays.asList` は元配列と要素を共有する固定長リストである。変更可能な独立リストが必要なら次を使う。

```java
List<String> mutable = new ArrayList<>(Arrays.asList(array));
```

配列との変換は[型変換](#型変換)、順序の指定は[比較とソート](#比較とソート)を参照する。

#### リストを空白区切りで出力する

テンプレートの `printArray` は、配列に加えて `List<?>` を受け取れる。

```java
printArray(List.of(1, 2, 3));           // 1 2 3
printArray(List.of(1L, 2L, 3L));        // 1 2 3
printArray(List.of("red", "green"));   // red green
printArray(List.of('a', 'b', 'c'));     // a b c
printArray(List.of());                // 改行だけ
```

要素間に空白を入れ、末尾に改行を出力する。`null` 要素は `null` として出力する。

カンマなど別の区切り文字を使う場合は、[区切り文字を指定した出力](#カンマなどの区切り文字を指定して出力する)を参照する。

### `Set`

```java
Set<Integer> hashSet = new HashSet<>();
Set<Integer> sortedSet = new TreeSet<>();

set.add(value);
set.remove(value);
boolean contains = set.contains(value);
int size = set.size();
```

#### `TreeSet` 固有の操作

```java
TreeSet<Integer> set = new TreeSet<>();
Integer lower = set.lower(value);     // value より小さい最大値
Integer floor = set.floor(value);     // value 以下の最大値
Integer ceiling = set.ceiling(value); // value 以上の最小値
Integer higher = set.higher(value);   // value より大きい最小値
Integer first = set.first();
Integer last = set.last();
```

該当要素がない `lower`、`floor`、`ceiling`、`higher` は `null` を返す。

#### `HashSet`、`LinkedHashSet`、`TreeSet`

| 型              | 順序     | 基本操作  |
| --------------- | -------- | --------- |
| `HashSet`       | 保証なし | 平均 O(1) |
| `LinkedHashSet` | 挿入順   | 平均 O(1) |
| `TreeSet`       | ソート順 | O(log N)  |

大小関係や `floor`、`ceiling` が必要なら `TreeSet`、順序が不要なら `HashSet` を基本とする。

### `Map`

```java
Map<String, Integer> map = new HashMap<>();
map.put(key, value);
Integer value = map.get(key);
int defaultValue = map.getOrDefault(key, 0);
boolean hasKey = map.containsKey(key);
map.remove(key);
int size = map.size();
```

#### 頻度カウント

```java
map.merge(key, 1, Integer::sum);
```

#### 値がない場合だけ作成

```java
map.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
```

#### 走査

```java
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    int value = entry.getValue();
}
```

キーをソートして保持する場合は `TreeMap` を使う。

```java
Map<Integer, String> sortedMap = new TreeMap<>();
```

#### `getOrDefault`、`putIfAbsent`、`computeIfAbsent`

| メソッド                          | Mapを変更するか            | 主な用途             |
| --------------------------------- | -------------------------- | -------------------- |
| `getOrDefault(key, defaultValue)` | 変更しない                 | 値の取得だけ         |
| `putIfAbsent(key, value)`         | キーがなければ追加         | 作成済みの値を登録   |
| `computeIfAbsent(key, function)`  | キーがなければ計算して追加 | リストなどを遅延作成 |

```java
int count = map.getOrDefault(key, 0);
map.putIfAbsent(key, initialValue);
map.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
```

#### リストを値に持つ Map を作る

```java
Map<Integer, List<Integer>> groups = new HashMap<>();
groups.computeIfAbsent(2, ignored -> new ArrayList<>()).add(5);
groups.computeIfAbsent(2, ignored -> new ArrayList<>()).add(8);
// groups.get(2) は [5, 8]
```

#### `HashMap`、`LinkedHashMap`、`TreeMap`

| 型              | キーの順序 | 基本操作  |
| --------------- | ---------- | --------- |
| `HashMap`       | 保証なし   | 平均 O(1) |
| `LinkedHashMap` | 挿入順     | 平均 O(1) |
| `TreeMap`       | ソート順   | O(log N)  |

キー順の走査や境界検索が必要なら `TreeMap` を使う。

#### `Map.Entry`

一時的な2要素の組として使える。

```java
Map.Entry<Integer, String> pair = Map.entry(1, "value");
int first = pair.getKey();
String second = pair.getValue();
```

`Map.entry` が返すエントリは変更不可である。

### キュー・スタック・両端キュー

`Deque` をキューとスタックの両方に使える。

```java
Deque<Integer> deque = new ArrayDeque<>();
```

#### キューとして使う

```java
deque.addLast(value);
int first = deque.removeFirst();
Integer nullableFirst = deque.pollFirst();
Integer peek = deque.peekFirst();
```

#### スタックとして使う

```java
deque.push(value);
int top = deque.pop();
Integer peek = deque.peek();
```

`remove`、`pop` は空の場合に例外を投げ、`poll` は `null` を返す。

#### `add` と `offer`、`remove` と `poll`、`element` と `peek`

キューでは、失敗時に例外を投げるメソッドと特別値を返すメソッドがある。

| 操作     | 例外を投げる | 特別値を返す              |
| -------- | ------------ | ------------------------- |
| 追加     | `add`        | `offer`（失敗時 `false`） |
| 取り出し | `remove`     | `poll`（空なら `null`）   |
| 先頭確認 | `element`    | `peek`（空なら `null`）   |

`ArrayDeque` は通常容量不足にならないため、追加では `add` と `offer` の差が現れにくい。空キューを許容する処理では `poll`、空でないことが前提なら `remove` を使う。

### `PriorityQueue`

#### 最小値を先頭にする

```java
PriorityQueue<Integer> queue = new PriorityQueue<>();
```

#### 最大値を先頭にする

```java
PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
```

#### 操作

```java
queue.add(value);
int first = queue.remove();
Integer nullableFirst = queue.poll();
Integer peek = queue.peek();
int size = queue.size();
```

#### 独自オブジェクトの優先順位を指定する

```java
record State(int vertex, long distance) {}
Comparator<State> byDistance = Comparator.comparingLong(State::distance)
    .thenComparingInt(State::vertex);
PriorityQueue<State> queue = new PriorityQueue<>(byDistance);
queue.add(new State(2, 10L));
queue.add(new State(1, 5L));
State nearest = queue.remove();
// nearest は State[vertex=1, distance=5]
```

PriorityQueue のイテレーターの走査順は優先順位順とは限らない。優先順に取り出す場合は `poll`・`remove` を繰り返す。登録中のオブジェクトの比較キーを変更しても、自動では並べ直されない。

### `Collections`

```java
Collections.sort(list);
Collections.reverse(list);
Collections.fill(list, value);
Collections.swap(list, firstIndex, secondIndex);
T minimum = Collections.min(list);
T maximum = Collections.max(list);
int frequency = Collections.frequency(list, value);
int index = Collections.binarySearch(list, key);
```

## 型変換

### 数値変換

#### 文字列と数値

```java
int intValue = Integer.parseInt("123");
long longValue = Long.parseLong("123");
double doubleValue = Double.parseDouble("1.25");

String intText = Integer.toString(123);
String longText = Long.toString(123L);
String doubleText = Double.toString(1.25);
```

整数として解釈できない文字列や、対象の型の範囲を超える値を渡すと `NumberFormatException` になる。

基数を指定した変換:

```java
int binaryValue = Integer.parseInt("1010", 2); // 10
long hexValue = Long.parseLong("ff", 16);       // 255
String binary = Integer.toBinaryString(10);     // "1010"
String hex = Integer.toHexString(255);           // "ff"
String base3 = Integer.toString(10, 3);          // "101"
```

#### 整数型・浮動小数点型の変換

値の範囲が広がる変換は暗黙に行われる。

```java
int intValue = 123;
long longValue = intValue;
double doubleValue = longValue;
```

値の範囲が狭まる変換にはキャストが必要となる。

```java
long longValue = 123L;
int intValue = (int) longValue;

double doubleValue = 12.9;
int truncated = (int) doubleValue; // 12。小数部分は0方向へ切り捨て
```

キャスト先の範囲を超えても例外は発生せず、上位ビットが失われる。`long` から `int` への変換で範囲超過を検出する場合は `Math.toIntExact` を使う。

```java
int exact = Math.toIntExact(longValue); // 範囲外ならArithmeticException
```

`long` から `double` への変換は値の範囲を扱えるが、大きな整数では精度が失われる可能性がある。

#### 数字と文字

```java
int digit = '7' - '0';                  // 7
char digitCharacter = (char) ('0' + 7); // '7'

int radixDigit = Character.digit('f', 16);      // 15
char radixCharacter = Character.forDigit(15, 16); // 'f'
```

`character - '0'` は文字が `'0'` 以上 `'9'` 以下と分かっている場合に使う。`Character.digit` は指定した基数の数字として解釈できない場合に `-1` を返す。

#### `parseInt` と `valueOf`

| メソッド                 | 戻り値    |
| ------------------------ | --------- |
| `Integer.parseInt(text)` | `int`     |
| `Integer.valueOf(text)`  | `Integer` |

```java
int primitive = Integer.parseInt("123");
Integer boxed = Integer.valueOf("123");
```

プリミティブ値が必要なら `parseInt`、オブジェクトが必要なら `valueOf` を使う。`Long`、`Double` などにも同様のメソッドがある。

### `int[]` と `long[]` を変換する

```java
int[] intValues = {1, 2, 3};
long[] longValues = Arrays.stream(intValues)
    .asLongStream()
    .toArray();
```

`long[]` から `int[]` へ安全に変換する場合:

```java
long[] longValues = {1L, 2L, 3L};
int[] intValues = Arrays.stream(longValues)
    .mapToInt(Math::toIntExact)
    .toArray();
```

`(int) value` を使うと範囲外の値が切り詰められる。`Math.toIntExact` は範囲外の値で `ArithmeticException` を投げる。

### プリミティブ配列とラッパー配列を変換する

```java
int[] values = {1, 2, 3};
Integer[] boxed = Arrays.stream(values).boxed().toArray(Integer[]::new);
int[] restored = Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
long[] longs = {1L, 2L};
Long[] boxedLongs = Arrays.stream(longs).boxed().toArray(Long[]::new);
long[] restoredLongs = Arrays.stream(boxedLongs).mapToLong(Long::longValue).toArray();
```

ラッパー配列・リストに `null` があると、アンボクシング時に `NullPointerException` になる。

### 配列とリストを変換する

```java
List<String> list = new ArrayList<>(Arrays.asList(array));
String[] array = list.toArray(String[]::new);

List<Integer> boxed = Arrays.stream(intArray).boxed().toList();
int[] primitive = boxed.stream().mapToInt(Integer::intValue).toArray();
```

`Stream.toList()` が返すリストは変更不可である。変更する場合は次を使う。

```java
List<Integer> mutable = Arrays.stream(intArray)
    .boxed()
    .collect(Collectors.toCollection(ArrayList::new));
```

### 文字列と文字配列を変換する

```java
char[] characters = text.toCharArray();
String restored = new String(characters);
```

### 文字列・数値・配列を組み合わせる

#### 文字列の各桁を配列に変換する

```java
int[] digits = text.chars().map(character -> character - '0').toArray();
```

整数から変換する場合:

```java
int[] digits = Long.toString(value)
    .chars()
    .map(character -> character - '0')
    .toArray();
```

負数を扱う場合は符号を除いてから変換する。

#### 各桁の配列を整数に変換する

逐次計算とオーバーフローの扱いは[アルゴリズム集](algorithms.md#各桁の配列を整数に変換する)を参照する。

#### 空白区切り文字列を数値配列に変換する

```java
String text = "10 20 30";
int[] values = Arrays.stream(text.strip().split("\\s+"))
    .mapToInt(Integer::parseInt)
    .toArray();
```

`strip()` 後の文字列が空の場合、`split` が返す空文字列を数値へ変換できず `NumberFormatException` になる。空入力の可能性がある場合は先に判定する。

```java
int[] values = text.isBlank()
    ? new int[0]
    : Arrays.stream(text.strip().split("\\s+"))
        .mapToInt(Integer::parseInt)
        .toArray();
```

大量の入力は、文字列全体を分割せず `FastScanner` から直接読み取る。

#### 英字と0始まりの添字を変換する

```java
int lowerIndex = lowerCharacter - 'a';
char lowerCharacter = (char) ('a' + lowerIndex);

int upperIndex = upperCharacter - 'A';
char upperCharacter = (char) ('A' + upperIndex);
```

## 比較とソート

### `==`、`equals`、`compareTo`

| 方法                      | 比較内容                         |
| ------------------------- | -------------------------------- |
| `first == second`         | 同じオブジェクトを参照しているか |
| `first.equals(second)`    | 内容が等しいか                   |
| `first.compareTo(second)` | 辞書順・大小関係                 |

文字列やラッパー型の内容比較には `equals` を使う。大小関係が必要なら `compareTo` を使う。

### 独自オブジェクトのソートに使う型とデータ

```java
record Item(int index, int score, long cost, String name) {}
List<Item> items = new ArrayList<>(List.of(
    new Item(0, 80, 300L, "beta"),
    new Item(1, 95, 200L, "alpha"),
    new Item(2, 95, 100L, "gamma")
));
```

以下の独自オブジェクトの例は、この `Item` と `items` を使う。`record` の各値は `score()` などのアクセサーで取得できる。

### `Comparable` と `Comparator`

| 型              | 役割                          |
| --------------- | ----------------------------- |
| `Comparable<T>` | クラス自身の自然順序を1つ定義 |
| `Comparator<T>` | 用途ごとの順序を外部から定義  |

```java
Comparator<Item> byScore = Comparator.comparingInt(Item::score);
Comparator<Item> byIndex = Comparator.comparingInt(Item::index);
```

同じ型を問題ごとに異なる順序で並べる競技プログラミングでは、`Comparator` を使うことが多い。

### ソートと `Comparator`

```java
list.sort(Comparator.naturalOrder());
list.sort(Comparator.reverseOrder());
```

オブジェクトの複数条件ソート:

```java
items.sort(
    Comparator.comparingInt(Item::score)
        .thenComparingInt(Item::index)
);
```

降順を含む場合:

```java
items.sort(
    Comparator.comparingInt(Item::score)
        .reversed()
        .thenComparingInt(Item::index)
);
```

整数比較では減算ではなく比較メソッドを使う。減算はオーバーフローする可能性がある。

```java
int order = Integer.compare(first, second);
int longOrder = Long.compare(firstLong, secondLong);
```

### 数値・文字列フィールドで並べる

```java
items.sort(Comparator.comparingLong(Item::cost));
items.sort(Comparator.comparing(Item::name));
items.sort((first, second) -> Integer.compare(first.score(), second.score()));
Item[] array = items.toArray(Item[]::new);
Arrays.sort(array, Comparator.comparingInt(Item::score));
```

通常のクラスも同様にソートできる。メソッド参照の代わりに `Comparator.comparingInt((Item item) -> item.score())` のようにラムダ式を使える。

### 逆順にする条件を指定する

```java
Comparator<Item> scoreDescendingIndexAscending = Comparator.comparingInt(Item::score)
    .reversed()
    .thenComparingInt(Item::index);

Comparator<Item> scoreAscendingIndexDescending = Comparator.comparingInt(Item::score)
    .thenComparing(Comparator.comparingInt(Item::index).reversed());

Comparator<Item> scoreDescendingIndexDescending = Comparator.comparingInt(Item::score)
    .thenComparingInt(Item::index)
    .reversed();
```

`reversed()` は、その位置までに組み立てた比較条件全体を反転する。

### 二次元配列を列指定でソートする

```java
int[][] rows = {{2, 9}, {1, 4}, {1, 7}};
Arrays.sort(rows, Comparator.<int[]>comparingInt(row -> row[0])
    .thenComparing(Comparator.<int[]>comparingInt(row -> row[1]).reversed()));
// {{1, 7}, {1, 4}, {2, 9}}
```

外側の配列の行を並べ替える。各行に指定した列が存在することが前提。`long[][]` の比較には `comparingLong` を使う。

### Map のエントリを値順に並べる

```java
Map<String, Integer> scores = Map.of("beta", 80, "alpha", 95, "gamma", 95);
List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());
entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed()
    .thenComparing(Map.Entry.comparingByKey()));
// 値の降順、同値ならキーの昇順。alpha=95, gamma=95, beta=80
```

元の Map の走査順は変更されない。並べた順序を使う場合は `entries` を走査する。

プリミティブ配列の昇順・降順や反転は[配列の並べ替え](#並べ替え)を参照する。

## Stream と Optional

### 生成

#### Stream を生成する

```java
int[] values = {1, 2, 3};
long[] longs = {1L, 2L};
String[] words = {"a", "bb"};
IntStream integers = Arrays.stream(values);
LongStream longIntegers = Arrays.stream(longs);
Stream<String> strings = Arrays.stream(words);
IntStream indices = IntStream.range(0, values.length); // 0 以上 length 未満
IntStream inclusive = IntStream.rangeClosed(1, 3); // 1, 2, 3
```

#### `IntStream` で整数リストを作る

```java
List<Integer> indices = IntStream.range(0, n).boxed().toList();
```

変更可能なリストが必要な場合:

```java
List<Integer> indices = IntStream.range(0, n)
    .boxed()
    .collect(Collectors.toCollection(ArrayList::new));
```

### 変換と絞り込み

#### map・mapToInt・mapToLong・mapToObj・boxed を使い分ける

```java
List<String> words = List.of("1", "20", "300");
int[] parsed = words.stream().mapToInt(Integer::parseInt).toArray();
long totalLength = words.stream().mapToLong(String::length).sum();
List<String> labels = IntStream.range(0, 3).mapToObj(index -> "v" + index).toList();
List<Integer> numbers = IntStream.range(0, 3).boxed().toList();
List<String> uppercase = List.of("a", "b").stream().map(String::toUpperCase).toList();
```

| メソッド | 戻り値の種類 |
| -------- | ------------ |
| オブジェクトの `map` | `Stream<R>` |
| `IntStream.map` | `IntStream` |
| `mapToInt` | `IntStream` |
| `mapToLong` | `LongStream` |
| `mapToObj` | `Stream<R>` |
| `IntStream.boxed` | `Stream<Integer>` |
| `LongStream.boxed` | `Stream<Long>` |

#### 絞り込み・重複除去・ソートを組み合わせる

```java
int[] values = {3, -1, 2, 3, 0};
int[] sortedUniquePositive = Arrays.stream(values)
    .filter(value -> value > 0)
    .distinct()
    .sorted()
    .toArray();
// {2, 3}
```

独自オブジェクトの並べ替えには `stream.sorted(comparator)` を使える。

#### 複数の配列・リストを平坦化する

```java
int[][] rows = {{1, 2}, {}, {3}};
int[] flattened = Arrays.stream(rows).flatMapToInt(Arrays::stream).toArray();
List<List<String>> groups = List.of(List.of("a", "b"), List.of("c"));
List<String> words = groups.stream().flatMap(List::stream).toList();
// flattened は {1, 2, 3}、words は [a, b, c]
```

### 判定と集計

#### `Stream` の最小限の使用例

```java
int sum = list.stream().mapToInt(Integer::intValue).sum();
int maximum = list.stream().mapToInt(Integer::intValue).max().orElseThrow();
long count = list.stream().filter(value -> value > 0).count();
List<Integer> sorted = list.stream().sorted().toList();
String joined = list.stream().map(String::valueOf).collect(Collectors.joining(" "));
```

競技プログラミングでは、速度やアロケーションが重要な箇所は通常のループのほうが扱いやすい。

存在確認・全要素の判定・添字の取得と空配列の結果は[配列の走査と条件判定](#走査と条件判定)、合計や最大・最小は[配列の集計](#集計)を参照する。

### 結果の収集

#### `Collectors.joining` で区切って出力する

```java
String result = values.stream()
    .map(String::valueOf)
    .collect(Collectors.joining(" "));
out.println(result);
```

プリミティブ配列の場合:

```java
String result = Arrays.stream(values)
    .mapToObj(String::valueOf)
    .collect(Collectors.joining(" "));
out.println(result);
```

#### カンマなどの区切り文字を指定して出力する

`java.util.stream.Collectors` を import して使用する。

```java
List<Integer> values = List.of(1, 2, 3);
String result = values.stream()
    .map(String::valueOf)
    .collect(Collectors.joining(","));
out.println(result); // 1,2,3
```

`int[]`・`long[]` は `Arrays.stream` から文字列へ変換する。

```java
int[] values = {1, 2, 3};
String result = Arrays.stream(values)
    .mapToObj(String::valueOf)
    .collect(Collectors.joining(","));
out.println(result); // 1,2,3
```

`joining(", ")` なら `1, 2, 3` のようにカンマの後へ空白を入れられる。空のリスト・配列では空文字列となり、`println` は改行だけを出力する。

文字列のリスト・配列には `String.join` も使える。

```java
out.println(String.join(",", List.of("red", "green"))); // red,green
out.println(String.join(",", new String[] {"red", "green"})); // red,green
```

#### groupingBy・counting で分類・集計する

```java
List<String> words = List.of("a", "bb", "a");
Map<String, Long> frequencies = words.stream()
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
Map<Integer, List<String>> byLength = words.stream()
    .collect(Collectors.groupingBy(String::length));
// frequencies.get("a") == 2L、byLength.get(1) は [a, a]
```

頻度だけを数える短いループには `Map.merge` も使える。`counting()` の結果は `Long`。

#### toMap で Map に変換する

```java
record Item(int index, int score) {}
List<Item> items = List.of(new Item(1, 80), new Item(1, 95), new Item(2, 70));
Map<Integer, Integer> bestScores = items.stream().collect(Collectors.toMap(
    Item::index,
    Item::score,
    Integer::max
));
// bestScores は {1=95, 2=70}。Map の走査順は保証しない
```

重複キーがある場合、マージ関数を省略すると `IllegalStateException` になる。

配列やリストへの収集は[配列とリストを変換する](#配列とリストを変換する)を参照する。

### 終端操作ごとに Stream を生成する

```java
int[] values = {1, 2, 3};
long count = Arrays.stream(values).filter(value -> value > 0).count();
long sum = Arrays.stream(values).asLongStream().sum();
// count == 3、sum == 6
```

Stream は `count()`・`sum()`・`toArray()` などの終端操作を一度実行すると再利用できない。同じ配列から別の結果を得る場合は `Arrays.stream(values)` を再度呼び出す。

ラムダ式から参照するローカル変数は final または実質的に final である必要がある。配列の合計は `asLongStream()` で加算前に拡張する。

### `Optional`

#### `OptionalInt` などから値を取り出す

```java
OptionalInt result = stream.max();
int value = result.orElse(defaultValue);
int required = result.orElseThrow();
```

対応する型:

- `Optional<T>`
- `OptionalInt`
- `OptionalLong`
- `OptionalDouble`

#### `orElse` と `orElseGet`

| メソッド              | デフォルト値の評価             |
| --------------------- | ------------------------------ |
| `orElse(value)`       | Optionalに値があっても先に評価 |
| `orElseGet(supplier)` | Optionalが空のときだけ評価     |

作成コストが高いデフォルト値には `orElseGet` を使う。

```java
Value value = optional.orElseGet(() -> createExpensiveDefault());
```

## ビット操作

### ビット演算

```java
int and = a & b;
int or = a | b;
int xor = a ^ b;
int complement = ~a;
int leftShift = value << count;
int rightShift = value >> count;
int unsignedRightShift = value >>> count;
```

標準 API:

```java
int bitCount = Integer.bitCount(value);
int leadingZeros = Integer.numberOfLeadingZeros(value);
int trailingZeros = Integer.numberOfTrailingZeros(value);
int highestBit = Integer.highestOneBit(value);
int lowestBit = Integer.lowestOneBit(value);
String binary = Integer.toBinaryString(value);
```

`long` には対応する `Long.bitCount`、`Long.numberOfLeadingZeros` などがある。

#### k ビット目

```java
boolean set = (value & (1L << k)) != 0;
long enabled = value | (1L << k);
long disabled = value & ~(1L << k);
long toggled = value ^ (1L << k);
```

### `BitSet`

真偽値をビット単位で保持する。

```java
BitSet bits = new BitSet(size);
bits.set(index);
bits.clear(index);
bits.flip(index);
boolean set = bits.get(index);
int count = bits.cardinality();
int next = bits.nextSetBit(fromIndex);
```

集合演算:

```java
bits.and(other);
bits.or(other);
bits.xor(other);
bits.andNot(other);
```

### ビットを使う全探索

部分集合の列挙、計算量、採用条件は[アルゴリズム集の bit 全探索](algorithms.md#bit-全探索)を参照する。

## import

```java
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
```

AtCoder では必要なクラスだけを個別に import しても、`java.util.*` でまとめてもよい。
