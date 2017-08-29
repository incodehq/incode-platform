package org.incode.domainapp.example.dom.dom.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import org.incode.domainapp.example.dom.demo.fixture.teardown.DemoModuleTearDown;

public class DemoObject_withAliases_tearDown extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // aliases
        isisJdoSupport.executeUpdate("delete from \"exampleDomAlias\".\"AliasForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeAlias\".\"Alias\"");

        // demo objects
        executionContext.executeChild(this, new DemoModuleTearDown());
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
