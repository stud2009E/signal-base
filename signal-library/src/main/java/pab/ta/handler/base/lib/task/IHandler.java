package pab.ta.handler.base.lib.task;

/**
 * Handles processing tasks for a specific time frame.
 * Implementations define the concrete processing logic.
 */
public interface IHandler<T> {

    /**
     * Executes processing for the given time frame.
     */
    void process(T data);
}