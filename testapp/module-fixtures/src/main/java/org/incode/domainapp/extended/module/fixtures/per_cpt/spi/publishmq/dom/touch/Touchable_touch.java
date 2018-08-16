package org.incode.domainapp.extended.module.fixtures.per_cpt.spi.publishmq.dom.touch;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Publishing;


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
