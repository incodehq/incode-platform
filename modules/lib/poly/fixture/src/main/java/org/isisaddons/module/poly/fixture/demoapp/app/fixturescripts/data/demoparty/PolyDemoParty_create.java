package org.isisaddons.module.poly.fixture.demoapp.app.fixturescripts.data.demoparty;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParties;
import org.isisaddons.module.poly.fixture.demoapp.demomodule.dom.party.PolyDemoParty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PolyDemoParty_create extends FixtureScript {

    @Getter @Setter
    private String name;

    @Getter
    private PolyDemoParty party;

    @Override
    protected void execute(final ExecutionContext ec) {

        final String name = checkParam("name", ec, String.class);

        this.party = parties.createParty(name);

        // also make available to UI
        ec.addResult(this, party);
    }

    @javax.inject.Inject
    PolyDemoParties parties;

}
