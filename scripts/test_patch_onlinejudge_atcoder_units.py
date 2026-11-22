"""互換パッチとインストール済みライブラリの解析処理を検証する。"""

from pathlib import Path
import types
import unittest

import bs4
import onlinejudge.service.atcoder as atcoder
import requests

from patch_onlinejudge_atcoder_units import (
    NEW_DETAIL_PARSER,
    NEW_TABLE_PARSER,
    OLD_DETAIL_PARSER,
    OLD_TABLE_PARSER,
    patch_source,
)


class MemoryUnitPatchTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        source = Path(atcoder.__file__).read_text(encoding="utf-8")
        cls.patched_source = patch_source(source)
        cls.patched = types.ModuleType(atcoder.__name__)
        exec(compile(cls.patched_source, atcoder.__file__, "exec"), cls.patched.__dict__)

    def test_reapplication(self):
        self.assertEqual(patch_source(self.patched_source), self.patched_source)

    def test_partial_patch(self):
        expected = NEW_TABLE_PARSER + NEW_DETAIL_PARSER
        self.assertEqual(patch_source(NEW_TABLE_PARSER + OLD_DETAIL_PARSER), expected)
        self.assertEqual(patch_source(OLD_TABLE_PARSER + NEW_DETAIL_PARSER), expected)

    def test_unknown_or_ambiguous_source(self):
        for source in ("", OLD_TABLE_PARSER, OLD_TABLE_PARSER * 2 + OLD_DETAIL_PARSER,
                       OLD_TABLE_PARSER + NEW_TABLE_PARSER + OLD_DETAIL_PARSER):
            with self.subTest(source=source), self.assertRaises(RuntimeError):
                patch_source(source)

    def test_memory_units_in_both_parsers(self):
        problem = self.patched.AtCoderProblem.from_url(
            "https://atcoder.jp/contests/abc485/tasks/abc485_a"
        )
        for unit, factor in (("KB", 1000), ("MB", 1000**2),
                             ("KiB", 1024), ("MiB", 1024**2)):
            with self.subTest(unit=unit):
                row = bs4.BeautifulSoup(
                    '<tr><td>A</td><td><a href="/contests/abc485/tasks/abc485_a">'
                    f'A</a></td><td>2 sec</td><td>1.5 {unit}</td></tr>', "html.parser"
                ).tr
                response = requests.Response()
                response._content = b""
                data = self.patched.AtCoderProblemData._from_table_row(
                    row, session=None, response=response, timestamp=None
                )
                self.assertEqual(data.memory_limit_byte, int(1.5 * factor))
                for label in ("Memory Limit", "メモリ制限"):
                    html = (
                        '<span class="h2">A - Test</span>'
                        f'<p>Time Limit: 2 sec / {label}: 1.5 {unit}</p>'
                    ).encode()
                    data = self.patched.AtCoderProblemData._from_html(html, problem=problem)
                    self.assertEqual(data.memory_limit_byte, int(1.5 * factor))


if __name__ == "__main__":
    unittest.main()
