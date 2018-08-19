package org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.contributions.Case_removeFromCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.CaseContent;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.contributions.CaseContent_removeFromCase;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CasePrimaryContentSubscriber extends AbstractSubscriber {

    /**
     * Cascade delete of the primary link
     */
    @Programmatic
    @Subscribe
    public void on(final Case_removeFromCaseContent.RemoveDomainEvent ev) {
        remove(ev.getCase(), ev.getContent(), ev.getEventPhase());
    }

    @Programmatic
    @Subscribe
    public void on(final CaseContent_removeFromCase.RemoveDomainEvent ev) {
        remove(ev.getCase(), ev.getContent(), ev.getEventPhase());
    }

    private void remove(final Case aCase, final CaseContent content, final AbstractDomainEvent.Phase eventPhase) {
        switch (eventPhase) {
        case EXECUTING:
            final CasePrimaryContentLink link = casePrimaryContentLinks.findByCaseAndContent(aCase, content);
            if (link != null) {
                container.remove(link);
            }
            break;
        }
    }

    @Inject
    CasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    DomainObjectContainer container;

}
