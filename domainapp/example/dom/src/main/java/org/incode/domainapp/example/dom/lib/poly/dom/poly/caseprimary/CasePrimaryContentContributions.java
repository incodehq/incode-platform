package org.incode.domainapp.example.dom.lib.poly.dom.poly.caseprimary;

import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContent;
import org.incode.domainapp.example.dom.lib.poly.dom.poly.casecontent.CaseContentContributions;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class CasePrimaryContentContributions {

    //region > primary (contributed property)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    public CaseContent primary(final Case aCase) {
        final CasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        return contentLink != null? contentLink.getPolymorphicReference(): null;
    }
    //endregion

    //region > makePrimary (contributed action), resetPrimary (contributed action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Case makePrimary(
            final Case aCase,
            final CaseContent caseContent) {
        return setPrimary(aCase, caseContent);
    }
    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    public Case resetPrimary(
            final Case aCase) {
        return setPrimary(aCase, null);
    }

    private Case setPrimary(final Case aCase, final CaseContent caseContent) {
        final CasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        if(contentLink != null) {
            container.removeIfNotAlready(contentLink);
        }
        if(caseContent != null) {
            casePrimaryContentLinks.createLink(aCase, caseContent);
        }
        return aCase;
    }

    public boolean hideMakePrimary(final Case aCase, final CaseContent caseContent) {
        // don't contribute to caseContent
        return caseContent != null;
    }

    public List<CaseContent> choices1MakePrimary(final Case aCase) {
        return caseContentContributions.contents(aCase);
    }
    //endregion


    @Inject
    private CaseContentContributions caseContentContributions;
    @Inject
    private CasePrimaryContentLinks casePrimaryContentLinks;
    @Inject
    private DomainObjectContainer container;

}
