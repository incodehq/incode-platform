package org.incode.domainapp.example.dom.dom.settings.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class ApplicationSetting_and_UserSetting_recreate5 extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new ApplicationSetting_and_UserSetting_tearDown());
        executionContext.executeChild(this, new ApplicationSetting_and_UserSetting_create5());

    }

}
