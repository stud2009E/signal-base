package pab.ta.handler.base.lib.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BaseMessage implements Message {

    private final String text;
    private final LocalDateTime createdAt;
}