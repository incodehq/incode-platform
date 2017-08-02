package org.incode.platform.dom.communications.integtests.app.fixtures.scenarios;

import javax.annotation.Nullable;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.incode.domainapp.example.dom.dom.communications.fixture.DemoModuleFixture;

import lombok.Getter;
import lombok.Setter;

public class DemoAppFixture extends FixtureScript {

    public DemoAppFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Nullable
    @Getter @Setter
    private Integer number;

    @Override
    protected void execute(final ExecutionContext ec) {

        // execute
        ec.executeChild(this, new DemoModuleFixture());

    }
}
