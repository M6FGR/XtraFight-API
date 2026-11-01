package team.frostfield.xtrafight_api.cls.loadable;

import team.frostfield.xtrafight_api.events.bus.ModBus;
import team.frostfield.xtrafight_api.events.bus.NeoBus;

public interface ILoadableClass {

    /** Primary method to register Items, Blocks, Entities, etc... Called Directly. */
    default void onModConstruct(ModBus modBus) {}

    /** Use to register Client-only listeners to the Mod Bus. Called Directly. */
    default void onModClientConstruct(ModBus modBus) {}

    /** Use to register listeners to the global NeoForge.EVENT_BUS. Called Directly. */
    default void onGameConstruct(NeoBus neoBus) {}

    /** Use to register listeners to the client side of NeoForge.EVENT_BUS. Called Directly. */
    default void onGameClientConstruct(NeoBus neoBus) {}

    /** Overridden so the class does not log/throws an exception that it wasn't loaded */
    default boolean ignoreNotLoadedWarning() {
        return false;
    }

}