package org.incode.domainapp.example.dom.spi.command.dom.demo.desc;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.background.BackgroundService2;
import org.apache.isis.applib.services.factory.FactoryService;

import org.incode.domainapp.example.dom.spi.command.dom.demo.SomeCommandAnnotatedObject;

@Mixin
public class SomeCommandAnnotatedObject_changeDescriptionExplicitlyInBackground {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_changeDescriptionExplicitlyInBackground(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    public SomeCommandAnnotatedObject $$(
            @Parameter(optionality = Optionality.OPTIONAL)
            @ParameterLayout(named = "Description")
            final String description) {

        backgroundService.executeMixin(SomeCommandAnnotatedObject_changeDescription.class, someCommandAnnotatedObject).$$(description);

        return someCommandAnnotatedObject;
    }

    public String default0$$() {
        return someCommandAnnotatedObject.getDescription();
    }



    @javax.inject.Inject
    private FactoryService factoryService;
    @javax.inject.Inject
    private BackgroundService2 backgroundService;

}
