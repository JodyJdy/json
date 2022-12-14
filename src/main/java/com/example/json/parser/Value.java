package com.example.json.parser;

import java.util.List;

public class Value {

    int type;
    Boolean b;
    Number num;
    String v;
    Json j;
    List<Value> array;

    /**
     * 空对象
     * 记录空对象是有必要的，例如 Json中要 [1,2,null,null,3]，用户可能想将null调整成0，就可以制定filter对value进行处理
     * 如果直接返回null，无法在反序列化时得到处理
     */
    public Value(){
        this.type = ValueType.NULL;
    }

    public Value(Number num) {
        this.num = num;
        this.type = ValueType.NUM;
    }

    public Value(String v) {
        this.v = v;
        this.type = ValueType.STRING;
    }


    public Value(Boolean b) {
        this.b = b;
        this.type = ValueType.BOOL;
    }

    public Value(Json j) {
        this.j = j;
        this.type = ValueType.JSON;
    }

    public Value(List<Value> jsonArray) {
        this.array = jsonArray;
        this.type = ValueType.ARRAY;
    }

    public int getType() {
        return type;
    }

    public Boolean getB() {
        return b;
    }

    public String getV() {
        return v;
    }

    public Json getJ() {
        return j;
    }

    public List<Value> getArray() {
        return array;
    }
    public boolean isNull(){
        return this.type  == ValueType.NULL;
    }

    public Number getNum() {
        return num;
    }
}
