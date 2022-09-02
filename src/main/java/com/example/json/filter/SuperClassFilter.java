package com.example.json.filter;


import java.util.HashSet;
import java.util.Set;

public abstract class SuperClassFilter implements Filter {
    public SuperClassFilter(Class<?>... superClasses) {
        this.superClasses = superClasses;
    }

    private Class<?>[] superClasses;
    private Set<Integer> sets = new HashSet<>(256);

    @Override
    public boolean doFilter(Class<?> clazz) {
        if (sets.contains(clazz.hashCode())) {
            return true;
        }
        for (Class<?> fa : superClasses) {
            if (fa.isAssignableFrom(clazz)) {
                sets.add(clazz.hashCode());
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return SUPER_CLASS_FILTER;
    }

    private boolean isSuperClass(Class sonClass, Class fa) {
        if (sonClass.equals(fa)) {
            return true;
        }
        Class<?>[] classes = sonClass.getInterfaces();
        for (Class<?> c : classes) {
            if (c.equals(fa)) {
                return true;
            }
            if (isSuperClass(c, fa)) {
                return true;
            }
        }
        return false;
    }
}
