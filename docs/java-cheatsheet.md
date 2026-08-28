# Java 21 Cheat Sheet for AtCoder

Java 21 の構文、型、標準 API、変換方法をすぐに確認するための早見表である。問題を解くためのアルゴリズムは `algorithms.md` に分離する。

## 目次

- [似たメソッド・型の違い](#似たメソッド型の違い)
- [よく使う import](#よく使う-import)
- [プリミティブ型](#プリミティブ型)
- [数値変換](#数値変換)
- [`Math`](#math)
- [配列](#配列)
- [`String`](#string)
- [`StringBuilder`](#stringbuilder)
- [`List`](#list)
- [`Set`](#set)
- [`Map`](#map)
- [キュー・スタック・両端キュー](#キュースタック両端キュー)
- [`PriorityQueue`](#priorityqueue)
- [ソートと `Comparator`](#ソートと-comparator)
- [簡単なデータ型](#簡単なデータ型)
- [`BigInteger`](#biginteger)
- [`BitSet`](#bitset)
- [`Collections`](#collections)
- [`Character`](#character)
- [ビット演算](#ビット演算)
- [`Stream` の最小限の使用例](#stream-の最小限の使用例)
- [`OptionalInt` などから値を取り出す](#optionalint-などから値を取り出す)
- [Java API の実用パターン](#java-api-の実用パターン)

## 似たメソッド・型の違い

用途を混同しやすい Java API の違いをまとめる。

### `trim` と `strip`

| メソッド  | 空白の判定                                       | Java          |
| --------- | ------------------------------------------------ | ------------- |
| `trim()`  | `U+0020` 以下の文字                              | Java 1.0 以降 |
| `strip()` | `Character.isWhitespace` が認識する Unicode 空白 | Java 11 以降  |

```java
String text = "\u2003Hello\u2003"; // EM SPACE
text.trim(); // EM SPACEは残る
text.strip(); // "Hello"
```

Java 21 では、Unicode 空白を扱える `strip()` を基本とする。先頭だけなら `stripLeading()`、末尾だけなら `stripTrailing()` を使う。

### `isEmpty` と `isBlank`

| メソッド    | `true` になる条件                |
| ----------- | -------------------------------- |
| `isEmpty()` | 長さが0                          |
| `isBlank()` | 長さが0、または全て Unicode 空白 |

```java
"   ".isEmpty(); // false
"   ".isBlank(); // true
```

### `==`、`equals`、`compareTo`

| 方法                      | 比較内容                         |
| ------------------------- | -------------------------------- |
| `first == second`         | 同じオブジェクトを参照しているか |
| `first.equals(second)`    | 内容が等しいか                   |
| `first.compareTo(second)` | 辞書順・大小関係                 |

文字列やラッパー型の内容比較には `equals` を使う。大小関係が必要なら `compareTo` を使う。

### `parseInt` と `valueOf`

| メソッド                 | 戻り値    |
| ------------------------ | --------- |
| `Integer.parseInt(text)` | `int`     |
| `Integer.valueOf(text)`  | `Integer` |

```java
int primitive = Integer.parseInt("123");
Integer boxed = Integer.valueOf("123");
```

プリミティブ値が必要なら `parseInt`、オブジェクトが必要なら `valueOf` を使う。`Long`、`Double` などにも同様のメソッドがある。

### `Arrays.asList`、`List.of`、`Stream.toList`

| 作成方法               | 要素変更 | 追加・削除 | `null`             |
| ---------------------- | -------- | ---------- | ------------------ |
| `Arrays.asList(array)` | 可       | 不可       | 可                 |
| `List.of(values...)`   | 不可     | 不可       | 不可               |
| `stream.toList()`      | 不可     | 不可       | Stream内にあれば可 |

`Arrays.asList` は元配列と要素を共有する固定長リストである。変更可能な独立リストが必要なら次を使う。

```java
List<String> mutable = new ArrayList<>(Arrays.asList(array));
```

### `List.remove(index)` と `List.remove(value)`

`List<Integer>` では整数引数がインデックスとして解釈される。

```java
list.remove(2);                  // index 2を削除
list.remove(Integer.valueOf(2)); // 値2を最初に見つけた位置から削除
```

### `getOrDefault`、`putIfAbsent`、`computeIfAbsent`

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

### `add` と `offer`、`remove` と `poll`、`element` と `peek`

キューでは、失敗時に例外を投げるメソッドと特別値を返すメソッドがある。

| 操作     | 例外を投げる | 特別値を返す              |
| -------- | ------------ | ------------------------- |
| 追加     | `add`        | `offer`（失敗時 `false`） |
| 取り出し | `remove`     | `poll`（空なら `null`）   |
| 先頭確認 | `element`    | `peek`（空なら `null`）   |

`ArrayDeque` は通常容量不足にならないため、追加では `add` と `offer` の差が現れにくい。空キューを許容する処理では `poll`、空でないことが前提なら `remove` を使う。

### `HashSet`、`LinkedHashSet`、`TreeSet`

| 型              | 順序     | 基本操作  |
| --------------- | -------- | --------- |
| `HashSet`       | 保証なし | 平均 O(1) |
| `LinkedHashSet` | 挿入順   | 平均 O(1) |
| `TreeSet`       | ソート順 | O(log N)  |

大小関係や `floor`、`ceiling` が必要なら `TreeSet`、順序が不要なら `HashSet` を基本とする。

### `HashMap`、`LinkedHashMap`、`TreeMap`

| 型              | キーの順序 | 基本操作  |
| --------------- | ---------- | --------- |
| `HashMap`       | 保証なし   | 平均 O(1) |
| `LinkedHashMap` | 挿入順     | 平均 O(1) |
| `TreeMap`       | ソート順   | O(log N)  |

キー順の走査や境界検索が必要なら `TreeMap` を使う。

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

### `Arrays.equals` と `Arrays.deepEquals`

| メソッド            | 比較対象                 |
| ------------------- | ------------------------ |
| `Arrays.equals`     | 一次元配列の各要素       |
| `Arrays.deepEquals` | 多次元配列を再帰的に比較 |

```java
Arrays.equals(firstRow, secondRow);
Arrays.deepEquals(firstGrid, secondGrid);
```

多次元配列へ `Arrays.equals` を使うと、内側の配列は参照として比較される。

### `floor`、`ceil`、`round`、`rint`

| メソッド     | 丸め方向                           | 戻り値         |
| ------------ | ---------------------------------- | -------------- |
| `Math.floor` | 負の無限大方向                     | `double`       |
| `Math.ceil`  | 正の無限大方向                     | `double`       |
| `Math.round` | 最も近い整数。中間は正の無限大方向 | `int` / `long` |
| `Math.rint`  | 最も近い整数値。中間は偶数側       | `double`       |

負数では「小数部分を捨てる」と `floor` が一致しない点に注意する。

### `orElse` と `orElseGet`

| メソッド              | デフォルト値の評価             |
| --------------------- | ------------------------------ |
| `orElse(value)`       | Optionalに値があっても先に評価 |
| `orElseGet(supplier)` | Optionalが空のときだけ評価     |

作成コストが高いデフォルト値には `orElseGet` を使う。

```java
Value value = optional.orElseGet(() -> createExpensiveDefault());
```

### `StringBuilder` と `StringBuffer`

| 型              | スレッドセーフ | 一般的な選択               |
| --------------- | -------------- | -------------------------- |
| `StringBuilder` | いいえ         | 単一スレッドで高速         |
| `StringBuffer`  | はい           | 複数スレッドで共有する場合 |

競技プログラミングは通常単一スレッドなので `StringBuilder` を使う。

## よく使う import

```java
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
```

AtCoder では必要なクラスだけを個別に import しても、`java.util.*` でまとめてもよい。

## プリミティブ型

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

### オーバーフローを避ける

演算前に `long` へ変換する。

```java
long product = (long) a * b;
```

## 数値変換

```java
int intValue = Integer.parseInt("123");
long longValue = Long.parseLong("123");
double doubleValue = Double.parseDouble("1.25");

String intText = Integer.toString(123);
String longText = Long.toString(123L);
String binary = Integer.toBinaryString(10);   // "1010"
String hex = Integer.toHexString(255);        // "ff"

int digit = Character.digit('7', 10);         // 7
char digitChar = Character.forDigit(7, 10);   // '7'
```

基数を指定した変換:

```java
int binaryValue = Integer.parseInt("1010", 2);
long hexValue = Long.parseLong("ff", 16);
String base3 = Integer.toString(10, 3);
```

## `Math`

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

## 配列

### 作成

```java
int[] values = new int[n];
long[] longs = new long[n];
boolean[] visited = new boolean[n];
int[][] grid = new int[height][width];
```

初期値は数値が `0`、`boolean` が `false`、参照型が `null` となる。

### コピー

```java
int[] copied = values.clone();
int[] resized = Arrays.copyOf(values, newLength);
int[] range = Arrays.copyOfRange(values, fromInclusive, toExclusive);
System.arraycopy(source, sourcePosition, destination, destinationPosition, length);
```

### 初期化・比較・文字列化

```java
Arrays.fill(values, -1);
boolean same = Arrays.equals(first, second);
boolean deepSame = Arrays.deepEquals(firstGrid, secondGrid);
String text = Arrays.toString(values);
String deepText = Arrays.deepToString(grid);
```

### 配列の出力と `char[]` の特別扱い

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

### ソート・検索

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

## `String`

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

### 分割・結合

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

### 文字配列との変換

```java
char[] characters = text.toCharArray();
String restored = new String(characters);
```

### 文字列比較

```java
boolean same = first.equals(second);
boolean sameIgnoringCase = first.equalsIgnoreCase(second);
int order = first.compareTo(second);
```

文字列の内容比較には `==` ではなく `equals` を使う。

## `StringBuilder`

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

## `List`

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

### 配列との変換

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

## `Set`

```java
Set<Integer> hashSet = new HashSet<>();
Set<Integer> sortedSet = new TreeSet<>();

set.add(value);
set.remove(value);
boolean contains = set.contains(value);
int size = set.size();
```

### `TreeSet` 固有の操作

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

## `Map`

```java
Map<String, Integer> map = new HashMap<>();
map.put(key, value);
Integer value = map.get(key);
int defaultValue = map.getOrDefault(key, 0);
boolean hasKey = map.containsKey(key);
map.remove(key);
int size = map.size();
```

### 頻度カウント

```java
map.merge(key, 1, Integer::sum);
```

### 値がない場合だけ作成

```java
map.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
```

### 走査

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

## キュー・スタック・両端キュー

`Deque` をキューとスタックの両方に使える。

```java
Deque<Integer> deque = new ArrayDeque<>();
```

### キューとして使う

```java
deque.addLast(value);
int first = deque.removeFirst();
Integer nullableFirst = deque.pollFirst();
Integer peek = deque.peekFirst();
```

### スタックとして使う

```java
deque.push(value);
int top = deque.pop();
Integer peek = deque.peek();
```

`remove`、`pop` は空の場合に例外を投げ、`poll` は `null` を返す。

## `PriorityQueue`

### 最小値を先頭にする

```java
PriorityQueue<Integer> queue = new PriorityQueue<>();
```

### 最大値を先頭にする

```java
PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
```

### 操作

```java
queue.add(value);
int first = queue.remove();
Integer nullableFirst = queue.poll();
Integer peek = queue.peek();
int size = queue.size();
```

## ソートと `Comparator`

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

## 簡単なデータ型

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

### `Map.Entry`

一時的な2要素の組として使える。

```java
Map.Entry<Integer, String> pair = Map.entry(1, "value");
int first = pair.getKey();
String second = pair.getValue();
```

`Map.entry` が返すエントリは変更不可である。

## `BigInteger`

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

## `BitSet`

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

## `Collections`

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

## `Character`

```java
boolean digit = Character.isDigit(character);
boolean letter = Character.isLetter(character);
boolean upper = Character.isUpperCase(character);
boolean lower = Character.isLowerCase(character);
char upperCharacter = Character.toUpperCase(character);
char lowerCharacter = Character.toLowerCase(character);
int numericValue = Character.getNumericValue(character);
```

## ビット演算

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

### k ビット目

```java
boolean set = (value & (1L << k)) != 0;
long enabled = value | (1L << k);
long disabled = value & ~(1L << k);
long toggled = value ^ (1L << k);
```

## `Stream` の最小限の使用例

```java
int sum = list.stream().mapToInt(Integer::intValue).sum();
int maximum = list.stream().mapToInt(Integer::intValue).max().orElseThrow();
long count = list.stream().filter(value -> value > 0).count();
List<Integer> sorted = list.stream().sorted().toList();
String joined = list.stream().map(String::valueOf).collect(Collectors.joining(" "));
```

競技プログラミングでは、速度やアロケーションが重要な箇所は通常のループのほうが扱いやすい。

## `OptionalInt` などから値を取り出す

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

## Java API の実用パターン

標準 API の組み合わせや、Java 固有の変換方法をまとめる。問題を解くためのアルゴリズムは [`algorithms.md`](algorithms.md) に記載する。

### 文字列の各桁を配列に変換する

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

### 各桁の配列を整数に変換する

各要素が `0` 以上 `9` 以下で、上位桁から並んでいる `int[]` を `long` へ変換する。

```java
int[] digits = {1, 2, 3, 4, 5};

long value = 0;
for (int digit : digits) {
    value = value * 10 + digit;
}

// value == 12345
```

現在の値を10倍して次の数字を加えることで、末尾へ1桁ずつ追加する。

```text
0 → 1 → 12 → 123 → 1234 → 12345
```

先頭に `0` がある場合、その情報は整数への変換時に失われる。

```java
int[] digits = {0, 0, 1, 2, 3};
// 変換結果は123
```

結果が `long` の範囲を超える可能性がある場合は、文字列や `BigInteger` を使う。オーバーフローを例外として検出するなら、次のように書ける。

```java
long value = 0;
for (int digit : digits) {
    value = Math.addExact(Math.multiplyExact(value, 10), digit);
}
```

この処理は各要素が `0` 以上 `9` 以下であることを前提とする。空配列の変換結果は `0` となる。

### `StringBuilder` で文字列を逆順にする

```java
String reversed = new StringBuilder(text).reverse().toString();
```

### 英字と0始まりの添字を変換する

```java
int lowerIndex = lowerCharacter - 'a';
char lowerCharacter = (char) ('a' + lowerIndex);

int upperIndex = upperCharacter - 'A';
char upperCharacter = (char) ('A' + upperIndex);
```

### `Stream` で配列の重複を除く

```java
int[] unique = Arrays.stream(values).distinct().toArray();
int[] sortedUnique = Arrays.stream(values).sorted().distinct().toArray();
```

### 二次元配列を同じ値で初期化する

```java
for (int[] row : grid) {
    Arrays.fill(row, -1);
}
```

### `IntStream` で整数リストを作る

```java
List<Integer> indices = IntStream.range(0, n).boxed().toList();
```

変更可能なリストが必要な場合:

```java
List<Integer> indices = IntStream.range(0, n)
    .boxed()
    .collect(Collectors.toCollection(ArrayList::new));
```

### `Math.floorMod` で剰余を0以上にする

```java
int normalized = Math.floorMod(value, mod);
long normalized = Math.floorMod(value, mod);
```

`value % mod` は `value` が負の場合に負の値を返す可能性がある。

### `Collectors.joining` で区切って出力する

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
