package com.example.json.filter.defaultfilter;

import com.example.json.filter.MatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.json.json2obj.Json2Obj.getObjList;

public class LinkedListMatchClassFilter extends MatchClassFilter {
    public LinkedListMatchClassFilter() {
        super(LinkedList.class);
    }

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
        List<T> tList = new LinkedList<>();
        for (Object obj : objects) {
            tList.add(tClass.cast(obj));
        }
        return tList;
    }
}
