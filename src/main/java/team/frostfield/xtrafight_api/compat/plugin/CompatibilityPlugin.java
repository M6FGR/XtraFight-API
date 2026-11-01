package team.frostfield.xtrafight_api.compat.plugin;

import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.events.bus.NeoBus;
import net.neoforged.fml.ModList;

public interface CompatibilityPlugin {
    // The target for the compatibility, if not present, it will not initialize the plugin
    String getTargetModID();

    // ONLY runs on the client side if the compatibility is client-side
    boolean isClientOnly();

    void onInitialize(NeoBus neoBus, ModBus modBus);

    default boolean shouldInit() {
        // if empty, it will not initialize, not a compatibility!
        if (this.getTargetModID() == null || this.getTargetModID().isBlank() || this.getTargetModID().isEmpty()) {
            return false;
        }
        return ModList.get().isLoaded(this.getTargetModID());
    }
}
