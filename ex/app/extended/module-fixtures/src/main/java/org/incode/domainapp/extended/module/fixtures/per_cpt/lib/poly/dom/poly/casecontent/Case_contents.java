package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;

@Mixin(method = "coll")
public class Case_contents {

    private final Case aCase;

    public Case_contents(Case aCase) {
        this.aCase = aCase;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<CaseContent> coll() {
        final Case aCase = this.aCase;
        return caseContentService.contentsFor(aCase);
    }

    @Inject
    CaseContentService caseContentService;


}
