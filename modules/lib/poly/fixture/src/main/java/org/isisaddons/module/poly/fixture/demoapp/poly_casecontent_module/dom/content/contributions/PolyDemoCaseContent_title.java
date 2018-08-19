package org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.title.TitleService;

import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;

@Mixin(method = "prop")
public class PolyDemoCaseContent_title {

    private final PolyDemoCaseContent caseContent;

    public PolyDemoCaseContent_title(PolyDemoCaseContent caseContent) {
        this.caseContent = caseContent;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @PropertyLayout(hidden = Where.OBJECT_FORMS)
    public String prop() {
        return titleService.titleOf(caseContent);
    }

    @Inject
    TitleService titleService;

}
