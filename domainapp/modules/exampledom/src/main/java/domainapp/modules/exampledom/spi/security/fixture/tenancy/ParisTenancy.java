package domainapp.modules.exampledom.spi.security.fixture.tenancy;

public class ParisTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Paris (France)";
    public static final String TENANCY_PATH = "/fr/par";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new FranceTenancy());

        create(TENANCY_NAME, TENANCY_PATH, FranceTenancy.TENANCY_PATH, executionContext);
    }

}
