package com.example.json.filter.defaultfilter;

import com.example.json.Util;
import com.example.json.annotation.JsonField;
import com.example.json.filter.Filter;
import com.example.json.filter.Filters;
import com.example.json.filter.MatchAnnotationFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.lang.reflect.Field;
import java.util.Objects;

public class JsonFieldMatchAnnotationFilter extends MatchAnnotationFilter {

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        JsonField jsonField = (JsonField) fieldType.getField().getAnnotation(c);
        //不进行反序列化
        if(!jsonField.deSerializable()){
            Class<?> clazz = fieldType.getObjType();
            if (fieldType.getObjType().isPrimitive()) {
                return Util.getPrimitiveValueWhileNull(clazz);
            }
            return null;
        }
        Filter filter = Filters.getAnnotationFilter(jsonField.filter());
        return filter.resolveValue(fieldType, value);
    }

    public JsonFieldMatchAnnotationFilter() {
        super(JsonField.class);
    }

    @Override
    public boolean doFilter(Field field) {
        if (field == null) {
            return false;
        }
        JsonField jsonField = (JsonField) field.getAnnotation(c);
        if(Objects.nonNull(jsonField)) {
            //不进行反序列化时
            if(!jsonField.deSerializable()){
                return true;
            }
            //手动指定了Filter时
            if(!Objects.equals(Filter.class, jsonField.filter())){
                return true;
            }
        }
        return false;
    }

}
