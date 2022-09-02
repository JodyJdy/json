package com.example.json.mapper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class SuperFieldClassMapper implements Mapper{
    public SuperFieldClassMapper(Class<?>... superClasses) {
        this.superClasses = superClasses;
    }

    private Class<?>[] superClasses;
    private Set<Integer> sets = new HashSet<>(256);

    @Override
    public boolean doMap(Field field) {
        return doMap(field.getType());
    }
    public boolean doMap(Class<?> clazz) {
        if (sets.contains(clazz.hashCode())) {
            return true;
        }
        for (Class<?> fa : superClasses) {
            if (fa.isAssignableFrom(clazz)) {
                sets.add(clazz.hashCode());
                return true;
            }
        }
        return false;
    }
    @Override
    public int getOrder() {
        return SUPER_FIELD_CLASS;
    }
}
