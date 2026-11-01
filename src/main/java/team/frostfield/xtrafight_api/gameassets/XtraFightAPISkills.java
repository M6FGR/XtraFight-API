package team.frostfield.xtrafight_api.gameassets;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;
import team.frostfield.xtrafight_api.cls.loadable.marks.Invoke;
import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.skills.common.CounterAttack;
import team.frostfield.xtrafight_api.skills.common.HeavyAttack;
import team.frostfield.xtrafight_api.utils.assets.DeferredRegistryUtil;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;

import java.util.function.Supplier;

public class XtraFightAPISkills implements ILoadableClass {
    private static final DeferredRegister<Skill> SKILLS = DeferredRegistryUtil.createDefaultSkills();

    public static final DeferredHolder<Skill, HeavyAttack> HEAVY_ATTACKS = XtraFightAPISkills.register("heavy_attack", HeavyAttack::createHeavyAttackBuilder);
    public static final DeferredHolder<Skill, CounterAttack> COUNTER_ATTACK = XtraFightAPISkills.register("counter_attack", CounterAttack::createCounterAttackBuilder);

    @Invoke
    private XtraFightAPISkills(ModBus modBus) {
        SKILLS.register(modBus);
    }

    private static <T extends SkillBuilder<?>, S extends Skill> DeferredHolder<Skill, S> register(String name, Supplier<T> supplier) {
        return SKILLS.register(name, key -> supplier.get().build(key));
    }
}
