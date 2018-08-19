package org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited.contributions;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited.SomeNotAuditedObject;
import org.isisaddons.module.audit.fixture.demoapp.demomodule.dom.notaudited.SomeNotAuditedObjects;

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
