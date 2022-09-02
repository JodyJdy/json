package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.SuperFieldClassMapper;
import com.example.json.parser.Value;

import java.lang.reflect.Field;

public class EnumSuperFieldClassMapper extends SuperFieldClassMapper {
    public EnumSuperFieldClassMapper() {
        super();
    }

    @Override
    public boolean doMap(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public boolean doMap(Field field) {
        return field.getType().isEnum();
    }
    @Override
    public Value map(Field field, Object value) {
        return new Value(value.toString());
    }
}
