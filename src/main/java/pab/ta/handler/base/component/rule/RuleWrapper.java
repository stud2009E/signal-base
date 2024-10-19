package pab.ta.handler.base.component.rule;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import pab.ta.handler.base.asset.AssetType;
import pab.ta.handler.base.asset.CandleInterval;
import pab.ta.handler.base.asset.RuleIdentity;
import pab.ta.handler.base.asset.SeriesContainer;

import java.util.Arrays;
import java.util.List;

public abstract class RuleWrapper {

    protected SeriesContainer container;

    public void setSeriesContainer(SeriesContainer container) {
        this.container = container;
    }

    protected abstract Indicator<Num> indicator();

    public abstract List<RuleIdentity> rules();

    public boolean applicableForType(AssetType type) {
        return Arrays.asList(AssetType.values()).contains(type);
    }

    public boolean applicableForInterval(CandleInterval interval) {
        return Arrays.asList(CandleInterval.values()).contains(interval);
    }
}