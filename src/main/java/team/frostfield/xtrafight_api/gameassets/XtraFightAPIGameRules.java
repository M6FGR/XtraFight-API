package team.frostfield.xtrafight_api.gameassets;

import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.IntegerValue;
import org.jetbrains.annotations.ApiStatus.Experimental;
import team.frostfield.xtrafight_api.builders.minecraft.GameRulesBuilder;
import team.frostfield.xtrafight_api.builders.minecraft.GameRulesBuilder.EnumValue;
import team.frostfield.xtrafight_api.builders.minecraft.GameRulesBuilder.SynchronizedGameRule;
import team.frostfield.xtrafight_api.cls.lists.BasicArrayList;
import team.frostfield.xtrafight_api.cls.lists.BasicList;
import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;
import team.frostfield.xtrafight_api.cls.loadable.marks.Invoke;
import team.frostfield.xtrafight_api.events.bus.ModBus;

@Experimental
public class XtraFightAPIGameRules implements ILoadableClass {
    private static final BasicList<SynchronizedGameRule<?>> GAME_RULES = new BasicArrayList<>();

    public static final SynchronizedGameRule<BooleanValue> BOOLEAN_GAMERULE = GameRulesBuilder.newBoolean("booleanVal", Category.PLAYER, false, true);

    @Invoke
    private XtraFightAPIGameRules(ModBus modBus) {
        modBus.run(this::register);
    }

    private static SynchronizedGameRule<BooleanValue> registerBoolean(String name, Category category, boolean defaultValue, boolean syncToAllPlayers) {
        SynchronizedGameRule<BooleanValue> boolRule = GameRulesBuilder.newBoolean(name, category, defaultValue, syncToAllPlayers);
        GAME_RULES.addOnlyOnce(boolRule);
        return boolRule;
    }

    private static SynchronizedGameRule<IntegerValue> registerInt(String name, Category category, int defaultValue, boolean syncToAllPlayers) {
        SynchronizedGameRule<IntegerValue> boolRule = GameRulesBuilder.newInteger(name, category, defaultValue, syncToAllPlayers);
        GAME_RULES.addOnlyOnce(boolRule);
        return boolRule;
    }

    private static <T extends Enum<T>> SynchronizedGameRule<EnumValue<T>> registerInt(String name, Category category, T defaultValue, boolean syncToAllPlayers) {
        SynchronizedGameRule<EnumValue<T>> boolRule = GameRulesBuilder.newEnum(name, category, defaultValue, syncToAllPlayers);
        GAME_RULES.addOnlyOnce(boolRule);
        return boolRule;
    }

    private void register() {
        GAME_RULES.forEach(SynchronizedGameRule::register);
    }

}
