package com.danhaywood.isis.domainservice.docx.util;

import com.google.common.base.Predicate;

public final class Types {
    
    private Types(){}

    public static Predicate<Object> withType(final Class<?> cls) {
        return new Predicate<Object>(){
            public boolean apply(Object object) {
                return cls.isAssignableFrom(object.getClass());
            }
        };
    }

}
