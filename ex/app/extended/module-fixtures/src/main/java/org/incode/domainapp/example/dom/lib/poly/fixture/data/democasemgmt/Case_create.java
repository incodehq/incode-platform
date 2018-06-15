package org.incode.domainapp.example.dom.lib.poly.fixture.data.democasemgmt;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Case;
import org.incode.domainapp.example.dom.lib.poly.dom.democasemgmt.Cases;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Case_create extends FixtureScript {

    @Getter @Setter
    private String name;

    private Case aCase;
    public Case getCase() { return aCase; }

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.aCase = cases.createCase(name);

        // also make available to UI
        ec.addResult(this, aCase);
    }

    @javax.inject.Inject
    Cases cases;

}
