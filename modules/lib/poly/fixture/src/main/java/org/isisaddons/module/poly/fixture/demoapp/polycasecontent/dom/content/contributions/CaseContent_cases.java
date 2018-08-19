package org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.CaseContent;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.CaseContentService;

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
