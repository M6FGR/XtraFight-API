package team.frostfield.xtrafight_api.cls.loadable.enums;

import team.frostfield.xtrafight_api.cls.loadable.ILoadableClass;

public interface LoadableEnum {
    Class<? extends ILoadableClass> getLoadableClass();

    boolean isClientOnly();

}
