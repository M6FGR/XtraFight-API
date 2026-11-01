package team.frostfield.xtrafight_api.events.mc.server;

import team.frostfield.xtrafight_api.events.dispatch.IDispatchableEvent;
import team.frostfield.xtrafight_api.events.dispatch.abstracts.DispatchEvent;
import team.frostfield.xtrafight_api.events.mc.MinecraftDispatchableEvents;
import net.minecraft.server.MinecraftServer;

import java.util.function.BooleanSupplier;

public class ServerTickDispatchEvent extends DispatchEvent implements IDispatchableEvent<ServerTickDispatchEvent> {
    private final MinecraftServer server;
    private final BooleanSupplier boolSupplier;

    public ServerTickDispatchEvent(MinecraftServer server, BooleanSupplier hasTimeLeft) {
        this.server = server;
        this.boolSupplier = hasTimeLeft;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public boolean hasTimeLeft() {
        return this.boolSupplier.getAsBoolean();
    }

    @Override
    public ServerTickDispatchEvent post() {
        return MinecraftDispatchableEvents.Server.SERVER_TICK.postEvent(this);
    }
}
