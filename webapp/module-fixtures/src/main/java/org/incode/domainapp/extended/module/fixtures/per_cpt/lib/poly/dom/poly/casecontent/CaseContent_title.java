package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.casecontent;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.title.TitleService;

@Mixin(method = "prop")
public class CaseContent_title {

    private final CaseContent caseContent;

    public CaseContent_title(CaseContent caseContent) {
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
