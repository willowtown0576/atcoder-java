# AtCoder Java

AtCoder に Java で参加するための、Docker ベースの開発環境です。エディタには Zed を使用し、コンパイル・サンプルテスト・提出はコンテナ内で実行します。

## 必要なもの

- [Zed](https://zed.dev/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## セットアップ

リポジトリをクローンし、ディレクトリを Zed で開きます。

```bash
git clone https://github.com/willowtown0576/atcoder-java.git
cd atcoder-java
zed .
```

コンテナをビルドして起動します。

```bash
docker compose up -d --build
```

以降の操作はコンテナ内で行います。

```bash
docker compose exec atcoder-java bash
```

プロジェクトはコンテナの `/workspace` にマウントされるため、Zed で編集した内容がそのまま反映されます。

## AtCoder へのログイン

コンテナ内で `aclogin` を実行します。

```bash
aclogin
```

ログインには AtCoder の `REVEL_SESSION` Cookie が必要です。取得方法は [aclogin のドキュメント](https://github.com/key-moon/aclogin)を参照してください。

ログイン情報と atcoder-cli の設定は Docker ボリューム `atcoder-config` に保存されます。コンテナを再作成しても保持されますが、ボリュームを削除すると失われます。

## 基本的な使い方

### 1. 問題をダウンロードする

コンテナ内でコンテスト ID を指定します。

```bash
acc new abc380
```

ダウンロード後は、たとえば `abc380/a/Main.java` を Zed で編集します。

### 2. サンプルテストを実行する

```bash
make test CONTEST=abc380 PROBLEM=a
```

`online-judge-tools` により `abc380/a/test/` のサンプルケースを実行します。

### 3. 提出する

```bash
make submit CONTEST=abc380 PROBLEM=a
```

`Main.java` を AtCoder 言語 ID `5005` で提出します。別の言語 ID を使用する場合は、`LANGUAGE_ID` を指定できます。

```bash
make submit CONTEST=abc380 PROBLEM=a LANGUAGE_ID=<言語ID>
```

提出前にサンプルテストが成功することを確認してください。

### Make ターゲットを確認する

```bash
make help
```

`CONTEST` または `PROBLEM` を省略した場合は、実行せずに指定方法を表示します。

## コンテナ操作

以下はホスト側のターミナルで実行します。

```bash
# 起動
docker compose up -d

# 再ビルドして起動
docker compose up -d --build

# コンテナに入る
docker compose exec atcoder-java bash

# 停止・削除
docker compose down

# キャッシュを使わずに再ビルド
docker compose build --no-cache
```

## 開発環境

| ツール             | バージョン・用途                    |
| ------------------ | ----------------------------------- |
| Java               | Eclipse Temurin OpenJDK 21 LTS      |
| Python             | 3.10（online-judge-tools、aclogin） |
| Node.js            | 18.x（atcoder-cli）                 |
| atcoder-cli        | 問題のダウンロード・提出            |
| online-judge-tools | サンプルテストの実行                |
| GNU Make           | テスト・提出コマンドの統一          |

## プロジェクト構成

```text
atcoder-java/
├── .devcontainer/        # Dev Container 共通設定
├── Dockerfile            # Java 21 開発イメージ
├── docker-compose.yml    # コンテナ・ボリューム設定
├── Makefile              # テスト・提出用コマンド
└── abc380/               # acc new で作成されるコンテストディレクトリ
    └── a/
        ├── Main.java
        └── test/
```

## Java テンプレート

`acc new` で作成される `Main.java` の初期内容です。

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        sc.close();
    }
}
```

## トラブルシューティング

### `make` や Java コマンドが見つからない

コマンドをホスト側ではなく、コンテナ内で実行しているか確認してください。

```bash
docker compose exec atcoder-java bash
java -version
make help
```

### コンテナを作り直したい

```bash
docker compose down
docker compose up -d --build
```

### AtCoder のログイン情報も初期化したい

次の操作は `atcoder-config` ボリュームを削除するため、保存済みのログイン情報も失われます。

```bash
docker compose down -v
docker compose up -d --build
```

## 参考リンク

- [AtCoder](https://atcoder.jp/)
- [atcoder-cli](https://github.com/Tatamo/atcoder-cli)
- [online-judge-tools](https://github.com/online-judge-tools/oj)
- [aclogin](https://github.com/key-moon/aclogin)
- [Zed](https://zed.dev/)
