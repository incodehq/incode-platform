#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.incode.domainapp.example.dom.spi.audit.dom.demo.audited;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeAuditedObject_delete {

    private final SomeAuditedObject someAuditedObject;

    public SomeAuditedObject_delete(SomeAuditedObject someAuditedObject) {
        this.someAuditedObject = someAuditedObject;
    }

    @Action()
    public void ${symbol_dollar}${symbol_dollar}() {
        someAuditedObjects.deleteSomeAuditedObject(someAuditedObject);
    }


    @javax.inject.Inject
    SomeAuditedObjects someAuditedObjects;

}
