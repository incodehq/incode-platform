package org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited;

import javax.annotation.Nullable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeNotAuditedObject_updateNameAndNumber {

    private final SomeNotAuditedObject mixee;

    public SomeNotAuditedObject_updateNameAndNumber(SomeNotAuditedObject mixee) {
        this.mixee = mixee;
    }

    @Action()
    public SomeNotAuditedObject $$(
            final String name,
            @Nullable
            final Integer number) {
        mixee.setName(name);
        mixee.setNumber(number);
        return mixee;
    }

    public String default0$$() {
        return mixee.getName();
    }
    public Integer default1$$() {
        return mixee.getNumber();
    }


}
