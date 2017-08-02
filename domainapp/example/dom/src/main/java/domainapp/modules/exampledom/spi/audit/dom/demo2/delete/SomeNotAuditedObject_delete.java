package domainapp.modules.exampledom.spi.audit.dom.demo2.delete;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import domainapp.modules.exampledom.spi.audit.dom.demo2.SomeNotAuditedObject;
import domainapp.modules.exampledom.spi.audit.dom.demo2.SomeNotAuditedObjects;

@Mixin
public class SomeNotAuditedObject_delete {

    private final SomeNotAuditedObject someNotAuditedObject;

    public SomeNotAuditedObject_delete(SomeNotAuditedObject someNotAuditedObject) {
        this.someNotAuditedObject = someNotAuditedObject;
    }

    @Action()
    public void $$() {
        someNotAuditedObjects.delete(someNotAuditedObject);
    }


    @javax.inject.Inject
    SomeNotAuditedObjects someNotAuditedObjects;

}
