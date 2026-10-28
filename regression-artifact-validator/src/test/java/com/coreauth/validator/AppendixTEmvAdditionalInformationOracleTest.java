package com.coreauth.validator;

import com.coreauth.validator.canonical.AppendixTEmvAdditionalInformationOracle;
import com.coreauth.validator.canonical.AppendixTEmvAdditionalInformationOracle.Assessment;
import com.coreauth.validator.canonical.AppendixTEmvAdditionalInformationOracle.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Golden fixtures are shaped per Appendix T-1/T-2 ({@code extracted_text.txt} around lines
 * 35557-35616), cross-checked against Elements 191/192/118's own definitions (lines ~24426-24454):
 * one EMV Table Data occurrence (Indicator/Table ID {@code 001}, value {@code EMVYES}/{@code EMVNOT})
 * and one CARC occurrence (Indicator/Table ID {@code 002}, a single-character value).
 */
class AppendixTEmvAdditionalInformationOracleTest {
    private final AppendixTEmvAdditionalInformationOracle oracle = new AppendixTEmvAdditionalInformationOracle();

    private Map<String, Assessment> byId(List<Assessment> assessments) {
        return assessments.stream().collect(Collectors.toMap(Assessment::businessRequirementId, a -> a));
    }

    @Test
    void assessSectionAlwaysReturnsAllSevenBusinessRequirements() {
        List<Assessment> assessments = oracle.assessSection("001", "006", "001", "EMVYES");

        assertThat(byId(assessments).keySet()).containsExactlyInAnyOrder(
                AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT,
                AppendixTEmvAdditionalInformationOracle.BR_LENGTH_FORMAT,
                AppendixTEmvAdditionalInformationOracle.BR_TABLE_ID_MATCH,
                AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA,
                AppendixTEmvAdditionalInformationOracle.BR_CARC,
                AppendixTEmvAdditionalInformationOracle.BR_TABLE_LENGTH_FIELD,
                AppendixTEmvAdditionalInformationOracle.BR_ECHO);
    }

    // --- BR_INDICATOR_FORMAT ---

    @Test
    void acceptsIndicatorValueOfOneHundredOne() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void acceptsIndicatorValueOfOneHundredTwo() {
        Assessment assessment = byId(oracle.assessSection("002", "004", "002", "5"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsIndicatorOutsideTheTwoDefinedTables() {
        Assessment assessment = byId(oracle.assessSection("003", "004", "003", "X"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsIndicatorThatIsNotThreeNumericDigits() {
        Assessment assessment = byId(oracle.assessSection("1", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsNonNumericIndicator() {
        Assessment assessment = byId(oracle.assessSection("0A1", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_INDICATOR_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_LENGTH_FORMAT ---

    @Test
    void acceptsLengthWithinOneToNineHundredEightyFive() {
        Assessment assessment = byId(oracle.assessSection("001", "985", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_LENGTH_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsLengthOfZero() {
        Assessment assessment = byId(oracle.assessSection("001", "000", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_LENGTH_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsLengthAboveNineHundredEightyFive() {
        Assessment assessment = byId(oracle.assessSection("001", "986", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_LENGTH_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void rejectsLengthThatIsNotThreeNumericDigits() {
        Assessment assessment = byId(oracle.assessSection("001", "6", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_LENGTH_FORMAT);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_TABLE_ID_MATCH ---

    @Test
    void acceptsTableIdMatchingTheIndicator() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_TABLE_ID_MATCH);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsTableIdThatDoesNotMatchTheIndicator() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "002", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_TABLE_ID_MATCH);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_EMV_TABLE_DATA ---

    @Test
    void acceptsEmvYesValueForTableOne() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void acceptsEmvNotValueForTableOne() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVNOT"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsUnrecognizedValueForTableOne() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "MAYBE1"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void emvTableDataIsNotApplicableWhenTableIdIsNotOne() {
        Assessment assessment = byId(oracle.assessSection("002", "004", "002", "5"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_EMV_TABLE_DATA);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_CARC ---

    @Test
    void acceptsOneCharacterCarcValueForTableTwo() {
        Assessment assessment = byId(oracle.assessSection("002", "004", "002", "5"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_CARC);
        assertThat(assessment.status()).isEqualTo(Status.PASS);
    }

    @Test
    void rejectsMultiCharacterCarcValue() {
        Assessment assessment = byId(oracle.assessSection("002", "004", "002", "55"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_CARC);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    @Test
    void carcIsNotApplicableWhenTableIdIsNotTwo() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_CARC);
        assertThat(assessment.status()).isEqualTo(Status.FAIL);
    }

    // --- BR_TABLE_LENGTH_FIELD and BR_ECHO: always REVIEW_REQUIRED by design ---

    @Test
    void tableLengthFieldIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_TABLE_LENGTH_FIELD);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void echoIsAlwaysReviewRequired() {
        Assessment assessment = byId(oracle.assessSection("001", "006", "001", "EMVYES"))
                .get(AppendixTEmvAdditionalInformationOracle.BR_ECHO);
        assertThat(assessment.status()).isEqualTo(Status.REVIEW_REQUIRED);
    }

    @Test
    void tableLengthFieldAndEchoStayReviewRequiredRegardlessOfOtherFieldValidity() {
        List<Assessment> assessments = oracle.assessSection(null, null, null, null);
        Map<String, Assessment> byId = byId(assessments);
        assertThat(byId.get(AppendixTEmvAdditionalInformationOracle.BR_TABLE_LENGTH_FIELD).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
        assertThat(byId.get(AppendixTEmvAdditionalInformationOracle.BR_ECHO).status())
                .isEqualTo(Status.REVIEW_REQUIRED);
    }
}
