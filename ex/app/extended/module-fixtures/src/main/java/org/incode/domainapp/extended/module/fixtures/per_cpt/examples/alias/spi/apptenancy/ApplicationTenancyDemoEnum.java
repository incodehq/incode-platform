package org.incode.domainapp.extended.module.fixtures.per_cpt.examples.alias.spi.apptenancy;

import lombok.Getter;

/**
 * In a real (not demo) application this would probably be the isisaddons' security module's <code>ApplicationTenancy</code> entity.
 */
public enum ApplicationTenancyDemoEnum {

    GLOBAL("/"),
    UK("/uk"),
    NL("/nl"),
    IT("/it"),
    SW("/sw"),
    FR("/fr");

    @Getter
    private final String path;

    ApplicationTenancyDemoEnum(final String path) {
        this.path = path;
    }

}

