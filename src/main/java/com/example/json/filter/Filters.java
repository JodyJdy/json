package com.example.json.filter;

import com.example.json.filter.defaultfilter.*;

import java.lang.reflect.Field;
import java.util.*;

public class Filters {

    /**
     * 缓存注解中定义的filter
     */
    private static final Map<Class<? extends Filter>, Filter> annotationFilterMap = new HashMap<>();

    /**
     * 默认的过滤器链，会将default filter目录下面的filter都加载进来
     */
    private static final List<Filter> filterChain = new ArrayList<>();

    static {
        filterChain.add(new JsonFieldMatchAnnotationFilter());
        filterChain.add(new JsonEnumCreatorMatchAnnotationFilter());
        filterChain.add(new BasicTypeMatchClassFilter());
        filterChain.add(new LinkedListMatchClassFilter());
        filterChain.add(new LinkedHashMapMatchClassFilter());
        filterChain.add(new DateMatchClassFilter());
        filterChain.add(new ListSuperClassFilter());
        filterChain.add(new SetSuperClassFilter());
        filterChain.add(new MapSuperClassFilter());
        filterChain.add(new EnumSuperClassFilter());
    }

    static {
        Collections.sort(filterChain);
    }

    public static void addAnnotationFilter(Class<? extends Filter> filterClazz, Filter filter) {
        annotationFilterMap.put(filterClazz, filter);
    }

    public static Filter getAnnotationFilter(Class<? extends Filter> filterClazz) {
        if (annotationFilterMap.containsKey(filterClazz)) {
            return annotationFilterMap.get(filterClazz);
        }
        try {
            Filter filter = filterClazz.getConstructor().newInstance();
            annotationFilterMap.put(filterClazz, filter);
            return filter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addFilter(Filter filter) {
        filterChain.add(filter);
        Collections.sort(filterChain);
    }

    public static void removeFilter(int index) {
        filterChain.remove(index);
        Collections.sort(filterChain);
    }

    public static void removeAll() {
        filterChain.clear();
    }

    public static Filter doFilter(Field field) {
        for (Filter f : filterChain) {
            if (f.doFilter(field)) {
                return f;
            }
        }
        return null;
    }

    public static Filter doFilter(Class<?> clazz) {
        for (Filter f : filterChain) {
            if (f.doFilter(clazz)) {
                return f;
            }
        }
        return null;
    }
}
