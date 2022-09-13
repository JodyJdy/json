package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.SuperFieldClassMapper;
import com.example.json.parser.Value;

import java.lang.reflect.Field;

public class NumberSuperFieldClassMapper extends SuperFieldClassMapper {
    public NumberSuperFieldClassMapper() {
        super(Number.class);
    }

    @Override
    public Value map(Field field, Object value) {
        Number number = (Number) value;
        return new Value(number.doubleValue());
    }
}
