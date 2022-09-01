package com.example.json.provider.nameprovider;

import com.example.json.provider.PropertyNameProvider;

import java.lang.reflect.Field;

/**
 * 驼峰转下划线
 */
public class LowerLinePropertyNameProvider implements PropertyNameProvider {
    @Override
    public String provide(Field field) {
        String init = field.getName();
        StringBuilder sb = new StringBuilder();
        for(char ch : init.toCharArray()){
            if(Character.isUpperCase(ch)){
                sb.append('_').append(Character.toLowerCase(ch));
            } else{
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
