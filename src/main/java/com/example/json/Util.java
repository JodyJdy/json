package com.example.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Util {
    /**
     * 判断字段是不是 final 修饰的 常量
     */
    public static boolean isFinalOrStatic(Field field) {
        return Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers());

    }

    public static boolean isBasicType(Class clazz) {
        return clazz.isPrimitive() || Byte.class.equals(clazz) || Short.class.equals(clazz) || Integer.class.equals(clazz)
                || Long.class.equals(clazz) || Float.class.equals(clazz) || Double.class.equals(clazz) || Character.class.equals(clazz)
                || Boolean.class.equals(clazz) || String.class.equals(clazz);

    }


    public static Object getPrimitiveValueWhileNull(Class clazz) {
        if (int.class.equals(clazz)) {
            return 0;
        }
        if (short.class.equals(clazz)) {
            return (short) 0;
        }
        if (long.class.equals(clazz)) {
            return 0L;
        }
        if (byte.class.equals(clazz)) {
            return (byte) 0;
        }
        if (char.class.equals(clazz)) {
            return (char) 0;
        }
        if (float.class.equals(clazz)) {
            return 0.0f;
        }
        if (boolean.class.equals(clazz)) {
            return false;
        }
        if (double.class.equals(clazz)) {
            return 0.0;
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getPrimitiveValueFromString(Class<T> type, String value) {
        if (value == null || value.isBlank()) {
            return (T) getPrimitiveValueWhileNull(type);
        }
        if (Integer.class.equals(type) || int.class.equals(type)) {
            return (T) Integer.valueOf(value);
        }
        if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            return (T) Boolean.valueOf(value);
        } else if (Long.class.equals(type) || long.class.equals(type)) {
            return (T) Long.valueOf(value);
        } else if (type.equals(String.class)) {
            return (T) value;
        }
        if (Character.class.equals(type) || char.class.equals(type)) {
            return (T) Character.valueOf(value.charAt(0));
        }
        if (float.class.equals(type) || Float.class.equals(type)) {
            return (T) Float.valueOf(value);
        } else if (Double.class.equals(type) || double.class.equals(type)) {
            return (T) Double.valueOf(value);
        }
        if (short.class.equals(type) || Short.class.equals(type)) {
            return (T) Short.valueOf(value);
        }
        if (byte.class.equals(type) || Byte.class.equals(type)) {
            return (T) Byte.valueOf(value);
        }
        throw new RuntimeException("void type is not allowed");
    }

}
