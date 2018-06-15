package org.incode.domainapp.module.fixtures.per_cpt.examples.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.module.fixtures.shared.demo.fixture.DemoObject_tearDown;

public class DemoObject_withAliases_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // aliases
        isisJdoSupport.executeUpdate("delete from \"exampleDomAlias\".\"AliasForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeAlias\".\"Alias\"");

        // demo objects
        executionContext.executeChild(this, new DemoObject_tearDown());
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
