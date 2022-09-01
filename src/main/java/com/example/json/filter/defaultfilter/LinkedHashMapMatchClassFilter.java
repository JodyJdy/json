package com.example.json.filter.defaultfilter;

import com.example.json.filter.MatchClassFilter;
import com.example.json.json2obj.MapWithKVType;
import com.example.json.parser.Json;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.json.json2obj.Json2Obj.getObjMap;

public class LinkedHashMapMatchClassFilter extends MatchClassFilter {
    public LinkedHashMapMatchClassFilter() {
        super(LinkedHashMap.class);
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        if (value == null || value.isNull() || value.getJ() == null) {
            return null;
        }
        FieldType keyType = fieldType.getInnerParameterTypes().get(0);
        FieldType valueType = fieldType.getInnerParameterTypes().get(1);
        Json json = value.getJ();
        MapWithKVType mapWithKVType = getObjMap(json, keyType, valueType);
        if (mapWithKVType.getMap().size() > 0) {
            return package2Generic(mapWithKVType.getKeyType(), mapWithKVType.getValType(), mapWithKVType.getMap());
        }
        return new HashMap<>();
    }


    private <K, V> Map<K, V> package2Generic(Class<K> kClass, Class<V> vClass, Map<Object, Object> map) {
        Map<K, V> kvMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            kvMap.put(kClass.cast(k), vClass.cast(v));
        });
        return kvMap;
    }
}
