package team.frostfield.xtrafight_api.animation;

import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.datastructure.ParameterizedHashMap;

public class XtraFightAPIAnimationStates extends EntityState {

    public static final StateFactor<Boolean> CAN_COUNTER = new StateFactor<>("canCounter", false);

    public XtraFightAPIAnimationStates(ParameterizedHashMap<StateFactor<?>> states) {
        super(states);
    }
}
