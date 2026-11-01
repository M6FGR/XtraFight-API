package team.frostfield.xtrafight_api.mixins.epicfight.common;

import team.frostfield.xtrafight_api.input.XtraFightAPIInputActions;
import team.frostfield.xtrafight_api.skills.XtraFightSkillSlots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.input.InputManager;
import yesman.epicfight.api.client.input.action.EpicFightInputAction;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.api.client.input.action.MinecraftInputAction;
import yesman.epicfight.api.event.types.player.SkillCastEvent;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.InputUtils;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillSlot;

@Mixin(value = ControlEngine.class, remap = false)
public abstract class ControlEngineMixin {
    @Shadow
    private LocalPlayerPatch playerpatch;

    @Shadow
    protected abstract boolean isCurrentHoldingAction(@NotNull InputAction other);

    @Shadow
    private static void consumeVanillaAttackKeyClicks() {}

    @Shadow
    private LocalPlayer player;

    @Shadow
    private boolean attackLightPressToggle;

    @Shadow
    protected abstract void reserveKey(SkillSlot slot, InputAction action);

    @Shadow
    private boolean weaponInnatePressToggle;

    @Shadow
    private int weaponInnatePressCounter;

    @Unique
    private void epicAPI$maybeHeavyAttack() {
        if (!this.playerpatch.isEpicFightMode() || isCurrentHoldingAction(XtraFightAPIInputActions.HEAVY_ATTACK)) {
            return;
        }
        final MinecraftInputAction vanillaAttack = MinecraftInputAction.ATTACK_DESTROY;
        final XtraFightAPIInputActions heavyAttack = XtraFightAPIInputActions.HEAVY_ATTACK;

        boolean shouldPlayAttackAnimation = this.playerpatch.canPlayAttackAnimation();
        if (vanillaAttack.keyMapping().getKey() == heavyAttack.keyMapping().getKey() && Minecraft.getInstance().hitResult != null && shouldPlayAttackAnimation) {
            consumeVanillaAttackKeyClicks();
        }

        if (shouldPlayAttackAnimation) {
            if (!InputManager.isBoundToSamePhysicalInput(heavyAttack, EpicFightInputAction.SWITCH_MODE)) {
                SkillCastEvent skillCastEvent = this.playerpatch.getSkill(XtraFightSkillSlots.HEAVY_ATTACK).sendCastRequest(this.playerpatch, ControlEngine.getInstance());

                if (skillCastEvent.isExecutable()) {
                    this.player.resetAttackStrengthTicker();
                    this.attackLightPressToggle = false;
                    ControlEngine.getInstance().releaseAllServedKeys();
                } else {
                    if (!this.player.isSpectator()) {
                        this.reserveKey(XtraFightSkillSlots.HEAVY_ATTACK, heavyAttack);
                    }
                }

                ControlEngine.getInstance().lockHotkeys();
                this.attackLightPressToggle = false;
                this.weaponInnatePressToggle = false;
                this.weaponInnatePressCounter = 0;
            } else {
                if (!this.weaponInnatePressToggle) {
                    this.weaponInnatePressToggle = true;
                }
            }
        }
    }

    @Unique
    private void epicAPI$maybeCounterAttack() {
        if (!this.playerpatch.isEpicFightMode() || this.isCurrentHoldingAction(XtraFightAPIInputActions.COUNTER_ATTACK)) {
            return;
        }
        final MinecraftInputAction vanillaAttack = MinecraftInputAction.ATTACK_DESTROY;
        final XtraFightAPIInputActions counterAttack = XtraFightAPIInputActions.COUNTER_ATTACK;

        boolean shouldPlayAttackAnimation = this.playerpatch.canPlayAttackAnimation();
        if (vanillaAttack.keyMapping().getKey() == counterAttack.keyMapping().getKey() && Minecraft.getInstance().hitResult != null && shouldPlayAttackAnimation) {
            consumeVanillaAttackKeyClicks();
        }

        if (shouldPlayAttackAnimation) {
            if (!InputManager.isBoundToSamePhysicalInput(counterAttack, EpicFightInputAction.SWITCH_MODE)) {
                SkillCastEvent skillCastEvent = this.playerpatch.getSkill(XtraFightSkillSlots.COUNTER_ATTACK).sendCastRequest(this.playerpatch, ControlEngine.getInstance());

                if (skillCastEvent.isExecutable()) {
                    this.player.resetAttackStrengthTicker();
                    this.attackLightPressToggle = false;
                    ControlEngine.getInstance().releaseAllServedKeys();
                } else {
                    if (!this.player.isSpectator()) {
                        this.reserveKey(XtraFightSkillSlots.COUNTER_ATTACK, counterAttack);
                    }
                }

                ControlEngine.getInstance().lockHotkeys();
                this.attackLightPressToggle = false;
                this.weaponInnatePressToggle = false;
                this.weaponInnatePressCounter = 0;
            } else {
                if (!this.weaponInnatePressToggle) {
                    this.weaponInnatePressToggle = true;
                }
            }
        }
    }

    @Unique
    private void xtraFightAPI$runKeyboardMouseEvent(InputAction action, Runnable task) {
        InputManager.triggerOnPress(action, () -> InputUtils.runKeyboardMouseEvent(action, task));
    }

    @Inject(
            at = @At("HEAD"),
            remap = false,
            method = "handleEpicFightKeyMappings"
    )
    private void injectHeavyAttack(CallbackInfo ci) {
        if (this.playerpatch != null) {
            this.xtraFightAPI$runKeyboardMouseEvent(XtraFightAPIInputActions.HEAVY_ATTACK, this::epicAPI$maybeHeavyAttack);
            this.xtraFightAPI$runKeyboardMouseEvent(XtraFightAPIInputActions.COUNTER_ATTACK, this::epicAPI$maybeCounterAttack);
        }

    }
}
