package org.isisaddons.module.audit.fixture.dom;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeAuditedObject_delete {

    private final SomeAuditedObject someAuditedObject;

    public SomeAuditedObject_delete(SomeAuditedObject someAuditedObject) {
        this.someAuditedObject = someAuditedObject;
    }

    @Action()
    public void $$() {
        someAuditedObjects.delete(someAuditedObject);
    }


    @javax.inject.Inject
    SomeAuditedObjects someAuditedObjects;

}
