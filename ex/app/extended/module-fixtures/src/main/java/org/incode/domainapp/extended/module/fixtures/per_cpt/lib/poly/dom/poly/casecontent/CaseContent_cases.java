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
public class CaseContent_cases {

    private final CaseContent caseContent;

    public CaseContent_cases(CaseContent caseContent) {
        this.caseContent = caseContent;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<Case> coll() {
        final CaseContent caseContent = this.caseContent;
        return caseContentService.casesFor(caseContent);
    }

    @Inject
    CaseContentService caseContentService;


}
