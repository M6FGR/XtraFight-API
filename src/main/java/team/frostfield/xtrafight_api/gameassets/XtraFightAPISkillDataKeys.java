package team.frostfield.xtrafight_api.gameassets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;
import team.frostfield.xtrafight_api.cls.loadable.marks.Invoke;
import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.skills.common.CounterAttack;
import team.frostfield.xtrafight_api.skills.common.HeavyAttack;
import team.frostfield.xtrafight_api.utils.assets.DeferredRegistryUtil;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillDataKey;

public class XtraFightAPISkillDataKeys implements ILoadableClass {
    private static final DeferredRegister<SkillDataKey<?>> SKILL_DATA_KEYS = DeferredRegistryUtil.createDefaultSDK();
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Integer>> HEAVY_COUNTER = XtraFightAPISkillDataKeys.register(
            "heavy_counter",
            ByteBufCodecs.INT,
            0,
            false,
            HeavyAttack.class
    );
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Integer>> MOTION_COUNTER = XtraFightAPISkillDataKeys.register(
            "motion_counter",
            ByteBufCodecs.INT,
            0,
            false,
            CounterAttack.class
    );

    @Invoke
    private XtraFightAPISkillDataKeys(ModBus modBus) {
        SKILL_DATA_KEYS.register(modBus);
    }

    @SafeVarargs
    private static <T> DeferredHolder<SkillDataKey<?>, SkillDataKey<T>> register(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec, T defaultValue, boolean synchronizeTrackingPlayers, Class<? extends Skill>... skillClass) {
        return SKILL_DATA_KEYS.register(name, () -> SkillDataKey.createSkillDataKey(packetCodec, defaultValue, synchronizeTrackingPlayers, skillClass));
    }
}
