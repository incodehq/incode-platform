package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoCasePrimaryContentService;

@Mixin(method="act")
public class PolyDemoCase_resetPrimary {

    private final PolyDemoCase aCase;

    public PolyDemoCase_resetPrimary(PolyDemoCase aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            contributed = Contributed.AS_ACTION
    )
    public PolyDemoCase act() {
        return casePrimaryContentService.setPrimary(aCase, null);
    }

    @Inject
    PolyDemoCasePrimaryContentService casePrimaryContentService;

}
