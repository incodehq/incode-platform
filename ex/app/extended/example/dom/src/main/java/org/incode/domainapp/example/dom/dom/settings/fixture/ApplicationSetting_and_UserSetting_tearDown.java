package org.incode.domainapp.example.dom.dom.settings.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class ApplicationSetting_and_UserSetting_tearDown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        isisJdoSupport.executeUpdate("delete from \"isissettings\".\"UserSetting\"");
        isisJdoSupport.executeUpdate("delete from \"isissettings\".\"ApplicationSetting\"");
    }


    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
