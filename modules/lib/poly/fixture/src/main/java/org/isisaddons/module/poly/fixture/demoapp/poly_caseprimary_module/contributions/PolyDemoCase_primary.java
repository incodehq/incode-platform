package org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.contributions;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.casemgmt.PolyDemoCase;
import org.isisaddons.module.poly.fixture.demoapp.poly_casecontent_module.dom.content.PolyDemoCaseContent;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoPolyDemoCasePrimaryContentLink;
import org.isisaddons.module.poly.fixture.demoapp.poly_caseprimary_module.dom.PolyDemoCasePrimaryContentLinks;

@Mixin(method="prop")
public class PolyDemoCase_primary {

    private final PolyDemoCase aCase;

    public PolyDemoCase_primary(PolyDemoCase aCase) {
        this.aCase = aCase;
    }

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION,
            named = "Primary"
    )
    public PolyDemoCaseContent prop() {
        final PolyDemoPolyDemoCasePrimaryContentLink contentLink = casePrimaryContentLinks.findByCase(aCase);
        return contentLink != null? contentLink.getPolymorphicReference(): null;
    }

    @Inject
    PolyDemoCasePrimaryContentLinks casePrimaryContentLinks;

}
