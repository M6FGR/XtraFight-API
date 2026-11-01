package team.frostfield.xtrafight_api.events.dispatch.abstracts;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import team.frostfield.xtrafight_api.events.dispatch.dispatchers.extras.EventPriority;

import java.lang.reflect.Modifier;

public abstract class DispatchEvent extends Event implements IModBusEvent {

    protected boolean canceled;
    protected EventPriority currentPriority;

    protected DispatchEvent() {
        super();
    }
    @OverrideOnly
    public void onDispatch() {}

    @OverrideOnly
    public void onPost() {}

    @OverrideOnly
    public void onCancel() {}


    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean canBeOverridden() {
        return true;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
        this.onCancel();
    }

    public EventPriority getPriority() {
        return this.currentPriority;
    }

    public void setPriority(EventPriority currentPriority) {
        this.currentPriority = currentPriority;
    }

    public String getEventClsName() {
        Class<?> cls = this.getClass();
        Class<?> superclass = cls.getSuperclass();

        if (superclass != null && superclass != Object.class && superclass != DispatchEvent.class && Modifier.isAbstract(superclass.getModifiers())) {
            return superclass.getSimpleName() + "#" + cls.getSimpleName();
        }

        return cls.getSimpleName();
    }
}
