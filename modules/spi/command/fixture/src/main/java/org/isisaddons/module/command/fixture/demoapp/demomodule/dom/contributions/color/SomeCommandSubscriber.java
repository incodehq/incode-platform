package org.isisaddons.module.command.fixture.demoapp.demomodule.dom.contributions.color;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.background.BackgroundService2;
import org.apache.isis.applib.services.user.UserService;

import org.isisaddons.module.command.fixture.demoapp.demomodule.dom.SomeCommandAnnotatedObject;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SomeCommandSubscriber extends AbstractSubscriber {

    @Subscribe
    public void on(SomeCommandAnnotatedObject.ChangeColorActionDomainEvent event) {

        if(!event.getEventPhase().isExecuted()) {
            return;
        }

        SomeCommandAnnotatedObject scao = event.getSource();
        scheduleBackgroundCommandsToCopyColourOver(scao);

    }

    @Subscribe
    public void on(SomeCommandAnnotatedObject_changeColorViaMixin.ActionDomainEvent event) {

        if(!event.getEventPhase().isExecuted()) {
            return;
        }

        SomeCommandAnnotatedObject scao = (SomeCommandAnnotatedObject) event.getMixedIn();
        scheduleBackgroundCommandsToCopyColourOver(scao);

    }

    void scheduleBackgroundCommandsToCopyColourOver(final SomeCommandAnnotatedObject scao) {
        // these will create new objects.
        backgroundService2.execute(scao).updateCopyOfColorUpdatedBySubscribedBackgroundDirectAction(scao.getColour());
        backgroundService2.executeMixin(SomeCommandAnnotatedObj_updateCopyOfColorBySubscribedBackgrndMixinAct.class, scao).$$(scao.getColour());

        // not supported
        // backgroundService2.execute(scao).setCopyOfColorUpdatedBySubscribedBackgroundPropertyEdit(scao.getColour());
    }

    @Inject
    BackgroundService2 backgroundService2;
    @Inject
    UserService userService;

}
