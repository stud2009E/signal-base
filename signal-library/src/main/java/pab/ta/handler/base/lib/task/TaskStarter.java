package pab.ta.handler.base.lib.task;

/**
 * Controls execution of time-scheduled tasks with different intervals.
 * Provides methods to trigger tasks with specific temporal granularity.
 */
public interface TaskStarter {

    /**
     * Executes a task.
     */
    void runTask();
}