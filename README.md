# AtCoder Java

AtCoder に Java で参加するための Dev Container 対応開発環境である。

Dev Container に対応したエディタでプロジェクトを開くと、Java、atcoder-cli、online-judge-tools など、競技プログラミングに必要なツールをコンテナ内で利用できる。ホスト環境へ個別にツールをインストールする必要はない。

## 必要なもの

- Dev Container に対応したエディタ
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)などのコンテナ実行環境
- Git

## セットアップ

### 1. プロジェクトをクローンする

```bash
git clone https://github.com/willowtown0576/atcoder-java.git
```

### 2. Dev Container で開く

1. Dev Container に対応したエディタで、クローンした `atcoder-java` ディレクトリを開く。
2. エディタの Dev Container メニューまたはコマンドパレットから、コンテナで開く操作を選択する。
3. 初回は開発用イメージが自動的にビルドされる。ビルド完了後、ワークスペースがコンテナ内の `/workspace` として開かれる。

以降、この README に記載するコマンドは、特記がない限り**エディタ上のコンテナ内ターミナル**で実行する。

## AtCoder へのログイン

コンテナ内ターミナルで `aclogin` を実行する。

```bash
aclogin
```

ログインには AtCoder の `REVEL_SESSION` Cookie が必要である。取得方法は [aclogin のドキュメント](https://github.com/key-moon/aclogin)を参照すること。

ログイン情報と atcoder-cli の設定は Docker ボリューム `atcoder-config` に保存される。コンテナを再作成しても保持されるが、関連ボリュームを削除すると失われる。

## 基本的な使い方

### 1. 問題をダウンロードする

コンテスト ID を指定して問題をダウンロードする。

```bash
acc new abc380
```

問題ごとにディレクトリと `Main.java`、サンプルケースが作成される。

```text
abc380/
└── a/
    ├── Main.java
    └── test/
```

`abc380/a/Main.java` をエディタで開いて実装する。

### 2. サンプルテストを実行する

問題ディレクトリを指定する。

```bash
make test abc380/a
```

`online-judge-tools` が `abc380/a/test/` にあるサンプルケースを使用して `Main.java` を実行する。

問題ディレクトリの指定がない場合や、対象ディレクトリが存在しない場合は、テストを開始せずエラーを表示する。

### 3. 提出する

```bash
make submit abc380/a
```

`abc380/a/Main.java` を AtCoder 言語 ID `5005` で提出する。提出前にサンプルテストが成功することを確認すること。

別の言語 ID を使用する場合は、`LANGUAGE_ID` を指定できる。

```bash
make submit abc380/a LANGUAGE_ID=<言語ID>
```

### 4. コンテストディレクトリを削除する

```bash
make clean
```

プロジェクト直下にあり、atcoder-cli が生成する `contest.acc.json` を含むディレクトリをコンテストディレクトリと判定して削除する。ディレクトリ名には依存しないため、ABC、ARC、AGC、AHC などをまとめて削除できる。

この操作は対象ディレクトリ内の `Main.java` やサンプルケースも削除する。未コミットのファイルは復元できないため、必要なコードをコミットしたことを確認してから実行すること。

### Make ターゲットを確認する

```bash
make help
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
├── .devcontainer/
│   └── devcontainer.json  # Dev Container 設定
├── Dockerfile             # Java 21 開発イメージ
├── docker-compose.yml     # コンテナと永続ボリュームの設定
├── Makefile               # テスト・提出・同期・クリーンコマンド
├── template/
│   ├── Main.java          # Javaテンプレート本体
│   └── template.json      # atcoder-cliテンプレート設定
└── abc380/                # acc new で作成されるコンテストディレクトリ
    └── a/
        ├── Main.java
        └── test/
```

## Java テンプレート

Java テンプレートは Dockerfile ではなく、プロジェクト内の `template/` で管理する。

- `template/Main.java`: `acc new` で生成する Java コード
- `template/template.json`: atcoder-cli のテンプレート設定

スニペット、入力補助クラス、定数、ユーティリティメソッドなどを追加する場合は、`template/Main.java` を直接編集する。編集後、次のコマンドで atcoder-cli の設定ディレクトリへ反映する。

```bash
make template-sync
```

同期後に実行した `acc new` から新しいテンプレートが使われる。すでに作成済みの問題にある `Main.java` は変更されない。

Dev Container の新規作成時にも `postCreateCommand` から自動同期されるため、通常は次の流れとなる。

1. `template/Main.java` を編集する。
2. `make template-sync` を実行する。
3. `acc new <コンテストID>` で問題を作成する。

テンプレート更新だけで Dev Container を再ビルドする必要はない。

## 参考リンク

- [Development Containers](https://containers.dev/)
- [AtCoder](https://atcoder.jp/)
- [atcoder-cli](https://github.com/Tatamo/atcoder-cli)
- [online-judge-tools](https://github.com/online-judge-tools/oj)
- [aclogin](https://github.com/key-moon/aclogin)
