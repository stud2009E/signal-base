package pab.ta.handler.base.asset.provider;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.asset.AssetInfo;
import pab.ta.handler.base.asset.TimeFrame;


public interface DataProvider {

    BarSeries getSeries(AssetInfo info, TimeFrame tf);
}
