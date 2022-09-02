package com.example.json.annotation;

import com.example.json.jsonadjuster.JsonAdjuster;
import com.example.json.provider.PropertyNameProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 序列化，反序列化时nameProvider都会生效
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonClass {
    /**
     * 使用PropertyNameProvider生成 json中的key值
     */
    Class<? extends PropertyNameProvider> nameProvider() default PropertyNameProvider.class;


    Class<? extends JsonAdjuster> adjuster() default JsonAdjuster.class;
}
