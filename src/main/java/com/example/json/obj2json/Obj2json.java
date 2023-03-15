package com.example.json.obj2json;

import com.example.json.Util;
import com.example.json.jsonadjuster.JsonAdjuster;
import com.example.json.mapper.Mapper;
import com.example.json.mapper.Mappers;
import com.example.json.parser.Json;
import com.example.json.parser.Value;
import com.example.json.provider.PropertyNameProvider;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Obj2json {
    public static Value getValue(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj.getClass().isArray()){
            List<Value> valueList = new ArrayList<>();
            int len = Array.getLength(obj);
            for(int i=0;i<len;i++){
                valueList.add(getValue(Array.get(obj,i)));
            }
            return new Value(valueList);
        }
        Mapper mapper = Mappers.doMap(obj);
        if (mapper != null) {
            return JsonAdjuster.adjust(obj.getClass(),mapper.map(null, obj));
        }
        mapper = Mappers.doMap(obj.getClass());
        if (mapper != null) {
            return JsonAdjuster.adjust(obj.getClass(),mapper.map(null, obj));
        }
        Class<?> clazz = obj.getClass();
        Json json = new Json();

        //获取所有当前类以及父类的Field
        List<Field> fieldList = Util.getAllFields(clazz);
        for (Field field : fieldList) {
            int modifier = field.getModifiers();
            field.setAccessible(true);
            //默认过滤加了transient关键字的内容
            if (Modifier.isTransient(modifier)) {
                continue;
            }
            Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            //按照值进行过滤
            Mapper mapper1 = Mappers.doMap(value);
            if (mapper1 != null) {
                putValue(mapper1, json, value, field);
                continue;
            }
            //按照Field进行过滤
            mapper1 = Mappers.doMap(field);
            if (mapper1 != null) {
                putValue(mapper1, json, value, field);
                continue;
            }
            //按照Class进行过滤
            mapper1 = Mappers.doMap(obj.getClass());
            if (mapper1 != null) {
                putValue(mapper1, json, value, field);
                continue;
            }
            //进行默认处理
            json.put(PropertyNameProvider.getFieldName(field),getValue(value));
        }
        return JsonAdjuster.adjust(obj.getClass(),new Value(json));
    }
    /**
     * Json中必要的引号，是在这里加的；
     */
    public static String value2String(Value value){
        if(value.getB() != null){
            return String.valueOf(value.getB());
        }
        if(value.getNum() != null){
            return String.valueOf(value.getNum());
        }
        if(value.getV()!=null){
            return  value.getV();
        }
        StringBuilder sb  = new StringBuilder();
        if(value.getArray() != null){
            sb.append("[");
            Iterator<Value> iterator = value.getArray().iterator();
            while (iterator.hasNext()){
                sb.append(value2String(iterator.next()));
                if(iterator.hasNext()){
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }
        if(value.getJ() != null){
            Json json = value.getJ();
            sb.append("{");
            Iterator<Map.Entry<String,Value>> entryIterator =json.entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<String,Value> entry = entryIterator.next();
                if(entry.getValue() != null) {
                    sb.append(Util.getStringWithQuote(entry.getKey())).append(":");
                    if (entry.getValue().getV() != null) {
                        sb.append(Util.getStringWithQuote(entry.getValue().getV()));
                    } else {
                        sb.append(value2String(entry.getValue()));
                    }
                    if (entryIterator.hasNext()) {
                        sb.append(",");
                    }
                }
            }
            sb.append("}");
            return sb.toString();
        }
        // new Value() 对应的是json中的null
        return "null";
    }
    private static void putValue(Mapper mapper, Json json, Object v, Field field) {
        Value value = mapper.map(field, v);
        if (value != null) {
            json.put(PropertyNameProvider.getFieldName(field), value);
        }
    }
}
