package org.incode.domainapp.example.dom.spi.security.fixture.tenancy.sub;

public class ApplicationTenancy_create_FranceNice extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Nice (France)";
    public static final String TENANCY_PATH = "/fr/nce";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ApplicationTenancy_create_France());

        create(TENANCY_NAME, TENANCY_PATH, ApplicationTenancy_create_France.TENANCY_PATH, executionContext);
    }

}
