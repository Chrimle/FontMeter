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

  /**
   * The <em>Builder</em>-step for configuring <em>"pre-calculating"</em> widths. Effectively,
   * allowing {@link Character}-widths to be calculated <em>ahead-of-time</em>, before the {@link
   * IFontMetrics}-instance has completed the instantiation. This <em>step</em> allows configuring
   * the {@link IFontMetrics}, to balance <strong>memory-footprint</strong> and
   * <strong>lookup-performance</strong>, according to the available resources and use-cases.
   *
   * @since 0.1.0
   * @author Chrimle
   */
  interface PreCalculateStep {
    /**
     * Skips the <em>"pre-calculation" step</em>, which will result in an {@link
     * IFontMetrics}-instance with only a single {@code fontSize} pre-calculated - namely, the
     * {@code baselineMap}.
     *
     * @return <em>this</em> builder.
     * @since 0.1.0
     */
    OnDemandCalculateStep skipPreCalculation();
  }

  /**
   * The <em>Builder</em>-step for configuring <em>"on-demand"</em> calculations of widths.
   * Effectively, allowing {@link Character}-widths to be calculated <em>on-demand</em>, whenever
   * the {@link IFontMetrics} does not have the widths for a given {@code fontSize} calculated. This
   * <em>step</em> allows configuring {@link IFontMetrics}, to balance
   * <strong>memory-footprint</strong> and <strong>lookup-performance</strong>, according to the
   * available resources and use-cases.
   *
   * @since 0.1.0
   * @author Chrimle
   */
  interface OnDemandCalculateStep {
    /**
     * Disables <em>"on-demand"</em> calculations of widths. This will result in an {@link
     * IFontMetrics}-instance which can <strong>only</strong> resolve {@link Character}-widths for
     * <em>pre-calculated</em> {@code fontSize}s. The resulting {@link IFontMetrics} will
     * <strong>not</strong> <em>calculate</em> and <em>cache</em> {@link Character}-widths.
     *
     * <p>A common use-case for this option, is when <strong>all expected</strong> {@code fontSize}s
     * have been <em>pre-calculated</em>.
     *
     * @return <em>this</em> builder.
     * @since 0.1.0
     * @see #enableOnDemandCalculations() Enabling "on-demand" calculations without caching.
     */
    BuildStep disableOnDemandCalculations();

    /**
     * Enables <em>"on-demand"</em> calculating of widths, <strong>without caching the
     * results</strong>. This will result in an {@link IFontMetrics}-instance which may resolve the
     * width of any supported {@link Character}, but may do so at lesser performance for {@code
     * fontSize}s which have not been pre-calculated and cached.
     *
     * <p>A common use-case for this option, is when the <strong>vast majority of expected</strong>
     * {@code fontSize}s have been <em>pre-calculated</em>, but some expected {@code fontSize}s are
     * exceptionally rare and do not warrant being cached.
     *
     * @return <em>this</em> builder.
     * @since 0.1.0
     * @see #disableOnDemandCalculations() Disabling "on-demand" calculations.
     */
    BuildStep enableOnDemandCalculations();

    BuildStep enableOnDemandCalculationsWithFifoCache(final int cacheSizeLimit);

    BuildStep enableOnDemandCalculationsWithPopularityCache(final int cacheSizeLimit);

    BuildStep enableUnlimitedOnDemandCalculations();
  }

  interface BuildStep {
    IFontMetrics build();
  }
}
