package org.isisaddons.module.security.fixture.scripts.tenancy;

public class NiceTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Nice (France)";
    public static final String TENANCY_PATH = "/fr/nce";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new FranceTenancy());

        create(TENANCY_NAME, TENANCY_PATH, FranceTenancy.TENANCY_PATH, executionContext);
    }

}
