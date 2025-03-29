package pab.ta.handler.base.asset;

import org.ta4j.core.Rule;
import pab.ta.handler.base.component.rule.RuleGroup;

public interface RuleIdentity {
    String id();

    RuleGroup group();

    Rule rule();

    Direction direction();
}
