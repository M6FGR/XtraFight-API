package team.frostfield.xtrafight_api.main;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import team.frostfield.xtrafight_api.cls.loadable.LoadableClassManager;
import team.frostfield.xtrafight_api.cls.loadable.enums.XtraFightLoadableEnums;
import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.events.bus.NeoBus;
import team.frostfield.xtrafight_api.events.xtrafight_api.dispatch.DispatchingEvent;
import team.frostfield.xtrafight_api.events.xtrafight_api.registry.EntityPatchDispatchEvent;
import team.frostfield.xtrafight_api.input.XtraFightAPIInputActions;
import team.frostfield.xtrafight_api.skills.XtraFightAPISkillCategories;
import team.frostfield.xtrafight_api.skills.XtraFightSkillSlots;
import team.frostfield.xtrafight_api.utils.EnvironmentHelper;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

@Mod(XtraFightAPI.MOD_ID)
public final class XtraFightAPIMod implements XtraFightAPI {
    private final ModContainer container;
    private final ModBus modBus;
    static XtraFightAPIMod instance;

    public XtraFightAPIMod(IEventBus modBus, ModContainer container) {
        instance = this;
        this.modBus = ModBus.from(modBus);
        this.container = container;
        this.doEpicAPIStuff();
    }

    private void modConstruct(FMLConstructModEvent event) {
        EntityPatchDispatchEvent entityPatchEH = new EntityPatchDispatchEvent();
        event.enqueueWork(entityPatchEH::post);
    }

    private void doEpicAPIStuff() {
        DispatchingEvent.postEvent();
        this.modBus.addListener(this::modConstruct);
        LoadableClassManager.loadEnum(this.modBus, XtraFightLoadableEnums.values());

        SkillSlot.ENUM_MANAGER.registerEnumCls(XtraFightAPI.MOD_ID, XtraFightSkillSlots.class);
        SkillCategory.ENUM_MANAGER.registerEnumCls(XtraFightAPI.MOD_ID, XtraFightAPISkillCategories.class);
        InputAction.ENUM_MANAGER.registerEnumCls(XtraFightAPI.MOD_ID, XtraFightAPIInputActions.class);
    }

    @Override
    public boolean isDeveloperEnvironment() {
        return EnvironmentHelper.isDevelopmentEnvironment();
    }

    public ModContainer getModContainer() {
        return this.container;
    }

    public ModBus getModBus() {
        return this.modBus;
    }

    public ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(XtraFightAPI.MOD_ID, path);
    }

    @Override
    public NeoBus getNeoBus() {
        return NeoBus.EVENT_BUS;
    }

    // Logger helpers
    public static void err(String message, Object... args) {
        XtraFightAPI.LOGGER.error(message, args);
    }

    public static void errIfDevSide(String message, Object... args) {
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            XtraFightAPI.LOGGER.error(message, args);
        }
    }

    public static void warn(String message, Object... args) {
        XtraFightAPI.LOGGER.warn(message, args);
    }

    public static void debug(String message, Object... args) {
        XtraFightAPI.LOGGER.debug(message, args);
    }

    public static void debugIfDevSide(String message, Object... args) {
        if (EnvironmentHelper.getEnvironment().isDevEnv()) {
            XtraFightAPI.LOGGER.debug(message, args);
        }
    }

    public static void info(String message, Object... args) {
        XtraFightAPI.LOGGER.info(message, args);
    }
}
