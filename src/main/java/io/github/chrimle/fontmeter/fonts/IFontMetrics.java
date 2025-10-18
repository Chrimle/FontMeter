package io.github.chrimle.fontmeter.fonts;

import java.util.Map;

/**
 * Just an abstraction-layer for {@link FontMetrics}. Contains the interfaces for the {@link
 * FontMetrics}-builder class.
 *
 * @author Chrimle
 * @since 0.1.0
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
   * @author Chrimle
   * @since 0.1.0
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
   * @author Chrimle
   * @since 0.1.0
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
    default OnDemandCalculateStep skipPreCalculation() {
      return (OnDemandCalculateStep) this;
    }
  }

  /**
   * The <em>Builder</em>-step for configuring <em>"on-demand"</em> calculations of widths.
   * Effectively, allowing {@link Character}-widths to be calculated <em>on-demand</em>, whenever
   * the {@link IFontMetrics} does not have the widths for a given {@code fontSize} calculated. This
   * <em>step</em> allows configuring {@link IFontMetrics}, to balance
   * <strong>memory-footprint</strong> and <strong>lookup-performance</strong>, according to the
   * available resources and use-cases.
   *
   * @author Chrimle
   * @since 0.1.0
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
     * @see #enableOnDemandCalculations() Enabling "on-demand" calculations without caching.
     * @since 0.1.0
     */
    default BuildStep disableOnDemandCalculations() {
      return (BuildStep) this;
    }

    /**
     * Enables <em>"on-demand"</em> calculations of widths, <strong>without caching the
     * results</strong>. This will result in an {@link IFontMetrics}-instance which may resolve the
     * width of any supported {@link Character}, but may do so at lesser performance for {@code
     * fontSize}s which have not been pre-calculated and cached.
     *
     * <p>A common use-case for this option, is when the <strong>vast majority of expected</strong>
     * {@code fontSize}s have been <em>pre-calculated</em>, but some expected {@code fontSize}s are
     * exceptionally rare and do not warrant being cached.
     *
     * @return <em>this</em> builder.
     * @see #disableOnDemandCalculations() Disabling "on-demand" calculations.
     * @since 0.1.0
     */
    default BuildStep enableOnDemandCalculations() {
      throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Enables <em>"on-demand"</em> calculations of widths, using a <em>First-In-First-Out
     * (<strong>FIFO</strong>)</em> cache eviction strategy, when the cache-size exceeds {@code
     * onDemandCacheSizeLimit}. This will result in an {@link IFontMetrics}-instance which may
     * resolve the width of any supported {@link Character}, and will cache the results. If the size
     * of the cache exceeds {@code onDemandCacheSizeLimit}, the <em>oldest</em> "on-demand"
     * cache-entry will be removed.
     *
     * <p>A common use-case for this option, is when the <strong>vast majority of expected</strong>
     * {@code fontSize}s have been <em>pre-calculated</em>, and where all other {@code fontSize}s
     * are equally common.
     *
     * @param onDemandCacheSizeLimit at which point the <strong>oldest</strong> cache entry is
     *     evicted in favour of the most recently calculated width. This value relates to the
     *     "on-demand" cache only, and should not include the number of pre-calculated entries.
     * @return <em>this</em> builder.
     * @throws IllegalArgumentException if {@code onDemandCacheSizeLimit} is negative, or zero.
     * @see #enableOnDemandCalculationsWithPopularityCache(int) Enabling "on-demand" calculations
     *     with Popularity-based Cache Eviction.
     * @since 0.1.0
     */
    default BuildStep enableOnDemandCalculationsWithFifoCache(final int onDemandCacheSizeLimit)
        throws IllegalArgumentException {
      throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Enables <em>"on-demand"</em> calculations of widths, using a <em>Popularity-based (read
     * count)</em> cache eviction strategy, when the cache-size exceeds {@code
     * onDemandCacheSizeLimit}. This will result in an {@link IFontMetrics}-instance which may
     * resolve the width of any supported {@link Character}, and will cache the results. If the size
     * of the cache exceeds {@code onDemandCacheSizeLimit}, the <em>least popular (read the
     * least)</em> "on-demand" cache-entry will be removed from the "on-demand" cache.
     *
     * <p>A common use-case for this option, is when the <strong>vast majority of expected</strong>
     * {@code fontSize}s have been <em>pre-calculated</em>, and where the occurrence-rates of other
     * {@code fontSize}s are unknown and/or not equally common.
     *
     * @param onDemandCacheSizeLimit at which point the <strong>least popular</strong> cache entry
     *     is evicted in favour of the most recently calculated width. This value relates to the
     *     "on-demand" cache only, and should not include the number of pre-calculated entries.
     * @return <em>this</em> builder.
     * @throws IllegalArgumentException if {@code onDemandCacheSizeLimit} is negative, or zero.
     * @see #enableOnDemandCalculationsWithFifoCache(int) Enabling "on-demand" calculations with
     *     FIFO-based Cache Eviction.
     * @since 0.1.0
     */
    default BuildStep enableOnDemandCalculationsWithPopularityCache(
        final int onDemandCacheSizeLimit) throws IllegalArgumentException {
      throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Enables <em>"on-demand"</em> calculations of widths, using an
     * <strong>unlimited/unbounded</strong> cache size. This will result in an {@link
     * IFontMetrics}-instance which may resolve the width of any supported {@link Character}, and
     * will cache the results.
     *
     * <p>A common use-case for this option, is when <em>memory-footprint</em> is not of concern,
     * and the number of unique {@code fontSize}s is a known <em>limited</em> amount.
     *
     * <p><strong>NOTE:</strong> Use at own risk, consider using limited caches instead, or ensure
     * that the number of unique {@code fontSize}s are limited.
     *
     * @return <em>this</em> builder.
     * @see #enableOnDemandCalculationsWithFifoCache(int) Enabling "on-demand" calculations with
     *     FIFO-based Cache Eviction.
     * @see #enableOnDemandCalculationsWithPopularityCache(int) Enabling "on-demand" calculations
     *     with Popularity-based Cache Eviction.
     * @since 0.1.0
     */
    default BuildStep enableOnDemandCalculationsWithUnlimitedCache() {
      throw new UnsupportedOperationException("Not implemented yet");
    }
  }

  /**
   * The <em>Builder</em>-step for <em>building</em> the {@link IFontMetrics}-instance, according to
   * the options set in previous builder-steps.
   *
   * @since 0.1.0
   * @author Chrimle
   */
  interface BuildStep {
    /**
     * Builds the {@link IFontMetrics}-instance.
     *
     * @return the new {@link IFontMetrics}-instance.
     * @since 0.1.0
     */
    IFontMetrics build();
  }
}
