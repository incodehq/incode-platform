package org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.poly.caseprimary;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.incode.domainapp.extended.module.fixtures.per_cpt.lib.poly.dom.democasemgmt.Case;

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
        return casePrimaryContentContributions.setPrimary(aCase, null);
    }

    @Inject
    CasePrimaryContentContributions casePrimaryContentContributions;

}
