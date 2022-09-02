package com.example.json.mapper.defaultmapper;

import com.example.json.annotation.JsonEnumCreator;
import com.example.json.mapper.MatchFieldClassMapper;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.Json;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 如果枚举类中有一个方法上添加了 @JsonCreator注解就好生效
 */
public class JsonCreatorClassMapper extends MatchFieldClassMapper {
    public JsonCreatorClassMapper() {
        super();
    }
    @Override
    public boolean doMap(Class<?> clazz) {
        if(clazz.isEnum()){
            for(Method method : clazz.getDeclaredMethods()){
                 if(method.getAnnotation(JsonEnumCreator.class)!=null){
                     return true;
                 }
            }
        }
        return false;
    }

    @Override
    public boolean doMap(Field field) {
       return doMap(field.getType());
    }

    @Override
    public Value map(Field field, Object value) {
        if(value == null){
            return null;
        }
        Class<?> clazz =value.getClass();
        JsonEnumCreator jsonEnumCreator = null;
        for(Method method : clazz.getDeclaredMethods()){
           jsonEnumCreator = method.getAnnotation(JsonEnumCreator.class);
           if(jsonEnumCreator != null){
               break;
           }
        }
        assert jsonEnumCreator != null;
        String[] fieldNames = jsonEnumCreator.jsonFieldNames();
        Json json = new Json();
        for(String enumFieldName : fieldNames){
            try {
                Field f = clazz.getDeclaredField(enumFieldName);
                f.setAccessible(true);
                Object obj = f.get(value);
                json.put(enumFieldName, Obj2json.getValue(obj));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return new Value(json);
    }
}
