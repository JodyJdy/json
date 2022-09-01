package com.example.json.mapper;

import com.example.json.parser.Value;
import com.example.json.type.FieldType;

/**
 *  用于映射对象到字符串
 */
public interface Mapper extends Comparable<Mapper>{

    int getOrder();

    Object resolveValue(FieldType fieldType, Value value);

    @Override
    default int compareTo(Mapper o) {
        return getOrder() - o.getOrder();
    }
}
