package pab.ta.handler.base.lib.task;

/**
 * Controls execution of time-scheduled tasks with different intervals.
 * Provides methods to trigger tasks with specific temporal granularity.
 */
public interface ITaskStarter {

    /**
     * Executes a task designed to run every 4 hours
     */
    void runTask();
}
