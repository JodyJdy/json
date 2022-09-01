package com.example.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加在Enum中方法的注解，可以通过该方法获取到Enum对象，需要Enum中有相关实现,需要方法是静态的
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonEnumCreator {
    /**
     *  方法参数对应的 json 键的名称，数量，顺序要完全对应
     */
    String[] jsonFieldNames()default{};
}
