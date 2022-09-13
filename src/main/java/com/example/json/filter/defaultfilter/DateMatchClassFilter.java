package com.example.json.filter.defaultfilter;

import com.example.json.filter.MatchClassFilter;
import com.example.json.parser.Value;
import com.example.json.type.FieldType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMatchClassFilter extends MatchClassFilter {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateMatchClassFilter() {
        super(Date.class);
    }

    @Override
    public Object resolveValue(FieldType fieldType, Value value) {
        if (value == null || value.isNull()) {
            return null;
        }
        if (value.getNum() != null) {
            return new Date(value.getNum().longValue());
        }
        if (value.getV() != null) {
            try {
                return DEFAULT_DATE_FORMAT.parse(value.getV());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
