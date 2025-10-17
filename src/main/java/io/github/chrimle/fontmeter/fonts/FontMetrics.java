package io.github.chrimle.fontmeter.fonts;

import java.util.Map;

public final class FontMetrics implements IFontMetrics {

  private FontMetrics() {}

  public static IFontMetrics.BaselineStep builder() {
    return FontMetricsBuilder.newInstance();
  }

  public static final class FontMetricsBuilder
      implements IFontMetrics.BaselineStep,
          IFontMetrics.PreCalculateStep,
          IFontMetrics.OnDemandCalculateStep,
          IFontMetrics.BuildStep {

    private FontMetricsBuilder() {}

    private static IFontMetrics.BaselineStep newInstance() {
      return new FontMetricsBuilder();
    }

    @Override
    public IFontMetrics.PreCalculateStep setBaseline(
        int fontSize, Map<Character, Double> baselineMap) {
      return this;
    }

    @Override
    public IFontMetrics.OnDemandCalculateStep skipPreCalculation() {
      return this;
    }

    @Override
    public IFontMetrics.BuildStep disableOnDemandCalculations() {
      return this;
    }

    @Override
    public BuildStep enableOnDemandCalculations() {
      return this;
    }

    @Override
    public BuildStep enableOnDemandCalculationsWithFifoCache(int onDemandCacheSizeLimit) {
      return this;
    }

    @Override
    public BuildStep enableOnDemandCalculationsWithPopularityCache(int onDemandCacheSizeLimit) {
      return this;
    }

    @Override
    public IFontMetrics.BuildStep enableUnlimitedOnDemandCalculations() {
      return this;
    }

    @Override
    public IFontMetrics build() {
      return new FontMetrics();
    }
  }
}
