package io.github.chrimle.fontmeter.fonts;

import java.util.Map;

/**
 * Just an abstraction-layer for {@link FontMetrics}. Contains the interfaces for the {@link
 * FontMetrics}-builder class.
 *
 * @since 0.1.0
 * @author Chrimle
 */
public sealed interface IFontMetrics permits FontMetrics {

  /**
   * The <em>Builder</em>-step for configuring the <em>baseline</em> for {@link IFontMetrics}. The
   * <em>baseline</em> is a {@link Map} of supported {@link Character}s and their respective
   * <em>widths</em> (in points {@code pt}).
   *
   * <p>If <em>"pre-calculations"</em> and/or <em>"on-demand calculations"</em> is
   * <strong>enabled</strong>, this <em>baseline</em> will be used to compute the {@link
   * Character}-widths with other <em>font-sizes</em>.
   *
   * @since 0.1.0
   * @author Chrimle
   */
  interface BaselineStep {
    /**
     * Sets the <em>baseline</em>-map of supported {@link Character}s and their respective widths
     * (<em>in points</em> {@code pt}).
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
    PreCalculateStep setBaseline(final int fontSize, final Map<Character, Double> baselineMap)
        throws IllegalArgumentException;
  }

  interface PreCalculateStep {
    OnDemandCalculateStep skipPreCalculation();
  }

  interface OnDemandCalculateStep {
    BuildStep disableOnDemandCalculations();

    BuildStep enableLimitedOnDemandCalculations(final int limit);

    BuildStep enableUnlimitedOnDemandCalculations();
  }

  interface BuildStep {
    IFontMetrics build();
  }
}
