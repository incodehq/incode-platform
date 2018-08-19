package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.dom.PolymorphicAssociationLink;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinks;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PolyDemoCaseContentService {

    @Programmatic
    public List<PolyDemoCase> casesFor(final PolyDemoCaseContent caseContent) {
        return caseContentLinks.findByContent(caseContent)
                .stream()
                .map(PolyDemoCaseContentLink::getSubject)
                .collect(Collectors.toList());
    }

    @Programmatic
    public List<PolyDemoCaseContent> contentsFor(final PolyDemoCase aCase) {
        return caseContentLinks.findByCase(aCase)
                .stream()
                .map(PolymorphicAssociationLink::getPolymorphicReference)
                .collect(Collectors.toList());
    }




    @Inject
    PolyDemoCaseContentLinks caseContentLinks;
    @Inject
    DomainObjectContainer container;

}
