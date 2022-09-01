package com.example.json.type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于记录要转换成的json类中字段的类型，有以下分类
 */
public class FieldType implements Cloneable {
    /**
     * 存储Field信息，FieldType是嵌套的结果，只在最外层有填充Field的必要
     */
    private Field field = null;

    /**
     * 是否是数组类型
     */
    private boolean arrayType = false;

    public Field getField() {
        return field;
    }

    /**
     * 如果是泛型类型，泛型的名称
     */
    private String generic = null;
    /**
     * 对象类型
     */
    private Class<?> objType = null;

    /**
     * 数组component类型
     */
    private FieldType objType2 = null;

    public void setObjType2(FieldType objType2) {
        this.objType2 = objType2;
    }

    public FieldType getObjType2() {
        return objType2;
    }

    /**
     * 对象内部类型
     * Map<K,V> m;    K,V就是内部类型
     */
    private List<FieldType> innerParameterTypes = null;

    public boolean isArrayType() {
        return arrayType;
    }

    public void setArrayType(boolean arrayType) {
        this.arrayType = arrayType;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public Class<?> getObjType() {
        return objType;
    }

    public void setObjType(Class<?> objType) {
        this.objType = objType;
    }

    public List<FieldType> getInnerParameterTypes() {
        return innerParameterTypes;
    }

    public void setInnerParameterTypes(List<FieldType> innerParameterTypes) {
        this.innerParameterTypes = innerParameterTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isArrayType()) {
            sb.append("[");
        }
        if (generic != null) {
            sb.append(generic);
        }
        if (objType != null) {
            sb.append(objType.getName());
        }
        if (objType2 != null) {
            sb.append(objType2);
        }
        if (innerParameterTypes != null) {
            sb.append("<");
            List<String> paramStrs = new ArrayList<>();
            for (FieldType f : innerParameterTypes) {
                paramStrs.add(f.toString());
            }
            sb.append(String.join(",", paramStrs));
            sb.append(">");
        }
        return sb.toString();
    }

    @Override
    protected FieldType clone() {
        FieldType fi = new FieldType();
        fi.setArrayType(this.arrayType);
        fi.setGeneric(this.getGeneric());
        fi.setObjType(this.objType);
        if (this.objType2 != null) {
            fi.setObjType2(this.objType2.clone());
        }
        if (this.innerParameterTypes != null) {
            List<FieldType> innerParameters = new ArrayList<>();
            for (FieldType ff : this.innerParameterTypes) {
                innerParameters.add(ff.clone());
            }
            fi.setInnerParameterTypes(innerParameters);
        }
        return fi;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
