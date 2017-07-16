package domainapp.modules.exampledom.spi.security.fixture.tenancy;

import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class ItalyTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Italy";
    public static final String TENANCY_PATH = "/it";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new GlobalTenancy());

        create(TENANCY_NAME, TENANCY_PATH, GlobalTenancy.TENANCY_PATH, executionContext);
    }

}
