package team.frostfield.xtrafight_api.cls.loadable.enums;

import team.frostfield.xtrafight_api.client.sound.data.XtraFightAPISoundsManager;
import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;
import team.frostfield.xtrafight_api.config.XtraFightAPIConfigs;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPIGameRules;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPIKeyMappings;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPISkillDataKeys;
import team.frostfield.xtrafight_api.gameassets.XtraFightAPISkills;
import team.frostfield.xtrafight_api.network.XtraFightAPINetworkManager;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public enum XtraFightLoadableEnums implements LoadableEnum {
    NETWORK_MANAGER(XtraFightAPINetworkManager.class),
    GAME_RULES(XtraFightAPIGameRules.class),
    CONFIG(XtraFightAPIConfigs.class),
    SOUND_MANAGER(XtraFightAPISoundsManager.class),
    SKILLS(XtraFightAPISkills.class),
    SKILL_DATA_KEYS(XtraFightAPISkillDataKeys.class),
    // Client Registries
    KEY_MAPPINGS(XtraFightAPIKeyMappings.class, true);

    private final Class<? extends ILoadableClass> loadableClass;
    private final boolean clientOnly;

    XtraFightLoadableEnums(Class<? extends ILoadableClass> loadableClass) {
        this(loadableClass, false);
    }

    XtraFightLoadableEnums(Class<? extends ILoadableClass> loadableClass, boolean clientOnly) {
        this.loadableClass = loadableClass;
        this.clientOnly = clientOnly;
    }

    public Class<? extends ILoadableClass> getLoadableClass() {
        return this.loadableClass;
    }

    public boolean isClientOnly() {
        return this.clientOnly;
    }
}
