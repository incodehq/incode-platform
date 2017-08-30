package org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub;

public class ApplicationTenancy_create_SwedenStockholm extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Stockholm (Sweden)";
    public static final String TENANCY_PATH = "/se/stk";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ApplicationTenancy_create_Sweden());

        create(TENANCY_NAME, TENANCY_PATH, ApplicationTenancy_create_Sweden.TENANCY_PATH, executionContext);
    }

}
