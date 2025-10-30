FROM ubuntu:22.04

# 環境変数設定
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Tokyo

# 基本パッケージとJavaのインストール
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    build-essential \
    software-properties-common \
    ca-certificates \
    gnupg \
    lsb-release \
    tzdata \
    && rm -rf /var/lib/apt/lists/*

# OpenJDK 17のインストール (AtCoderでサポートされているバージョン)
RUN apt-get update && apt-get install -y openjdk-17-jdk && rm -rf /var/lib/apt/lists/* && \
    # クロスプラットフォーム対応: 実際のJavaパスから固定パスへのシンボリックリンク作成
    JAVA_PATH=$(find /usr/lib/jvm -name "java-17-openjdk*" -type d | head -1) && \
    ln -sf "$JAVA_PATH" /usr/lib/jvm/java-17-openjdk

# Python 3.10のインストール (競技プログラミング用最小構成)
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    && rm -rf /var/lib/apt/lists/*

# Node.js 18.xのインストール
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# 作業ディレクトリの設定
WORKDIR /workspace

# atcoder-cliのインストール
RUN npm install -g atcoder-cli

# online-judge-toolsのインストール
RUN pip3 install online-judge-tools

# acloginのインストール
RUN pip3 install aclogin

# 必要なディレクトリを作成
RUN mkdir -p /root/.config/atcoder-cli-nodejs

# テンプレート設定用のディレクトリを作成
RUN mkdir -p /root/.config/atcoder-cli-nodejs/java

# テンプレートファイルの作成
COPY <<EOF /root/.config/atcoder-cli-nodejs/java/template.json
{
  "task":{
    "program": ["Main.java"],
    "submit": "Main.java",
    "testdir": "test"
  }
}
EOF

# Main.javaテンプレートの作成
COPY <<EOF /root/.config/atcoder-cli-nodejs/java/Main.java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);



        sc.close();
    }
}
EOF

# atcoder-cliのデフォルト設定
RUN acc config default-task-choice all
RUN acc config default-template java

# プロジェクトファイルをコピー
COPY test /workspace/test
COPY submit /workspace/submit

# 実行権限を付与
RUN chmod +x /workspace/test /workspace/submit
# 完了メッセージ
RUN echo "AtCoder Java Environment Ready!"

# 環境変数の設定
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

# デフォルトコマンド
CMD ["/bin/bash"]
