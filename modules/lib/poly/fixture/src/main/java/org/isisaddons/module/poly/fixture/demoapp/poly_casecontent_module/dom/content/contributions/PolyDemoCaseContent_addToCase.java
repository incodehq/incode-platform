package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCases;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContentService;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.contentlink.PolyDemoCaseContentLinks;

@Mixin(method = "act")
public class PolyDemoCaseContent_addToCase {

    private final PolyDemoCaseContent caseContent;

    public PolyDemoCaseContent_addToCase(PolyDemoCaseContent caseContent) {
        this.caseContent = caseContent;
    }

    @Action()
    public PolyDemoCase act(final PolyDemoCase aCase) {
        caseContentLinks.createLink(aCase, caseContent);
        return aCase;
    }
    public List<PolyDemoCase> choices0Act() {
        final List<PolyDemoCase> allCases = cases.listAllCases();
        final List<PolyDemoCase> currentCases = caseContentService.casesFor(caseContent);
        allCases.removeAll(currentCases);
        return allCases;
    }

    @Inject
    PolyDemoCaseContentService caseContentService;
    @Inject
    PolyDemoCaseContentLinks caseContentLinks;
    @Inject
    PolyDemoCases cases;

}
