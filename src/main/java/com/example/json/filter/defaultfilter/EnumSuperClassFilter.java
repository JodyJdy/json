package com.example.json.filter.defaultfilter;

import com.example.json.filter.MatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

/**
 * 处理枚举类的转换，默认 按照enum.name()转换
 */
public class EnumSuperClassFilter extends MatchClassFilter {
    public EnumSuperClassFilter() {
        super();
    }

    @Override
    public boolean doFilter(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        return resolve(fieldType.getObjType(), value);
    }

    private Object resolve(Class<?> enumType, Value value) {
        if (value == null || value.isNull()) {
            return null;
        }
        Enum<?>[] enums = (Enum<?>[]) enumType.getEnumConstants();
        for (Enum<?> e : enums) {
            if (e.name().equals(value.getV())) {
                return e;
            }
        }
        return null;
    }
}
