package com.example.json.filter;

import java.lang.annotation.Annotation;

public abstract class MatchAnnotationFilter implements Filter {
    protected final Class<? extends Annotation> c;

    public MatchAnnotationFilter(Class<? extends Annotation> c) {
        this.c = c;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
