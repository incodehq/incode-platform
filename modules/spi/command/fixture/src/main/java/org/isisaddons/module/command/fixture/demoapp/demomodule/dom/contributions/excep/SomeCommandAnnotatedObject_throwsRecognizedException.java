package org.isisaddons.module.command.fixture.demoapp.demomodule.dom.contributions.excep;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObject;

@Mixin
public class SomeCommandAnnotatedObject_throwsRecognizedException {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_throwsRecognizedException(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    public SomeCommandAnnotatedObject $$() {
        throw new ApplicationException("This failed...");
    }


}
