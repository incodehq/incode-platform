package domainapp.modules.exampledom.spi.audit.dom.demo.delete;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import domainapp.modules.exampledom.spi.audit.dom.demo.SomeAuditedObject;
import domainapp.modules.exampledom.spi.audit.dom.demo.SomeAuditedObjects;

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
