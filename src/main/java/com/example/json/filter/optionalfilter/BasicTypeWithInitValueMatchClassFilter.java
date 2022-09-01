package com.example.json.filter.optionalfilter;

import com.example.json.filter.defaultfilter.BasicTypeMatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

/**
 * 如果json中不含有基础类型 对应的value，为其设置默认值
 */
public class BasicTypeWithInitValueMatchClassFilter extends BasicTypeMatchClassFilter {
    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        Class<?> type = fieldType.getObjType();
        if (value == null || value.isNull()) {
            if (Integer.class.equals(type) || int.class.equals(type)) {
                return 0;
            }
            if (Boolean.class.equals(type) || boolean.class.equals(type)) {
                return false;
            }
            if (Long.class.equals(type) || long.class.equals(type)) {
                return 0L;
            }
            if (type.equals(String.class)) {
                return "";
            }
            if (Character.class.equals(type) || char.class.equals(type)) {
                return null;
            }
            if (float.class.equals(type) || Float.class.equals(type)) {
                return 0F;
            }
            if (Double.class.equals(type) || double.class.equals(type)) {
                return 0.0;
            }
            if (short.class.equals(type) || Short.class.equals(type)) {
                return (short) 0;
            }
            if (byte.class.equals(type) || Byte.class.equals(type)) {
                return (byte) 0;
            }
        }
        return super.resolveValue(fieldType, value);
    }
}
