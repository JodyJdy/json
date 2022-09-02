package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.SuperFieldClassMapper;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.Json;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Map;

public class MapSuperFieldClassMapper extends SuperFieldClassMapper {
    public MapSuperFieldClassMapper() {
        super(Map.class, AbstractMap.class);
    }
    @Override
    public Value map(Field field, Object value) {
        if(value == null){
            return null;
        }
        Map<Object,Object> map = (Map<Object, Object>) value;
        Json json = new Json();
        map.forEach((k,v)->{
            Value kValue = Obj2json.getValue(k);
            Value vValue = Obj2json.getValue(v);
            json.put(Obj2json.value2String(kValue),vValue);
        });
        return new Value(json);
    }
}
