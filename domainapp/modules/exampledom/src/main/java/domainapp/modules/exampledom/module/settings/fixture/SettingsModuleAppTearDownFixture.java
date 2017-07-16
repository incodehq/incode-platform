package domainapp.modules.exampledom.module.settings.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class SettingsModuleAppTearDownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"isissettings\".\"UserSetting\"");
        isisJdoSupport.executeUpdate("delete from \"isissettings\".\"ApplicationSetting\"");
    }


    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
