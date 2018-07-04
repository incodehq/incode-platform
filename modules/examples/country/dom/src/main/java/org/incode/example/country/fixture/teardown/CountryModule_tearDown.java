package org.incode.example.country.fixture.teardown;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.example.country.dom.impl.Country;
import org.incode.example.country.dom.impl.State;

public class CountryModule_tearDown extends TeardownFixtureAbstract2 {

    @Override
    protected void execute(ExecutionContext executionContext) {
        deleteFrom(State.class);
        deleteFrom(Country.class);
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
