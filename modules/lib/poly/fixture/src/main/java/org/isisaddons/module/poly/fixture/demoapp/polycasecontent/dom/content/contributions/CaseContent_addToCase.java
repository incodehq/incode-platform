package org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Mixin;

@Mixin(method = "act")
public class CaseContent_addToCase {

    private final CaseContent caseContent;

    public CaseContent_addToCase(CaseContent caseContent) {
        this.caseContent = caseContent;
    }

    @Action()
    public Case act(final Case aCase) {
        caseContentLinks.createLink(aCase, caseContent);
        return aCase;
    }
    public List<Case> choices0Act() {
        final List<Case> allCases = cases.listAllCases();
        final List<Case> currentCases = caseContentService.casesFor(caseContent);
        allCases.removeAll(currentCases);
        return allCases;
    }

    @Inject
    CaseContentService caseContentService;
    @Inject
    CaseContentLinks caseContentLinks;
    @Inject
    Cases cases;

}
