"""AtCoder の KiB/MiB 表記に online-judge-api-client を対応させる互換パッチ。

10.10.1 は KB/MB しか解析できず、問題一覧・問題詳細の取得時に
AssertionError になる。インストール済みの atcoder.py の2箇所を置換し、
KB/MB は従来の1000進、KiB/MiB は1024進でバイト数に変換する。

Docker ビルド時に実行する。手動適用はコンテナ内で
python scripts/patch_onlinejudge_atcoder_units.py を実行する。
適用済みの箇所は維持し、想定外のソースには書き込まず失敗する。
依存ライブラリが両方の解析箇所に対応したら、このパッチと Dockerfile の適用処理を削除する。
"""

from pathlib import Path

OLD_TABLE_PARSER = """\
        if tds[3].text.endswith(' KB'):
            memory_limit_byte = int(float(utils.remove_suffix(tds[3].text, ' KB')) * 1000)
        elif tds[3].text.endswith(' MB'):
            memory_limit_byte = int(float(utils.remove_suffix(tds[3].text, ' MB')) * 1000 * 1000)  # TODO: confirm this is MB truly, not MiB
        else:
            assert False
"""
NEW_TABLE_PARSER = """\
        parsed_memory_limit = re.fullmatch(r'([0-9.]+) (KB|MB|KiB|MiB)', tds[3].text)
        assert parsed_memory_limit
        memory_limit_value = float(parsed_memory_limit.group(1))
        memory_limit_unit = parsed_memory_limit.group(2)
        memory_limit_factors = {
            'KB': 1000,
            'MB': 1000 * 1000,
            'KiB': 1024,
            'MiB': 1024 * 1024,
        }
        memory_limit_byte = int(memory_limit_value * memory_limit_factors[memory_limit_unit])
"""

OLD_DETAIL_PARSER = """\
        parsed_memory_limit = re.search(r'^(メモリ制限|Memory Limit): ([0-9.]+) (KB|MB)', memory_limit)
        assert parsed_memory_limit

        memory_limit_value = parsed_memory_limit.group(2)
        memory_limit_unit = parsed_memory_limit.group(3)
        if memory_limit_unit == 'KB':
            memory_limit_byte = int(float(memory_limit_value) * 1000)
        elif memory_limit_unit == 'MB':
            memory_limit_byte = int(float(memory_limit_value) * 1000 * 1000)
        else:
            assert False
"""
NEW_DETAIL_PARSER = """\
        parsed_memory_limit = re.search(r'^(メモリ制限|Memory Limit): ([0-9.]+) (KB|MB|KiB|MiB)', memory_limit)
        assert parsed_memory_limit

        memory_limit_value = float(parsed_memory_limit.group(2))
        memory_limit_unit = parsed_memory_limit.group(3)
        memory_limit_factors = {
            'KB': 1000,
            'MB': 1000 * 1000,
            'KiB': 1024,
            'MiB': 1024 * 1024,
        }
        memory_limit_byte = int(memory_limit_value * memory_limit_factors[memory_limit_unit])
"""


def patch_source(source: str) -> str:
    """2箇所を検証して置換する。すべての検証が通ってから呼び出し元が保存する。"""
    # shortcut: 10.10.1 のソース構造に依存するため、依存更新時は適用可否を再確認する。
    for old, new in (
        (OLD_TABLE_PARSER, NEW_TABLE_PARSER),
        (OLD_DETAIL_PARSER, NEW_DETAIL_PARSER),
    ):
        if source.count(old) + source.count(new) != 1:
            raise RuntimeError(
                "Unsupported online-judge-api-client source; patch was not applied."
            )
        source = source.replace(old, new, 1)
    return source


def main() -> None:
    import onlinejudge.service.atcoder

    source_path = Path(onlinejudge.service.atcoder.__file__)
    source = source_path.read_text(encoding="utf-8")
    patched = patch_source(source)
    if patched == source:
        print(f"AtCoder memory-unit patch already applied: {source_path}")
        return
    source_path.write_text(patched, encoding="utf-8")
    print(f"Patched AtCoder memory units in: {source_path}")


if __name__ == "__main__":
    main()
