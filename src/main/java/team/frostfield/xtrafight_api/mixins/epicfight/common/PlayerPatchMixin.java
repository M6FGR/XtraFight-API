package team.frostfield.xtrafight_api.mixins.epicfight.common;

import team.frostfield.xtrafight_api.events.xtrafight_api.player.AddPlayerMotionsDispatchEvent;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPISkills;
import team.frostfield.xtrafight_api.skills.XtraFightSkillSlots;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.skill.PlayerSkills;

@Mixin(value = PlayerPatch.class, remap = false)
public abstract class PlayerPatchMixin<T extends Player> extends LivingEntityPatch<T> {
    public PlayerPatchMixin(T entity) {
        super(entity);
    }

    @Shadow
    public abstract PlayerSkills getPlayerSkills();


    @Inject(
            at = @At(value = "HEAD"),
            method = "onJoinWorld(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Z)V",
            remap = false
    )
    public void epicAPI$onJoinWorld(T entity, Level level, boolean worldgenSpawn, CallbackInfo ci) {
        // add HeavyAttack and CounterAttack as a default skill
        PlayerSkills skillCapability = this.getPlayerSkills();
        skillCapability.getSkillContainerFor(XtraFightSkillSlots.HEAVY_ATTACK).setSkill(XtraFightAPISkills.HEAVY_ATTACKS.get());
        skillCapability.getSkillContainerFor(XtraFightSkillSlots.COUNTER_ATTACK).setSkill(XtraFightAPISkills.COUNTER_ATTACK.get());
    }

    @Inject(
            at = @At("TAIL"),
            method = "initAnimator",
            remap = false
    )

    public void injectAddMotionsEH(Animator animator, CallbackInfo ci) {
        AddPlayerMotionsDispatchEvent addPlayerMotionsEH = new AddPlayerMotionsDispatchEvent(animator, (PlayerPatch<?>) (Object) this);
        addPlayerMotionsEH.post();
    }
}
