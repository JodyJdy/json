package test;

import com.example.json.JSON;
import com.example.json.annotation.JsonField;
import com.example.json.provider.nameprovider.LowerLinePropertyNameProvider;
import example.TestEnum3;

import java.util.HashMap;
import java.util.Map;

public class TestSerlizable {
    public static void main(String[] args) {
       A a = JSON.getObj(A.class,"{'a':1}");
        System.out.println(a);

    }
    static class A{
            double a,b,c;

        public A() {
        }
    }
    static class b{
        String c,d,e;
    }
}
