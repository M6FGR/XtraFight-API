package team.frostfield.xtrafight_api.events.dispatch.marks;

import team.frostfield.xtrafight_api.events.dispatch.dispatchers.extras.EventBusType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// marked on the events that can be
// either posted by a @SubscribeEvent annotation or dispatched by the EventDispatcher
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HybridEvent {
     // Deprecated, will be only to both bus type, WILL be removed in the next update!
     @Deprecated(forRemoval = true)
     EventBusType value() default EventBusType.BOTH;
}
