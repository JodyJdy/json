package com.example.json.filter;

import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.lang.reflect.Field;

/**
 * 使用Filter完成json到对象的转换
 * 使用注解定义的Filter要比FilterChain中的优先级高
 */
public interface Filter extends Comparable<Filter> {
    default boolean doFilter(Class<?> clazz) {
        return false;
    }

    default boolean doFilter(Field field) {
        return false;
    }

    /**
     * order是Filter作用的顺序，小的先执行
     * MatchAnnotationFilter.order = MATCH_ANNOTATION_FILTER0
     * MatchClassFilter.order = MATCH_CLASS_FILTER1
     * SuperClassFilter.order = SUPER_CLASS_FILTER2
     */
    int getOrder();

    Object resolveValue(FieldType fieldType, Value value);

    @Override
    default int compareTo(Filter o) {
        return getOrder() - o.getOrder();
    }


    int MATCH_ANNOTATION_FILTER = 100;
    int MATCH_CLASS_FILTER = 200;
    int SUPER_CLASS_FILTER = 300;

}
