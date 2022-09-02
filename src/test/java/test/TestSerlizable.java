package test;

import com.example.json.JSON;
import com.example.json.annotation.JsonField;
import com.example.json.provider.nameprovider.LowerLinePropertyNameProvider;

public class TestSerlizable {
    public static void main(String[] args) {
        System.out.println(JSON.toJson(new A()));

    }
    static class A{
        Integer aB = 1;
        @JsonField(nameProvider = LowerLinePropertyNameProvider.class)
        Integer helloWorld = 1;

        public A() {
        }
    }
}
