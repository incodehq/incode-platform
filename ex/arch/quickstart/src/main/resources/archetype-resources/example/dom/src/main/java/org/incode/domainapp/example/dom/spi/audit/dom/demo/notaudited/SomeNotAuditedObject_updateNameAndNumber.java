#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited;

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
    public SomeNotAuditedObject ${symbol_dollar}${symbol_dollar}(
            final String name,
            @Nullable
            final Integer number) {
        mixee.setName(name);
        mixee.setNumber(number);
        return mixee;
    }

    public String default0${symbol_dollar}${symbol_dollar}() {
        return mixee.getName();
    }
    public Integer default1${symbol_dollar}${symbol_dollar}() {
        return mixee.getNumber();
    }


}
