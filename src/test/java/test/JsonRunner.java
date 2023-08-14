package test;

import com.example.json.JSON;
import com.example.json.type.ClassType;
import example.TestEnum3;

import java.util.*;

public class JsonRunner {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(JSON.getArray(int[][].class, "[[1,2],[3,4]]")));
        JSON.getObjList("[[1,2],[3,4]]", int[].class).forEach(x -> {
            System.out.println(Arrays.toString(x));
            ;
        });
        //复杂结构测试
        String json = "{\n" +
                "\n" +
                "\"i\":1,\n" +
                "\"is\":[1,2,3,4],\n" +

                "\"il1\":[1,2,3,4],\n" +
                "\"str\":\"helo world\",\n" +
                "\"c\":3,\n" +
                "\"d\":\"hello wor\",\n" +
                "\"e\":4,\n" +
                "\"s\":[5,6,7,8],\n" +
                "\"cc\":[[1,2,3],[4,5,6]],\n" +
                "\"xx\":[[1,2,3],[4,5,6]],\n" +
                "\"mm\":{\"1\":\"a\",\"2\":\"b\"},\n" +
                "\"vv\":{\"[1,2,3]\":\"helo\",\"[4,5,6]\":\"world\"}\n" +
                "\n" +
                "\n" +
                "}";
//       ClassType<A<Integer,String,Integer>> classType = new ClassType<>(){};
////       //反序列化测试
//        A a = JSON.getObj(A.class,json,classType);
//        //序列化测试
//        A b = JSON.getObj(A.class,JSON.toJson(a),classType);

        //language=JSON
        String json2 = "{\n" +
                "  \"a\": \"void\",\n" +
                "  \"sldf\":\"sldfj\"\n" +
                "  \n" +
                "}";
        ClassType<Map<String, String>> classType = new ClassType<>() {
        };
        Map<String, String> m = JSON.getObj(Map.class, json2, classType);
        System.out.println();


    }
    public static class A<C,D,E>{
        Integer i;
        Integer[] is;
        int il;
        short sht;
        long sdf;
        float ff;
        double sdfsdf;
        char csdc;
        int[] il1;
        String str;
        C c;
        D d;
        E E;
        //复杂对象注释掉，会上升一个数量级
        List<C> s;
        LinkedList<ArrayList<C>> cc;
        HashSet<C>[] xx;
        Map<C,D> mm;
        LinkedHashMap<LinkedList<C>,D> vv;
        TestEnum3 testEnum3;
    }


}
