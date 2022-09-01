package com.example.json.filter.defaultfilter;

import com.example.json.filter.SuperClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static com.example.json.json2obj.Json2Obj.getObjList;

public class ListSuperClassFilter extends SuperClassFilter {
    public ListSuperClassFilter() {
        super(List.class, AbstractList.class);
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        if (value == null || value.isNull() || value.getArray() == null) {
            return null;
        }
        //List内容类型
        FieldType contentType = fieldType.getInnerParameterTypes().get(0);
        List<Object> list = getObjList(value.getArray(), contentType);
        if (list.size() > 0) {
            return package2Generic(list.get(0).getClass(), list);
        }
        return new ArrayList<>();
    }

    private <T> List<T> package2Generic(Class<T> tClass, List<Object> objects) {
        List<T> tList = new ArrayList<>();
        for (Object obj : objects) {
            tList.add(tClass.cast(obj));
        }
        return tList;
    }
}
