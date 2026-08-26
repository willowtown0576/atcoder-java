CONTEST ?=
PROBLEM ?=
LANGUAGE_ID ?=5005
TARGET_DIR = $(CONTEST)/$(PROBLEM)

.PHONY: help validate test submit

help:
	@echo "AtCoder Java commands"
	@echo "  make test CONTEST=abc380 PROBLEM=a"
	@echo "  make submit CONTEST=abc380 PROBLEM=a"

validate:
	@test -n "$(CONTEST)" || (echo "エラー: CONTESTを指定してください（例: CONTEST=abc380）"; exit 1)
	@test -n "$(PROBLEM)" || (echo "エラー: PROBLEMを指定してください（例: PROBLEM=a）"; exit 1)
	@test -d "$(TARGET_DIR)" || (echo "エラー: ディレクトリ '$(TARGET_DIR)' は存在しません"; exit 1)

test: validate
	cd "$(TARGET_DIR)" && oj t -c "java Main.java" -d ./test/

submit: validate
	cd "$(TARGET_DIR)" && acc submit Main.java -- -l "$(LANGUAGE_ID)"
