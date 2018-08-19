package org.isisaddons.module.publishmq.fixture.demoapp.touchmodule.contributions;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Publishing;

import org.isisaddons.module.publishmq.fixture.demoapp.touchmodule.dom.Touchable;

@Mixin
public class Touchable_touch {

    private final Touchable touchable;

    public Touchable_touch(Touchable touchable) {
        this.touchable = touchable;
    }

    @Action(
            publishing = Publishing.ENABLED
    )
    public Touchable $$() {
        return touchable;
    }

}
