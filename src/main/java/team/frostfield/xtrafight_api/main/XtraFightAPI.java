package team.frostfield.xtrafight_api.main;

import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.events.bus.NeoBus;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public sealed interface XtraFightAPI permits XtraFightAPIMod {
    String MOD_ID = "xtrafight_api";
    String MOD_NAME = "XtraFight-API";
    Logger LOGGER = LogManager.getLogger(MOD_NAME);

    static XtraFightAPI getInstance() {
        return XtraFightAPIMod.instance;
    }

    ModContainer getModContainer();

    ModBus getModBus();

    boolean isDeveloperEnvironment();

    ResourceLocation identifier(String path);

    NeoBus getNeoBus();

}
