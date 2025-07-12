package pab.ta.handler.base.lib.task;

import pab.ta.handler.base.lib.asset.ITimeFrame;

/**
 * Handles processing tasks for a specific time frame.
 * Implementations define the concrete processing logic.
 */
public interface ITaskHandler {

    /**
     * Executes processing for the given time frame
     * @param tf The time frame to process
     */
    void process(ITimeFrame tf);
}