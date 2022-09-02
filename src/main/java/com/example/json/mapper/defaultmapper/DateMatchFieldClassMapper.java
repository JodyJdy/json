package com.example.json.mapper.defaultmapper;

import com.example.json.mapper.MatchFieldClassMapper;
import com.example.json.parser.Value;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMatchFieldClassMapper extends MatchFieldClassMapper{
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateMatchFieldClassMapper() {
        super(Date.class);
    }
    @Override
    public Value map(Field field, Object value) {
        if(value == null){
            return null;
        }
        return new Value(DEFAULT_DATE_FORMAT.format(value));
    }
}
