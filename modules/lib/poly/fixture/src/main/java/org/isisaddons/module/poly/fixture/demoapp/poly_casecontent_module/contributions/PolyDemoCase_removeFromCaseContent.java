package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.contributions;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCases;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinks;

@Mixin(method = "act")
public class PolyDemoCase_removeFromCaseContent {

    private final PolyDemoCase aCase;

    public PolyDemoCase_removeFromCaseContent(PolyDemoCase aCase) {
        this.aCase = aCase;
    }

    public static class RemoveDomainEvent extends ActionDomainEvent<PolyDemoCase> {
        public PolyDemoCaseContent getContent() {
            return (PolyDemoCaseContent) getArguments().get(0);
        }
        public PolyDemoCase getCase() {
            return super.getSource();
        }
    }

    @Action(domainEvent = RemoveDomainEvent.class)
    public PolyDemoCase act(final PolyDemoCaseContent caseContent) {
        caseContentLinks.findByCase(aCase)
                .stream().filter(x -> x.getPolymorphicReference() == caseContent)
                .forEach(caseContentLink -> container.removeIfNotAlready(caseContentLink));
        return aCase;
    }

    public String disableAct() {
        return choices0Act().isEmpty()? "PolyDemoCase has no contents": null;
    }

    public List<PolyDemoCaseContent> choices0Act() {
        return caseContentLinks.findByCase(aCase)
                .stream()
                .map(PolyDemoCaseContentLink::getPolymorphicReference)
                .collect(Collectors.toList());
    }



    @Inject
    PolyDemoCaseContentLinks caseContentLinks;
    @Inject
    PolyDemoCases cases;
    @Inject
    DomainObjectContainer container;

}
