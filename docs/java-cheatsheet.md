# Java 21 Cheat Sheet for AtCoder

Java の標準 API、型、変換方法と、短い定番処理をすぐに確認するための早見表である。`template/Main.java` に実装済みのアルゴリズムは重複して扱わない。

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

### ソート・検索

```java
Arrays.sort(values);
Arrays.sort(objects, comparator);
int index = Arrays.binarySearch(values, key);
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

## 頻出コードパターン

`template/Main.java` にない、競技プログラミングで頻出するコードパターンをまとめる。

### 各桁の数字の和

文字列から求める場合:

```java
int digitSum = text.chars().map(character -> character - '0').sum();
```

整数から求める場合:

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

### 10進数の桁数

```java
int digits = Long.toString(Math.abs(value)).length();
```

`Long.MIN_VALUE` を含む場合:

```java
int digits = Long.toString(value).replace("-", "").length();
```

### 各桁を配列にする

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

### 文字列が回文か判定する

```java
boolean palindrome = text.contentEquals(new StringBuilder(text).reverse());
```

### 文字列を逆順にする

```java
String reversed = new StringBuilder(text).reverse().toString();
```

### 英小文字と0始まりの添字を変換する

```java
int index = character - 'a';
char character = (char) ('a' + index);
```

英大文字の場合:

```java
int index = character - 'A';
char character = (char) ('A' + index);
```

### 英小文字の出現回数を数える

```java
int[] frequencies = new int[26];
for (char character : text.toCharArray()) {
    frequencies[character - 'a']++;
}
```

### 配列の重複を除く

ソート順を維持する必要がない場合:

```java
int[] unique = Arrays.stream(values).distinct().toArray();
```

ソート済みの一意な値が必要な場合:

```java
int[] sortedUnique = Arrays.stream(values).sorted().distinct().toArray();
```

### 二次元配列を同じ値で初期化する

```java
for (int[] row : grid) {
    Arrays.fill(row, -1);
}
```

### プリミティブ配列を降順にする

`Arrays.sort` はプリミティブ配列に `Comparator` を指定できないため、昇順ソート後に反転する。

```java
Arrays.sort(values);
for (int left = 0, right = values.length - 1; left < right; left++, right--) {
    int temporary = values[left];
    values[left] = values[right];
    values[right] = temporary;
}
```

### 0からn-1までの整数リストを作る

```java
List<Integer> indices = IntStream.range(0, n).boxed().toList();
```

変更可能なリストが必要な場合:

```java
List<Integer> indices = IntStream.range(0, n)
    .boxed()
    .collect(Collectors.toCollection(ArrayList::new));
```

### 全ての部分集合をビットで列挙する

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

### あるビット集合の部分集合を列挙する

```java
for (long subset = mask; subset > 0; subset = (subset - 1) & mask) {
    // 空集合以外のsubset
}
```

空集合も処理する場合はループ後に別途処理する。

### 値を0以上の剰余に正規化する

```java
int normalized = Math.floorMod(value, mod);
long normalized = Math.floorMod(value, mod);
```

`value % mod` は `value` が負の場合に負の値を返す可能性がある。

### 区切り文字付きでコレクションを出力する

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
