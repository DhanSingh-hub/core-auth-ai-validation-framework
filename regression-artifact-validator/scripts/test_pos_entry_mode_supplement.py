import json
import importlib.util
import tempfile
import unittest
from pathlib import Path

_SPEC = importlib.util.spec_from_file_location(
    "build_pos_entry_mode_supplement", Path(__file__).with_name("build-pos-entry-mode-supplement.py")
)
builder = importlib.util.module_from_spec(_SPEC)
_SPEC.loader.exec_module(builder)


class PosEntryModeSupplementTest(unittest.TestCase):
    def test_build_produces_expected_counts(self):
        package, evidence = builder.build(
            builder.DEFAULT_SOURCE, builder.DEFAULT_COMPLETE, builder.DEFAULT_ANNEXURE
        )
        self.assertEqual(package["manifest"]["counts"]["businessRequirements"], 50)
        self.assertEqual(package["manifest"]["counts"]["testScenarios"], 61)
        self.assertEqual(package["manifest"]["counts"]["testCases"], 373)
        self.assertEqual(package["manifest"]["counts"]["testData"], 373)
        self.assertGreater(len(evidence), 0)

    def test_old_token_td_is_marked_defective_and_superseded(self):
        package, _ = builder.build(
            builder.DEFAULT_SOURCE, builder.DEFAULT_COMPLETE, builder.DEFAULT_ANNEXURE
        )
        overlay = package["supersessionOverlay"]["supersededTestData"][0]
        self.assertEqual(overlay["id"], "TD-SEG100-APPO-ENTRY-MODE")
        self.assertEqual(overlay["codeRecognitionOfOldValue"], "INVALID_FORMAT")
        self.assertEqual(overlay["oracleVerdictsOnOldPayload"]["BR-POSEM-001"], "FAIL")
        self.assertEqual(overlay["supersededBy"], "TD-POSEM-AMEX-TOKEN-R01")

    def test_check_detects_stale_and_current_outputs(self):
        package, evidence = builder.build(
            builder.DEFAULT_SOURCE, builder.DEFAULT_COMPLETE, builder.DEFAULT_ANNEXURE
        )
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp)
            builder.write_outputs(output, package, evidence)
            self.assertEqual(builder.check_outputs(output, package, evidence), 0)
            data = json.loads((output / "pos-entry-mode-supplement.json").read_text(encoding="utf-8"))
            data["manifest"]["packageId"] = "BROKEN"
            (output / "pos-entry-mode-supplement.json").write_text(
                json.dumps(data, indent=2, ensure_ascii=False) + "\n", encoding="utf-8"
            )
            self.assertEqual(builder.check_outputs(output, package, evidence), 1)


if __name__ == "__main__":
    unittest.main()
