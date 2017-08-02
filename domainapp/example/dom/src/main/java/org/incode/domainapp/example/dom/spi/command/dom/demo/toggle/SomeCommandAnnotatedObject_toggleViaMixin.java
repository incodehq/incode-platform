package org.incode.domainapp.example.dom.spi.command.dom.demo.toggle;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObject;

@Mixin
public class SomeCommandAnnotatedObject_toggleViaMixin {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_toggleViaMixin(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    @ActionLayout(
            describedAs = "Toggle, for testing (mixin) bulk actions"
    )
    public SomeCommandAnnotatedObject $$() {
        someCommandAnnotatedObject.toggleForBulkActions();
        return someCommandAnnotatedObject;
    }

}
