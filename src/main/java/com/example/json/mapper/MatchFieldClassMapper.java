package com.example.json.mapper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public abstract class MatchFieldClassMapper implements Mapper{
    protected Class<?>[] targets;

    private Set<Integer> hashCodes = new HashSet<>();

    public MatchFieldClassMapper(Class<?>... targets) {
        this.targets = targets;
        for (Class<?> c : targets) {
            hashCodes.add(c.hashCode());
        }
    }

    @Override
    public boolean doMap(Class<?> clazz) {
       return hashCodes.contains(clazz.hashCode());
    }

    @Override
    public boolean doMap(Field field) {
        return hashCodes.contains(field.getType().hashCode());
    }
    @Override
    public int getOrder() {
        return MATCH_FIELD_CLASS;
    }
}
