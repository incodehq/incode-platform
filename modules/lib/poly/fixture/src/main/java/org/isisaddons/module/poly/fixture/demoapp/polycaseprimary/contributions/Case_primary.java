package org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.polycasecontent.dom.content.CaseContent;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentLink;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentLinks;

@Mixin(method="prop")
public class Case_primary {

    private final Case aCase;

    public Case_primary(Case aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION,
            named = "Primary"
    )
    public CaseContent prop() {
        final CasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        return contentLink != null? contentLink.getPolymorphicReference(): null;
    }

    @Inject
    CasePrimaryContentLinks casePrimaryContentLinks;

}
