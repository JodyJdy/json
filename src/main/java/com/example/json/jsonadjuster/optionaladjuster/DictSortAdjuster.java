package com.example.json.jsonadjuster.optionaladjuster;

import com.example.json.jsonadjuster.JsonAdjuster;
import com.example.json.parser.Json;
import com.example.json.parser.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Json Key 按照 字典序列排列
 */
public class DictSortAdjuster implements JsonAdjuster {
    @Override
    public Value adjust(Value src) {
        if(src.getJ() == null){
            return src;
        }
        Json srcJson = src.getJ();
        List<String> keys = new ArrayList<>(srcJson.keySet());
        Collections.sort(keys);
        Json json = new Json();
        for(String k : keys){
            json.put(k,srcJson.get(k));
        }
        return new Value(json);
    }
}
