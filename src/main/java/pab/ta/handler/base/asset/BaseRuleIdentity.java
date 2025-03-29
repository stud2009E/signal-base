package pab.ta.handler.base.asset;

import org.ta4j.core.Rule;
import pab.ta.handler.base.component.rule.RuleGroup;

public record BaseRuleIdentity(String id, RuleGroup group, Rule rule,
                               Direction direction) implements RuleIdentity {
}