package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub;

import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class ApplicationTenancy_create_France extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "France";
    public static final String TENANCY_PATH = "/fr";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new GlobalTenancy());

        create(TENANCY_NAME, TENANCY_PATH, GlobalTenancy.TENANCY_PATH, executionContext);
    }

}
