package org.incode.domainapp.example.dom.spi.security.fixture.tenancy;

import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class FranceTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "France";
    public static final String TENANCY_PATH = "/fr";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new GlobalTenancy());

        create(TENANCY_NAME, TENANCY_PATH, GlobalTenancy.TENANCY_PATH, executionContext);
    }

}
