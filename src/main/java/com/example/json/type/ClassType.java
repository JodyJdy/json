package com.example.json.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于记录 json要转换成的类的类型
 */
public class ClassType<T> {

    private Class<?> rawType = null;

    private Map<String, FieldType> genericTypeMap = new LinkedHashMap<>();

    public ClassType(Map<String, FieldType> m) {
        this.genericTypeMap = m;
    }

    @SuppressWarnings("all")
    public ClassType() {
        // thisClass是一个临时的匿名类，
        Class<?> thisClass = this.getClass();
        //通过superClass获取到 TypeReference类型
        Type superClass = thisClass.getGenericSuperclass();

        if (superClass.getClass().isEnum()) {
            throw new RuntimeException("枚举类型无泛型类型，不允许使用无参构造函数创建ClasssType对象");
        }
        /// 实际要处理的类型
        Type processType = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        // 例如 Map<String,Integer>， 含有泛型参数
        if (processType instanceof ParameterizedType) {
            ParameterizedType argType = (ParameterizedType) processType;
            Class<?> rawType = (Class<?>) argType.getRawType();
            this.rawType = rawType;
            Type[] argTypes = argType.getActualTypeArguments();
            TypeVariable[] typeVariables = rawType.getTypeParameters();
            for (int i = 0; i < argTypes.length; i++) {
                genericTypeMap.put(typeVariables[i].getName(), TypeHelper.resolveFieldType(argTypes[i]));
            }
        } else if(processType instanceof Class){
            // 例如 Map.class 不含泛型参数
            Class argClass = (Class) processType;
            this.rawType = argClass;
            TypeVariable[] typeVariables = argClass.getTypeParameters();
            for (TypeVariable typeVariable : typeVariables) {
                genericTypeMap.put(typeVariable.getName(), TypeHelper.resolveFieldType(typeVariable.getBounds()[0]));
            }
        }
    }

    public FieldType getGenericReference(String generic) {
        if (genericTypeMap.containsKey(generic)) {
            return genericTypeMap.get(generic);
        }
        throw new RuntimeException("不存在泛型类型 " + generic + "的具体类型的映射");
    }

    public void setRawType(Class<?> rawType) {
        this.rawType = rawType;
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public Map<String, FieldType> getGenericTypeMap() {
        return genericTypeMap;
    }

    @Override
    public String toString() {
        return genericTypeMap.toString();
    }
}
