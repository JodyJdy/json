package com.example.json.annotation;

import com.example.json.filter.Filter;
import com.example.json.mapper.Mapper;
import com.example.json.provider.PropertyNameProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Filter用于反序列化  Mapper用于序列化
 * 为了能让序列化的对象和反序列化的能够互相转换 jsonName 和 PropertyNameProvider
 *  如果做了配置，序列化和反序列化都进行使用； 而不是专门为序列化或者反序列化配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonField {
    /**
     * 使用注解制定过滤器
     */
    Class<? extends Filter> filter() default Filter.class;
    /**
     * 使用注解制定映射器
     */
    Class<? extends Mapper> mapper() default Mapper.class;
    /**
     * 对应json中的key值
     */
    String jsonName() default "";
    /**
     * 使用PropertyNameProvider生成 Field对应json中的key值
     *
     */
    Class<? extends PropertyNameProvider> nameProvider() default PropertyNameProvider.class;
    /**
     *反序列化·
     */
    boolean deSerializable() default true;

    /**
     *序列化
     */
    boolean serializable() default true;

    /**
     * null值调整为 Json中的null
     */
    boolean serializableNullAsJsonNull() default false;

    /**
     * null 调整成空串
     */
    boolean serializableNullAsEmptyString() default false;
}
