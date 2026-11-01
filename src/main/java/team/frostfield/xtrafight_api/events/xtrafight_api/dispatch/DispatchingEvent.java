package team.frostfield.xtrafight_api.events.xtrafight_api.dispatch;

import net.neoforged.fml.ModLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.ApiStatus.Internal;
import team.frostfield.xtrafight_api.events.dispatch.abstracts.DispatchEvent;

// Used on @SubscribeEvent to dispatch it anywhere, no specific methods needed
public class DispatchingEvent extends DispatchEvent {
    private DispatchingEvent() {}

    @Override
    public boolean canBeOverridden() {
        return false;
    }

    @Internal
    public static void postEvent() {
        DispatchingEvent instance = new DispatchingEvent();
        NeoForge.EVENT_BUS.post(instance);
        ModLoader.postEvent(instance);
    }

}
