package com.example.json.provider;

import com.example.json.annotation.JsonClass;
import com.example.json.annotation.JsonField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用于提供Java字段对应的json字段名
 */
public interface PropertyNameProvider {


    Map<Class<? extends PropertyNameProvider>,PropertyNameProvider> map = new HashMap<>();
    String provide(Field field);

    static PropertyNameProvider getPropertyNameProvider(Class<? extends PropertyNameProvider> clazz){
        if(map.containsKey(clazz)){
            return map.get(clazz);
        }
        try {
            PropertyNameProvider p = clazz.getConstructor().newInstance();
            map.put(clazz,p);
            return p;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static String getFieldName(Field field){
        JsonField jsonField = field.getAnnotation(JsonField.class);
        JsonClass jsonClass = field.getDeclaringClass().getAnnotation(JsonClass.class);
        String fieldName = field.getName();
        if (Objects.nonNull(jsonField)) {
            if (!jsonField.jsonName().isBlank()) {
                return  jsonField.jsonName();
            } else if (Objects.nonNull(jsonField.nameProvider())) {
                Class<? extends PropertyNameProvider> nameProvider = jsonField.nameProvider();
                if (! Objects.equals(PropertyNameProvider.class,nameProvider)) {
                   return getPropertyNameProvider(nameProvider).provide(field);
                }
            }
        }
        if (Objects.nonNull(jsonClass)) {
            Class<? extends PropertyNameProvider> nameProvider = jsonClass.nameProvider();
            if (! Objects.equals(PropertyNameProvider.class,nameProvider)) {
               return  getPropertyNameProvider(jsonClass.nameProvider()).provide(field);
            }
        }
        return fieldName;
    }
}
