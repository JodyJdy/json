package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.MatchFieldClassMapper;
import com.example.json.parser.Value;

import java.lang.reflect.Field;

public class BasicTypeMatchFieldClassMapper extends MatchFieldClassMapper {
    public BasicTypeMatchFieldClassMapper() {
        super(byte.class, Byte.class, Boolean.class, boolean.class, Character.class, char.class, Short.class, short.class
                , Integer.class, int.class, Long.class, long.class, float.class, Float.class, Double.class, double.class, String.class);
    }

    @Override
    public Value map(Field field, Object value ) {
        if(value == null){
            return null;
        }
        return getValueFromPrimitive(value);
    }

    private static  Value getValueFromPrimitive(Object primitive) {
        Class<?> clazz = primitive.getClass();
        if (Integer.class.equals(clazz)) {
            return  new Value(Long.valueOf(primitive.toString()));
        }
        if (Boolean.class.equals(clazz)) {
            return  new Value((boolean)primitive);
        }
        if (Long.class.equals(clazz)) {
            return  new Value((long)primitive);
        }
        //这里没有对字符串加引号
        if (clazz.equals(String.class)) {
            return  new Value(String.valueOf(primitive));
        }
        if (Character.class.equals(clazz)) {
            return  new Value(String.valueOf(primitive));
        }
        if (Float.class.equals(clazz)) {
            return  new Value(Double.valueOf(primitive.toString()));
        }
        if (Double.class.equals(clazz)) {
            return  new Value(Double.valueOf(primitive.toString()));
        }
        if (Short.class.equals(clazz)) {
            return  new Value(Long.valueOf(primitive.toString()));
        }
        if (Byte.class.equals(clazz)) {
            return new Value(Long.valueOf(primitive.toString()));
        }
        throw new RuntimeException("不支持的类型");
    }

}
