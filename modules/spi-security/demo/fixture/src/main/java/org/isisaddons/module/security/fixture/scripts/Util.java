package org.isisaddons.module.security.fixture.scripts;

public final class Util {
    
    private Util(){}

    public static String coalesce(final String... strings) {
        for (String str : strings) {
            if(str != null) { return str; }
        }
        return null;
    }

}