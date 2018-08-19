package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.contributions;

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
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoCasePrimaryContentService;

@Mixin(method="act")
public class PolyDemoCase_makePrimary {

    private final PolyDemoCase aCase;

    public PolyDemoCase_makePrimary(PolyDemoCase aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public PolyDemoCase act(final PolyDemoCaseContent caseContent) {
        return casePrimaryContentService.setPrimary(aCase, caseContent);
    }

    public List<PolyDemoCaseContent> choices0Act() {
        return caseContentService.contentsFor(aCase);
    }

    @Inject
    PolyDemoCasePrimaryContentService casePrimaryContentService;
    @Inject
    PolyDemoCaseContentService caseContentService;


}
