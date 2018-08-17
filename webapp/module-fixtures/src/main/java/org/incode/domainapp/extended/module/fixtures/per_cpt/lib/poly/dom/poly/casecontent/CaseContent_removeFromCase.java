package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;

@Mixin(method = "act")
public class CaseContent_removeFromCase {

    private final CaseContent caseContent;

    public CaseContent_removeFromCase(CaseContent caseContent) {
        this.caseContent = caseContent;
    }

    public static class RemoveDomainEvent extends ActionDomainEvent<CaseContent> {
        public Case getCase() {
            return (Case) getArguments().get(0);
        }
        public CaseContent getContent() {
            return super.getSource();
        }
    }

    @Action(domainEvent = RemoveDomainEvent.class)
    public Case act(final Case aCase) {
        final List<CaseContentLink> contentLinks = caseContentLinks.findByCase(aCase);
        for (CaseContentLink contentLink : contentLinks) {
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

    public List<Case> choices0Act() {
        return caseContentLinks.findByContent(caseContent)
                .stream()
                .map(CaseContentLink::getSubject)
                .collect(Collectors.toList());
    }

    @Inject
    CaseContentLinks caseContentLinks;
    @Inject
    DomainObjectContainer container;

}
