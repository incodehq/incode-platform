package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.security.fixture;

public final class Util {
    
    private Util(){}

    public static String coalesce(final String... strings) {
        for (String str : strings) {
            if(str != null) { return str; }
        }
        return null;
    }

}