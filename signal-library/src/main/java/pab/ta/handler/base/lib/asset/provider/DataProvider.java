package pab.ta.handler.base.lib.asset.provider;

import org.ta4j.core.BarSeries;
import pab.ta.handler.base.lib.asset.AssetInfo;
import pab.ta.handler.base.lib.asset.TimeFrame;


public interface DataProvider {

    BarSeries getSeries(AssetInfo info, TimeFrame tf);
}
