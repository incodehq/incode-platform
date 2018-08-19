package org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.CaseContent;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CasePrimaryContentService {


    @Programmatic
    public Case setPrimary(final Case aCase, final CaseContent caseContent) {
        final CasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        if(contentLink != null) {
            container.removeIfNotAlready(contentLink);
        }
        if(caseContent != null) {
            casePrimaryContentLinks.createLink(aCase, caseContent);
        }
        return aCase;
    }


    @Inject
    CasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    DomainObjectContainer container;

}
