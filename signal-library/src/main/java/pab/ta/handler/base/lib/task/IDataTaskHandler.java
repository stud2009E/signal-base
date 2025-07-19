package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.TimeFrame;

public interface IDataTaskHandler extends ITaskHandler {

    void setTimeFrame(TimeFrame tf);

}
