package org.incode.domainapp.example.dom.spi.audit.dom.demo.audited;

import javax.annotation.Nullable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeAuditedObject_updateNameAndNumber {

    private final SomeAuditedObject someAuditedObject;

    public SomeAuditedObject_updateNameAndNumber(SomeAuditedObject someAuditedObject) {
        this.someAuditedObject = someAuditedObject;
    }

    @Action()
    public SomeAuditedObject $$(
            final String name,
            @Nullable
            final Integer number) {
        someAuditedObject.setName(name);
        someAuditedObject.setNumber(number);
        return someAuditedObject;
    }

    public String default0$$() {
        return someAuditedObject.getName();
    }
    public Integer default1$$() {
        return someAuditedObject.getNumber();
    }


}
