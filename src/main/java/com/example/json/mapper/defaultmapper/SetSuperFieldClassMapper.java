package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.SuperFieldClassMapper;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetSuperFieldClassMapper extends SuperFieldClassMapper {
    public SetSuperFieldClassMapper() {
        super(Set.class, HashSet.class);
    }
    @Override
    public Value map(Field field, Object value) {
        if(value == null){
            return null;
        }
        Set set = (Set)value;
        List<Value> valueList = new ArrayList<>();
        set.forEach(o->{
            valueList.add(Obj2json.getValue(o));
        });
        return new Value(valueList);
    }
}
