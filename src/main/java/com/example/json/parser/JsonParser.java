package com.example.json.parser;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private  final JsonLexer jsonLexer;

    private void skipComment(){
        while (jsonLexer._token.getType() == TokenType.SINGLE_LINE_COMMENT || jsonLexer._token.getType() == TokenType.MULTI_LINE_COMMENT){
            next();
        }
    }
    private void match(int i){
        if(jsonLexer._token.getType() == i){
            jsonLexer.nextToken();
            skipComment();
            return;
        }
        throw new RuntimeException("error token");
    }
    private void next(){
        jsonLexer.nextToken();
        skipComment();
    }

    public JsonParser(JsonLexer jsonLexer) {
        this.jsonLexer = jsonLexer;
        jsonLexer.nextToken();
    }
    public Json parseJson(){
        match(TokenType.LEFT_BRACE);
        List<Pair> pairList = new ArrayList<>();
        while(true){
            Key key = parseKey();
            if(key != null){
                pairList.add(new Pair(key,parseValue()));
            }
            if(jsonLexer._token.getType() == TokenType.RIGHT_BRACE){
                break;
            }
            match(TokenType.COMMA);
        }
        match(TokenType.RIGHT_BRACE);
        return new Json(pairList);
    }
    public List<Value> parseJsonArray(){
        match(TokenType.LEFT_BRACKET);
        List<Value> values = new ArrayList<>();
        while (true){
            Value v = parseValue();
            if(v != null){
                values.add(v);
            }
            if(jsonLexer._token.getType() == TokenType.RIGHT_BRACKET){
                break;
            }
            match(TokenType.COMMA);
        }
        match(TokenType.RIGHT_BRACKET);
        return values;
    }
    private Key parseKey(){
        if(jsonLexer._token.getType() == TokenType.RIGHT_BRACE){
            return null;
        }
        Token t = jsonLexer._token;
        match(TokenType.STRING);
        match(TokenType.COLON);
        return new Key(getStrValue(t.getText()));
    }
    public Value parseValue(){
        Token t = jsonLexer._token;
        switch (t.getType()){
            case TokenType.STRING: next();return new Value(getStrValue(t.getText()));
            case TokenType.LITERAL:next();
                switch (t.getText()){
                    case "true":next();return new Value(true);
                    case "false":next();return new Value(false);
                    case "null": next();return new Value();
                }
            case TokenType.NUMBER:
                try{
                    Long l  = Long.parseLong(t.getText());
                    next();
                    return new Value(l);
                } catch (Exception e){
                    Double d = Double.parseDouble(t.getText());
                    next();
                    return new Value(d);
                }
            case TokenType.LEFT_BRACE:return new Value(parseJson());
            case TokenType.LEFT_BRACKET:return new Value(parseJsonArray());
            //识别到数组结尾
            case TokenType.RIGHT_BRACKET:return null;
            default:throw new RuntimeException("不能识别的符号:"+t);
        }
    }

    /**
     * 获取 "xx" 的值 xxx
     */
    private String getStrValue(String s){
        if("''".equals(s) || "\"\"".equals(s)){
            return s;
        }
        return s.substring(1,s.length() -1);
    }



}
