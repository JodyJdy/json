package com.example.json.mapper;

import com.example.json.parser.Value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class MatchFieldAnnotationMapper implements Mapper{
    protected final Class<? extends Annotation> c;

    public MatchFieldAnnotationMapper(Class<? extends Annotation> c) {
        this.c = c;
    }
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean doMap(Field field) {
        return false;
    }

}
