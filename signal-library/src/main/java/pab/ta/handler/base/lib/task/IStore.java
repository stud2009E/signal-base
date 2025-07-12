package pab.ta.handler.base.lib.task;

import java.util.List;

/**
 * Generic storage interface for managing collections of items.
 * Provides basic CRUD-like operations for any data type.
 *
 * @param <T> the type of elements stored in this collection
 */
public interface IStore<T> {

    /**
     * Adds an item to the store
     *
     * @param data the item to store
     */
    void put(T data);

    /**
     * Removes all items from the store
     */
    void clear();

    /**
     * Retrieves all items currently in the store
     *
     * @return an immutable list of stored items
     */
    List<T> get();
}
