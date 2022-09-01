package com.example.json.annotation;

import com.example.json.filter.Filter;
import com.example.json.provider.PropertyNameProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonField {
    /**
     * 使用注解制定过滤器
     */
    Class<? extends Filter> filter() default Filter.class;

    /**
     * 对应json中的key值
     */
    String jsonName() default "";

    /**
     * 使用PropertyNameProvider生成 json中的key值
     */
    Class<? extends PropertyNameProvider> nameProvider() default PropertyNameProvider.class;
}
