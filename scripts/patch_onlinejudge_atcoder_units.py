"""Patch online-judge-api-client to parse AtCoder's binary memory units."""

from pathlib import Path

import onlinejudge.service.atcoder

source_path = Path(onlinejudge.service.atcoder.__file__)
source = source_path.read_text(encoding="utf-8")

if "(KB|MB|KiB|MiB)" in source:
    print(f"AtCoder memory-unit patch already applied: {source_path}")
    raise SystemExit(0)

old_table_parser = """        if tds[3].text.endswith(' KB'):
            memory_limit_byte = int(float(utils.remove_suffix(tds[3].text, ' KB')) * 1000)
        elif tds[3].text.endswith(' MB'):
            memory_limit_byte = int(float(utils.remove_suffix(tds[3].text, ' MB')) * 1000 * 1000)  # TODO: confirm this is MB truly, not MiB
        else:
            assert False
"""
new_table_parser = """        parsed_memory_limit = re.fullmatch(r'([0-9.]+) (KB|MB|KiB|MiB)', tds[3].text)
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

old_detail_parser = """        parsed_memory_limit = re.search(r'^(メモリ制限|Memory Limit): ([0-9.]+) (KB|MB)', memory_limit)
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
new_detail_parser = """        parsed_memory_limit = re.search(r'^(メモリ制限|Memory Limit): ([0-9.]+) (KB|MB|KiB|MiB)', memory_limit)
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

if old_table_parser not in source or old_detail_parser not in source:
    raise RuntimeError(
        f"Unsupported online-judge-api-client source; patch was not applied: {source_path}"
    )

source = source.replace(old_table_parser, new_table_parser, 1)
source = source.replace(old_detail_parser, new_detail_parser, 1)
_ = source_path.write_text(source, encoding="utf-8")
print(f"Patched AtCoder memory units in: {source_path}")
