package org.isisaddons.module.security.fixture.demoapp.appmodule.fixturescripts.tenancy.sub;

public class ApplicationTenancy_create_FranceParis extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Paris (France)";
    public static final String TENANCY_PATH = "/fr/par";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ApplicationTenancy_create_France());

        create(TENANCY_NAME, TENANCY_PATH, ApplicationTenancy_create_France.TENANCY_PATH, executionContext);
    }

}
