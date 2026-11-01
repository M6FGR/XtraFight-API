package team.frostfield.xtrafight_api.events.dispatch.dispatchers.extras;

import team.frostfield.xtrafight_api.events.dispatch.abstracts.DispatchEvent;

@FunctionalInterface
public interface EventDispatcher<T extends DispatchEvent> {
    void dispatch(T event);
}
