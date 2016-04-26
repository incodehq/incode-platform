package org.isisaddons.module.command.fixture.dom;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin
public class SomeCommandAnnotatedObject_mixinAction {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_mixinAction(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action()
    public SomeCommandAnnotatedObject $$(final String dummy) {
        return someCommandAnnotatedObject;
    }

}
