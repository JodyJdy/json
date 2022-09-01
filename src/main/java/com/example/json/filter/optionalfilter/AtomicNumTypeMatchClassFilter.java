package com.example.json.filter.optionalfilter;

import com.example.json.filter.MatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 常用原子类
 */
public class AtomicNumTypeMatchClassFilter extends MatchClassFilter {
    public AtomicNumTypeMatchClassFilter() {
        super(AtomicBoolean.class, AtomicInteger.class, AtomicLong.class);
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        Class clazz = fieldType.getObjType();
        if (clazz.equals(AtomicBoolean.class)) {
            return new AtomicBoolean(value.getB());
        }
        if (clazz.equals(AtomicInteger.class)) {
            return new AtomicInteger(value.getL().intValue());
        }
        if (clazz.equals(AtomicLong.class)) {
            return new AtomicLong(value.getL());
        }
        return null;
    }
}
