package io.github.chrimle.fontmeter.fonts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FontMetricsTest {

  @Nested
  class FontMetricsBuilderTests {

    @Test
    void testInstantiation() {
      final var fontMetrics =
          FontMetrics.builder()
              .setBaseline(7, Map.of('a', 42d))
              .skipPreCalculation()
              .disableOnDemandCalculations()
              .build();
      assertNotNull(fontMetrics);
    }

    @Nested
    class BaselineStepTests {

      @Nested
      class SetBaselineTests {

        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -42, -1, 0})
        void testInvalidFontSize(final int fontSize) {
          final var builder = FontMetrics.builder();
          final var baselineMap = Map.of('a', 42d);
          final var exception =
              assertThrows(
                  IllegalArgumentException.class, () -> builder.setBaseline(fontSize, baselineMap));
          assertEquals("`fontSize` MUST be positive", exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 42, Integer.MAX_VALUE})
        void testValidFontSize(final int fontSize) {
          final var builder = FontMetrics.builder();
          final var baselineMap = Map.of('a', 42d);
          assertDoesNotThrow(() -> builder.setBaseline(fontSize, baselineMap));
        }

        @Test
        void testNullBaselineMapThrows() {
          final var builder = FontMetrics.builder();
          final var fontSize = 42;
          final var exception =
              assertThrows(
                  IllegalArgumentException.class, () -> builder.setBaseline(fontSize, null));
          assertEquals("`baselineMap` MUST not be `null`", exception.getMessage());
        }

        @Test
        void testEmptyBaselineMapThrows() {
          final var builder = FontMetrics.builder();
          final var fontSize = 42;
          final Map<Character, Double> baselineMap = Map.of();
          final var exception =
              assertThrows(
                  IllegalArgumentException.class, () -> builder.setBaseline(fontSize, baselineMap));
          assertEquals("`baselineMap` MUST not be empty", exception.getMessage());
        }
      }
    }
  }

  @Nested
  class GetNullableCharacterWidthTests {

    private final IFontMetrics fontMetrics =
        FontMetrics.builder()
            .setBaseline(7, Map.of('x', 42d))
            .skipPreCalculation()
            .disableOnDemandCalculations()
            .build();

    @Nested
    class CharacterTests {
      @Test
      void testNullCharacterThrowsNPE() {
        assertThrows(
            NullPointerException.class, () -> fontMetrics.getNullableCharacterWidth(null, 7));
      }

      @Test
      void testUnsupportedCharacterReturnsNull() {
        assertNull(fontMetrics.getNullableCharacterWidth('z', 7));
      }

      @Test
      void testSupportedCharacterReturnsExpectedWidth() {
        assertEquals(42d, fontMetrics.getNullableCharacterWidth('x', 7));
      }
    }

    @Nested
    class FontSizeTests {
      @ParameterizedTest
      @ValueSource(ints = {Integer.MIN_VALUE, -42, -7, -1})
      void testNegativeFontSizeThrowsIAE(final int fontSize) {
        assertThrows(
            IllegalArgumentException.class,
            () -> fontMetrics.getNullableCharacterWidth('x', fontSize));
      }

      @Test
      void testZeroFontSizeThrowsIAE() {
        assertThrows(
            IllegalArgumentException.class, () -> fontMetrics.getNullableCharacterWidth('x', 0));
      }

      @ParameterizedTest
      @ValueSource(ints = {1, 3, 7, 11, 42, Integer.MAX_VALUE})
      void testPositiveFontSizeDoesNotThrow(final int fontSize) {
        assertDoesNotThrow(() -> fontMetrics.getNullableCharacterWidth('x', fontSize));
      }

      @Test
      void testUnsupportedFontSizeReturnsNull() {
        assertNull(fontMetrics.getNullableCharacterWidth('x', 11));
      }

      @Test
      void testSupportedFontSizeReturnsExpectedWidth() {
        assertEquals(42d, fontMetrics.getNullableCharacterWidth('x', 7));
      }
    }
  }
}
