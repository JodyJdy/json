package com.example.json;



import com.example.json.json2obj.Json2Obj;
import com.example.json.obj2json.Obj2json;
import com.example.json.parser.JsonLexer;
import com.example.json.parser.JsonParser;
import com.example.json.type.ClassType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 反序列化时，可通过 自定义Filter扩展功能；可以将自定义的Filter添加到Filters:filterChain中发挥作用
 * 或者使用JsonField.filter注解指定反序列化某个Field使用的Filter;
 *
 * 序列化时，通过自定义Mapper扩展功能； 加入MapperChain，或者在JsonField.mapper中指定Mapper
 *
 * 还可以自定义 注解，添加处理注解用的Filter/Mapper到 FilterChain/MapperChain中
 *
 *   Field.name与 Json.key的转换关系，可以通过 JsonField注解指定，使用JsonField.name定义Field.name对应的Json.key
 *   或者使用JsonField.nameProvider定义转换类完成转换
 */
public class JSON {
    public static JsonParser getJsonParser(String text) {
        return new JsonParser(new JsonLexer(text));
    }
    public static JsonParser getJsonParser(InputStream inputStream) {
        try {
            return new JsonParser(new JsonLexer(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonParser getJsonParser(File file) {
        try {
            return new JsonParser(new JsonLexer(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonParser getJsonParser(InputStream inputStream, Charset charset) {
        try {
            return new JsonParser(new JsonLexer(inputStream, charset));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonParser getJsonParser(File file, Charset charset) {
        try {
            return new JsonParser(new JsonLexer(file, charset));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param clazz     生成obj数据类型
     * @param json      可以是普通json{},也可以是json数组[]，也可以是值, true,false....
     * @param classType T 类型中含有泛型时，使用ClassType传入泛型类型
     */
    public static <T> T getObj(Class<T> clazz, String json, ClassType classType) {
        return Json2Obj.getObj(clazz, getJsonParser(json).parseValue(), classType);
    }

    public static <T> T getObj(Class<T> clazz, String json) {
        return Json2Obj.getObj(clazz, getJsonParser(json).parseValue());
    }

    public static <T> T getObj(Class<T> clazz, JsonParser jsonParser, ClassType classType) {
        return Json2Obj.getObj(clazz, jsonParser.parseValue(), classType);
    }

    public static <T> T getObj(Class<T> clazz, JsonParser jsonParser) {
        return Json2Obj.getObj(clazz, jsonParser.parseValue());
    }

    /**
     * @param arrayClass    数组类型，如果要返回int数组，就传入 int[].class
     * @param json          json数组, []
     * @param componentType 数组元素 对应的ClassType
     */
    public static <T> T getArray(Class<T> arrayClass, String json, ClassType componentType) {
        return Json2Obj.getArray(arrayClass, getJsonParser(json).parseValue(), componentType);
    }

    public static <T> T getArray(Class<T> arrayClass, String json) {
        return Json2Obj.getArray(arrayClass, getJsonParser(json).parseValue());
    }

    public static <T> T getArray(Class<T> arrayClass, JsonParser jsonParser) {
        return Json2Obj.getArray(arrayClass, jsonParser.parseValue());
    }

    public static <T> T getArray(Class<T> arrayClass, JsonParser jsonParser, ClassType componentType) {
        return Json2Obj.getArray(arrayClass, jsonParser.parseValue(), componentType);
    }
    /**
     * @param contentClazz list元素类型
     * @param json         json数组, []
     * @param contentType  list元素 对应的ClassType
     */
    public static <T> List<T> getObjList(String json, Class<T> contentClazz, ClassType contentType) {
        return Json2Obj.getObjList(getJsonParser(json).parseJsonArray(), contentClazz, contentType);
    }

    public static <T> List<T> getObjList(String json, Class<T> contentClazz) {
        return Json2Obj.getObjList(getJsonParser(json).parseJsonArray(), contentClazz);
    }

    public static <T> List<T> getObjList(JsonParser jsonParser, Class<T> contentClazz, ClassType contentType) {
        return Json2Obj.getObjList(jsonParser.parseJsonArray(), contentClazz, contentType);
    }

    public static <T> List<T> getObjList(JsonParser jsonParser, Class<T> contentClazz) {
        return Json2Obj.getObjList(jsonParser.parseJsonArray(), contentClazz);
    }
    public static String toJson(Object obj){
        return Obj2json.value2String(Obj2json.getValue(obj));
    }

}
