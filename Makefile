ATCODER_JAVA_LANGUAGE_ID := 5005
TEMPLATE_DIR := /root/.config/atcoder-cli-nodejs/java
ARGUMENTS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
TARGET_DIR := $(firstword $(ARGUMENTS))
CONTEST_DIRS := $(patsubst %/contest.acc.json,%,$(wildcard */contest.acc.json))

.PHONY: help validate test submit template-sync clean $(ARGUMENTS)

help:
	@echo "AtCoder Java commands"
	@echo "  make test abc380/a"
	@echo "  make submit abc380/a"
	@echo "  make template-sync"
	@echo "  make clean"

validate:
	@test "$(words $(ARGUMENTS))" -le 1 || (echo "Error: Specify only one problem directory."; exit 1)
	@test -n "$(TARGET_DIR)" || (echo "Error: Specify a problem directory (e.g. abc380/a)."; exit 1)
	@test -d "$(TARGET_DIR)" || (echo "Error: Directory '$(TARGET_DIR)' does not exist."; exit 1)

test: validate
	cd "$(TARGET_DIR)" && oj t -c "java Main.java" -d ./test/

submit: validate
	cd "$(TARGET_DIR)" && acc submit Main.java -- -l "$(ATCODER_JAVA_LANGUAGE_ID)"

template-sync:
	@install -d "$(TEMPLATE_DIR)"
	@cp template/Main.java template/template.json "$(TEMPLATE_DIR)/"
	@echo "Java template synchronized."

clean:
	@if [ -z "$(strip $(CONTEST_DIRS))" ]; then \
		echo "No contest directories to remove."; \
	else \
		echo "Removing contest directories: $(CONTEST_DIRS)"; \
		rm -rf -- $(CONTEST_DIRS); \
		echo "Contest directories removed."; \
	fi

$(ARGUMENTS):
	@:
