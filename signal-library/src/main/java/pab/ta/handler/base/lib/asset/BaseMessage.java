package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class BaseMessage implements Message {

    private final String text;
    private final ZonedDateTime createdAt;
}