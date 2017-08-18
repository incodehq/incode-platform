package org.incode.domainapp.example.dom.spi.audit.dom.demo.notaudited;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeNotAuditedObject_delete {

    private final SomeNotAuditedObject someNotAuditedObject;

    public SomeNotAuditedObject_delete(SomeNotAuditedObject someNotAuditedObject) {
        this.someNotAuditedObject = someNotAuditedObject;
    }

    @Action()
    public void $$() {
        someNotAuditedObjects.deleteSomeNotAuditedObject(someNotAuditedObject);
    }


    @javax.inject.Inject
    SomeNotAuditedObjects someNotAuditedObjects;

}
