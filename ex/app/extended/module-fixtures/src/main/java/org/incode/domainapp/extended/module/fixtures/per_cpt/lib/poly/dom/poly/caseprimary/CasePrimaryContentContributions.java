package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.CaseContent;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CasePrimaryContentContributions {


    Case setPrimary(final Case aCase, final CaseContent caseContent) {
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
