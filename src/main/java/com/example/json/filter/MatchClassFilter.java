package com.example.json.filter;

import java.util.HashSet;
import java.util.Set;

public abstract class MatchClassFilter implements Filter {
    protected Class<?>[] targets;

    private Set<Integer> hashCodes = new HashSet<>();

    public MatchClassFilter(Class<?>... targets) {
        this.targets = targets;
        for (Class c : targets) {
            hashCodes.add(c.hashCode());
        }
    }

    @Override
    public boolean doFilter(Class<?> clazz) {
        return hashCodes.contains(clazz.hashCode());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
