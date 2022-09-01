package com.example.json.mapper;

import com.example.json.parser.Json;

import java.lang.reflect.Field;
import java.util.Map;

/**
 *  用于映射对象到字符串
 *  "hello "
 */
public interface Mapper extends Comparable<Mapper>{

    int getOrder();

    boolean doMap(Field field);

    void map(Field field, Object value, Json resultJson);

    @Override
    default int compareTo(Mapper o) {
        return getOrder() - o.getOrder();
    }
}
