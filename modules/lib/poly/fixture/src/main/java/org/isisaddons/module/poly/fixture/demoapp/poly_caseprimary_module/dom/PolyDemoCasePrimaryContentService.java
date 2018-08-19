package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class PolyDemoCasePrimaryContentService {


    @Programmatic
    public PolyDemoCase setPrimary(final PolyDemoCase aCase, final PolyDemoCaseContent caseContent) {
        final PolyDemoPolyDemoCasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        if(contentLink != null) {
            container.removeIfNotAlready(contentLink);
        }
        if(caseContent != null) {
            casePrimaryContentLinks.createLink(aCase, caseContent);
        }
        return aCase;
    }


    @Inject
    PolyDemoCasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    DomainObjectContainer container;

}
