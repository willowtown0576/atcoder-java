FROM eclipse-temurin:24.0.2_12-jdk-noble

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


# Python 3.12と仮想環境のセットアップ
RUN apt-get update && apt-get install -y \
    python3-venv \
    && rm -rf /var/lib/apt/lists/* \
    && python3 -m venv /opt/atcoder-venv
ENV PATH="/opt/atcoder-venv/bin:${PATH}"

# Node.js 18.xのインストール
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# 作業ディレクトリの設定
WORKDIR /workspace

# atcoder-cliのインストール
RUN npm install -g atcoder-cli

# online-judge-toolsとacloginのインストール
# online-judge-tools が使う distutils を setuptools で補う
RUN python -m pip install setuptools online-judge-tools aclogin

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
