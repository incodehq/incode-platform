package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContentService;

@Mixin(method = "coll")
public class PolyDemoCaseContent_cases {

    private final PolyDemoCaseContent caseContent;

    public PolyDemoCaseContent_cases(PolyDemoCaseContent caseContent) {
        this.caseContent = caseContent;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<PolyDemoCase> coll() {
        final PolyDemoCaseContent caseContent = this.caseContent;
        return caseContentService.casesFor(caseContent);
    }

    @Inject
    PolyDemoCaseContentService caseContentService;


}
