package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.contributions;

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
public class PolyDemoCase_contents {

    private final PolyDemoCase aCase;

    public PolyDemoCase_contents(PolyDemoCase aCase) {
        this.aCase = aCase;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    public List<PolyDemoCaseContent> coll() {
        final PolyDemoCase aCase = this.aCase;
        return caseContentService.contentsFor(aCase);
    }

    @Inject
    PolyDemoCaseContentService caseContentService;


}
