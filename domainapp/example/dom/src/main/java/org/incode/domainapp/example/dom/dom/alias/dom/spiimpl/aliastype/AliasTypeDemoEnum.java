package org.incode.domainapp.example.dom.dom.alias.dom.spiimpl.aliastype;

import org.apache.isis.applib.annotation.Title;

import org.incode.module.alias.dom.spi.AliasType;

public enum AliasTypeDemoEnum implements AliasType {

    // in UK and NL
    GENERAL_LEDGER("GL"),
    // in UK and NL
    DOCUMENT_MANAGEMENT("DOC"),
    // in UK only
    PERSONNEL_SYSTEM("HR")
    ;

    private final String id;

    AliasTypeDemoEnum(final String id) {
        this.id = id;
    }

    @Title
    @Override
    public String getId() {
        return id;
    }
}

