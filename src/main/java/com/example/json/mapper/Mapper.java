package com.example.json.mapper;

import com.example.json.parser.Json;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.util.Map;

/**
 *  用于映射对象到字符串
 *  "hello "
 */
public interface Mapper extends Comparable<Mapper>{

    /**
     * 表示mapper的优先级
     * MatchFieldAnnotationMapper > MatchFieldClassMapper >
     * SuperFieldClassMapper > MatchClassAnnotationMapper
     */
    int getOrder();

    default boolean doMap(Field field){
        return false;
    }
    default boolean doMap(Object value){
        return false;
    }
    default boolean doMap(Class<?> clazz){
        return false;
    }

    Value map(Field field, Object value);

    @Override
    default int compareTo(Mapper o) {
        return getOrder() - o.getOrder();
    }

    int MATCH_FIELD_ANNOTATION = 100;
    int MATCH_FIELD_CLASS = 200;
    int SUPER_FIELD_CLASS =300;
    int MATCH_CLASS_ANNOTATION = 400;
}
