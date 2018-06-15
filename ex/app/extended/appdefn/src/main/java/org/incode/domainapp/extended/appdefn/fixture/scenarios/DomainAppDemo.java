package org.incode.domainapp.extended.appdefn.fixture.scenarios;

import javax.annotation.Nullable;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.extended.module.fixtures.shared.simple.fixture.scenario.SimpleObject_createUpTo10;

import org.incode.domainapp.extended.appdefn.fixture.teardown.DomainAppTearDown;
import lombok.Getter;
import lombok.Setter;

public class DomainAppDemo extends FixtureScript {

    public DomainAppDemo() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Nullable
    @Getter @Setter
    private Integer number;

    @Override
    protected void execute(final ExecutionContext ec) {

        // execute
        ec.executeChild(this, new DomainAppTearDown());
        ec.executeChild(this, new SimpleObject_createUpTo10().setNumber(number));

    }
}
