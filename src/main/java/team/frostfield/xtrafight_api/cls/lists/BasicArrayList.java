package team.frostfield.xtrafight_api.cls.lists;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class BasicArrayList<T> extends ArrayList<T> implements BasicList<T> {

    protected static final int DEFAULT_CAPACITY = 64;

    public BasicArrayList() {
        super(DEFAULT_CAPACITY);
    }

    public BasicArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public BasicArrayList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }

    @Override
    public boolean addOnlyOnce(T t) {
        if (this.contains(t)) {
            return false;
        }
        return super.add(t);
    }

    @SafeVarargs
    public final boolean addAsArray(T... all) {
        if (all == null || all.length == 0) return false;
        return this.addAll(Arrays.asList(all));
    }

    @SafeVarargs
    public final boolean removeAsArray(T... all) {
        if (all == null || all.length == 0) return false;
        return this.removeAll(Arrays.asList(all));
    }

    @SafeVarargs
    public final boolean containsAllArray(T... all) {
        if (all == null || all.length == 0) return true;
        return this.containsAll(Arrays.asList(all));
    }

    @SafeVarargs
    public final boolean containsAny(T... all) {
        if (all == null) return false;
        for (T item : all) {
            if (this.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public int cut(int at) {
        if (at >= 0 && this.size() > at) {
            this.subList(at, this.size()).clear();
        }
        return this.size();
    }

    @SuppressWarnings("unchecked")
    public T[] getArray(Class<T> clazz) {
        T[] array = (T[]) Array.newInstance(clazz, this.size());
        return this.toArray(array);
    }

    public Object[] getObjectArray() {
        return this.toArray();
    }
}