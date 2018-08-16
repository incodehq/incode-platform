package domainapp.appdefn.fixture.scenarios;

import javax.annotation.Nullable;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.modules.simple.fixture.scenario.SimpleObject_createUpTo10;

import domainapp.appdefn.fixture.teardown.DomainAppTearDown;
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
