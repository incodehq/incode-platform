package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.contributions;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinks;

@Mixin(method = "act")
public class PolyDemoCaseContent_removeFromCase {

    private final PolyDemoCaseContent caseContent;

    public PolyDemoCaseContent_removeFromCase(PolyDemoCaseContent caseContent) {
        this.caseContent = caseContent;
    }

    public static class RemoveDomainEvent extends ActionDomainEvent<PolyDemoCaseContent> {
        public PolyDemoCase getCase() {
            return (PolyDemoCase) getArguments().get(0);
        }
        public PolyDemoCaseContent getContent() {
            return super.getSource();
        }
    }

    @Action(domainEvent = RemoveDomainEvent.class)
    public PolyDemoCase act(final PolyDemoCase aCase) {
        final List<PolyDemoCaseContentLink> contentLinks = caseContentLinks.findByCase(aCase);
        for (PolyDemoCaseContentLink contentLink : contentLinks) {
            if(contentLink.getPolymorphicReference() == caseContent) {
                container.removeIfNotAlready(contentLink);
                break;
            }
        }
        return aCase;
    }

    public String disableAct() {
        return choices0Act().isEmpty()? "Not contained in any case": null;
    }

    public List<PolyDemoCase> choices0Act() {
        return caseContentLinks.findByContent(caseContent)
                .stream()
                .map(PolyDemoCaseContentLink::getSubject)
                .collect(Collectors.toList());
    }

    @Inject
    PolyDemoCaseContentLinks caseContentLinks;
    @Inject
    DomainObjectContainer container;

}
