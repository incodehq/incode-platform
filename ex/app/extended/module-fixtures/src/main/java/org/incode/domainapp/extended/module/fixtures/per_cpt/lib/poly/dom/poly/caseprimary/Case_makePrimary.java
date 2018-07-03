package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.CaseContent;
import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent.CaseContentContributions;

@Mixin(method="act")
public class Case_makePrimary {

    private final Case aCase;

    public Case_makePrimary(Case aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public Case act(final CaseContent caseContent) {
        return casePrimaryContentContributions.setPrimary(aCase, caseContent);
    }

    public List<CaseContent> choices0MakePrimary() {
        return caseContentContributions.contents(aCase);
    }

    @Inject
    CasePrimaryContentContributions casePrimaryContentContributions;

    @Inject
    CaseContentContributions caseContentContributions;

}
