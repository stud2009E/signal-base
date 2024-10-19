package pab.ta.handler.base.asset;

import com.google.common.base.Objects;

import java.time.LocalDateTime;

public record BaseMessage(String text, LocalDateTime dateTime) implements Message {

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMessage that = (BaseMessage) o;
        return Objects.equal(text, that.text);
    }
}