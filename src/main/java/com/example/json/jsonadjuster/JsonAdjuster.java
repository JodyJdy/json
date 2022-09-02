package com.example.json.jsonadjuster;

import com.example.json.annotation.JsonClass;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于调整最终生成的Json对象
 */
public interface JsonAdjuster {
    Value adjust(Value src);
    Map<Class<? extends JsonAdjuster>,JsonAdjuster> map = new HashMap<>();

    static JsonAdjuster getJsonAdjuster(Class<? extends JsonAdjuster> clazz){
        if(map.containsKey(clazz)){
            return map.get(clazz);
        }
        try {
            JsonAdjuster p = clazz.getConstructor().newInstance();
            map.put(clazz,p);
            return p;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static Value adjust(Class<?> clazz,Value value){
        JsonClass jsonClass = clazz.getAnnotation(JsonClass.class);
        if(jsonClass != null && !jsonClass.adjuster().equals(JsonAdjuster.class)){
            return getJsonAdjuster(jsonClass.adjuster()).adjust(value);
        }
        return value;
    }
}
