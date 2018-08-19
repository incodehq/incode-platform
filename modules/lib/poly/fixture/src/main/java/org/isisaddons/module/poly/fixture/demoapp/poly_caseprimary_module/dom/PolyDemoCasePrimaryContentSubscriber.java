package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.contributions.PolyDemoCase_removeFromCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.contributions.PolyDemoCaseContent_removeFromCase;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PolyDemoCasePrimaryContentSubscriber extends AbstractSubscriber {

    /**
     * Cascade delete of the primary link
     */
    @Programmatic
    @Subscribe
    public void on(final PolyDemoCase_removeFromCaseContent.RemoveDomainEvent ev) {
        remove(ev.getCase(), ev.getContent(), ev.getEventPhase());
    }

    @Programmatic
    @Subscribe
    public void on(final PolyDemoCaseContent_removeFromCase.RemoveDomainEvent ev) {
        remove(ev.getCase(), ev.getContent(), ev.getEventPhase());
    }

    private void remove(final PolyDemoCase aCase, final PolyDemoCaseContent content, final AbstractDomainEvent.Phase eventPhase) {
        switch (eventPhase) {
        case EXECUTING:
            final PolyDemoPolyDemoCasePrimaryContentLink link = casePrimaryContentLinks.findByCaseAndContent(aCase, content);
            if (link != null) {
                container.remove(link);
            }
            break;
        }
    }

    @Inject
    PolyDemoCasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    DomainObjectContainer container;

}
