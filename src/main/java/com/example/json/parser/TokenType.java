package com.example.json.parser;

public interface TokenType {
    int LEFT_BRACE = 1;
    int RIGHT_BRACE = 2;
    int LEFT_BRACKET = 3;
    int RIGHT_BRACKET = 4;
    int COMMA = 5;
    int COLON = 6;
    int SINGLE_LINE_COMMENT = 7;
    int MULTI_LINE_COMMENT = 8;
    int LITERAL = 9;
    int STRING = 10;
    int NUMBER = 11;
    int NUMERIC_LITERAL = 12;
    int SYMBOL = 13;
    int IDENTIFIER = 14;
    int WS = 15;
}
