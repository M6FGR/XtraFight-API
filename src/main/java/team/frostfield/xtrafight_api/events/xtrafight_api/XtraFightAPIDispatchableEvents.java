package team.frostfield.xtrafight_api.events.xtrafight_api;

import team.frostfield.xtrafight_api.events.dispatch.dispatchers.CancelableEventDispatch;
import team.frostfield.xtrafight_api.events.dispatch.dispatchers.EventDispatch;
import team.frostfield.xtrafight_api.events.xtrafight_api.entity.EntityInteractDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.player.AddPlayerMotionsDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.player.CounterAttackDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.player.HeavyAttackDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.registry.CameraAnimationRegistryDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.registry.EntityPatchDispatchEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.skill.AddGuardMotionsDispatchEvent;

public class XtraFightAPIDispatchableEvents {

    public static class Registry {
        private Registry(){}
        /* combines:
           EntityPatchRegistryEvent
           RegisterPatchedRenderersEvent.AddEntity
           into 1 event
        */
        public static final EventDispatch<EntityPatchDispatchEvent> ENTITY_PATCH = EventDispatch.createDispatchable();
        // registers camera animations
        public static final EventDispatch<CameraAnimationRegistryDispatchEvent> CAMERA_ANIMATIONS = EventDispatch.createDispatchable();
    }
    public XtraFightAPIDispatchableEvents() {}

    public static class Player {
        private Player() {}

        // Adds more default motions for the player, specifically in the tail of PlayerPatch#initAnimation();
        public static final EventDispatch<AddPlayerMotionsDispatchEvent> ADD_MOTIONS = EventDispatch.createDispatchable();
        // fires when the player heavy attacks if a heavy combo is present
        public static final CancelableEventDispatch<HeavyAttackDispatchEvent> HEAVY_ATTACK = CancelableEventDispatch.createDispatchable();
        // fires when the player counter-attacks if a counter is present
        public static final CancelableEventDispatch<CounterAttackDispatchEvent> COUNTER_ATTACK = CancelableEventDispatch.createDispatchable();
    }


    public static class Skill {
        private Skill() {}
        // adds guard motions and guard break motions for your weapon
        public static final EventDispatch<AddGuardMotionsDispatchEvent.Normal> ADD_NORMAL_GUARD_MOTIONS = EventDispatch.createDispatchable();
        // adds parrying motions for your weapon
        public static final EventDispatch<AddGuardMotionsDispatchEvent.Parry> ADD_PARRY_GUARD_MOTIONS = EventDispatch.createDispatchable();

    }

    public static class Entity {
        public static final EventDispatch<EntityInteractDispatchEvent> ENTITY_INTERACT = EventDispatch.createDispatchable();
    }


}
