package org.incode.domainapp.example.dom.spi.publishmq.dom.touch;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import javax.inject.Inject;


@Mixin
public class Touchable_wrappedTouch {

    private final Touchable touchable;

    public Touchable_wrappedTouch(Touchable touchable) {
        this.touchable = touchable;
    }

    @Action(
            publishing = Publishing.DISABLED // however, the touch method we call is enabled.
    )
    public Touchable $$() {
        wrapperFactory.wrap(factoryService.mixin(Touchable_touch.class, touchable)).$$();
        return touchable;
    }


    @Inject
    FactoryService factoryService;


    @Inject
    WrapperFactory wrapperFactory;

}
