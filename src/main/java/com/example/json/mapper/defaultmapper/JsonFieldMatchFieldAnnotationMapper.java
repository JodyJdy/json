package com.example.json.mapper.defaultmapper;

import com.example.json.annotation.JsonField;
import com.example.json.mapper.Mapper;
import com.example.json.mapper.Mappers;
import com.example.json.mapper.MatchFieldAnnotationMapper;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.Value;
import java.lang.reflect.Field;

public class JsonFieldMatchFieldAnnotationMapper extends MatchFieldAnnotationMapper {
    public JsonFieldMatchFieldAnnotationMapper() {
        super(JsonField.class);
    }
    @Override
    public boolean doMap(Field field) {
        return  field.getAnnotation(JsonField.class) != null;
    }

    @Override
    public Value map(Field field, Object value) {
        JsonField jsonField = field.getAnnotation(JsonField.class);
        if(!jsonField.serializable()){
            return null;
        }
        if(jsonField.mapper() != null && !jsonField.mapper().equals(Mapper.class)){
            return Mappers.getAnnotationMapper(jsonField.mapper()).map(field,value);
        }
        if(jsonField.serializableNullAsJsonNull() && value == null){
             return new Value();
        }
        if(jsonField.serializableNullAsEmptyString() && value == null){
            return new Value("");
        }
        return Obj2json.getValue(value);
    }
}
