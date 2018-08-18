package org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CaseContentService {

    @Programmatic
    public List<Case> casesFor(final CaseContent caseContent) {
        return caseContentLinks.findByContent(caseContent)
                .stream()
                .map(CaseContentLink::getSubject)
                .collect(Collectors.toList());
    }

    @Programmatic
    public List<CaseContent> contentsFor(final Case aCase) {
        return caseContentLinks.findByCase(aCase)
                .stream()
                .map(PolymorphicAssociationLink::getPolymorphicReference)
                .collect(Collectors.toList());
    }




    @Inject
    CaseContentLinks caseContentLinks;
    @Inject
    DomainObjectContainer container;

}
