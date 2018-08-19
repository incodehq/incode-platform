package org.isisaddons.module.command.fixture.demoapp.demomodule.dom.contributions.color;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObject;

@Mixin
public class SomeCommandAnnotatedObj_updateCopyOfColorBySubscribedBackgrndMixinAct {

    public static class ActionDomainEvent
            extends org.apache.isis.applib.services.eventbus.ActionDomainEvent <SomeCommandAnnotatedObj_updateCopyOfColorBySubscribedBackgrndMixinAct> {}

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObj_updateCopyOfColorBySubscribedBackgrndMixinAct(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(domainEvent = SomeCommandAnnotatedObj_updateCopyOfColorBySubscribedBackgrndMixinAct.ActionDomainEvent.class)
    public SomeCommandAnnotatedObject $$(final SomeCommandAnnotatedObject.Colour colour) {
        someCommandAnnotatedObject.setCopyOfColorUpdatedBySubscribedBackgroundMixinAction(colour);
        return someCommandAnnotatedObject;
    }

    public SomeCommandAnnotatedObject.Colour default0$$() {
        return someCommandAnnotatedObject.getColour();
    }

}
