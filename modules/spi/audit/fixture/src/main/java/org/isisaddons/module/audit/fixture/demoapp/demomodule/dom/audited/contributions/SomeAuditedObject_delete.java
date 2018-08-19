package org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.contributions;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.audited.SomeAuditedObjects;

@Mixin
public class SomeAuditedObject_delete {

    private final SomeAuditedObject someAuditedObject;

    public SomeAuditedObject_delete(SomeAuditedObject someAuditedObject) {
        this.someAuditedObject = someAuditedObject;
    }

    @Action()
    public void $$() {
        someAuditedObjects.deleteSomeAuditedObject(someAuditedObject);
    }


    @javax.inject.Inject
    SomeAuditedObjects someAuditedObjects;

}
