package org.incode.domainapp.example.dom.dom.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class AliasDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"exampleDomAlias\".\"AliasForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"exampleDomAlias\".\"DemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeAlias\".\"Alias\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
