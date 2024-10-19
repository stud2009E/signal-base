package pab.ta.handler.base.asset;

public record BaseSeriesIdentity(AssetInfo info, TimeFrame tf) implements SeriesIdentity{
}