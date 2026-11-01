package team.frostfield.xtrafight_api.events.xtrafight_api.player;

import team.frostfield.xtrafight_api.events.dispatch.IDispatchableEvent;
import team.frostfield.xtrafight_api.events.dispatch.abstracts.DispatchEvent;
import team.frostfield.xtrafight_api.events.dispatch.marks.HybridEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.XtraFightAPIDispatchableEvents;
import yesman.epicfight.skill.SkillContainer;

@HybridEvent
public class CounterAttackDispatchEvent extends DispatchEvent implements IDispatchableEvent<CounterAttackDispatchEvent> {
    private final SkillContainer skillContainer;

    public CounterAttackDispatchEvent(SkillContainer skillContainer) {
        this.skillContainer = skillContainer;
    }

    public SkillContainer getSkillContainer() {
        return this.skillContainer;
    }


    @Override
    public CounterAttackDispatchEvent post() {
       return XtraFightAPIDispatchableEvents.Player.COUNTER_ATTACK.postEvent(this);
    }
}
