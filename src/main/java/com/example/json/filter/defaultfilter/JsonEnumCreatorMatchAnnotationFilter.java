package com.example.json.filter.defaultfilter;

import com.example.json.annotation.JsonEnumCreator;
import com.example.json.filter.MatchAnnotationFilter;
import com.example.json.json2obj.Json2Obj;
import com.example.json.parser.Json;
import com.example.json.parser.Value;
import com.example.json.type.ClassType;
import com.example.json.type.FieldType;
import com.example.json.type.TypeHelper;

import java.lang.reflect.*;

public class JsonEnumCreatorMatchAnnotationFilter extends MatchAnnotationFilter {
    private Method creator = null;

    public JsonEnumCreatorMatchAnnotationFilter() {
        super(JsonEnumCreator.class);
    }

    @Override
    public boolean doFilter(Class<?> clazz) {
        if (clazz.isEnum()) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers()) && method.getAnnotation(c) != null) {
                    creator = method;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        //将枚举类当做对象处理
        if (value == null || value.isNull() || value.getJ() == null) {
            return null;
        }
        JsonEnumCreator jsonEnumCreator = creator.getAnnotation(JsonEnumCreator.class);
        String[] fieldName = jsonEnumCreator.jsonFieldNames();
        Parameter[] parameters = creator.getParameters();
        ClassType enumClassType = TypeHelper.trans2ClassType(fieldType);
        Json paramValues = value.getJ();
        //参照getObj里面对field处理的流程
        Object[] reallyParams = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter p = parameters[i];
            Type paramType = p.getType();
            FieldType paramFieldType = TypeHelper.resolveFieldType(paramType);
            Value paramValue = paramValues.get(fieldName[i]);
            reallyParams[i] = Json2Obj.getValue(paramValue, paramFieldType);
        }
        try {
            return creator.invoke(null, reallyParams);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
