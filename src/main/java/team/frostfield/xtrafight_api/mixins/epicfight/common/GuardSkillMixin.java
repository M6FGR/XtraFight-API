package team.frostfield.xtrafight_api.mixins.epicfight.common;

import team.frostfield.xtrafight_api.events.xtrafight_api.skill.AddGuardMotionsDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.skill.AddGuardMotionsDispatchEvent.Normal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.GuardSkill.Builder;

import java.util.function.Function;

@Mixin(value = GuardSkill.class, remap = false, priority = 1001)
public class GuardSkillMixin {

    @Inject(
            at = @At(value = "TAIL"),
            method = "createGuardBuilder",
            remap = false
    )

    private static void injectEvent(Function<Builder, GuardSkill> constructor, CallbackInfoReturnable<Builder> cir) {
        AddGuardMotionsDispatchEvent.Normal normalEH = new Normal(cir.getReturnValue());
        normalEH.post();
    }

}
