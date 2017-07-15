package domainapp.modules.exampledom.module.alias.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class AliasDemoObjectsTearDownFixture extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"incodeAliasDemo\".\"AliasForDemoObject\"");
        isisJdoSupport.executeUpdate("delete from \"incodeAliasDemo\".\"DemoObject\"");

        isisJdoSupport.executeUpdate("delete from \"incodeAlias\".\"Alias\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
