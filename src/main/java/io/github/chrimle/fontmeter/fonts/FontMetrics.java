package io.github.chrimle.fontmeter.fonts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FontMetrics implements IFontMetrics {

  private final ConcurrentHashMap<Integer, Map<Character, Double>> fontSizeMap;

  private FontMetrics(final int baseFontSize, final Map<Character, Double> baselineMap) {
    fontSizeMap = new ConcurrentHashMap<>();
    fontSizeMap.put(baseFontSize, baselineMap);
  }

  public static BaselineStep builder() {
    return FontMetricsBuilder.newInstance();
  }

  /**
   * <em>Builder-class</em> for {@link FontMetrics}-instances.
   *
   * @see FontMetrics#builder() To instantiate a new FontMetricsBuilder.
   * @since 0.1.0
   * @author Chrimle
   */
  public static final class FontMetricsBuilder
      implements BaselineStep, PreCalculateStep, OnDemandCalculateStep, BuildStep {

    private int baseFontSize;
    private Map<Character, Double> baselineMap;

    private FontMetricsBuilder() {}

    private static BaselineStep newInstance() {
      return new FontMetricsBuilder();
    }

    /**
     * {@inheritDoc}
     *
     * @param fontSize of the <em>baseline</em>. <strong>MUST</strong> be positive.
     * @param baselineMap of supported {@link Character}s and their respective widths when {@code
     *     fontSize} is used.
     * @return <em>this</em> builder.
     * @throws IllegalArgumentException if {@code fontSize} is negative, or zero.
     * @throws IllegalArgumentException if {@code baselineMap} is {@code null}.
     * @throws IllegalArgumentException if {@code baselineMap} is <em>empty</em>.
     * @throws IllegalArgumentException if {@code baselineMap} contains a {@code null} <em>key</em>.
     * @throws IllegalArgumentException if {@code baselineMap} contains a {@code null}
     *     <em>value</em>.
     * @since 0.1.0
     */
    @Override
    public PreCalculateStep setBaseline(
        final int fontSize, final Map<Character, Double> baselineMap)
        throws IllegalArgumentException {
      if (fontSize < 1) {
        throw new IllegalArgumentException("`fontSize` MUST be positive");
      }
      if (baselineMap == null) {
        throw new IllegalArgumentException("`baselineMap` MUST not be `null`");
      }
      if (baselineMap.isEmpty()) {
        throw new IllegalArgumentException("`baselineMap` MUST not be empty");
      }
      this.baseFontSize = fontSize;
      this.baselineMap = Map.copyOf(baselineMap);
      return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return the new {@link IFontMetrics}-instance.
     * @since 0.1.0
     */
    @Override
    public IFontMetrics build() {
      return new FontMetrics(baseFontSize, baselineMap);
    }
  }
}
