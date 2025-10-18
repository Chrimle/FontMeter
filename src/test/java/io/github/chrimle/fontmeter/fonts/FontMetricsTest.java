package io.github.chrimle.fontmeter.fonts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FontMetricsTest {

  @Nested
  class FontMetricsBuilderTests {

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
      }
    }
  }
}
