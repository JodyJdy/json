package com.example.json.annotation;

import com.example.json.provider.PropertyNameProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonClass {
    /**
     * 使用PropertyNameProvider生成 json中的key值
     */
    Class<? extends PropertyNameProvider> nameProvider() default PropertyNameProvider.class;
}
