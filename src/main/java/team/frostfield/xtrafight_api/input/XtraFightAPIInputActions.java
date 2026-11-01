package team.frostfield.xtrafight_api.input;

import net.minecraft.client.KeyMapping;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPIKeyMappings;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.api.client.input.controller.ControllerBinding;

import java.util.Optional;
@SuppressWarnings("experiment")
public enum XtraFightAPIInputActions implements InputAction {
    HEAVY_ATTACK,
    COUNTER_ATTACK;

    public KeyMapping keyMapping() {
        return switch (this) {
            case HEAVY_ATTACK -> XtraFightAPIKeyMappings.HEAVY_ATTACK;
            case COUNTER_ATTACK -> XtraFightAPIKeyMappings.COUNTER_ATTACK;
        };
    }

    private final int id;

    XtraFightAPIInputActions() {
        this.id = InputAction.ENUM_MANAGER.assign(this);
    }

    @Override
    public Optional<ControllerBinding> controllerBinding() {
        return Optional.empty();
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
