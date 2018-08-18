package org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

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
        return casePrimaryContentService.setPrimary(aCase, caseContent);
    }

    public List<CaseContent> choices0Act() {
        return caseContentService.contentsFor(aCase);
    }

    @Inject
    CasePrimaryContentService casePrimaryContentService;
    @Inject
    CaseContentService caseContentService;


}
