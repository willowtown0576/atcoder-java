FROM eclipse-temurin:21-jdk-jammy

# 環境変数設定
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Tokyo

# 基本パッケージのインストール
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

# atcoder-cli用Javaテンプレートをコピー
COPY template/ /root/.config/atcoder-cli-nodejs/java/

# atcoder-cliのデフォルト設定
RUN acc config default-task-choice all
RUN acc config default-template java

# Makeコマンドをコピー
COPY Makefile /workspace/Makefile

# 完了メッセージ
RUN echo "AtCoder Java Environment Ready!"


# デフォルトコマンド
CMD ["/bin/bash"]
