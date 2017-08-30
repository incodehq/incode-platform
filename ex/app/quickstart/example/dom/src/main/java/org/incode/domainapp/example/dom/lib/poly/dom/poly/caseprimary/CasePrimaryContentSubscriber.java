package org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary;

import javax.inject.Inject;
import com.google.common.eventbus.Subscribe;
import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContentContributions;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CasePrimaryContentSubscriber extends AbstractSubscriber {

    /**
     * Cascade delete of the primary link
     */
    @Programmatic
    @Subscribe
    public void on(final CaseContentContributions.RemoveFromCaseDomainEvent ev) {
        switch (ev.getEventPhase()) {
            case EXECUTING:
                final CasePrimaryContentLink link = casePrimaryContentLinks.findByCaseAndContent(ev.getCase(), ev.getContent());
                if(link != null) {
                    container.remove(link);
                }
                break;
        }
    }

    @Inject
    private CasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    private DomainObjectContainer container;

}
