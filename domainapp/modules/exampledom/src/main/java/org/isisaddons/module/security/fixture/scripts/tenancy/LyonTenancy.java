package org.isisaddons.module.security.fixture.scripts.tenancy;

public class LyonTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Lyon (France)";
    public static final String TENANCY_PATH = "/fr/lyn";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new FranceTenancy());

        create(TENANCY_NAME, TENANCY_PATH, FranceTenancy.TENANCY_PATH, executionContext);
    }

}
