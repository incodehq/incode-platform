package org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub;

public class ApplicationTenancy_create_ItalyRome extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Rome (Italy)";
    public static final String TENANCY_PATH = "/it/rom";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ApplicationTenancy_create_Italy());

        create(TENANCY_NAME, TENANCY_PATH, ApplicationTenancy_create_Italy.TENANCY_PATH, executionContext);
    }

}
