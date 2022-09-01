package com.example.json.filter.defaultfilter;

import com.example.json.filter.SuperClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.util.*;

import static com.example.json.json2obj.Json2Obj.getObjList;

public class SetSuperClassFilter extends SuperClassFilter {

    public SetSuperClassFilter() {
        super(Set.class, AbstractSet.class);
    }

    public Object resolveValue(FieldType fieldType, Value value) {
        if (value == null || value.isNull() || value.getArray() == null) {
            return null;
        }
        //Set内容类型
        FieldType contentType = fieldType.getInnerParameterTypes().get(0);
        List<Object> list = getObjList(value.getArray(), contentType);
        if (list.size() > 0) {
            return package2Generic(list.get(0).getClass(), list);
        }
        return new ArrayList<>();
    }

    private <T> Set<T> package2Generic(Class<T> tClass, List<Object> objects) {
        Set<T> tSet = new HashSet<>();
        for (Object obj : objects) {
            tSet.add(tClass.cast(obj));
        }
        return tSet;
    }
}
