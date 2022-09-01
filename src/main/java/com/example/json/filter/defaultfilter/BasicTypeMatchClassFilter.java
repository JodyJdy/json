package com.example.json.filter.defaultfilter;

import com.example.json.Util;
import com.example.json.filter.MatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;


public class BasicTypeMatchClassFilter extends MatchClassFilter {
    public BasicTypeMatchClassFilter() {
        super(byte.class, Byte.class, Boolean.class, boolean.class, Character.class, char.class, Short.class, short.class
                , Integer.class, int.class, Long.class, long.class, float.class, Float.class, Double.class, double.class, String.class);
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        if (value == null || value.isNull()) {
            Class clazz = fieldType.getObjType();
            if (fieldType.getObjType().isPrimitive()) {
                return Util.getPrimitiveValueWhileNull(clazz);
            }
            return null;
        }
        return getPrimitiveValue(fieldType.getObjType(), value);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getPrimitiveValue(Class<T> type, Value value) {
        if (Integer.class.equals(type) || int.class.equals(type)) {
            return (T) Integer.valueOf(value.getL().intValue());
        }
        if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            return (T) value.getB();
        }
        if (Long.class.equals(type) || long.class.equals(type)) {
            return (T) value.getL();
        }
        if (type.equals(String.class)) {
            return (T) value.getV();
        }
        if (Character.class.equals(type) || char.class.equals(type)) {
            return (T) Character.valueOf(value.getV().charAt(0));
        }
        if (float.class.equals(type) || Float.class.equals(type)) {
            return (T) Float.valueOf(value.getD().floatValue());
        }
        if (Double.class.equals(type) || double.class.equals(type)) {
            return (T) value.getD();
        }
        if (short.class.equals(type) || Short.class.equals(type)) {
            return (T) Short.valueOf(value.getL().shortValue());
        }
        if (byte.class.equals(type) || Byte.class.equals(type)) {
            return (T) Byte.valueOf(value.getL().byteValue());
        }
        throw new RuntimeException("void type is not allowed");
    }
}
