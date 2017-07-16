package domainapp.modules.exampledom.spi.security.fixture.tenancy;

public class StockholmTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Stockholm (Sweden)";
    public static final String TENANCY_PATH = "/se/stk";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new SwedenTenancy());

        create(TENANCY_NAME, TENANCY_PATH, SwedenTenancy.TENANCY_PATH, executionContext);
    }

}
