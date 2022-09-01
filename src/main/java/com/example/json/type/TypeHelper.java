package com.example.json.type;

import java.lang.reflect.*;
import java.util.*;

public class TypeHelper {

    /**
     * 将f中牵扯的所有泛型类型调整为具体的类型
     */
    public static FieldType specificGeneric(FieldType f, ClassType classType){
        if(f.getGeneric() != null){
            return classType.getGenericReference(f.getGeneric());
        }
        if(f.getObjType2() != null){
           f.setObjType2(specificGeneric(f.getObjType2(),classType));
        }
        if(f.getInnerParameterTypes() != null){
            List<FieldType> innerParameters = f.getInnerParameterTypes();
            innerParameters.replaceAll(fieldTypeReferece -> specificGeneric(fieldTypeReferece, classType));
        }
        return f;
    }

    public static FieldType generateFieldType(Field field){
        FieldType fieldType = resolveFieldType(field.getGenericType());
        fieldType.setField(field);
        return fieldType;
    }
    public static FieldType resolveFieldType(Type type){
        FieldType fieldType = new FieldType();
        //泛型类型
        if(type instanceof TypeVariable){
           fieldType.setGeneric(((TypeVariable<?>) type).getName());
       //带有参数类型的类型 List<V> list
        } else if(type instanceof ParameterizedType){
            fieldType.setObjType((Class<?>) ((ParameterizedType) type).getRawType());
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            List<FieldType> typeRefereceList = new ArrayList<>();
            for(Type t : types){
                typeRefereceList.add(resolveFieldType(t));
            }
            fieldType.setInnerParameterTypes(typeRefereceList);
        //上述两种类型的数组类型
        } else if(type instanceof GenericArrayType){
            fieldType.setArrayType(true);
            fieldType.setObjType2(resolveFieldType(((GenericArrayType) type).getGenericComponentType()));
        } else if(type instanceof Class){
            Class<?> clazz = (Class<?>) type;
            if(clazz.getComponentType() != null){
                fieldType.setArrayType(true);
                fieldType.setObjType2(resolveFieldType(clazz.getComponentType()));
            } else{
                fieldType.setObjType(clazz);
            }
        }
        return fieldType;
    }
    public static FieldType trans2FieldType(ClassType classType){
        FieldType fieldType = new FieldType();
        fieldType.setObjType(classType.getRawType());
        if(Objects.nonNull(classType.getGenericTypeMap())) {
            List<FieldType> fieldTypeList = new ArrayList<>(classType.getGenericTypeMap().values());
            fieldType.setInnerParameterTypes(fieldTypeList);
        }
        return fieldType;
    }
    /**
     * 将 FieldType转成 ClassType
     */
    public static ClassType<?> trans2ClassType(FieldType fieldType){
       return trans2ClassType(fieldType, fieldType.getObjType());
    }
    public static <T> ClassType<T> trans2ClassType(FieldType fieldType, Class<T> clazz){
        Map<String, FieldType> map = new HashMap<>();
        TypeVariable<Class<T>>[] generics = clazz.getTypeParameters();
        List<FieldType> paramFieldType = fieldType.getInnerParameterTypes();
        for(int i=0;i<generics.length;i++){
            map.put(generics[i].getName(), paramFieldType.get(i));
        }
        return new ClassType<>(map);
    }
}
