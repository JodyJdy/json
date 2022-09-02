package com.example.json.mapper;

import com.example.json.parser.Value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class MatchClassAnnotationMapper implements Mapper {
    protected final Class<? extends Annotation> c;

    public MatchClassAnnotationMapper(Class<? extends Annotation> c) {
        this.c = c;

    }

    @Override
    public int getOrder() {
        return MATCH_CLASS_ANNOTATION;
    }


    @Override
    public Value map(Field field, Object value) {
        return null;
    }
}
