package pab.ta.handler.base.asset;

import java.time.LocalDateTime;

/**
 * Represents a message with text content and timestamp information.
 * This interface provides access to the message content and its creation time.
 */
public interface Message {
    /**
     * Retrieves the text content of the message.
     *
     * @return the message text as a String, may be empty but should not be null
     */
    String getText();

    /**
     * Retrieves the date and time when the message was created.
     *
     * @return the creation timestamp as a {@link LocalDateTime} object
     */
    LocalDateTime getCreatedAt();
}
