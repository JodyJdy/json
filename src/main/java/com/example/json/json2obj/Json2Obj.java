package com.example.json.json2obj;

import com.example.json.Util;
import com.example.json.filter.Filter;
import com.example.json.filter.Filters;
import com.example.json.parser.*;
import com.example.json.provider.PropertyNameProvider;
import com.example.json.type.*;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static com.example.json.Util.*;

public class Json2Obj {
    /**
     * 暴露的几个API
     */

    public static <T> List<T> getObjList(List<Value> values, Class<T> contentClazz ,ClassType<T> contentType ) {
        List<T> objects = new ArrayList<>();
        FieldType fieldType = TypeHelper.resolveFieldType(contentType.getRawType());
        fieldType = TypeHelper.specificGeneric(fieldType,contentType);
        for (Value v : values) {
            objects.add(contentClazz.cast(getValue(v, fieldType)));
        }
        return objects;
    }
    public static <T> List<T> getObjList(List<Value> values, Class<T> contentClazz) {
        return getObjList(values,contentClazz,getDefaultClassType(contentClazz));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getArray(Class<T> arrayClass,Value values,ClassType componentType){
        Objects.requireNonNull(values);
        Objects.requireNonNull(values.getArray());
        List<Value> array = values.getArray();
        List<Object> res = new ArrayList<>();
        FieldType fieldType = TypeHelper.resolveFieldType(componentType.getRawType());
        fieldType = TypeHelper.specificGeneric(fieldType,componentType);
        for(Value v : array){
            res.add(getValue(v,fieldType));
        }
        T t = (T) Array.newInstance(componentType.getRawType(), res.size());
        for (int i = 0; i < res.size(); i++) {
            Array.set(t,i,res.get(i));
        }
        return t;
    }
    @SuppressWarnings("unchecked")
    private static <T> ClassType<T> getDefaultClassType(Class<T> clazz){
        ClassType<T> classType = new ClassType(new HashMap<>());
        classType.setRawType(clazz);
        return classType;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getArray(Class<T> arrayClass,Value values){
        ClassType classType = new ClassType(new HashMap<>());
        classType.setRawType(arrayClass.getComponentType());
        return getArray(arrayClass,values,classType);
    }
    public static <T> T getObj(Class<T> clazz, Json json) {
        return getObj(clazz, json,getDefaultClassType(clazz));
    }

    /**
     * 通过Value对象，不用要求传入的文本一定是 {}的json
     */
    public static <T> T getObj(Class<T> clazz, Value value, ClassType classType) {
        FieldType fieldType = TypeHelper.resolveFieldType(clazz);
        fieldType = TypeHelper.specificGeneric(fieldType,classType);
        //如果要转换成的类，可以被Filter处理，使用Filter处理
        Filter filter = Filters.doFilter(clazz);
        if (Objects.nonNull(filter)) {
            return clazz.cast(getValue( value, fieldType));
        }
        return getObj(clazz,value.getJ(),classType);
    }
    public static <T> T getObj(Class<T> clazz, Value value) {
        ClassType defaultClassType = getDefaultClassType(clazz);
        FieldType fieldType = TypeHelper.resolveFieldType(clazz);
        //如果要转换成的类，可以被Filter处理，使用Filter处理
        Filter filter = Filters.doFilter(clazz);
        if (Objects.nonNull(filter)) {
            return clazz.cast(getValue(value, fieldType));
        }
        return getObj(clazz,value.getJ(),defaultClassType);
    }

    /**
     *通过 Json转成对象
     */
    public static <T> T getObj(Class<T> clazz, Json json, ClassType classType) {
        if (json == null) {
            return null;
        }
        //将ClassType转成FieldType
        FieldType fieldType = TypeHelper.trans2FieldType(classType);
        fieldType = TypeHelper.specificGeneric(fieldType,classType);
        //如果要转换成的类，可以被Filter处理，使用Filter处理
        Filter filter = Filters.doFilter(clazz);
        if (Objects.nonNull(filter)) {
            return clazz.cast(getValue(new Value(json), fieldType));
        }
        try {
            Constructor<T> constructor = clazz.getConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            constructor.setAccessible(false);
            List<Field> fieldList = Util.getAllFields(clazz);
            for (Field field : fieldList) {
                if (isFinalOrStatic(field)) {
                    continue;
                }
                field.setAccessible(true);
                //即使v==null也不能跳过，因为可能有自定义的filter 为其设置默认值
                Value v = json.get(PropertyNameProvider.getFieldName(field));
                FieldType f = TypeHelper.generateFieldType(field);
                //将泛型类型调整为具体的类型
                f = TypeHelper.specificGeneric(f, classType);
                f.setField(field);
                field.set(instance, getValue(v, f));
                field.setAccessible(false);
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 外部调用api 结尾
     */


    private static Object getObjArray(FieldType field, Value array) {
        if(array == null){
            return null;
        }
        List<Value> values = array.getArray();
        //数组内部类型
        FieldType componentType = field.getObjType2();
        List<Object> objects = getObjList(values, componentType);
        //使用子类类型创建数组
        Object result;
        //如果是基本类型，需要使用componentType，如果使用getClass获取到的是Integer.class这类属性
        if(componentType.getObjType() != null && componentType.getInnerParameterTypes() == null){
            result = Array.newInstance(componentType.getObjType(),objects.size());
        } else{
            result = Array.newInstance(objects.get(0).getClass(), objects.size());
        }
        for (int i = 0; i < objects.size(); i++) {
            Array.set(result,i,objects.get(i));
        }
        return result;
    }

    public static MapWithKVType getObjMap(Json json, FieldType kType, FieldType vType) {
        Map<Object, Object> map = new HashMap<>();
        Class<?> keyClass = null;
        Class<?> valClass = null;
        for (Map.Entry<String, Value> entry : json.entrySet()) {
            Object key = getMapKey(entry.getKey(), kType);
            Object val = getValue(entry.getValue(), vType);
            if (keyClass == null) {
                keyClass = key.getClass();
            }
            if (valClass == null) {
                valClass = val.getClass();
            }
            map.put(key, val);
        }
        return new MapWithKVType(map, keyClass, valClass);
    }

    public static List<Object> getObjList(List<Value> values, FieldType contentType) {
        List<Object> objects = new ArrayList<>();
        for (Value v : values) {
            objects.add(getValue(v, contentType));
        }
        return objects;
    }

    @SuppressWarnings("unchecked")
    public static Object getValue(Value value, FieldType fieldType) {
        //数组类型
        if (fieldType.isArrayType()) {
            return getObjArray(fieldType, value);
        }
        Class<?> clazz = fieldType.getObjType();
        //对Field进行过滤 ,Field上面注解优先生效
        Filter filter = Filters.doFilter(fieldType.getField());
        if(filter != null){
            return filter.resolveValue(fieldType,value);
        }
        //对class进行过滤，包含了对基础类型的处理
        filter = Filters.doFilter(clazz);
        if (filter != null) {
            return filter.resolveValue(fieldType, value);
        }
        ClassType<?> classType =getDefaultClassType(clazz);
        //说明class有参数类型
        if (fieldType.getInnerParameterTypes() != null) {
            classType = TypeHelper.trans2ClassType(fieldType);
        }
        return getObj(clazz, value.getJ(), classType);
    }


    public static Object getMapKey(String value, FieldType keyType) {
        Object result;
        if (value == null) {
            return null;
        }
        if (Util.isBasicType(keyType.getObjType())) {
            return getPrimitiveValueFromString(keyType.getObjType(), value);
        }
        if (keyType.getObjType().equals(Object.class)) {
            return value;
        } else {
            //如果key能被解释为对象
            Value v = new JsonParser(new JsonLexer(value)).parseValue();
            result = getValue(v, keyType);
        }
        return result;
    }

}
