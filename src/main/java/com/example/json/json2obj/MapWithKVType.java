package com.example.json.json2obj;

import java.util.Map;

public class MapWithKVType {
    public Map<Object, Object> getMap() {
        return map;
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public Class<?> getValType() {
        return valType;
    }

    private final Map<Object,Object> map;

    public MapWithKVType(Map<Object, Object> map, Class<?> keyType, Class<?> valType) {
        this.map = map;
        this.keyType = keyType;
        this.valType = valType;
    }

    private final Class<?> keyType;
    private final Class<?> valType;
}
