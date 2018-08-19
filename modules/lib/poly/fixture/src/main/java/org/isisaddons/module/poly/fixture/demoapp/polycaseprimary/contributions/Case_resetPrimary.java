package org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.Case;
import org.isisaddons.module.poly.fixture.demoapp.polycaseprimary.dom.CasePrimaryContentService;

@Mixin(method="act")
public class Case_resetPrimary {

    private final Case aCase;

    public Case_resetPrimary(Case aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public Case act() {
        return casePrimaryContentService.setPrimary(aCase, null);
    }

    @Inject
    CasePrimaryContentService casePrimaryContentService;

}
