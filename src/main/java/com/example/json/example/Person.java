package com.example.json.example;

import com.example.json.annotation.JsonField;
import com.example.json.provider.nameprovider.LowerLinePropertyNameProvider;


public class Person<I> {

    @JsonField(nameProvider = LowerLinePropertyNameProvider.class)
    public I idName;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + idName +
                '}';
    }
}
