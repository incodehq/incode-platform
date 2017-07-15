package domainapp.application.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.application.fixture.teardown.DomainAppTearDown;
import domainapp.modules.exampledom.ext.flywaydb.fixture.scenario.RecreateFlywayDemoObjects;

public class DomainAppDemo extends FixtureScript {

    public DomainAppDemo() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > number (optional input)
    private Integer number;

    /**
     * The number of objects to create, up to 10; optional, defaults to 3.
     */
    public Integer getNumber() {
        return number;
    }

    public DomainAppDemo setNumber(final Integer number) {
        this.number = number;
        return this;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final int number = defaultParam("number", ec, 3);


        // execute
        ec.executeChild(this, new DomainAppTearDown());
        ec.executeChild(this, new RecreateFlywayDemoObjects().setNumber(number));

    }
}
