package team.frostfield.xtrafight_api.compat.plugin;

import team.frostfield.xtrafight_api.cls.maps.BasicHashMap;
import team.frostfield.xtrafight_api.cls.maps.BasicMap;
import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.events.bus.NeoBus;
import team.frostfield.xtrafight_api.main.XtraFightAPIMod;
import team.frostfield.xtrafight_api.utils.EnvironmentHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public final class CompatibilityPluginsManager {
    private static final Logger LOGGER = LogManager.getLogger(CompatibilityPluginsManager.class.getSimpleName());
    private static final BasicMap<String, CompatibilityPlugin> PLUGIN_BY_MOD_ID = new BasicHashMap<>();

    @SafeVarargs
    public static void registerPlugins(@NotNull ModBus modBus, Class<? extends CompatibilityPlugin>... pluginClasses) {
        for (Class<? extends CompatibilityPlugin> pluginClass : pluginClasses) {
            String simpleName = pluginClass.getSimpleName();
            try {
                Constructor<? extends CompatibilityPlugin> constructor = pluginClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                CompatibilityPlugin plugin = constructor.newInstance();

                if (plugin.shouldInit()) {
                    if (plugin.isClientOnly()) {
                        if (!EnvironmentHelper.getEnvironment().isClient()) {
                            continue;
                        }
                    }
                    plugin.onInitialize(NeoBus.EVENT_BUS, modBus);
                }

                PLUGIN_BY_MOD_ID.put(plugin.getTargetModID(), plugin);
                LOGGER.info("Initialized Compatibility Plugin: [{}]", simpleName);
            } catch (NoSuchMethodException e) {
                XtraFightAPIMod.err("Failed to initialize plugin [{}]: Missing default no-arg constructor!", simpleName);
            } catch (Exception e) {
                XtraFightAPIMod.err("Failed to initialize plugin [{}], {}", pluginClass.getName(), e);
                e.printStackTrace();
            }
        }
    }

    public boolean isPluginLoadedFor(String modID) {
        return PLUGIN_BY_MOD_ID.get(modID) != null;
    }
}