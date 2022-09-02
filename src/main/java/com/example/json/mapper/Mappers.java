package com.example.json.mapper;

import com.example.json.mapper.defaultmapper.*;

import java.lang.reflect.Field;
import java.util.*;

public class Mappers {
    /**
     * 缓存注解中定义的filter
     */
    private static final Map<Class<? extends Mapper>, Mapper> annotationMapperMap = new HashMap<>();
    /**
     *默认的MapperChain
     */
    private static final List<Mapper> mapperChain = new ArrayList<>();

    static {
        mapperChain.add(new JsonFieldMatchFieldAnnotationMapper());
        mapperChain.add(new BasicTypeMatchFieldClassMapper());
        mapperChain.add(new JsonCreatorClassMapper());
        mapperChain.add(new DateMatchFieldClassMapper());
        mapperChain.add(new ListSuperFieldClassMapper());
        mapperChain.add(new EnumSuperFieldClassMapper());
        mapperChain.add(new MapSuperFieldClassMapper());
        mapperChain.add(new SetSuperFieldClassMapper());
    }

    static {
        Collections.sort(mapperChain);
    }

    public static Mapper getAnnotationMapper(Class<? extends Mapper> mapperClazz) {
        if (annotationMapperMap.containsKey(mapperClazz)) {
            return annotationMapperMap.get(mapperClazz);
        }
        try {
            Mapper mapper = mapperClazz.getConstructor().newInstance();
            annotationMapperMap.put(mapperClazz, mapper);
            return mapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addMapper(Mapper mapper) {
        mapperChain.add(mapper);
        Collections.sort(mapperChain);
    }

    public static void removeMapper(int index) {
        mapperChain.remove(index);
        Collections.sort(mapperChain);
    }

    public static void removeAll() {
        mapperChain.clear();
    }

    public static Mapper doMap(Class clazz){
        for(Mapper m : mapperChain){
            if(m.doMap(clazz)){
                return m;
            }
        }
        return null;
    }
    public static Mapper doMap(Field field) {
        for (Mapper f : mapperChain) {
            if (f.doMap(field)) {
                return f;
            }
        }
        return null;
    }
    public static Mapper doMap(Object value) {
        for (Mapper f : mapperChain) {
            if (f.doMap(value)) {
                return f;
            }
        }
        return null;
    }
}
