package com.example.json.example;

import com.example.json.annotation.JsonEnumCreator;

public enum TestEnum3 {
  A(1),B(2),C(3);

        int code;

    TestEnum3(int code){
        this.code = code;
    }

    @JsonEnumCreator(jsonFieldNames = {"code"})
    public static TestEnum3 get(int code){
        for(TestEnum3 t : TestEnum3.values()){
            if(t.code == code){
                return t;
            }
        }
        return null;
    }

}
