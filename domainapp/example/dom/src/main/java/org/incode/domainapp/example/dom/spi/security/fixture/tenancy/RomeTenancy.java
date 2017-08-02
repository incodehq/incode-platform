package org.incode.domainapp.example.dom.spi.security.fixture.tenancy;

public class RomeTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Rome (Italy)";
    public static final String TENANCY_PATH = "/it/rom";

    @Override
    protected void execute(ExecutionContext executionContext) {

        // prereqs
        executionContext.executeChild(this, new ItalyTenancy());

        create(TENANCY_NAME, TENANCY_PATH, ItalyTenancy.TENANCY_PATH, executionContext);
    }

}
