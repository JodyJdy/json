package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.SuperFieldClassMapper;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.Json;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSuperFieldClassMapper extends SuperFieldClassMapper {
    public ListSuperFieldClassMapper() {
        super(List.class, AbstractList.class);
    }

    @Override
    public Value map(Field field, Object value) {
        if(value == null){
            return null;
        }
       List list = (List)value;
       List<Value> values = new ArrayList<>();
       for(Object obj : list){
           values.add(Obj2json.getValue(obj));
       }
       return new Value(values);
    }

}
